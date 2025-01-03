package main;




import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Scanner;

public class Main {
    
    private static final Logger logger = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
        
        DatabaseManager.setupDatabase();
        GestioneUtenti gestioneUtenti = new GestioneUtenti();
        GestioneLibri gestioneLibri = new GestioneLibri();
        Scanner scanner = new Scanner(System.in);

        logger.info("Sistema di Gestione Biblioteca Digitale avviato.");

        
        gestioneUtenti.registraUtente("admin", "admin123", "amministratore");

       
        logger.info("Richiesta autenticazione.");
        System.out.print("Inserisci username: ");
        String username = scanner.nextLine();
        System.out.print("Inserisci password: ");
        String password = scanner.nextLine();

        if (gestioneUtenti.autenticaUtente(username, password)) {
            logger.info("Utente autenticato con successo: {}", username);
            boolean continua = true;

            while (continua) {
                logger.info("Mostra menu principale.");
                System.out.println("\n1. Aggiungi un libro");
                System.out.println("2. Visualizza libri");
                System.out.println("3. Esci");
                System.out.print("Scegli un'opzione: ");

                int scelta = scanner.nextInt();
                scanner.nextLine(); 

                switch (scelta) {
                    case 1 -> {
                       
                        logger.info("Scelta: Aggiungi un libro.");
                        System.out.print("Titolo del libro: ");
                        String titolo = scanner.nextLine();
                        System.out.print("Autore del libro: ");
                        String autore = scanner.nextLine();
                        System.out.print("Genere del libro: ");
                        String genere = scanner.nextLine();
                        gestioneLibri.aggiungiLibro(titolo, autore, genere);
                    }
                    case 2 -> {
                        
                        logger.info("Scelta: Visualizza libri.");
                        gestioneLibri.visualizzaLibri();
                    }
                    case 3 -> {
                       
                        logger.info("Scelta: Esci dal sistema.");
                        continua = false;
                        logger.info("Chiusura del sistema.");
                    }
                    default -> logger.warn("Scelta non valida: {}", scelta);
                }
            }
        } else {
            logger.error("Autenticazione fallita per l'utente: {}", username);
        }
    }
}