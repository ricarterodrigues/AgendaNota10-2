/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ifpb.agendanota10.controle;

import com.ifpb.agendanota10.entidade.Agenda;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author ricar
 */
public interface AgendaDao {
    
    public Agenda read(String nome) throws ClassNotFoundException, SQLException, IOException;
    public List<Agenda> list(String email) throws ClassNotFoundException, SQLException, IOException;
    public boolean create(Agenda agenda) throws ClassNotFoundException, SQLException, IOException;
    public boolean delete(String nome) throws ClassNotFoundException, SQLException, IOException;
    public boolean update(Agenda agendaNova, Agenda agendaAntiga) throws ClassNotFoundException, SQLException, IOException;
    
}
