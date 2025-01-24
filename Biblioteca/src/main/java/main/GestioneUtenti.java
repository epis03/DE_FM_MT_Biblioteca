package main;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GestioneUtenti {
    private static final Logger logger = LogManager.getLogger(GestioneUtenti.class);
    
    

        
        public String getUserRole(String email) {
            String ruolo = null;
            String sql = "SELECT ruolo FROM utenti WHERE email = ?";

            try (Connection conn = DatabaseManager.getConnection();
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {
                
                pstmt.setString(1, email);
                ResultSet rs = pstmt.executeQuery();

                if (rs.next()) {
                    ruolo = rs.getString("ruolo");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return ruolo;
        }

       
        public boolean changeUserPassword(String email, String newPassword) {
            String sql = "UPDATE utenti SET password = ? WHERE email = ?";
            boolean updated = false;

            try (Connection conn = DatabaseManager.getConnection();
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {
                
                pstmt.setString(1, newPassword); 
                pstmt.setString(2, email);
                int rowsAffected = pstmt.executeUpdate();

                updated = rowsAffected > 0;
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return updated;
        }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    

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
    
    public boolean emailEsiste(String email) {
        String sql = "SELECT 1 FROM utenti WHERE email = ?";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                logger.warn("L'email '{}' esiste già ", email);
                return true;
            } else {
                logger.info("L'email '{}' non è presente nel sistema.", email);
                return false;
            }
        } catch (SQLException e) {
            logger.error("Errore durante la verifica dell'esistenza dell'email.", e);
            return false;
        }
    }
}