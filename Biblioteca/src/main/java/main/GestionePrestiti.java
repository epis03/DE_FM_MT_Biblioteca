package main;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class GestionePrestiti {

	 private static final Logger logger = LogManager.getLogger(GestionePrestiti.class);
	 
	 public List<Libro> getLibriConStatoRitirato() {
		    List<Libro> libriRitirati = new ArrayList<>();
		    String sql = "SELECT id, titolo, autore, genere, stato, copie, inizio_prestito, fine_prestito " +
		                 "FROM libri " +
		                 "WHERE stato = 'RITIRATO'";

		    try (Connection conn = DatabaseManager.getConnection();
		         PreparedStatement pstmt = conn.prepareStatement(sql);
		         ResultSet rs = pstmt.executeQuery()) {

		        while (rs.next()) {
		          
		            int id = rs.getInt("id");
		            String titolo = rs.getString("titolo");
		            String autore = rs.getString("autore");
		            String genere = rs.getString("genere");
		            Stato stato = Stato.valueOf(rs.getString("stato")); 
		            int copie = rs.getInt("copie");
		            LocalDate inizioPrestito = rs.getDate("inizio_prestito").toLocalDate();
		            LocalDate finePrestito = rs.getDate("fine_prestito").toLocalDate();

		           
		            Libro libro = new Libro(id, titolo, autore, genere, stato, copie, inizioPrestito, finePrestito);
		            libriRitirati.add(libro);
		        }
		    } catch (SQLException e) {
		        e.printStackTrace();
		    }
		    return libriRitirati;
		}
	 
	 public List<String> getEmailUtentiPrestitoScaduto() {
		    List<String> emailScadute = new ArrayList<>();
		    String sql = "SELECT DISTINCT u.email " +
		                 "FROM utenti u " +
		                 "JOIN prenotazioni p ON u.email = p.email " +
		                 "WHERE p.fine_prestito < DATE('now')";

		    try (Connection conn = DatabaseManager.getConnection();
		         PreparedStatement pstmt = conn.prepareStatement(sql);
		         ResultSet rs = pstmt.executeQuery()) {

		        while (rs.next()) {
		            
		            String email = rs.getString("email");
		            emailScadute.add(email);
		        }
		    } catch (SQLException e) {
		        e.printStackTrace();
		    }
		    return emailScadute;
		}
	 
	 
	 
	 public List<Integer> getIdLibriPrenotazioneScaduta() {
		    List<Integer> idLibriScaduti = new ArrayList<>();
		    String sql = "SELECT id_libro " +
		                 "FROM prenotazioni " +
		                 "WHERE data_fineprenotazione < DATE('now')";

		    try (Connection conn = DatabaseManager.getConnection();
		         PreparedStatement pstmt = conn.prepareStatement(sql);
		         ResultSet rs = pstmt.executeQuery()) {

		        while (rs.next()) {
		            
		            int idLibro = rs.getInt("id_libro");
		            idLibriScaduti.add(idLibro);
		        }
		    } catch (SQLException e) {
		        e.printStackTrace();
		    }
		    return idLibriScaduti;
		}
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	  public static void prenotaLibro(String email, int idLibro) {
	    String sql = "INSERT INTO prenotazioni (email, id_libro, data_prenotazione, data_fineprenotazione) " +
	                 "VALUES (?, ?, ?, ?)";

	    try (Connection conn = DatabaseManager.getConnection();
	         PreparedStatement pstmt = conn.prepareStatement(sql)) {

	        LocalDate currentDate = LocalDate.now();
	        Date dataPrenotazione = Date.valueOf(currentDate);
	        LocalDate finePrenotazione = currentDate.plusDays(7);
	        Date dataFinePrenotazione = Date.valueOf(finePrenotazione);

	        pstmt.setString(1, email);
	        pstmt.setInt(2, idLibro);
	        pstmt.setDate(3, dataPrenotazione);
	        pstmt.setDate(4, dataFinePrenotazione);

	        pstmt.executeUpdate();
	        logger.info("Prenotazione effettuata con successo per il libro ID {} e l'utente {}", idLibro, email);

	    } catch (SQLException e) {
	        logger.error("Errore durante la prenotazione del libro.", e);
	    }
	}
	
	public static List<Integer> getLibriPrenotati(String email) {
	    String sql = "SELECT id_libro FROM prenotazioni WHERE email = ?";
	    List<Integer> libriPrenotati = new ArrayList<>();

	    try (Connection conn = DatabaseManager.getConnection();
	         PreparedStatement pstmt = conn.prepareStatement(sql)) {

	        pstmt.setString(1, email);

	        try (ResultSet rs = pstmt.executeQuery()) {
	            while (rs.next()) {
	                int idLibro = rs.getInt("id_libro");
	                libriPrenotati.add(idLibro);
	            }
	        }

	    } catch (SQLException e) {
	        logger.error("Errore durante il recupero dei libri prenotati per l'utente {}", email, e);
	    }

	    return libriPrenotati;
	}	
	
	public static void aggiornaPrestito(int idLibro) {
	    String sqlUpdate = "UPDATE prenotazioni " +
	                       "SET inizio_prestito = ?, fine_prestito = ? " +
	                       "WHERE id_libro = ?";

	    LocalDate inizioPrestito = LocalDate.now();
	    LocalDate finePrestito = inizioPrestito.plusDays(30);

	    try (Connection conn = DatabaseManager.getConnection();
	         PreparedStatement pstmt = conn.prepareStatement(sqlUpdate)) {

	        pstmt.setDate(1, java.sql.Date.valueOf(inizioPrestito));
	        pstmt.setDate(2, java.sql.Date.valueOf(finePrestito));
	        pstmt.setInt(3, idLibro);

	        int rowsUpdated = pstmt.executeUpdate();

	        if (rowsUpdated > 0) {
	            logger.info("Prestito aggiornato per id_libro '{}'. Inizio: {}, Fine: {}.",
	                        idLibro, inizioPrestito, finePrestito);
	        } else {
	            logger.warn("Nessuna prenotazione trovata per id_libro '{}'.", idLibro);
	        }
	    } catch (SQLException e) {
	        logger.error("Errore durante l'aggiornamento del prestito per id_libro '{}'.", idLibro, e);
	    }
	};
	
	public static void eliminaPrenotazione(int idLibro) {
	    String sqlDelete = "DELETE FROM prenotazioni WHERE id_libro = ?";

	    try (Connection conn = DatabaseManager.getConnection();
	         PreparedStatement pstmt = conn.prepareStatement(sqlDelete)) {

	        pstmt.setInt(1, idLibro);

	        int rowsDeleted = pstmt.executeUpdate();

	        if (rowsDeleted > 0) {
	            logger.info("Prenotazione eliminata per id_libro '{}'.", idLibro);
	        } else {
	            logger.warn("Nessuna prenotazione trovata per id_libro '{}'.", idLibro);
	        }
	    } catch (SQLException e) {
	        logger.error("Errore durante l'eliminazione della prenotazione per id_libro '{}'.", idLibro, e);
	    }
	}




}
