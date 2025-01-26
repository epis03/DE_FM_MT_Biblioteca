package main;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.*;

public class DatabaseManagerTest {

	 private static final Logger logger = LogManager.getLogger(DatabaseManagerTest.class);
	 
	 
    @Test
    void testGetConnection() {
        try (Connection conn = DatabaseManager.getConnection()) {
            assertNotNull(conn, "La connessione al database non dovrebbe essere null.");
            assertFalse(conn.isClosed(), "La connessione al database dovrebbe essere aperta.");
        } catch (SQLException e) {
            fail("Eccezione durante la connessione al database: " + e.getMessage());
        }
    }

    @Test
    void testSetupDatabase() {
        // Configura il database
        DatabaseManager.setupDatabase();

        // Verifica che le tabelle siano state create
        try (Connection conn = DatabaseManager.getConnection();
             Statement stmt = conn.createStatement()) {
            
            // Controllo della tabella 'utenti'
            ResultSet utentiTable = stmt.executeQuery("SELECT name FROM sqlite_master WHERE type='table' AND name='utenti';");
            assertTrue(utentiTable.next(), "La tabella 'utenti' dovrebbe esistere.");

            // Controllo della tabella 'libri'
            ResultSet libriTable = stmt.executeQuery("SELECT name FROM sqlite_master WHERE type='table' AND name='libri';");
            assertTrue(libriTable.next(), "La tabella 'libri' dovrebbe esistere.");

            // Controllo della tabella 'prenotazioni'
            ResultSet prenotazioniTable = stmt.executeQuery("SELECT name FROM sqlite_master WHERE type='table' AND name='prenotazioni';");
            assertTrue(prenotazioniTable.next(), "La tabella 'prenotazioni' dovrebbe esistere.");

        } catch (SQLException e) {
            fail("Eccezione durante la verifica delle tabelle: " + e.getMessage());
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
            fail("Eccezione durante la verifica delle tabelle: " + e.getMessage());
        }
}
}