/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ifpb.agendanota10.banco;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author ricar
 */
public class BancoConexao {
    
    public static Connection getConnection() throws ClassNotFoundException, SQLException{
        
        Class.forName("org.postgresql.Driver");
        
        String url = "jdbc:postgresql://localhost:5432/AgendaNota10";
        String usuario = "postgres";
        String senha = "4170234";
        
        return DriverManager.getConnection(url, usuario, senha);
        
    }
    
}
