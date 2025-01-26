package main;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

import jakarta.mail.Transport;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GestioneEmailTest {

    @BeforeEach
    void setup() {
        // Mocking del Transport per evitare invio effettivo delle email
        mockStatic(Transport.class);
    }

    @Test
    void testVerificaEmail() {
        String destinatario = "test@example.com";
        String codice = GestioneEmail.verificaEmail(destinatario);

        // Verifica che il codice generato abbia la lunghezza corretta
        assertNotNull(codice, "Il codice non dovrebbe essere null.");
        assertEquals(6, codice.length(), "Il codice dovrebbe avere 6 caratteri.");
        // Verifica che l'email venga inviata
        verify(Transport.class);
    }

    @Test
    void testIdentificaUtente() {
        String destinatario = "utente@example.com";
        String codice = GestioneEmail.identificaUtente(destinatario);

        // Verifica che il codice generato sia valido
        assertNotNull(codice, "Il codice non dovrebbe essere null.");
        assertEquals(6, codice.length(), "Il codice dovrebbe avere 6 caratteri.");
        // Verifica che l'email venga inviata
        verify(Transport.class);
    }

    @Test
    void testSegnalaPrestitoScaduto() {
        String destinatario = "utente@example.com";
        Libro libro = new Libro("Il Signore degli Anelli", "J.R.R. Tolkien", "Fantasy");

        GestioneEmail.SegnalaPrestitoScaduto(destinatario, libro);

        // Verifica che l'email venga inviata
        verify(Transport.class);
    }

    @Test
    void testSegnalaRitiroScaduto() {
        String destinatario = "utente@example.com";
        Libro libro = new Libro("1984", "George Orwell", "Distopia");

        GestioneEmail.SegnalaRitiroScaduto(destinatario, libro);

        // Verifica che l'email venga inviata
        verify(Transport.class);
    }

    @Test
    void testPrenotazione() {
        String destinatario = "utente@example.com";
        Libro libro = new Libro("Harry Potter", "J.K. Rowling", "Fantasy");

        GestioneEmail.prenotazione(destinatario, libro);

        // Verifica che l'email venga inviata
        verify(Transport.class);
    }
}
