package main;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.*;

class DatabaseManagerTest {

    private static final Logger logger = LogManager.getLogger(DatabaseManagerTest.class);

    @BeforeAll
    static void setupDatabase() {
        logger.info("Configurazione del database per i test...");
        DatabaseManager.setupDatabase();
    }

    @Test
    void testConnection() {
        logger.info("Esecuzione del test: testConnection");
        try (Connection conn = DatabaseManager.getConnection()) {
            assertNotNull(conn, "La connessione al database dovrebbe essere valida.");
            logger.info("Connessione al database verificata con successo.");
        } catch (SQLException e) {
            logger.error("Errore durante la connessione al database", e);
            fail("Eccezione durante la connessione al database: " + e.getMessage());
        }
    }

    @Test
    void testUtentiTableExists() {
        logger.info("Esecuzione del test: testUtentiTableExists");
        try (Connection conn = DatabaseManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT name FROM sqlite_master WHERE type='table' AND name='utenti';")) {

            assertTrue(rs.next(), "La tabella 'utenti' dovrebbe esistere.");
            logger.info("La tabella 'utenti' esiste nel database.");
        } catch (SQLException e) {
            logger.error("Errore durante il controllo della tabella 'utenti'", e);
            fail("Errore durante il controllo della tabella 'utenti': " + e.getMessage());
        }
    }

    @Test
    void testLibriTableExists() {
        logger.info("Esecuzione del test: testLibriTableExists");
        try (Connection conn = DatabaseManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT name FROM sqlite_master WHERE type='table' AND name='libri';")) {

            assertTrue(rs.next(), "La tabella 'libri' dovrebbe esistere.");
            logger.info("La tabella 'libri' esiste nel database.");
        } catch (SQLException e) {
            logger.error("Errore durante il controllo della tabella 'libri'", e);
            fail("Errore durante il controllo della tabella 'libri': " + e.getMessage());
        }
    }
    
    @Test
    void testInsertAndQueryUtenti() {
        try (Connection conn = DatabaseManager.getConnection();
             Statement stmt = conn.createStatement()) {
            // Inserimento di un record nella tabella 'utenti'
            stmt.execute("INSERT INTO utenti (email, password, ruolo) VALUES ('test@example.com', 'password123', 'admin');");
            
            // Query per recuperare il record
            ResultSet rs = stmt.executeQuery("SELECT * FROM utenti WHERE email='test@example.com';");
            
            // Verifica del record recuperato
            assertTrue(rs.next(), "Il record dovrebbe essere stato inserito nella tabella 'utenti'.");
            assertEquals("test@example.com", rs.getString("email"), "L'email dovrebbe essere 'test@example.com'.");
            assertEquals("admin", rs.getString("ruolo"), "Il ruolo dovrebbe essere 'admin'.");
        } catch (SQLException e) {
            fail("Errore durante il test di inserimento e query per la tabella 'utenti': " + e.getMessage());
        }
    }

    @Test
    void testInsertAndQueryLibri() {
        try (Connection conn = DatabaseManager.getConnection();
             Statement stmt = conn.createStatement()) {
            // Inserimento di un record nella tabella 'libri'
            stmt.execute("INSERT INTO libri (titolo, autore, genere, stato, copie) " +
                         "VALUES ('Il Signore degli Anelli', 'J.R.R. Tolkien', 'Fantasy', 'disponibile', 5);");
            
            // Query per recuperare il record
            ResultSet rs = stmt.executeQuery("SELECT * FROM libri WHERE titolo='Il Signore degli Anelli';");
            
            // Verifica del record recuperato
            assertTrue(rs.next(), "Il record dovrebbe essere stato inserito nella tabella 'libri'.");
            assertEquals("Il Signore degli Anelli", rs.getString("titolo"), "Il titolo dovrebbe essere 'Il Signore degli Anelli'.");
            assertEquals("J.R.R. Tolkien", rs.getString("autore"), "L'autore dovrebbe essere 'J.R.R. Tolkien'.");
            assertEquals("Fantasy", rs.getString("genere"), "Il genere dovrebbe essere 'Fantasy'.");
            assertEquals("disponibile", rs.getString("stato"), "Lo stato dovrebbe essere 'disponibile'.");
            assertEquals(5, rs.getInt("copie"), "Il numero di copie dovrebbe essere 5.");
        } catch (SQLException e) {
            fail("Errore durante il test di inserimento e query per la tabella 'libri': " + e.getMessage());
        }
    }

    
    @Test
    void testUtentiSchema() {
        try (Connection conn = DatabaseManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("PRAGMA table_info(utenti);")) {

            boolean emailColumnFound = false;
            while (rs.next()) {
                if (rs.getString("name").equals("email")) {
                    emailColumnFound = true;
                    assertEquals("TEXT", rs.getString("type"), "La colonna 'email' dovrebbe essere di tipo TEXT.");
                    assertEquals(1, rs.getInt("notnull"), "La colonna 'email' dovrebbe essere NOT NULL.");
                }
            }
            assertTrue(emailColumnFound, "La colonna 'email' dovrebbe esistere nella tabella 'utenti'.");
        } catch (SQLException e) {
            fail("Errore durante la verifica dello schema della tabella 'utenti': " + e.getMessage());
        }
    }
    
    


    @AfterAll
    static void cleanupDatabase() {
        logger.info("Pulizia del database dopo i test...");
        try (Connection conn = DatabaseManager.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute("DROP TABLE IF EXISTS utenti;");
            stmt.execute("DROP TABLE IF EXISTS libri;");
            logger.info("Tabelle eliminate con successo.");
        } catch (SQLException e) {
            logger.error("Errore durante la pulizia del database", e);
        }
    }
}
