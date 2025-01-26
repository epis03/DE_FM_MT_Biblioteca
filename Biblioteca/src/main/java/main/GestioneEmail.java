package main;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Properties;

import org.apache.commons.text.RandomStringGenerator;
import jakarta.mail.*;
import jakarta.mail.internet.*;

public  class GestioneEmail {

	private static String codice;
	 private static final Logger logger = LogManager.getLogger(GestioneEmail.class);
	 private static final String AppPassword = "paku nwnb bjwg tumf";
	 private static final String emailMittente = "noreply.infoBiblioteca";
	
	 
	 public static String verificaEmail(String destinatario) {
		RandomStringGenerator codiceCasuale = new RandomStringGenerator.Builder()
                .withinRange('0', 'z') // 
                .filteredBy(Character::isLetterOrDigit) 
                .build();
		codice = codiceCasuale.generate(6);
		
		// Parametri email
        String host = "smtp.gmail.com"; // SMTP di Gmail
        final String mittente = emailMittente;
        final String password = AppPassword; 
        
        // Configurazione delle proprietà per il server SMTP
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", "587");

        // Creazione di una sessione email
        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(mittente, password);
            }
        });

        try {
            // messaggio
            Message messaggio = new MimeMessage(session);
            messaggio.setFrom(new InternetAddress(mittente)); // Mittente
            messaggio.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinatario)); // Destinatario
            messaggio.setContent(
            	    "<div style='text-align: center; font-family: Arial, sans-serif; color: #333;'>" +
            	    "    <h3>Gentile Utente,</h3>" +
            	    "    <p>Per completare l'operazione di registrazione, utilizza il seguente codice:</p>" +
            	    "    <p style='margin: 20px 0; font-size: 24px; font-weight: bold; color: #000;'>" + codice + "</p>" +
            	    "    <p>Questa email è stata generata automaticamente dal sistema, si prega di non rispondere!</p>" +
            	    "    <p>Cordiali saluti,<br>Team Biblioteca</p>" +
            	    "</div>",
            	    "text/html; charset=UTF-8"
            	);
           messaggio.setReplyTo(InternetAddress.parse("no-reply@emailfittizia.com")); // Impedisce l'invio di risposte
            // Invio 
            Transport.send(messaggio);
            logger.info("Email inviata con successo a " + destinatario);

        } catch (MessagingException e) {
        	logger.error("Invio email a " + destinatario + "fallito");
            e.printStackTrace();
        }
       
		return codice;
	}
	 
	 public static String identificaUtente(String destinatario){
		 RandomStringGenerator codiceCasuale = new RandomStringGenerator.Builder()
	                .withinRange('0', 'z') // 
	                .filteredBy(Character::isLetterOrDigit) 
	                .build();
			codice = codiceCasuale.generate(6);
			
			// Parametri email
	        String host = "smtp.gmail.com"; // SMTP di Gmail
	        final String mittente = emailMittente;
	        final String password = AppPassword; 
	        
	        // Configurazione delle proprietà per il server SMTP
	        Properties props = new Properties();
	        props.put("mail.smtp.auth", "true");
	        props.put("mail.smtp.starttls.enable", "true");
	        props.put("mail.smtp.host", host);
	        props.put("mail.smtp.port", "587");

	        // Creazione di una sessione email
	        Session session = Session.getInstance(props, new Authenticator() {
	            protected PasswordAuthentication getPasswordAuthentication() {
	                return new PasswordAuthentication(mittente, password);
	            }
	        });

	        try {
	            // messaggio
	            Message messaggio = new MimeMessage(session);
	            messaggio.setFrom(new InternetAddress(mittente)); // Mittente
	            messaggio.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinatario)); // Destinatario
	            messaggio.setContent(
	            	    "<div style='text-align: center; font-family: Arial, sans-serif; color: #333;'>" +
	            	    "    <h3>Gentile Utente,</h3>" +
	            	    "    <p>Inserisci il seguente codice per confermare la tua identità:</p>" +
	            	    "    <p style='margin: 20px 0; font-size: 24px; font-weight: bold; color: #000;'>" + codice + "</p>" +
	            	    "    <p>Questa email è stata generata automaticamente dal sistema, si prega di non rispondere!</p>" +
	            	    "    <p>Cordiali saluti,<br>Team Biblioteca</p>" +
	            	    "</div>",
	            	    "text/html; charset=UTF-8"
	            	);
	           messaggio.setReplyTo(InternetAddress.parse("no-reply@emailfittizia.com")); // Impedisce l'invio di risposte
	            // Invio 
	            Transport.send(messaggio);
	            logger.info("Email inviata con successo a " + destinatario);

	        } catch (MessagingException e) {
	        	logger.error("Invio email a " + destinatario + "fallito");
	            e.printStackTrace();
	        }
	       
			return codice;
		 
	 }
	 public static void SegnalaPrestitoScaduto(String destinatario, Libro libro){
		// Parametri email
	        String host = "smtp.gmail.com"; // SMTP di Gmail
	        final String mittente = emailMittente;
	        final String password = AppPassword; 
	        
	        // Configurazione delle proprietà per il server SMTP
	        Properties props = new Properties();
	        props.put("mail.smtp.auth", "true");
	        props.put("mail.smtp.starttls.enable", "true");
	        props.put("mail.smtp.host", host);
	        props.put("mail.smtp.port", "587");

	        // Creazione di una sessione email
	        Session session = Session.getInstance(props, new Authenticator() {
	            protected PasswordAuthentication getPasswordAuthentication() {
	                return new PasswordAuthentication(mittente, password);
	            }
	        });

	        try {
	            // messaggio
	            Message messaggio = new MimeMessage(session);
	            messaggio.setFrom(new InternetAddress(mittente)); // Mittente
	            messaggio.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinatario)); // Destinatario
	            messaggio.setContent(
	            	    "<div style='text-align: center; font-family: Arial, sans-serif; color: #333;'>" +
	            	    "    <h3>Gentile Utente,</h3>" +
	            	    "    <p>la informiamo che il suo prestito: </p>" +
	            	    "    <p>titolo: " + libro.getTitolo() + "</p>" +
	            	    "    <p>autore: " + libro.getAutore() + "</p>" +
	            	    "    <p>è scaduto in data: </p>" +
	            	    "    <p style='margin: 20px 0; font-size: 24px; font-weight: bold; color: #000;'>" + libro.getFinePrestito()  + "</p>" +
	            	    "    <p>Questa email è stata generata automaticamente dal sistema, si prega di non rispondere!</p>" +
	            	    "    <p>Cordiali saluti,<br>Team Biblioteca</p>" +
	            	    "</div>",
	            	    "text/html; charset=UTF-8"
	            	);
	           messaggio.setReplyTo(InternetAddress.parse("no-reply@emailfittizia.com")); // Impedisce l'invio di risposte
	            // Invio 
	            Transport.send(messaggio);
	            logger.info("Email inviata con successo a " + destinatario);

	        } catch (MessagingException e) {
	        	logger.error("Invio email a " + destinatario + "fallito");
	            e.printStackTrace();
	        }
	       
		}
	 
	 public static void SegnalaRitiroScaduto(String destinatario, Libro libro){
		// Parametri email
	        String host = "smtp.gmail.com"; // SMTP di Gmail
	        final String mittente = emailMittente;
	        final String password = AppPassword; 
	        
	        // Configurazione delle proprietà per il server SMTP
	        Properties props = new Properties();
	        props.put("mail.smtp.auth", "true");
	        props.put("mail.smtp.starttls.enable", "true");
	        props.put("mail.smtp.host", host);
	        props.put("mail.smtp.port", "587");

	        // Creazione di una sessione email
	        Session session = Session.getInstance(props, new Authenticator() {
	            protected PasswordAuthentication getPasswordAuthentication() {
	                return new PasswordAuthentication(mittente, password);
	            }
	        });

	        try {
	            // messaggio
	            Message messaggio = new MimeMessage(session);
	            messaggio.setFrom(new InternetAddress(mittente)); // Mittente
	            messaggio.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinatario)); // Destinatario
	            messaggio.setContent(
	            	    "<div style='text-align: center; font-family: Arial, sans-serif; color: #333;'>" +
	            	    "    <h3>Gentile Utente,</h3>" +
	            	    "    <p>la informiamo che la sua prenotazione: </p>" +
	            	    "    <p>titolo: " + libro.getTitolo() + "</p>" +
	            	    "    <p>autore: " + libro.getAutore() + "</p>" +
	            	    "    <p>è scaduto in data: </p>" +
	            	    "    <p style='margin: 20px 0; font-size: 24px; font-weight: bold; color: #000;'>" + libro.getFinePrestito()  + "</p>" +
	            	    "    <p>Questa email è stata generata automaticamente dal sistema, si prega di non rispondere!</p>" +
	            	    "    <p>Cordiali saluti,<br>Team Biblioteca</p>" +
	            	    "</div>",
	            	    "text/html; charset=UTF-8"
	            	);
	           messaggio.setReplyTo(InternetAddress.parse("no-reply@emailfittizia.com")); // Impedisce l'invio di risposte
	            // Invio 
	            Transport.send(messaggio);
	            logger.info("Email inviata con successo a " + destinatario);

	        } catch (MessagingException e) {
	        	logger.error("Invio email a " + destinatario + "fallito");
	            e.printStackTrace();
	        }
	       
		}
	 
	 public static void prenotazione(String destinatario, Libro libro){
			// Parametri email
		        String host = "smtp.gmail.com"; // SMTP di Gmail
		        final String mittente = emailMittente;
		        final String password = AppPassword; 
		        
		        // Configurazione delle proprietà per il server SMTP
		        Properties props = new Properties();
		        props.put("mail.smtp.auth", "true");
		        props.put("mail.smtp.starttls.enable", "true");
		        props.put("mail.smtp.host", host);
		        props.put("mail.smtp.port", "587");

		        // Creazione di una sessione email
		        Session session = Session.getInstance(props, new Authenticator() {
		            protected PasswordAuthentication getPasswordAuthentication() {
		                return new PasswordAuthentication(mittente, password);
		            }
		        });

		        try {
		            // messaggio
		            Message messaggio = new MimeMessage(session);
		            messaggio.setFrom(new InternetAddress(mittente)); // Mittente
		            messaggio.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinatario)); // Destinatario
		            messaggio.setContent(
		            	    "<div style='text-align: center; font-family: Arial, sans-serif; color: #333;'>" +
		            	    "    <h3>Gentile Utente,</h3>" +
		            	    "    <p> La infromiamo che la sua prenotazione: </p>" +
		            	    "    <p>titolo: " + libro.getTitolo() + "</p>" +
		            	    "    <p>autore: " + libro.getAutore() + "</p>" +
		            	    "    <p>è avvenuta con successo! Ha tempo fino a: </p>" +
		            	    "    <p style='margin: 20px 0; font-size: 24px; font-weight: bold; color: #000;'>" + libro.getFinePrestito()  + "</p>" +
		            	    "    <p>per ritirare il libro./p>" +
		            	    "    <p>Questa email è stata generata automaticamente dal sistema, si prega di non rispondere!</p>" +
		            	    "    <p>Cordiali saluti,<br>Team Biblioteca</p>" +
		            	    "</div>",
		            	    "text/html; charset=UTF-8"
		            	);
		           messaggio.setReplyTo(InternetAddress.parse("no-reply@emailfittizia.com")); // Impedisce l'invio di risposte
		            // Invio 
		            Transport.send(messaggio);
		            logger.info("Email inviata con successo a " + destinatario);

		        } catch (MessagingException e) {
		        	logger.error("Invio email a " + destinatario + "fallito");
		            e.printStackTrace();
		        }
		       
			}
	 }

