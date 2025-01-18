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
                "email TEXT UNIQUE NOT NULL, " +
                "password TEXT NOT NULL, " +
                "ruolo TEXT NOT NULL" +
                ");";

        String createLibriTable = "CREATE TABLE IF NOT EXISTS libri (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "titolo TEXT NOT NULL, " +
                "autore TEXT NOT NULL, " +
                "genere TEXT, " +
                "stato TEXT NOT NULL DEFAULT 'disponibile', " +
                "copie INTEGER NOT NULL" +
                ");";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(createUtentiTable);
            stmt.execute(createLibriTable);
            logger.info("Tabelle create con successo.");
        } catch (SQLException e) {
            logger.error("Errore durante la configurazione del database.", e);
        }
    }
}