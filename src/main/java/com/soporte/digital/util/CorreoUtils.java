package com.soporte.digital.util;

import java.util.*;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.*;

public class CorreoUtils {

    public void EnviarCorreo(String asunto, String cuerpo, String correo) {
        Properties propiedad = new Properties();
        propiedad.setProperty("mail.smtp.host", "smtp.gmail.com");
        propiedad.setProperty("mail.smtp.starttls.enable", "true");
        propiedad.setProperty("mail.smtp.port", "587");
        propiedad.setProperty("mail.smtp.auth", "true");

        Session sesion = Session.getDefaultInstance(propiedad);
        MimeMessage mail = new MimeMessage(sesion);

        try {
            mail.setFrom(new InternetAddress(ConstantesApp.CORREO_ENVIA_REMIT));
            mail.addRecipient(Message.RecipientType.TO, new InternetAddress(correo));
            mail.setSubject(asunto);
            mail.addHeader("Content-Type", "text/html; charset=UTF-8");

            mail.setContent(cuerpo, "text/html");

            Transport transporte = sesion.getTransport("smtp");
            transporte.connect(ConstantesApp.CORREO_ENVIA_REMIT, ConstantesApp.PASSWORD_ENVIA_REMIT);

            transporte.sendMessage(mail, mail.getRecipients(Message.RecipientType.TO));
            transporte.close();

            System.out.println("Correo Enviado...!!");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
