package main;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseManager {
    private static final Logger logger = LogManager.getLogger(DatabaseManager.class);
    private static final String URL = "jdbc:sqlite:biblioteca.db";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL);
    }

    public static void setupDatabase() {
        String createUtentiTable = "CREATE TABLE IF NOT EXISTS utenti (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "username TEXT UNIQUE NOT NULL, " +
                "password TEXT NOT NULL, " +
                "ruolo TEXT NOT NULL" +
                ");";

        String createLibriTable = "CREATE TABLE IF NOT EXISTS libri (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "titolo TEXT NOT NULL, " +
                "autore TEXT NOT NULL, " +
                "genere TEXT" +
                ");";

        String createPrestitiTable = "CREATE TABLE IF NOT EXISTS prestiti (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "utente_id INTEGER, " +
                "libro_id INTEGER, " +
                "data_prestito TEXT, " +
                "data_restituzione TEXT, " +
                "FOREIGN KEY (utente_id) REFERENCES utenti(id), " +
                "FOREIGN KEY (libro_id) REFERENCES libri(id)" +
                ");";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(createUtentiTable);
            stmt.execute(createLibriTable);
            stmt.execute(createPrestitiTable);
            logger.info("Tabelle create con successo.");
        } catch (SQLException e) {
            logger.error("Errore durante la configurazione del database.", e);
        }
    }
}