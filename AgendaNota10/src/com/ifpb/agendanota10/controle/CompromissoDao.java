/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ifpb.agendanota10.controle;

import com.ifpb.agendanota10.entidade.Compromisso;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author ricar
 */
public interface CompromissoDao {

    public Compromisso readCompromissos(LocalDate data, String hora, String agenda) throws IOException, ClassNotFoundException, SQLException;
    public List<Compromisso> listCompromissos(String agenda) throws IOException, ClassNotFoundException, SQLException;
    public List<Compromisso> listCompromissos() throws IOException, ClassNotFoundException, SQLException;
    public List<Compromisso> listCompromissos(LocalDate inicio, LocalDate fim, String agenda) throws ClassNotFoundException, IOException, SQLException;
    public boolean createCompromissos(Compromisso comp) throws IOException, ClassNotFoundException, SQLException;
    public boolean deleteCompromissos(Compromisso comp) throws IOException, ClassNotFoundException, SQLException;
    public boolean updateCompromissos(Compromisso compNovo, Compromisso compAntigo) throws IOException, ClassNotFoundException, SQLException;

}
