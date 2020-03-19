package twofactorauth1;

import java.security.GeneralSecurityException;
import java.util.*; 
import javax.mail.*; 
import javax.mail.internet.*; 
import javax.activation.*; 
import javax.mail.Session; 
import javax.mail.Transport; 
  
  
public class Mail  
{ 
  
   public static void main(String [] args) throws GeneralSecurityException  
   {     
      // email ID of Recipient. 
      String recipient = "avi.khatwani@gmail.com"; 
  
      // email ID of  Sender. 
      String sender = "otpbot545@gmail.com"; 
  
      // using host as localhost 
      String host = "smtp.gmail.com"; 
  
      // Getting system properties 
      Properties properties = System.getProperties(); 
  
      // Setting up mail server 
      properties.put("mail.smtp.host", host);
      properties.put("mail.smtp.port", "465");
      properties.put("mail.smtp.ssl.enable", "true");
      properties.put("mail.smtp.auth", "true");
      
      // creating session object to get properties 
      Session session = Session.getInstance(properties, new javax.mail.Authenticator() {

          protected PasswordAuthentication getPasswordAuthentication() {

              return new PasswordAuthentication("otpbot545@gmail.com","Google123$");

          }

      });
      
      session.setDebug(true);
  
      try 
      { 
         // MimeMessage object. 
         MimeMessage message = new MimeMessage(session); 
  
         // Set From Field: adding senders email to from field. 
         message.setFrom(new InternetAddress(sender)); 
  
         // Set To Field: adding recipient's email to from field. 
         message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient)); 
  
         // Set Subject: subject of the email 
         message.setSubject("This is Suject"); 
  
         String r1 = TimeBasedOneTimePasswordUtil.generateCurrentNumberString("YBM5W7IFAC5BYAWQ");
         // set body of the email. 
         message.setText(r1); 
         System.out.println(r1); 
  
         // Send email. 
         Transport.send(message); 
         System.out.println("Mail successfully sent"); 
      } 
      catch (MessagingException mex)  
      { 
         mex.printStackTrace(); 
      } 
   } 
} 