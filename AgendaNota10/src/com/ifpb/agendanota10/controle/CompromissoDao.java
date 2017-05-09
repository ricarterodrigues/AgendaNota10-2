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
import java.time.LocalTime;
import java.util.List;

/**
 *
 * @author ricar
 */
public interface CompromissoDao {

    public Compromisso read(LocalDate data, LocalTime hora, String agenda) throws IOException, ClassNotFoundException, SQLException;
    public List<Compromisso> list(String agenda) throws IOException, ClassNotFoundException, SQLException;
    public List<Compromisso> list() throws IOException, ClassNotFoundException, SQLException;
    public List<Compromisso> list(LocalDate inicio, LocalDate fim, String agenda) throws ClassNotFoundException, IOException, SQLException;
    public boolean create(Compromisso comp) throws IOException, ClassNotFoundException, SQLException;
    public boolean delete(Compromisso comp) throws IOException, ClassNotFoundException, SQLException;
    public boolean update(Compromisso compNovo, Compromisso compAntigo) throws IOException, ClassNotFoundException, SQLException;

}
