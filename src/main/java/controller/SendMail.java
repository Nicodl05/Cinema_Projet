package controller;


import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;

public class SendMail {
   public SendMail() {
   }
   public void Emailsent(){

        String from="nico.dreylaq@gmail.com", pwd="cecile05!", to="nico.laqfus@gmail.com", sub="test", msg="Hello world";
       Properties p = new Properties();
       p.put("mail.smtp.host", "smtp.gmail.com");
       p.put("mail.smtp.socketFactory.port", "465");
       p.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
       p.put("mail.smtp.auth", "true");
       p.put("mail.smtp.port", "465");
       //Session
       Session s = Session.getDefaultInstance(p,
               new javax.mail.Authenticator() {
                   protected PasswordAuthentication getPasswordAuthentication() {
                       return new PasswordAuthentication(from, pwd);
                   }
               });
       //composer le message
       try {
           MimeMessage m = new MimeMessage(s);
           m.addRecipient(Message.RecipientType.TO,new InternetAddress(to));
           m.setSubject(sub);
           m.setText(msg);
           //envoyer le message
           Transport.send(m);
           System.out.println("Message envoyé avec succès");
       } catch (MessagingException e) {
           e.printStackTrace();
       }
}


    // Used to debug SMTP issues






}
