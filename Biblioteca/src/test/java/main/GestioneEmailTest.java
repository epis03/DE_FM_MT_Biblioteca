package main;

import org.junit.jupiter.api.*;
import org.mockito.Mockito;

import jakarta.mail.Transport;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GestioneEmailTest {

    private static Libro libro;

    @BeforeAll
    static void setup() {
        // Mocking statico eseguito una sola volta
        mockStatic(Transport.class);

        // Creazione di un libro di esempio
        libro = new Libro(
                1, 
                "Il Signore degli Anelli", 
                "J.R.R. Tolkien", 
                "Fantasy", 
                Stato.DISPONIBILE, 
                1, 
                LocalDate.of(2023, 1, 1), 
                LocalDate.of(2023, 1, 15)
        );
    }

    @AfterAll
    static void teardown() {
        // Rilascia il mocking statico
        Mockito.framework().clearInlineMocks();
    }

    @Test
    void testVerificaEmail() {
        String destinatario = "test@example.com";
        String codice = GestioneEmail.verificaEmail(destinatario);

        assertNotNull(codice, "Il codice non dovrebbe essere null.");
        assertEquals(6, codice.length(), "Il codice dovrebbe avere 6 caratteri.");
        verify(Transport.class);
    }

    @Test
    void testIdentificaUtente() {
        String destinatario = "utente@example.com";
        String codice = GestioneEmail.identificaUtente(destinatario);

        assertNotNull(codice, "Il codice non dovrebbe essere null.");
        assertEquals(6, codice.length(), "Il codice dovrebbe avere 6 caratteri.");
        verify(Transport.class);
    }

    @Test
    void testSegnalaPrestitoScaduto() {
        String destinatario = "utente@example.com";

        GestioneEmail.SegnalaPrestitoScaduto(destinatario, libro);
        verify(Transport.class);
    }

    @Test
    void testSegnalaRitiroScaduto() {
        String destinatario = "utente@example.com";

        GestioneEmail.SegnalaRitiroScaduto(destinatario, libro);
        verify(Transport.class);
    }

    @Test
    void testPrenotazione() {
        String destinatario = "utente@example.com";

        GestioneEmail.prenotazione(destinatario, libro);
        verify(Transport.class);
    }
}