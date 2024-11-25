package main;

import java.util.Scanner;


public class Main {
    public static void main(String[] args) {
        GestioneUtenti gestioneUtenti = new GestioneUtenti();
        Scanner scanner = new Scanner(System.in);

       
        gestioneUtenti.registraUtente("admin", "admin123", "amministratore");
        gestioneUtenti.registraUtente("utente1", "password1", "utente");

        
        System.out.println("Inserisci il tuo username:");
        String username = scanner.nextLine();
        System.out.println("Inserisci la tua password:");
        String password = scanner.nextLine();

        gestioneUtenti.autenticaUtente(username, password);
        
     
        gestioneUtenti.listaUtenti();
    }
}
