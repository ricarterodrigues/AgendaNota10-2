package com.ifpb.agendanota10.controle;

import com.ifpb.agendanota10.entidade.Agenda;
import static com.ifpb.agendanota10.visao.TelaInicial.userLogado;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 * Essa classe contém métodos referentes a construção da agenda no arquivo
 * @author Ricarte
 */
public class AgendaDaoBinario implements AgendaDao{
    private File agendas;

    /**
     * Construtor da AgendaDaoBinario
     */
    public AgendaDaoBinario() {
        agendas = new File("Agendas.bin");

        if (!agendas.exists()) {
            try {
                agendas.createNewFile();
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null,
                        "Falha na conexão com o arquivo",
                        "Mensagem de Erro",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Lê uma agenda
     * @param nome O nome da agenda
     * @return Os dados da agenda
     * @throws ClassNotFoundException
     * @throws SQLException
     * @throws IOException 
     */
    @Override
    public Agenda read(String nome) throws ClassNotFoundException, SQLException, IOException {
        List<Agenda> agendasUsers = list(userLogado.getEmail());

        for (Agenda a : agendasUsers) {
            if (a.getNome().equals(nome)) {
                return a;
            }
        }
        return null;    
    }

    /**
     * Lista as agendas
     * @param email O email do usuario
     * @return Lista de agendas do usuário
     * @throws ClassNotFoundException
     * @throws SQLException
     * @throws IOException 
     */
    @Override
    public List<Agenda> list(String email) throws ClassNotFoundException, SQLException, IOException {
        List<Agenda> agendasList = new ArrayList<>();
        List<Agenda> retorno;
        if (agendas.length() > 0) {
            ObjectInputStream input;
            input = new ObjectInputStream(
                    new FileInputStream(agendas));
            retorno = (List<Agenda>) input.readObject();
            for (Agenda a : retorno) {
                if (a.getEmail().equals(userLogado.getEmail())){
                    agendasList.add(a);
                }
            }
            return agendasList;
        } else {
            return new ArrayList<>();
        }
    }

    /**
     * Cria uma agenda
     * @param agenda Os dados da agenda que sera criada
     * @return A confirmacao da criacao da agenda
     * @throws ClassNotFoundException
     * @throws SQLException
     * @throws IOException 
     */
    @Override
    public boolean create(Agenda agenda) throws ClassNotFoundException, SQLException, IOException {
        List<Agenda> agendasUsers = list(userLogado.getEmail());

        for (Agenda a : agendasUsers) {
            if (a.getNome().equals(agenda.getNome()) && agenda.getEmail().equals(a.getEmail())) {
                return false;
            }
        }

        agendasUsers.add(agenda);

        atualizarArquivo(agendasUsers);

        return true;
    }

    /**
     * Deleta uma agenda
     * @param nome O nome da agenda que sera deletada
     * @return A confirmacao de que a agenda foi deletada
     * @throws ClassNotFoundException
     * @throws SQLException
     * @throws IOException 
     */
    @Override
    public boolean delete(String nome) throws ClassNotFoundException, SQLException, IOException {
        List<Agenda> agendasUsers = list(userLogado.getEmail());

        for (Agenda a : agendasUsers) {

            if (a.getNome().equals(nome) && a.getEmail().equals(read(nome).getEmail())) {
                agendasUsers.remove(a);
                atualizarArquivo(agendasUsers);
                return true;
            }
        }
        return false;
    }

    /**
     * Atualiza uma agenda
     * @param agendaNova Os novos dados da agenda
     * @param agendaAntiga Os antigos dados da agenda
     * @return A confirmacao da atualizacao da agenda
     * @throws ClassNotFoundException
     * @throws SQLException
     * @throws IOException 
     */
    @Override
    public boolean update(Agenda agendaNova, Agenda agendaAntiga) throws ClassNotFoundException, SQLException, IOException {
        List<Agenda> agendasUsers = list(userLogado.getEmail());

        for (int i = 0; i < agendasUsers.size(); i++) {
            if (agendasUsers.get(i).getNome().
                    equals(agendaAntiga.getNome()) && agendasUsers.get(i).getEmail().equals(agendaAntiga.getEmail())) {
                agendasUsers.set(i, agendaNova);
                atualizarArquivo(agendasUsers);

                return true;
            }
        }
        return false;
    }
    
    /**
     * Atualiza o arquivo
     * @param agendas Lista de agendas
     * @throws IOException 
     */
    private void atualizarArquivo(List<Agenda> agendas) throws IOException {
        ObjectOutputStream out = new ObjectOutputStream(
                new FileOutputStream(this.agendas));

        out.writeObject(agendas);
        out.close();
    }
}
