package main;

import org.junit.jupiter.api.*;
import org.mockito.Mockito;

import jakarta.mail.Transport;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GestioneEmailTest {

    private Libro libro;

    @BeforeEach
    void setup() {
        // Mocking del Transport per evitare invio effettivo delle email
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

        GestioneEmail.SegnalaPrestitoScaduto(destinatario, libro);

        // Verifica che l'email venga inviata
        verify(Transport.class);
    }

    @Test
    void testSegnalaRitiroScaduto() {
        String destinatario = "utente@example.com";

        GestioneEmail.SegnalaRitiroScaduto(destinatario, libro);

        // Verifica che l'email venga inviata
        verify(Transport.class);
    }

    @Test
    void testPrenotazione() {
        String destinatario = "utente@example.com";

        GestioneEmail.prenotazione(destinatario, libro);

        // Verifica che l'email venga inviata
        verify(Transport.class);
    }
}