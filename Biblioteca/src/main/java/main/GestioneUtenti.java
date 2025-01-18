package main;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GestioneUtenti {
    private static final Logger logger = LogManager.getLogger(GestioneUtenti.class);

    public boolean registraUtente(String email, String password) {
        String ruolo = email.contains("@unibg") ? "amministratore" : "utente";
        String sql = "INSERT INTO utenti (email, password, ruolo) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, email);
            pstmt.setString(2, password);
            pstmt.setString(3, ruolo);
            pstmt.executeUpdate();
            logger.info("Utente '{}' registrato come '{}'.", email, ruolo);
            return true;
        } catch (SQLException e) {
            logger.error("Errore durante la registrazione dell'utente.", e);
            return false;
        }
    }

    public boolean autenticaUtente(String email, String password) {
        String sql = "SELECT password FROM utenti WHERE email = ?";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next() && rs.getString("password").equals(password)) {
                logger.info("Autenticazione riuscita per l'utente '{}'.", email);
                return true;
            } else {
                logger.error("Autenticazione fallita per l'utente '{}'.", email);
                return false;
            }
        } catch (SQLException e) {
            logger.error("Errore durante l'autenticazione.", e);
            return false;
        }
    }
}