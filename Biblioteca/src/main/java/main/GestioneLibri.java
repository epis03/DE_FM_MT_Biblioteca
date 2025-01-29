package main;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class GestioneLibri {
    private static final Logger logger = LogManager.getLogger(GestioneLibri.class);

    public void aggiungiLibro(String titolo, String autore, String genere, int copie) {
        String checkSql = "SELECT id, copie FROM libri WHERE titolo = ? AND autore = ? AND genere = ?";
        String insertSql = "INSERT INTO libri (titolo, autore, genere, stato, copie) VALUES (?, ?, ?, 'DISPONIBILE', ?)";
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
                			pstmtInsert.setInt(4, copie);
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




    public static int prenotaLibro(String titolo, String autore) {
    	LocalDate inizioPrestito = LocalDate.now();
    	LocalDate finePrestito = inizioPrestito.plusDays(7);
    	int libroId = -1;


    	String checkSql = "SELECT MIN(id) FROM libri WHERE titolo = ? AND autore = ? AND stato = 'DISPONIBILE' ";
    	String sqlUpdate = "UPDATE libri SET stato = 'PRENOTATO', inizio_prestito = ?, fine_prestito = ? " +
    			"WHERE id = ? ";
    	String sqlUpdateCopie = "UPDATE libri SET copie = copie - 1 WHERE titolo = ? AND autore = ?";
    	String sqlUpdateStato = "UPDATE libri SET stato = 'NON_DISPONIBILE' WHERE titolo = ? AND autore = ? AND stato = 'DISPONIBILE' AND copie = 0";

    	try (Connection conn = DatabaseManager.getConnection();
    			PreparedStatement pstmtUpdate = conn.prepareStatement(sqlUpdate);
    			PreparedStatement pstmtCheck = conn.prepareStatement(checkSql);
    			PreparedStatement pstmUpdateCopie = conn.prepareStatement(sqlUpdateCopie);
    			PreparedStatement pstmtUpdateStato = conn.prepareStatement(sqlUpdateStato)) {

    		pstmtCheck.setString(1, titolo);
    		pstmtCheck.setString(2, autore);

    		ResultSet rs = pstmtCheck.executeQuery();
    		if (rs.next()) {
    			libroId = rs.getInt(1);
    			pstmtUpdate.setDate(1, java.sql.Date.valueOf(inizioPrestito));
    			pstmtUpdate.setDate(2, java.sql.Date.valueOf(finePrestito));
    			pstmtUpdate.setInt(3, libroId);  


    			int rowsUpdated = pstmtUpdate.executeUpdate();

    			if (rowsUpdated > 0) {

    				pstmUpdateCopie.setString(1, titolo);
    				pstmUpdateCopie.setString(2, autore);
    				pstmUpdateCopie.executeUpdate();


    			}
    			pstmtUpdateStato.setString(1, titolo);
    			pstmtUpdateStato.setString(2, autore);
    			pstmtUpdateStato.executeUpdate();
    			if(libroId>0) {
    				logger.info("Il libro con titolo '{}' e autore '{}' è stato prenotato con successo.", titolo, autore);
    			}
    			else {
    				logger.warn("Il libro con titolo '{}' e autore '{}' non è disponibile per la prenotazione.", titolo, autore);
    			}
    		} else {
    			logger.warn("Il libro con titolo '{}' e autore '{}' non è disponibile per la prenotazione.", titolo, autore);
    		}
    	} catch (SQLException e) {
    		logger.error("Errore durante la prenotazione del libro.", e);
    	}

        return libroId;
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
    
    public static List<Libro> getListaLibri(boolean distinct) {
        String sql;
        List<Libro> libri = new ArrayList<>();
        if (distinct) {
            sql = "SELECT titolo, autore, genere, MAX(id) AS id, stato, copie, inizio_prestito, fine_prestito " +
                  "FROM libri GROUP BY titolo, autore, genere";
        } else {
            sql = "SELECT * FROM libri";
        }

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
                LocalDate inizioPrestito = rs.getDate("inizio_prestito") != null ? rs.getDate("inizio_prestito").toLocalDate() : null;
                LocalDate finePrestito = rs.getDate("fine_prestito") != null ? rs.getDate("fine_prestito").toLocalDate() : null;
                
                Libro libro = LibroFactory.createLibro(id, titolo, autore, genere, stato, copie, inizioPrestito, finePrestito);
                libri.add(libro);
            }

            logger.info("Libri estratti con successo dal database.");

        } catch (SQLException e) {
            logger.error("Errore durante l'estrazione dei libri.", e);
        }

        return libri;
    }

    
    public static void eliminaLibri(String titolo, String autore, String genere) {
        String sql = "DELETE FROM libri WHERE titolo = ? AND autore = ? AND genere = ?;";
        try (Connection conn =  DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, titolo);
            pstmt.setString(2, autore);
            pstmt.setString(3, genere);
            int rowsDeleted = pstmt.executeUpdate();
            logger.info(rowsDeleted + " libri eliminati con successo.");
        } catch (SQLException e) {
            logger.error("Errore durante l'eliminazione dei libri.", e);
        }
    }
    
    public static List<Libro> filtraAutore(String autore) {
        String query = "SELECT titolo, autore, genere, MAX(id) AS id, stato, copie, inizio_prestito, fine_prestito " +
                       "FROM libri WHERE autore = ? GROUP BY titolo, autore, genere ORDER BY id;";
        List<Libro> libri = new ArrayList<>();
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, autore);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String titolo = rs.getString("titolo");
                String genere = rs.getString("genere");
                Stato stato = Stato.valueOf(rs.getString("stato"));
                int copie = rs.getInt("copie");
                LocalDate inizioPrestito = rs.getDate("inizio_prestito") != null ? rs.getDate("inizio_prestito").toLocalDate() : null;
                LocalDate finePrestito = rs.getDate("fine_prestito") != null ? rs.getDate("fine_prestito").toLocalDate() : null;

                Libro libro = LibroFactory.createLibro(id, titolo, autore, genere, stato, copie, inizioPrestito, finePrestito);
                libri.add(libro);
            }
        } catch (SQLException e) {
            logger.error("Errore durante il filtraggio per autore.", e);
        }
        return libri;
    }

    public static List<Libro> filtraGenere(String genere) {
        String query = "SELECT titolo, autore, genere, MAX(id) AS id, stato, copie, inizio_prestito, fine_prestito " +
                       "FROM libri WHERE genere = ? GROUP BY titolo, autore, genere ORDER BY id;";
        List<Libro> libri = new ArrayList<>();
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, genere);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String titolo = rs.getString("titolo");
                String autore = rs.getString("autore");
                Stato stato = Stato.valueOf(rs.getString("stato"));
                int copie = rs.getInt("copie");
                LocalDate inizioPrestito = rs.getDate("inizio_prestito") != null ? rs.getDate("inizio_prestito").toLocalDate() : null;
                LocalDate finePrestito = rs.getDate("fine_prestito") != null ? rs.getDate("fine_prestito").toLocalDate() : null;

                Libro libro = LibroFactory.createLibro(id, titolo, autore, genere, stato, copie, inizioPrestito, finePrestito);
                libri.add(libro);
            }
        } catch (SQLException e) {
            logger.error("Errore durante il filtraggio per genere.", e);
        }
        return libri;
    }


    public static List<Libro> filtraTitolo(String titolo) {
        String query = "SELECT titolo, autore, genere, MAX(id) AS id, stato, copie, inizio_prestito, fine_prestito " +
                       "FROM libri WHERE titolo = ? GROUP BY titolo, autore, genere ORDER BY id;";
        List<Libro> libri = new ArrayList<>();
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, titolo);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String autore = rs.getString("autore");
                String genere = rs.getString("genere");
                Stato stato = Stato.valueOf(rs.getString("stato"));
                int copie = rs.getInt("copie");
                LocalDate inizioPrestito = rs.getDate("inizio_prestito") != null ? rs.getDate("inizio_prestito").toLocalDate() : null;
                LocalDate finePrestito = rs.getDate("fine_prestito") != null ? rs.getDate("fine_prestito").toLocalDate() : null;

                Libro libro = LibroFactory.createLibro(id, titolo, autore, genere, stato, copie, inizioPrestito, finePrestito);
                libri.add(libro);
            }
        } catch (SQLException e) {
            logger.error("Errore durante il filtraggio per titolo.", e);
        }
        return libri;
    }


    public static List<Libro> filtra(String titolo, String autore, String genere) {
        String query = "SELECT titolo, autore, genere, id, stato, copie, inizio_prestito, fine_prestito " +
                       "FROM libri WHERE titolo = ? AND autore = ? AND genere = ? ORDER BY id;";
        List<Libro> libri = new ArrayList<>();
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, titolo);
            pstmt.setString(2, autore);
            pstmt.setString(3, genere);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                Stato stato = Stato.valueOf(rs.getString("stato"));
                int copie = rs.getInt("copie");
                LocalDate inizioPrestito = rs.getDate("inizio_prestito") != null ? rs.getDate("inizio_prestito").toLocalDate() : null;
                LocalDate finePrestito = rs.getDate("fine_prestito") != null ? rs.getDate("fine_prestito").toLocalDate() : null;

                Libro libro = LibroFactory.createLibro(id, titolo, autore, genere, stato, copie, inizioPrestito, finePrestito);
                libri.add(libro);
            }
        } catch (SQLException e) {
            logger.error("Errore durante il filtraggio per tutti i criteri.", e);
        }
        return libri;
    }

    public static void modificaLibri(String vecchioTitolo, String vecchioAutore, String vecchioGenere, String nuovoTitolo, String nuovoAutore, String nuovoGenere, int nuoveCopie) {
    	String selectQuery = "SELECT id,copie FROM libri WHERE titolo = ? AND autore = ? AND genere = ? AND stato = 'DISPONIBILE' ORDER BY id ASC"; 
    	String updateQuery = "UPDATE libri SET titolo = ?, autore = ?, genere = ?, copie = ? WHERE titolo = ? AND autore = ? AND genere = ?";
    	String insertQuery = "INSERT INTO libri (titolo, autore, genere, stato, copie) VALUES (?, ?, ?, 'DISPONIBILE', ?)";
    	String deleteQuery = "DELETE FROM libri WHERE id=?";

    	try (Connection conn = DatabaseManager.getConnection()) {

    		PreparedStatement selectStmt = conn.prepareStatement(selectQuery);
    		selectStmt.setString(1, vecchioTitolo);
    		selectStmt.setString(2, vecchioAutore);
    		selectStmt.setString(3, vecchioGenere);

    		ResultSet
    		rs = selectStmt.executeQuery();

    		int copieAttuali = 0;
    		List<Integer> libroIds = new ArrayList<>();


    		while (rs.next()) {
    			int id = rs.getInt("id");     
    			libroIds.add(id);
    		}
    		copieAttuali= libroIds.size();

    		PreparedStatement updateStmt = conn.prepareStatement(updateQuery);
    		updateStmt.setString(1, nuovoTitolo);
    		updateStmt.setString(2, nuovoAutore);
    		updateStmt.setString(3, nuovoGenere);
    		updateStmt.setInt(4, nuoveCopie);
    		updateStmt.setString(5, vecchioTitolo);
    		updateStmt.setString(6, vecchioAutore);
    		updateStmt.setString(7, vecchioGenere);
    		int righeModificate = updateStmt.executeUpdate();

    		logger.info(righeModificate + " libri aggiornati con successo.");
    		if(nuoveCopie > copieAttuali) {
    			int differenza =  nuoveCopie - copieAttuali;
    			for (int i = 0; i < differenza; i++) {
    				try (PreparedStatement pstmtInsert = conn.prepareStatement(insertQuery)) {
    					pstmtInsert.setString(1, nuovoTitolo);
    					pstmtInsert.setString(2, nuovoAutore);
    					pstmtInsert.setString(3, nuovoGenere);
    					pstmtInsert.setInt(4, nuoveCopie);
    					pstmtInsert.executeUpdate();

    				}
    				logger.info("Creati: " + differenza + " nuovi libri.");
    			}
    		}

            if (nuoveCopie < copieAttuali) {
            	int differenza = copieAttuali - nuoveCopie;


            	for (int i=0; i < differenza;i++) {
            		int idToDelete = libroIds.get(i);
            		PreparedStatement deleteStmt = conn.prepareStatement(deleteQuery);
            		deleteStmt.setInt(1, idToDelete);
            		int righeEliminate= deleteStmt.executeUpdate();
            		logger.info("Libro con id " + idToDelete + " eliminato.");
            		              
            	}
            }

        } catch (SQLException e) {
            logger.error("Errore durante l'aggiornamento dei libri.", e);
        }
    }

    public static int getCopie(String autore, String titolo) {
        int numeroCopie = 0;
        
        String query = "SELECT DISTINCT copie FROM libri WHERE autore = ? AND titolo = ?";
        
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setString(1, autore);
            pstmt.setString(2, titolo);
            
            ResultSet rs = pstmt.executeQuery();
          
            numeroCopie = rs.getInt("copie");
                      
        } catch (SQLException e) {
            logger.error("Errore durante il recupero del numero di copie.", e);
        }
        
        return numeroCopie;
    }

    public static Libro getLibroId(int id) {
        String sql = "SELECT * FROM libri WHERE id = ?";
        Libro libro = null;

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    String titolo = rs.getString("titolo");
                    String autore = rs.getString("autore");
                    String genere = rs.getString("genere");
                    String statoString = rs.getString("stato");
                    Stato stato = Stato.valueOf(statoString);  
                    int copie = rs.getInt("copie");
                    LocalDate inizioPrestito = rs.getDate("inizio_prestito") != null ? rs.getDate("inizio_prestito").toLocalDate() : null;
                    LocalDate finePrestito = rs.getDate("fine_prestito") != null ? rs.getDate("fine_prestito").toLocalDate() : null;
                    libro = LibroFactory.createLibro(id, titolo, autore, genere, stato, copie, inizioPrestito, finePrestito);
                }
            }
        } catch (SQLException e) {
            logger.error("Errore durante il recupero del libro con id {}", id, e);
        }
        return libro;
    }
    public static void cambiaStatoInDisponibile(int libroId) {
        String sqlSelect = "SELECT titolo, autore, genere, stato FROM libri WHERE id = ?";
        String sqlUpdateStatoLibro = "UPDATE libri SET stato = 'DISPONIBILE' WHERE id = ?";
        String sqlUpdateStatoAltriLibri = "UPDATE libri SET stato = 'DISPONIBILE',inizio_prestito = '', fine_prestito = ''  WHERE titolo = ? AND autore = ? AND genere = ? AND stato = 'NON_DISPONIBILE'";
        String sqlUpdateCopieLibro = "UPDATE libri SET copie = copie + 1 WHERE id = ?";
        String sqlUpdateCopieAltriLibri = "UPDATE libri SET copie = copie + 1 WHERE titolo = ? AND autore = ? AND genere = ? AND stato = 'NON_DISPONIBILE'";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmtSelect = conn.prepareStatement(sqlSelect);
             PreparedStatement pstmtUpdateStatoLibro = conn.prepareStatement(sqlUpdateStatoLibro);
             PreparedStatement pstmtUpdateStatoAltriLibri = conn.prepareStatement(sqlUpdateStatoAltriLibri);
             PreparedStatement pstmtUpdateCopieLibro = conn.prepareStatement(sqlUpdateCopieLibro);
             PreparedStatement pstmtUpdateCopieAltriLibri = conn.prepareStatement(sqlUpdateCopieAltriLibri)) {

            pstmtSelect.setInt(1, libroId);
            try (ResultSet rs = pstmtSelect.executeQuery()) {
                if (rs.next()) {
                    String titolo = rs.getString("titolo");
                    String autore = rs.getString("autore");
                    String genere = rs.getString("genere");
                    String stato = rs.getString("stato");

                    if ("DISPONIBILE".equals(stato)) {
                        logger.info("Il libro con ID {} è già DISPONIBILE. Nessuna modifica effettuata.", libroId);
                        return;
                    }

                    pstmtUpdateStatoLibro.setInt(1, libroId);
                    pstmtUpdateStatoLibro.executeUpdate();

                    pstmtUpdateCopieLibro.setInt(1, libroId);
                    pstmtUpdateCopieLibro.executeUpdate();

                    pstmtUpdateStatoAltriLibri.setString(1, titolo);
                    pstmtUpdateStatoAltriLibri.setString(2, autore);
                    pstmtUpdateStatoAltriLibri.setString(3, genere);
                    pstmtUpdateStatoAltriLibri.executeUpdate();

                    pstmtUpdateCopieAltriLibri.setString(1, titolo);
                    pstmtUpdateCopieAltriLibri.setString(2, autore);
                    pstmtUpdateCopieAltriLibri.setString(3, genere);
                    pstmtUpdateCopieAltriLibri.executeUpdate();

                    logger.info("Il libro con ID {} è stato aggiornato a DISPONIBILE e le copie sono aumentate. Gli altri libri con lo stesso titolo, autore e genere, se NON_DISPONIBILE, sono stati aggiornati.", libroId);
                } else {
                    logger.warn("Nessun libro trovato con l'ID {}.", libroId);
                }
            }
        } catch (SQLException e) {
            logger.error("Errore durante l'aggiornamento dello stato del libro con ID {}.", libroId, e);
        }
    }
    
    public static void cambiaStatoInRitirato(int libroId) {
        String sqlSelect = "SELECT stato FROM libri WHERE id = ?";
        String sqlUpdate = "UPDATE libri SET stato = 'RITIRATO' , inizio_prestito = ?,fine_prestito = ?  WHERE id = ? AND stato = 'PRENOTATO'";
        LocalDate inizioPrestito = LocalDate.now();
        LocalDate finePrestito = inizioPrestito.plusDays(30);
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmtSelect = conn.prepareStatement(sqlSelect);
             PreparedStatement pstmtUpdate = conn.prepareStatement(sqlUpdate)) {

            pstmtSelect.setInt(1, libroId);
            try (ResultSet rs = pstmtSelect.executeQuery()) {
                if (rs.next()) {
                    String stato = rs.getString("stato");

                    if (!"PRENOTATO".equals(stato)) {
                        logger.info("Il libro con ID {} non è in stato PRENOTATO. Nessuna modifica effettuata.", libroId);
                        return;
                    }
                    pstmtUpdate.setDate(1, java.sql.Date.valueOf(inizioPrestito));
                    pstmtUpdate.setDate(2, java.sql.Date.valueOf(finePrestito));
                    pstmtUpdate.setInt(3, libroId);  
                    
                    int rowsUpdated = pstmtUpdate.executeUpdate();

                    if (rowsUpdated > 0) {
                        logger.info("Il libro con ID {} è stato aggiornato a RITIRATO.", libroId);
                    } else {
                        logger.warn("Impossibile aggiornare il libro con ID {} a RITIRATO. Verifica lo stato.", libroId);
                    }
                } else {
                    logger.warn("Nessun libro trovato con l'ID {}.", libroId);
                }
            }
        } catch (SQLException e) {
            logger.error("Errore durante l'aggiornamento del libro con ID {} a RITIRATO.", libroId, e);
        }
    }




}
