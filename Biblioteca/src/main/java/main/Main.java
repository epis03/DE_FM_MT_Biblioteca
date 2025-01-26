package main;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.Console;
import java.util.InputMismatchException;
import java.util.Scanner;
import gui.Home;

public class Main {
    private static final Logger logger = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
       
        DatabaseManager.setupDatabase();
        GestioneUtenti gestioneUtenti = new GestioneUtenti();
        GestioneLibri gestioneLibri = new GestioneLibri();
        Scanner scanner = new Scanner(System.in);

        logger.info("Sistema di Gestione Biblioteca Digitale avviato.");
        
        Home start = new Home(); //Avvio GUI
        start.main(args);
        
        System.out.print("Inserisci email: ");
        String email = scanner.nextLine();

        char[] password = null;
        Console console = System.console();
        if (console != null) {
            password = console.readPassword("Inserisci password: ");
        } else {
            
            System.out.print("Inserisci password: ");
            password = scanner.nextLine().toCharArray();
        }

        
        if (gestioneUtenti.autenticaUtente(email, password)) {
            logger.info("Utente autenticato con successo: {}", email);
            boolean continua = true;

            while (continua) {
                
                logger.info("Mostra menu principale.");
                System.out.println("\n1. Aggiungi un libro");
                System.out.println("2. Visualizza libri");
                System.out.println("3. Prenota un libro");
                System.out.println("4. Esci");
                System.out.print("Scegli un'opzione: ");

                try {
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
                            System.out.print("Numero di copie: ");
                            int copie = scanner.nextInt();
                            scanner.nextLine();
                            gestioneLibri.aggiungiLibro(titolo, autore, genere, copie);
                        }
                        case 2 -> {
                            
                            logger.info("Scelta: Visualizza libri.");
                            gestioneLibri.visualizzaLibri();
                        }
                        case 3 -> {
                            logger.info("Scelta: Prenota un libro.");
                            System.out.print("Inserisci l'ID del libro da prenotare: ");
                            int libroId = scanner.nextInt();
                            scanner.nextLine();

                           
                            String libroIdString = String.valueOf(libroId);

                           
                            int result = GestioneLibri.prenotaLibro(libroIdString, email);

                            if (result != -1) {
                                System.out.println("Libro prenotato con successo. ID: " + result);
                                logger.info("Libro con ID {} prenotato dall'utente {}", result, email);
                            } else {
                                System.out.println("Errore durante la prenotazione del libro.");
                                logger.error("Prenotazione fallita per il libro con ID {}", libroId);
                            }
                        }

                        case 4 -> {
                          
                            logger.info("Scelta: Esci dal sistema.");
                            continua = false;
                            logger.info("Chiusura del sistema.");
                        }
                        default -> {
                            
                            logger.warn("Scelta non valida.");
                            System.out.println("Opzione non valida. Riprova.");
                        }
                    }
                } catch (InputMismatchException e) {
                   
                    logger.error("Input non valido.");
                    System.out.println("Errore: Inserisci un numero valido.");
                    scanner.nextLine(); 
                }
            }
        } else {
            
            logger.error("Autenticazione fallita per l'utente: {}", email);
            System.out.println("Email o password non corretti. Riprova.");
        }

      
        if (password != null) {
            java.util.Arrays.fill(password, ' ');
        }
    }
}
