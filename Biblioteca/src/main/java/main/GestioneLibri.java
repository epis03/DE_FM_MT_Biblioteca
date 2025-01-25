package main;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GestioneLibri {
    private static final Logger logger = LogManager.getLogger(GestioneLibri.class);

    public void aggiungiLibro(String titolo, String autore, String genere, int copie) {
        String checkSql = "SELECT id, copie FROM libri WHERE titolo = ? AND autore = ? AND genere = ?";
        String insertSql = "INSERT INTO libri (titolo, autore, genere, stato, copie) VALUES (?, ?, ?, 'DISPONIBILE', 1)";
        String updateSql = "UPDATE libri SET copie = copie + ? WHERE id = ?";
        String updateStatusSql = "UPDATE libri SET stato = 'DISPONIBILE' WHERE titolo = ? AND autore = ? AND genere = ? AND copie > 0";

        try (Connection conn = DatabaseManager.getConnection()) {
    
            try (PreparedStatement pstmtCheck = conn.prepareStatement(checkSql)) {
                pstmtCheck.setString(1, titolo);
                pstmtCheck.setString(2, autore);
                pstmtCheck.setString(3, genere);

                ResultSet rs = pstmtCheck.executeQuery();
                if (rs.next()) {
                   
                    int bookId = rs.getInt("id");
                    int currentCopies = rs.getInt("copie");

                    try (PreparedStatement pstmtUpdate = conn.prepareStatement(updateSql)) {
                        pstmtUpdate.setInt(1, copie);
                        pstmtUpdate.setInt(2, bookId);
                        pstmtUpdate.executeUpdate();
                        logger.info("Aggiornato il numero di copie del libro '{}' di '{}' a {} copie.", titolo, autore, currentCopies + copie);
                    }

                    
                    if (currentCopies == 0) {
                        try (PreparedStatement pstmtUpdateStatus = conn.prepareStatement(updateStatusSql)) {
                            pstmtUpdateStatus.setString(1, titolo);
                            pstmtUpdateStatus.setString(2, autore);
                            pstmtUpdateStatus.setString(3, genere);
                            pstmtUpdateStatus.executeUpdate();
                            logger.info("Lo stato del libro '{}' di '{}' è stato aggiornato a 'disponibile' per tutti i libri con lo stesso titolo, autore e genere.", titolo, autore);
                        }
                    }
                } else {

                    for (int i = 0; i < copie; i++) {
                        try (PreparedStatement pstmtInsert = conn.prepareStatement(insertSql)) {
                            pstmtInsert.setString(1, titolo);
                            pstmtInsert.setString(2, autore);
                            pstmtInsert.setString(3, genere);
                            pstmtInsert.executeUpdate();
                        }
                    }
                    logger.info("Aggiunto il libro '{}' di '{}' con {} copie.", titolo, autore, copie);
                }
            }
        } catch (SQLException e) {
            logger.error("Errore durante l'aggiunta o l'aggiornamento del libro.", e);
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
    
    public static List<Libro> getListaLibri() {
        List<Libro> libri = new ArrayList<>();
        String sql = "SELECT * FROM libri";

        try (Connection conn = DatabaseManager.getConnection();
        		PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery(sql)) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String titolo = rs.getString("titolo");
                String autore = rs.getString("autore");
                String genere = rs.getString("genere");
                Stato stato = (Stato.valueOf(rs.getString("stato")));
                int copie = rs.getInt("copie");

                Libro libro = LibroFactory.createLibro(id, titolo, autore, genere, stato, copie);
                libri.add(libro);
            }

            logger.info("Libri estratti con successo dal database.");

        } catch (SQLException e) {
            logger.error("Errore durante l'estrazione dei libri.", e);
        }

        return libri;
    }
}
