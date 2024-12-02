package main;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Scanner;


 public class Main {
    private static final Logger logger = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
        GestioneUtenti gestioneUtenti = new GestioneUtenti();
        Scanner scanner = new Scanner(System.in);

       
        gestioneUtenti.registraUtente("admin", "admin123", "amministratore");
        gestioneUtenti.registraUtente("utente1", "password1", "utente");

       
        logger.info("Inserisci il tuo username:");
        String username = scanner.nextLine();
        logger.info("Inserisci la tua password:");
        String password = scanner.nextLine();

        gestioneUtenti.autenticaUtente(username, password);

        
        gestioneUtenti.listaUtenti();
    }
}