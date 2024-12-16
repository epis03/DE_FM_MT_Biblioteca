package main;

import com.biblioteca.database.DatabaseManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GestioneLibri {
    private static final Logger logger = LogManager.getLogger(GestioneLibri.class);

    public void aggiungiLibro(String titolo, String autore, String genere) {
        String sql = "INSERT INTO libri (titolo, autore, genere) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, titolo);
            pstmt.setString(2, autore);
            pstmt.setString(3, genere);
            pstmt.executeUpdate();
            logger.info("Libro '{}' di '{}' aggiunto con successo.", titolo, autore);
        } catch (SQLException e) {
            logger.error("Errore durante l'aggiunta del libro.", e);
        }
    }

    public void visualizzaLibri() {
        String sql = "SELECT * FROM libri";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                logger.info("ID: {}, Titolo: {}, Autore: {}, Genere: {}",
                        rs.getInt("id"),
                        rs.getString("titolo"),
                        rs.getString("autore"),
                        rs.getString("genere"));
            }
        } catch (SQLException e) {
            logger.error("Errore durante la visualizzazione dei libri.", e);
        }
    }
}