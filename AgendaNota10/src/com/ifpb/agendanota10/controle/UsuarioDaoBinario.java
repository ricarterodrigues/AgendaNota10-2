/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ifpb.agendanota10.controle;

import com.ifpb.agendanota10.entidade.Usuario;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author ricar
 */
public class UsuarioDaoBinario implements UsuarioDao {

    private File users;

    public UsuarioDaoBinario() {
        users = new File("Users.bin");

        if (!users.exists()) {
            try {
                users.createNewFile();
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null,
                        "Falha na conexão com o usuarios",
                        "Mensagem de Erro",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    @Override
    public Usuario read(String email) throws ClassNotFoundException, SQLException, IOException {
        List<Usuario> usuarios = list();

        for (Usuario u : usuarios) {
            if (u.getEmail().equals(email)) {
                return u;
            }
        }
        return null;
    }

    @Override
    public List<Usuario> list() throws ClassNotFoundException, SQLException, IOException {
        if (users.length() > 0) {
            ObjectInputStream input = new ObjectInputStream(
                    new FileInputStream(users));

            return (List<Usuario>) input.readObject();

        } else {
            return new ArrayList<>();
        }
    }

    @Override
    public boolean create(Usuario usuario) throws ClassNotFoundException, SQLException, IOException {
        List<Usuario> usuarios = list();

        for (Usuario u : usuarios) {
            if (u.getEmail().equals(usuario.getEmail())) {
                return false;
            }
        }

        usuarios.add(usuario);

        atualizarArquivo(usuarios);

        return true;
    }

    @Override
    public boolean delete(String email) throws ClassNotFoundException, SQLException, IOException {
        List<Usuario> usuarios = list();

        for (Usuario u : usuarios) {

            if (u.getEmail().equals(email)) {
                usuarios.remove(u);
                atualizarArquivo(usuarios);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean update(Usuario usuario) throws ClassNotFoundException, SQLException, IOException {
        List<Usuario> usuarios = list();

        if (usuario.getNascimento().isAfter(LocalDate.now())) {
            return false;
        }

        for (int i = 0; i < usuarios.size(); i++) {

            if (usuarios.get(i).getEmail().
                    equals(usuario.getEmail())) {
                usuarios.set(i, usuario);

                atualizarArquivo(usuarios);

                return true;
            }

        }
        return false;
    }
    
    private void atualizarArquivo(List<Usuario> usuarios) throws IOException {
        ObjectOutputStream out = new ObjectOutputStream(
                new FileOutputStream(users));

        out.writeObject(usuarios);
        out.close();
    }

}
