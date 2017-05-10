package com.ifpb.agendanota10.controle;

import com.ifpb.agendanota10.entidade.Compromisso;
import static com.ifpb.agendanota10.visao.TelaInicial.userLogado;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 * Essa classe contém métodos referentes a construção do compromisso no arquivo
 * @author Ricarte
 */
public class CompromissoDaoBinario implements CompromissoDao {

    File compromissos;

    /**
     * Construtor do CompromissoDaoBinario
     */
    public CompromissoDaoBinario() {
        compromissos = new File("Compromissos.bin");

        if (!compromissos.exists()) {
            try {
                compromissos.createNewFile();
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null,
                        "Falha na conexão com o arquivo",
                        "Mensagem de Erro",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Le um compromisso
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
        List<Compromisso> compromissos = list(agenda);

        for (Compromisso c : compromissos) {
            if (c.getData().equals(data) && c.getHora().equals(hora) && c.getAgenda().equals(agenda) && c.getEmail().equals(userLogado.getEmail())) {
                return c;
            }
        }
        return null;
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
        List<Compromisso> compromissos = new ArrayList<>();
        List<Compromisso> retorno = new ArrayList<>();
        if (this.compromissos.length() > 0) {
            ObjectInputStream input;
            input = new ObjectInputStream(
                    new FileInputStream(this.compromissos));
            retorno = (List<Compromisso>) input.readObject();

            if (retorno != null) {
                for (Compromisso c : retorno) {
                    if (c.getEmail().equals(userLogado.getEmail()) && c.getAgenda().equals(agenda));
                    compromissos.add(c);
                }
            }
            return compromissos;
        } else {
            return new ArrayList<>();
        }
    }

    /**
     * Lista compromissos
     * @return Os compromissos do usuário
     * @throws IOException
     * @throws ClassNotFoundException
     * @throws SQLException 
     */
    @Override
    public List<Compromisso> list() throws IOException, ClassNotFoundException, SQLException {
        List<Compromisso> compromissos = new ArrayList<>();
        List<Compromisso> retorno;
        if (this.compromissos.length() > 0) {
            ObjectInputStream input;
            input = new ObjectInputStream(
                    new FileInputStream(this.compromissos));
            retorno = (List<Compromisso>) input.readObject();

            if (retorno != null) {
                for (Compromisso c : retorno) {
                    if (c.getEmail().equals(userLogado.getEmail())){
                        compromissos.add(c);
                    }
                }
                return compromissos;
            }
            
        } else {
            return new ArrayList<>();
        }
        return null;
    }

    /**
     * Lista compromissos
     * @param inicio A data de início do intervalo
     * @param fim A data final do intervalo
     * @param agenda A agenda que será listada os compromissos
     * @return A lista de compromissos dentro do intervalo passado
     * @throws ClassNotFoundException
     * @throws IOException
     * @throws SQLException 
     */
    @Override
    public List<Compromisso> list(LocalDate inicio, LocalDate fim, String agenda) throws ClassNotFoundException, IOException, SQLException {
        List<Compromisso> compromissosIntervalo = new ArrayList<>();
        List<Compromisso> compromissos = list();

        if (agenda == "Todas") {
            for (int i = 0; i < compromissos.size(); i++) {
                if (compromissos.get(i).getEmail().equals(userLogado.getEmail())&&((compromissos.get(i).getData().isAfter(inicio)||compromissos.get(i).getData().equals(inicio))&&(compromissos.get(i).getData().isBefore(fim)||compromissos.get(i).getData().equals(fim)))) {
                    compromissosIntervalo.add(compromissos.get(i));
                }
            }
            return compromissosIntervalo;
        } else {
            for (int i = 0; i < compromissos.size(); i++) {
                if ((compromissos.get(i).getData().isAfter(inicio) || compromissos.get(i).getData().equals(inicio)) && (compromissos.get(i).getData().isBefore(fim) || compromissos.get(i).getData().equals(fim))&& compromissos.get(i).getAgenda().equals(agenda)) {
                    compromissosIntervalo.add(compromissos.get(i));
                }
            }
            return compromissosIntervalo;
        }
    }

    /**
     * Cria um compromisso
     * @param comp Os dados do compromisso que será criado
     * @return A confirmação da criação do compromisso
     * @throws IOException
     * @throws ClassNotFoundException
     * @throws SQLException 
     */
    @Override
    public boolean create(Compromisso comp) throws IOException, ClassNotFoundException, SQLException {
        List<Compromisso> compromissos = list();

        for (Compromisso c : compromissos) {
            if (c.getData().equals(comp.getData()) && c.getHora().equals(comp.getHora()) && c.getAgenda().equals(comp.getAgenda()) && c.getEmail().equals(userLogado.getEmail())) {
                return false;
            }
        }

        compromissos.add(comp);

        atualizarArquivo(compromissos);

        return true;
    }

    /**
     * Deleta um compromisso
     * @param comp Os dados com compromisso que será deletado
     * @return A confirmação de que o compromisso foi deletado
     * @throws IOException
     * @throws ClassNotFoundException
     * @throws SQLException 
     */
    @Override
    public boolean delete(Compromisso comp) throws IOException, ClassNotFoundException, SQLException {
        List<Compromisso> compromissos = list();

        for (Compromisso c : compromissos) {

            if (c.getData().equals(comp.getData()) && c.getHora().equals(comp.getHora()) && c.getAgenda().equals(comp.getAgenda()) && c.getEmail().equals(userLogado.getEmail())) {
                compromissos.remove(c);
                atualizarArquivo(compromissos);
                return true;
            }
        }
        return false;
    }

    /**
     * Atualiza um usuário
     * @param compNovo Os novos dados do compromisso
     * @param compAntigo Os antigos dados do compromisso
     * @return A confirmação da atualização do compromisso
     * @throws IOException
     * @throws ClassNotFoundException
     * @throws SQLException 
     */
    @Override
    public boolean update(Compromisso compNovo, Compromisso compAntigo) throws IOException, ClassNotFoundException, SQLException {
        List<Compromisso> compromissos = list();

        for (int i = 0; i < compromissos.size(); i++) {
            if (compromissos.get(i).getData().equals(compAntigo.getData()) && compromissos.get(i).getHora().equals(compAntigo.getHora()) && compromissos.get(i).getAgenda().equals(compAntigo.getAgenda()) && compromissos.get(i).getEmail().equals(userLogado.getEmail())) {
                compromissos.set(i, compNovo);
                atualizarArquivo(compromissos);

                return true;
            }
        }
        return false;
    }
    
    /**
     * Atualiza o arquivo
     * @param compromissos Lista dos compromissos
     * @throws IOException 
     */
    private void atualizarArquivo(List<Compromisso> compromissos) throws IOException {
        ObjectOutputStream out = new ObjectOutputStream(
                new FileOutputStream(this.compromissos));

        out.writeObject(compromissos);
        out.close();
    }
}
