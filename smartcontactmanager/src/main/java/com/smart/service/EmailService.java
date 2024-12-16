package com.smart.service;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.stereotype.Service;



@Service
public class EmailService {
	
	public boolean sendEmail(String subject,String message,String to)
	{
		
		//rest of the coode
		
		boolean f=false;
		String from ="rajsothiya22@gmail.com";
		String host="smtp.gmail.com";
		
		//variable of mail 
		
		Properties properties= System.getProperties();
		System.out.println("Properties "+ properties);
		
		properties.put("mail.smtp.host",host);
		properties.put("mail.smtp.port","465");
		properties.put("mail.smtp.ssl.enable","true");
		properties.put("mail.smtp.auth", "true");
		
		//step 1 get session object
		
		Session session= Session.getInstance(properties,new Authenticator() {
		
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication("rajsothiya22@gmail.com", "nxap yjle bxia xeux");
			}
		
		});
		session.setDebug(true);
		
		//step 2 compose the message textmultimedia
		
		MimeMessage m= new MimeMessage(session);
		
		
		try {
			//from email
			m.setFrom(from);
			
			//add recipient to message
			m.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
			
			//subject to message
			
			m.setSubject(subject);

			//text to message
			//m.setText(message);
			m.setContent(message, "text/html");
			//send
			Transport.send(m);
			
			System.out.println(" sent message -----");
			f=true;
			
		} catch (Exception e) {
			// TODO: handle exception
		    e.printStackTrace();
		}
		
				
		

		
		return f;
	}

}
