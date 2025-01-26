package main;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class HashPasswordsTest {

    @Test
    void testHashPasswords() {
        // Verifica che l'hashing non restituisca mai lo stesso valore
        String password1 = "password123";
        String password2 = "password123";
        String hash1 = HashPasswords.hashPassword(password1);
        String hash2 = HashPasswords.hashPassword(password2);

        assertNotNull(hash1, "L'hash non dovrebbe essere null");
        assertNotNull(hash2, "L'hash non dovrebbe essere null");
        assertNotEquals(password1, hash1, "L'hash non dovrebbe essere uguale alla password in chiaro");
        assertNotEquals(hash1, hash2, "Due hash della stessa password non dovrebbero essere uguali (BCrypt usa salt)");
    }

    @Test
    void testVerificaPassword() {
        // Verifica che la password corrisponda all'hash
        String plainPassword = "password123";
        String hashedPassword = HashPasswords.hashPassword(plainPassword);

        assertTrue(HashPasswords.verificaPassword(plainPassword, hashedPassword), 
            "La password in chiaro dovrebbe corrispondere all'hash");

        // Verifica che una password errata non corrisponda all'hash
        String wrongPassword = "wrongpassword";
        assertFalse(HashPasswords.verificaPassword(wrongPassword, hashedPassword), 
            "Una password sbagliata non dovrebbe corrispondere all'hash");
    }

    @Test
    void testEmptyPassword() {
        // Verifica che una password vuota funzioni correttamente
        String emptyPassword = "";
        String hashedPassword = HashPasswords.hashPassword(emptyPassword);

        assertTrue(HashPasswords.verificaPassword(emptyPassword, hashedPassword), 
            "La password vuota dovrebbe corrispondere al proprio hash");

        // Verifica che un'altra password non corrisponda
        assertFalse(HashPasswords.verificaPassword("nonempty", hashedPassword), 
            "Una password non vuota non dovrebbe corrispondere a un hash di una password vuota");
    }
}

