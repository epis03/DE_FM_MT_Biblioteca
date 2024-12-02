package main;

public class Utente {
    private String username;
    private String password;
    private String ruolo; // "utente" o "amministratore"

    
    public Utente(String username, String password, String ruolo) {
        this.username = username;
        this.password = password;
        this.ruolo = ruolo;
    }

    
    public String getUsername() {
        return username;
    }

    public String getRuolo() {
        return ruolo;
    }

   
    public boolean verificaPassword(String password) {
        return this.password.equals(password);
    }
}