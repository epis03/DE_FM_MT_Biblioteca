package main;

public class LibroFactory {

    public static Libro createLibro(int id, String titolo, String autore, String genere, Stato stato, int copie) {
        
    	
        return new Libro(id, titolo, autore, genere, stato, copie);
    }
}
