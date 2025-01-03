package main;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;



 
public class GestioneUtenti {
    private static final Logger logger = LogManager.getLogger(GestioneUtenti.class);

    private Map<String, Utente> utenti; // Mappa username -> Utente

    public GestioneUtenti() {
        utenti = new HashMap<>();
    }

    
    public boolean registraUtente(String username, String password, String ruolo) {
        if (utenti.containsKey(username)) {
            logger.warn("Tentativo di registrazione fallito: L'utente '{}' esiste già.", username);
            return false;
        }
        utenti.put(username, new Utente(username, password, ruolo));
        logger.info("Utente '{}' registrato con successo come '{}'.", username, ruolo);
        return true;
    }

    
    public boolean autenticaUtente(String username, String password) {
        Utente utente = utenti.get(username);
        if (utente != null && utente.verificaPassword(password)) {
            logger.info("Autenticazione riuscita per l'utente '{}'.", username);
            return true;
        } else {
            logger.error("Autenticazione fallita per l'utente '{}'.", username);
            return false;
        }
    }

    
    public void listaUtenti() {
        logger.info("Elenco degli utenti registrati:");
        for (Utente utente : utenti.values()) {
            logger.info("- {} (Ruolo: {})", utente.getUsername(), utente.getRuolo());
        }
    }
}