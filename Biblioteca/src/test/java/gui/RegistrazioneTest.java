package gui;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Arrays;

class RegistrazioneTest {

    private Registrazione registrazione;

    @BeforeEach
    void setUp() {
        registrazione = new Registrazione();
    }

    @Test
    void testVerificaDatiEmailEPasswordVuote() {
        assertFalse(registrazione.verificaDati("", new char[]{}, new char[]{}));
    }

    @Test
    void testVerificaDatiSoloEmailVuota() {
        assertFalse(registrazione.verificaDati("", "Password1!".toCharArray(), "Password1!".toCharArray()));
    }

    @Test
    void testVerificaDatiFormatoEmailErrato() {
        assertFalse(registrazione.verificaDati("emailerrato", "Password1!".toCharArray(), "Password1!".toCharArray()));
    }

    @Test
    void testVerificaDatiPasswordVuota() {
        assertFalse(registrazione.verificaDati("test@example.com", new char[]{}, new char[]{}));
    }

    @Test
    void testVerificaDatiConfermaPasswordVuota() {
        assertFalse(registrazione.verificaDati("test@example.com", "Password1!".toCharArray(), new char[]{}));
    }

    @Test
    void testVerificaDatiPasswordNonCorrispondono() {
        assertFalse(registrazione.verificaDati("test@example.com", "Password1!".toCharArray(), "Password2@".toCharArray()));
    }

    @Test
    void testVerificaDatiPasswordTroppoCorta() {
        assertFalse(registrazione.verificaDati("test@example.com", "Pass1!".toCharArray(), "Pass1!".toCharArray()));
    }

    @Test
    void testVerificaDatiPasswordSenzaMaiuscola() {
        assertFalse(registrazione.verificaDati("test@example.com", "password1!".toCharArray(), "password1!".toCharArray()));
    }

    @Test
    void testVerificaDatiPasswordSenzaNumero() {
        assertFalse(registrazione.verificaDati("test@example.com", "Password!".toCharArray(), "Password!".toCharArray()));
    }

    @Test
    void testVerificaDatiPasswordSenzaCarattereSpeciale() {
        assertFalse(registrazione.verificaDati("test@example.com", "Password1".toCharArray(), "Password1".toCharArray()));
    }

    @Test
    void testVerificaDatiValidi() {
        assertTrue(registrazione.verificaDati("test@example.com", "Password1!".toCharArray(), "Password1!".toCharArray()));
    }

    @Test
    void testVerificaDatiEmailConFormatoValido() {
        assertTrue(registrazione.verificaDati("utente@mail.it", "Password1@".toCharArray(), "Password1@".toCharArray()));
    }

    @Test
    void testVerificaDatiEmailConFormatoNonValido() {
        assertFalse(registrazione.verificaDati("utente@.it", "Password1@".toCharArray(), "Password1@".toCharArray()));
    }

    @Test
    void testVerificaDatiPasswordMassimaValidita() {
        assertTrue(registrazione.verificaDati("test@example.com", "Passw@rd1".toCharArray(), "Passw@rd1".toCharArray()));
    }
}



