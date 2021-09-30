package com.training.queue;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SendEmail {

	@Value("${random-access-key}")
	private String pass;
	
    public void mail(int totalProcessed, int passedProducts) {

        // Recipient's email ID needs to be mentioned.
        String to = "kajal.jha@invenio-solutions.com";

        // Sender's email ID needs to be mentioned
        String from = "pallav945@gmail.com";

        // Assuming you are sending email from through gmails smtp
        String host = "smtp.gmail.com";
        
        // Get system properties
        Properties properties = System.getProperties();

        // Setup mail server
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");

        // Get the Session object.// and pass username and password
        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {

            protected PasswordAuthentication getPasswordAuthentication() {

                return new PasswordAuthentication("pallav945@gmail.com", pass);

            }

        });

        // Used to debug SMTP issues
        session.setDebug(true);

        try {
            // Create a default MimeMessage object.
            MimeMessage message = new MimeMessage(session);

            // Set From: header field of the header.
            message.setFrom(new InternetAddress(from));

            // Set To: header field of the header.
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

            // Set Subject: header field
            message.setSubject("UPDATE: Batch processing!!");

            // Now set the actual message
            message.setText("Dear Product Owner/Manager,\nBatch processing completed successfully.\nTotal Products processed: "+totalProcessed+"\nTotal products passed: "+passedProducts+"\nTotal failed: "+(totalProcessed-passedProducts)+"\nLink to modify failed products: +link+\nThis is an auto-generated mail. Please do not reply.\n\nTHANKS,\n-Team 1");

            System.out.println("sending...");
            // Send message
            Transport.send(message);
            System.out.println("Sent message successfully....");
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }

    }

}