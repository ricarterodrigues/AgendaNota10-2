/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ifpb.agendanota10.controle;

import com.ifpb.agendanota10.banco.BancoConexao;
import com.ifpb.agendanota10.entidade.Usuario;
import com.ifpb.agendanota10.excecoes.InvalidEmailException;
import com.ifpb.agendanota10.excecoes.InvalidPasswordException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ricar
 */
public class UsuarioDaoBanco implements UsuarioDao{

    @Override
    public Usuario read(String email) throws ClassNotFoundException, SQLException, IOException {
        Connection con = BancoConexao.getConnection();
        PreparedStatement stmt = con.prepareStatement(
                "SELECT * FROM usuario WHERE email = ?");

        stmt.setString(1, email);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            Usuario usuario = new Usuario();

            try {
                usuario.setEmail(rs.getString("email"));
            } catch (InvalidEmailException ex) {
                System.out.println(ex.getMessage());
            }
            usuario.setNome(rs.getString("nome"));
            usuario.setNascimento(rs.getDate("nascimento").toLocalDate());
            usuario.setSexo(rs.getString("sexo").charAt(0));
            try {
                usuario.setSenha(rs.getString("senha"));
            } catch (InvalidPasswordException ex) {
                System.out.println(ex.getMessage());
            }

            con.close();
            return usuario;
        } else {
            con.close();
            return null;
        }    
    }

    @Override
    public List<Usuario> list() throws ClassNotFoundException, SQLException, IOException {
        Connection con = BancoConexao.getConnection();
        PreparedStatement stmt = con.prepareStatement(
                "SELECT * FROM usuario");

        ResultSet rs = stmt.executeQuery();
        List<Usuario> usuarios = new ArrayList<>();

        while (rs.next()) {

            Usuario usuario = new Usuario();
            try {
                usuario.setEmail(rs.getString("email"));
            } catch (InvalidEmailException ex) {
                System.out.println(ex.getMessage());
            }
            usuario.setNome(rs.getString("nome"));
            usuario.setNascimento(rs.getDate("nascimento").toLocalDate());
            usuario.setSexo(rs.getString("sexo").charAt(0));
            try {
                usuario.setSenha(rs.getString("senha"));
            } catch (InvalidPasswordException ex) {
                System.out.println(ex.getMessage());
            }
            usuarios.add(usuario);
        }

        con.close();
        return usuarios;
    }

    @Override
    public boolean create(Usuario usuario) throws ClassNotFoundException, SQLException, IOException {
        Connection con = BancoConexao.getConnection();
        PreparedStatement stmt = con.prepareStatement(
            "INSERT INTO usuario (email, nome, sexo,"
            + " nascimento, senha) VALUES (?,?,?,?,?)");
        
        stmt.setString(1, usuario.getEmail());
        stmt.setString(2, usuario.getNome());
        stmt.setString(3, ""+usuario.getSexo());
        stmt.setDate(4, Date.valueOf(usuario.getNascimento()));
        stmt.setString(5, usuario.getSenha());
        
        boolean retorna = stmt.executeUpdate()>0;
        con.close();
        return retorna;
    }

    @Override
    public boolean delete(String email) throws ClassNotFoundException, SQLException, IOException {
        Connection con = BancoConexao.getConnection();
        
        PreparedStatement stmt = con.prepareStatement(
            "DELETE FROM usuario WHERE email = ?");
        stmt.setString(1, email);
        
        boolean retorno = stmt.executeUpdate() > 0;
        con.close();
        return retorno; 
    }

    @Override
    public boolean update(Usuario usuario) throws ClassNotFoundException, SQLException, IOException {
        Connection con = BancoConexao.getConnection();
        PreparedStatement stmt = con.prepareStatement(
            "UPDATE usuario SET (nome, sexo, nascimento, senha)"
                    + " = (?,?,?,?) WHERE email = ?");
        
        stmt.setString(1, usuario.getNome());
        stmt.setString(2, ""+usuario.getSexo());
        stmt.setDate(3, Date.valueOf(usuario.getNascimento()));
        stmt.setString(5, usuario.getSenha());
        stmt.setString(6, usuario.getEmail());
        
        boolean retorno = stmt.executeUpdate()>0;
        con.close();
        return retorno;
    }
    
}
