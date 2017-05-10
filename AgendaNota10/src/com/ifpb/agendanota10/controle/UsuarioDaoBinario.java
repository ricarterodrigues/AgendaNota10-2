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
 * Essa classe contém métodos referentes a construção do usuário no arquivo
 * @author Ricarte
 */
public class UsuarioDaoBinario implements UsuarioDao {

    private File users;

    /**
     * Construtor do UsuarioDaoBinario
     */
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

    /**
     * Lê um usuário
     * @param email O email do login do usuário
     * @return Os dados do usuário selecionado
     * @throws ClassNotFoundException
     * @throws SQLException
     * @throws IOException 
     */
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

    /**
     * Lista os usuários
     * @return Os usuários
     * @throws ClassNotFoundException
     * @throws SQLException
     * @throws IOException 
     */
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

    /**
     * Cria um usuário
     * @param usuario Os dados do usuário que será criado
     * @return A confirmação da criação do usuário
     * @throws ClassNotFoundException
     * @throws SQLException
     * @throws IOException 
     */
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

    /**
     * Deleta um usuário
     * @param email O email do login do usuário
     * @return A confirmação de que o usuário foi deletado
     * @throws ClassNotFoundException
     * @throws SQLException
     * @throws IOException 
     */
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
    
    /**
     * Atualiza um usuário
     * @param usuario O email do login do usuário que será atualizad
     * @return A confirmação da atualização do usuário
     * @throws ClassNotFoundException
     * @throws SQLException
     * @throws IOException 
     */
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
