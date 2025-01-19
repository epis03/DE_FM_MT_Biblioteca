package main;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

import jakarta.mail.Transport;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

public class GestioneEmailTest {

    private static final Logger logger = LogManager.getLogger(GestioneEmailTest.class);

    @Test
    public void testVerificaEmail() {
        // Mocking del metodo Transport.send
        try (MockedStatic<Transport> mockedTransport = mockStatic(Transport.class)) {        
        	
        	String destinatario = "test@example.com";
            String codiceGenerato = GestioneEmail.verificaEmail(destinatario);

            // Verifica il risultato
            assertNotNull(codiceGenerato, "Il codice generato non dovrebbe essere null");

            // Verifica che il metodo Transport.send sia stato chiamato almeno una volta
            mockedTransport.verify(() -> Transport.send(any()), times(1));

            // Verifica il Logger
            logger.info("Test eseguito per l'invio email.");
        }
    }
}

