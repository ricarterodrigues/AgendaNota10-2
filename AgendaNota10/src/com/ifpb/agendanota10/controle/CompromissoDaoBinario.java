/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author ricar
 */
public class CompromissoDaoBinario implements CompromissoDao {

    File compromissos;

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

    @Override
    public Compromisso readCompromissos(LocalDate data, String hora, String agenda) throws IOException, ClassNotFoundException, SQLException {
        List<Compromisso> compromissos = listCompromissos(agenda);

        for (Compromisso c : compromissos) {
            if (c.getData().equals(data) && c.getHora().equals(hora) && c.getAgenda().equals(agenda) && c.getEmail().equals(userLogado.getEmail())) {
                return c;
            }
        }
        return null;
    }

    @Override
    public List<Compromisso> listCompromissos(String agenda) throws IOException, ClassNotFoundException, SQLException {
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

    @Override
    public List<Compromisso> listCompromissos() throws IOException, ClassNotFoundException, SQLException {
        List<Compromisso> compromissos = new ArrayList<>();
        List<Compromisso> retorno = new ArrayList<>();
        if (this.compromissos.length() > 0) {
            ObjectInputStream input;
            input = new ObjectInputStream(
                    new FileInputStream(this.compromissos));
            retorno = (List<Compromisso>) input.readObject();

            if (retorno != null) {
                for (Compromisso c : retorno) {
                    if (c.getEmail().equals(userLogado.getEmail()));
                    compromissos.add(c);
                }
            }
            return compromissos;
        } else {
            return new ArrayList<>();
        }
    }

    @Override
    public List<Compromisso> listCompromissos(LocalDate inicio, LocalDate fim, String agenda) throws ClassNotFoundException, IOException, SQLException {
        List<Compromisso> compromissosIntervalo = new ArrayList<>();
        List<Compromisso> compromissos = listCompromissos();

        if (agenda == "Todas") {
            for (int i = 0; i < compromissos.size(); i++) {
                if (compromissos.get(i).getData().isAfter(inicio) && compromissos.get(i).getData().isBefore(fim)) {
                    compromissosIntervalo.add(compromissos.get(i));
                }
            }
            return compromissosIntervalo;
        } else {
            for (int i = 0; i < compromissos.size(); i++) {
                if (compromissos.get(i).getData().isAfter(inicio) && compromissos.get(i).getData().isBefore(fim) && compromissos.get(i).getAgenda().equals(agenda)) {
                    compromissosIntervalo.add(compromissos.get(i));
                }
            }
            return compromissosIntervalo;
        }
    }

    @Override
    public boolean createCompromissos(Compromisso comp) throws IOException, ClassNotFoundException, SQLException {
        List<Compromisso> compromissos = listCompromissos();

        for (Compromisso c : compromissos) {
            if (c.getData().equals(comp.getData()) && c.getHora().equals(comp.getHora()) && c.getAgenda().equals(comp.getAgenda()) && c.getEmail().equals(userLogado.getEmail())) {
                return false;
            }
        }

        compromissos.add(comp);

        atualizarArquivo(compromissos);

        return true;
    }

    @Override
    public boolean deleteCompromissos(Compromisso comp) throws IOException, ClassNotFoundException, SQLException {
        List<Compromisso> compromissos = listCompromissos();

        for (Compromisso c : compromissos) {

            if (c.getData().equals(comp.getData()) && c.getHora().equals(comp.getHora()) && c.getAgenda().equals(comp.getAgenda()) && c.getEmail().equals(userLogado.getEmail())) {
                compromissos.remove(c);
                atualizarArquivo(compromissos);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean updateCompromissos(Compromisso compNovo, Compromisso compAntigo) throws IOException, ClassNotFoundException, SQLException {
        List<Compromisso> compromissos = listCompromissos();

        for (int i = 0; i < compromissos.size(); i++) {
            if (compromissos.get(i).getData().equals(compAntigo.getData()) && compromissos.get(i).getHora().equals(compAntigo.getHora()) && compromissos.get(i).getAgenda().equals(compAntigo.getAgenda()) && compromissos.get(i).getEmail().equals(userLogado.getEmail())) {
                compromissos.set(i, compNovo);
                atualizarArquivo(compromissos);

                return true;
            }
        }
        return false;
    }
    
    private void atualizarArquivo(List<Compromisso> compromissos) throws IOException {
        ObjectOutputStream out = new ObjectOutputStream(
                new FileOutputStream(this.compromissos));

        out.writeObject(compromissos);
        out.close();
    }
}
