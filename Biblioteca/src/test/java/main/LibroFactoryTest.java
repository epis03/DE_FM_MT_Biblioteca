package main;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class LibroFactoryTest {

    @Test
    void testCreateLibro() {
    	
        int id = 1;
        String titolo = "titoloExample";
        String autore = "autoreExample";
        String genere = "genereExample";
        Stato stato = Stato.DISPONIBILE;
        int copie = 5;
        LocalDate inizioPrestito = LocalDate.of(2025, 1, 1);
        LocalDate finePrestito = LocalDate.of(2025, 1, 15);

        Libro libro = LibroFactory.createLibro(id, titolo, autore, genere, stato, copie, inizioPrestito, finePrestito);

        // Verifica delle proprietà del libro creato
        assertNotNull(libro, "Il libro creato non dovrebbe essere null");
        assertEquals(id, libro.getId(), "L'ID del libro non corrisponde");
        assertEquals(titolo, libro.getTitolo(), "Il titolo del libro non corrisponde");
        assertEquals(autore, libro.getAutore(), "L'autore del libro non corrisponde");
        assertEquals(genere, libro.getGenere(), "Il genere del libro non corrisponde");
        assertEquals(stato, libro.getStato(), "Lo stato del libro non corrisponde");
        assertEquals(copie, libro.getCopie(), "Il numero di copie del libro non corrisponde");
        assertEquals(inizioPrestito, libro.getInizioPrestito(), "La data di inizio prestito non corrisponde");
        assertEquals(finePrestito, libro.getFinePrestito(), "La data di fine prestito non corrisponde");
    }
}

