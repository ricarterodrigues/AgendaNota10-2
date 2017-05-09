/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ifpb.agendanota10.controle;

import com.ifpb.agendanota10.banco.BancoConexao;
import com.ifpb.agendanota10.entidade.Agenda;
import static com.ifpb.agendanota10.visao.TelaInicial.userLogado;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ricar
 */
public class AgendaDaoBanco implements AgendaDao{

    @Override
    public Agenda read(String nome) throws ClassNotFoundException, SQLException, IOException {
        Connection con = BancoConexao.getConnection();
        PreparedStatement stmt = con.prepareStatement(
                "SELECT * FROM agenda WHERE nome = ? and email = ?");

        stmt.setString(1, nome);
        stmt.setString(2, userLogado.getEmail());
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            Agenda agenda = new Agenda();

            agenda.setNome(rs.getString("nome"));
            agenda.setEmail(rs.getString("email"));
            con.close();
            return agenda;
        } else {
            con.close();
            return null;
        }
    }

    @Override
    public List<Agenda> list(String email) throws ClassNotFoundException, SQLException, IOException {
        Connection con = BancoConexao.getConnection();
        PreparedStatement stmt = con.prepareStatement(
                "SELECT * FROM agenda where email = ?");

        stmt.setString(1, email);

        ResultSet rs = stmt.executeQuery();
        List<Agenda> agendas = new ArrayList<>();

        while (rs.next()) {

            Agenda agenda = new Agenda();

            agenda.setNome(rs.getString("nome"));
            agenda.setEmail(rs.getString("email"));

            agendas.add(agenda);
        }

        con.close();
        return agendas;
    }

    @Override
    public boolean create(Agenda agenda) throws ClassNotFoundException, SQLException, IOException {
        Connection con = BancoConexao.getConnection();
        PreparedStatement stmt = con.prepareStatement(
                "INSERT INTO agenda (nome, email) VALUES (?,?)");

        stmt.setString(1, agenda.getNome());
        stmt.setString(2, agenda.getEmail());

        boolean retorno = stmt.executeUpdate() > 0;
        con.close();
        return retorno;
    }

    @Override
    public boolean delete(String nome) throws ClassNotFoundException, SQLException, IOException {
        Connection con = BancoConexao.getConnection();

        PreparedStatement stmt = con.prepareStatement(
                "DELETE FROM agenda WHERE nome = ? and email = ?");
        stmt.setString(1, nome);
        stmt.setString(2, userLogado.getEmail());

        boolean retorno = stmt.executeUpdate() > 0;
        con.close();
        return retorno;
    }

    @Override
    public boolean update(Agenda newAgenda, Agenda oldAgenda) throws ClassNotFoundException, SQLException, IOException {
        Connection con = BancoConexao.getConnection();
        PreparedStatement stmt = con.prepareStatement(
                "UPDATE agenda SET (nome, email)"
                + " = (?,?) WHERE nome = ? and email = ?");
        stmt.setString(1, newAgenda.getNome());
        stmt.setString(2, newAgenda.getEmail());
        stmt.setString(3, oldAgenda.getNome());
        stmt.setString(4, userLogado.getEmail());

        boolean retorno = stmt.executeUpdate() > 0;
        con.close();
        return retorno;
    }
    
}
