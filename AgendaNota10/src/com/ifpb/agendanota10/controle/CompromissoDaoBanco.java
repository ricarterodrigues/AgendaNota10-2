package com.ifpb.agendanota10.controle;

import com.ifpb.agendanota10.banco.BancoConexao;
import com.ifpb.agendanota10.entidade.Agenda;
import com.ifpb.agendanota10.entidade.Compromisso;
import static com.ifpb.agendanota10.visao.TelaInicial.userLogado;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 * Essa classe contém métodos referentes a construção do compromisso no banco de dados
 * @author Ricarte
 */
public class CompromissoDaoBanco implements CompromissoDao {

    AgendaDaoBanco agendaDao;
    List<Agenda> agendas ;

    /**
     * Construtor do CompromissoDaoBanco
     */
    public CompromissoDaoBanco(){
        agendaDao = new AgendaDaoBanco();
    }

    /**
     * Lê um compromisso
     * @param data A data do compromisso que será lido
     * @param hora A hora do compromisso que será lido
     * @param agenda A agenda do compromisso que será lido
     * @return Os dados do compromisso
     * @throws IOException
     * @throws ClassNotFoundException
     * @throws SQLException 
     */
    @Override
    public Compromisso read(LocalDate data, LocalTime hora, String agenda) throws IOException, ClassNotFoundException, SQLException {
        Connection con = BancoConexao.getConnection();
        PreparedStatement stmt = con.prepareStatement(
                "SELECT * FROM compromisso WHERE data =? and hora = ? and agenda = ? and email = ?");

        stmt.setDate(1, Date.valueOf(data));
        stmt.setTime(2, java.sql.Time.valueOf(hora));
        stmt.setString(3, agenda);
        stmt.setString(4, userLogado.getEmail());
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            Compromisso comp = new Compromisso();
            comp.setData(rs.getDate("data").toLocalDate());
            comp.setHora(toLocalTime(rs.getTime("hora")));
            comp.setDescricao(rs.getString("descricao"));
            comp.setAgenda(rs.getString("agenda"));
            comp.setLocal(rs.getString("local"));

            con.close();
            return comp;
        } else {
            con.close();
            return null;
        }
    }

    /**
     * 
     * @param time
     * @return 
     */
    public static LocalTime toLocalTime(java.sql.Time time) {
        return time.toLocalTime();
    }
    
    /**
     * Lista compromissos
     * @param agenda A agenda que será listada os compromissos
     * @return Os compromissos da agenda 
     * @throws IOException
     * @throws ClassNotFoundException
     * @throws SQLException 
     */
    @Override
    public List<Compromisso> list(String agenda) throws IOException, ClassNotFoundException, SQLException {
        Connection con = BancoConexao.getConnection();
        PreparedStatement stmt = con.prepareStatement(
                "SELECT * FROM compromisso WHERE email = ? and agenda = ?");

        stmt.setString(1, userLogado.getEmail());
        stmt.setString(2, agenda);
        ResultSet rs = stmt.executeQuery();
        List<Compromisso> compromissos = new ArrayList<>();
        while (rs.next()) {

            Compromisso comp = new Compromisso();

            comp.setData(rs.getDate("data").toLocalDate());
            comp.setHora(toLocalTime(rs.getTime("hora")));
            comp.setDescricao(rs.getString("descricao"));
            comp.setAgenda(rs.getString("agenda"));
            comp.setLocal(rs.getString("local"));

            compromissos.add(comp);

        }
        con.close();
        return compromissos;
    }

    /**
     * Lista compromissos
     * @return Os compromisso do usuário
     * @throws IOException
     * @throws ClassNotFoundException
     * @throws SQLException 
     */
    @Override
    public List<Compromisso> list() throws IOException, ClassNotFoundException, SQLException {
        Connection con = BancoConexao.getConnection();
        PreparedStatement stmt = con.prepareStatement(
                "SELECT * FROM compromisso WHERE email = ?");

        stmt.setString(1, userLogado.getEmail());
        ResultSet rs = stmt.executeQuery();
        List<Compromisso> compromissos = new ArrayList<>();
        while (rs.next()) {

            Compromisso comp = new Compromisso();

            comp.setData(rs.getDate("data").toLocalDate());
            comp.setHora(toLocalTime(rs.getTime("hora")));
            comp.setDescricao(rs.getString("descricao"));
            comp.setAgenda(rs.getString("agenda"));
            comp.setLocal(rs.getString("local"));

            compromissos.add(comp);

        }
        con.close();
        return compromissos;
    }

    /**
     * Lista compromissos
     * @param inicio A data de inicio do intervalo
     * @param fim A data final do intervalo
     * @param agenda A agenda que será listada os compromissos
     * @return A lista de compromissos dentro do intervalo passado
     * @throws ClassNotFoundException
     * @throws IOException
     * @throws SQLException 
     */
    @Override
    public List<Compromisso> list(LocalDate inicio, LocalDate fim, String agenda) throws ClassNotFoundException, IOException, SQLException {
        Connection con = BancoConexao.getConnection();
        PreparedStatement stmt;
        if (agenda.equals("Todas")) {
            stmt = con.prepareStatement(
                    "SELECT * FROM compromisso where email = ? and data between ? and ?");

            stmt.setString(1, userLogado.getEmail());
            stmt.setDate(2, java.sql.Date.valueOf(inicio));
            stmt.setDate(3, java.sql.Date.valueOf(fim));
        } else {
            stmt = con.prepareStatement(
                    "SELECT * FROM compromisso where agenda = ? and email = ? and data between ? and ?");

            stmt.setString(1, agenda);
            stmt.setString(2, userLogado.getEmail());
            stmt.setDate(3, java.sql.Date.valueOf(inicio));
            stmt.setDate(4, java.sql.Date.valueOf(fim));
        }
        ResultSet rs = stmt.executeQuery();
        List<Compromisso> compromissos = new ArrayList<>();

        while (rs.next()) {

            Compromisso compromisso = new Compromisso();

            compromisso.setData(rs.getDate("data").toLocalDate());
            compromisso.setHora(toLocalTime(rs.getTime("hora")));
            compromisso.setLocal(rs.getString("local"));
            compromisso.setDescricao(rs.getString("descricao"));

            compromissos.add(compromisso);
        }

        con.close();
        return compromissos;
    }

    /**
     * Cria compromissos
     * @param comp O dados do compromisso que será criado
     * @return A confirmação da criação do usuário
     * @throws IOException
     * @throws ClassNotFoundException
     * @throws SQLException 
     */
    @Override
    public boolean create(Compromisso comp) throws IOException, ClassNotFoundException, SQLException {
        Connection con = BancoConexao.getConnection();
        PreparedStatement stmt = con.prepareStatement(
                "INSERT INTO compromisso (data,hora,descricao,local,agenda,email) VALUES (?,?,?,?,?,?)");
        stmt.setDate(1, Date.valueOf(comp.getData()));
        stmt.setTime(2, java.sql.Time.valueOf(comp.getHora()));
        stmt.setString(3, comp.getDescricao());
        stmt.setString(4, comp.getLocal());
        stmt.setString(5, comp.getAgenda());
        stmt.setString(6, userLogado.getEmail());

        boolean retorno = stmt.executeUpdate() > 0;
        con.close();
        return retorno;
    }

    /**
     * Deleta um usuário
     * @param comp Os dados do compromisso que será deletado
     * @return A confirmação de que o compromisso fo deletado
     * @throws IOException
     * @throws ClassNotFoundException
     * @throws SQLException 
     */
    @Override
    public boolean delete(Compromisso comp) throws IOException, ClassNotFoundException, SQLException {
        Connection con = BancoConexao.getConnection();

        PreparedStatement stmt = con.prepareStatement(
                "DELETE FROM compromisso WHERE data = ? and hora = ? and agenda = ? and email = ?");

        stmt.setDate(1, Date.valueOf(comp.getData()));
        stmt.setTime(2, java.sql.Time.valueOf(comp.getHora()));
        stmt.setString(3, comp.getAgenda());
        stmt.setString(4, userLogado.getEmail());

        boolean retorno = stmt.executeUpdate() > 0;
        con.close();
        return retorno;
    }

    /**
     * Atualiza um compromisso
     * @param compNovo Os novos dados do compromisso
     * @param compAntigo Os antigos dados do compromisso
     * @return A confirmação da atualização do compromisso
     * @throws IOException
     * @throws ClassNotFoundException
     * @throws SQLException 
     */
    @Override
    public boolean update(Compromisso compNovo, Compromisso compAntigo) throws IOException, ClassNotFoundException, SQLException {
        Connection con = BancoConexao.getConnection();
        PreparedStatement stmt = con.prepareStatement(
                "UPDATE compromisso SET (data,hora,descricao,local,agenda,email)"
                + " = (?,?,?,?,?,?) WHERE data = ? and hora = ? and agenda = ? and email = ?");
        stmt.setDate(1, Date.valueOf(compNovo.getData()));
        stmt.setTime(2, java.sql.Time.valueOf(compNovo.getHora()));
        stmt.setString(3, compNovo.getDescricao());
        stmt.setString(4, compNovo.getLocal());
        stmt.setString(5, compNovo.getAgenda());
        stmt.setString(6, userLogado.getEmail());
        stmt.setDate(7, Date.valueOf(compAntigo.getData()));
        stmt.setTime(8, java.sql.Time.valueOf(compAntigo.getHora()));
        stmt.setString(9, compAntigo.getAgenda());
        stmt.setString(10, userLogado.getEmail());

        boolean retorno = stmt.executeUpdate() > 0;
        con.close();
        return retorno;
    }

}
