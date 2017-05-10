/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ifpb.agendanota10.controle;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

/**
 *
 * @author ThigoYure
 */
public class EnviarEmail {

    public void sendEmail() throws EmailException {

        SimpleEmail email = new SimpleEmail();
        //Utilize o hostname do seu provedor de email
        System.out.println("alterando hostname...");
        email.setHostName("smtp.gmail.com");
        //Quando a porta utilizada não é a padrão (gmail = 465)
        email.setSmtpPort(465);
        //Adicione os destinatários
        email.addTo("thigoyure@gmail.com", "Thiago");
        //Configure o seu email do qual enviará
        email.setFrom("ricarte.cz@gmail.com", "Agenda Nota 10!");
        //Adicione um assunto
        email.setSubject("Test message");
        //Adicione a mensagem do email
        email.setMsg("This is a simple test of commons-email");
        //Para autenticar no servidor é necessário chamar os dois métodos abaixo
        System.out.println("autenticando...");
        email.setAuthentication("ricaret.cz@gmail.com", "sucodelaranja10*");
        System.out.println("enviando...");
        email.setSSL(true);
        email.send();
        email.setDebug(true);
        System.out.println("Email enviado!");
    }

}
