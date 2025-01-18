package main;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GestioneLibri {
    private static final Logger logger = LogManager.getLogger(GestioneLibri.class);

    public void aggiungiLibro(String titolo, String autore, String genere, int copie) {
        String sql = "INSERT INTO libri (titolo, autore, genere, stato, copie) VALUES (?, ?, ?, 'disponibile', ?)";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, titolo);
            pstmt.setString(2, autore);
            pstmt.setString(3, genere);
            pstmt.setInt(4, copie);
            pstmt.executeUpdate();
            logger.info("Libro '{}' di '{}' aggiunto con successo.", titolo, autore);
        } catch (SQLException e) {
            logger.error("Errore durante l'aggiunta del libro.", e);
        }
    }

    public void prenotaLibro(int libroId) {
        String sql = "UPDATE libri SET stato = 'prenotato', copie = copie - 1 WHERE id = ? AND stato = 'disponibile' AND copie > 0";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, libroId);
            int rowsUpdated = pstmt.executeUpdate();
            if (rowsUpdated > 0) {
                logger.info("Libro con ID '{}' prenotato con successo.", libroId);
            } else {
                logger.warn("Il libro con ID '{}' non è disponibile per la prenotazione.", libroId);
            }
        } catch (SQLException e) {
            logger.error("Errore durante la prenotazione del libro.", e);
        }
    }

    public void visualizzaLibri() {
        String sql = "SELECT * FROM libri";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                logger.info("ID: {}, Titolo: {}, Autore: {}, Genere: {}, Stato: {}, Copie: {}",
                        rs.getInt("id"),
                        rs.getString("titolo"),
                        rs.getString("autore"),
                        rs.getString("genere"),
                        rs.getString("stato"),
                        rs.getInt("copie"));
            }
        } catch (SQLException e) {
            logger.error("Errore durante la visualizzazione dei libri.", e);
        }
    }
}
