/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ifpb.agendanota10.excecoes;

/**
 *
 * @author ricar
 */
public class InvalidDateException extends RuntimeException{
    
    public InvalidDateException(String msg) {
        super(msg);
    }
    
}
