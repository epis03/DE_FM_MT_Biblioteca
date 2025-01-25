package main;

import java.time.LocalDate;

public class LibroFactory {

    public static Libro createLibro(int id, String titolo, String autore, String genere, Stato stato, int copie,LocalDate inizioPrestito, LocalDate finePrestito) {
        
    	
        return new Libro(id, titolo, autore, genere, stato, copie, inizioPrestito, finePrestito);
    }
}
