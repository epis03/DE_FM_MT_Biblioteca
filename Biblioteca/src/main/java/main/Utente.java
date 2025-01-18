package main;

public class Utente {
    private String email;
    private String password;
    private String ruolo; 

    public Utente(String email, String password, String ruolo) {
        this.email = email;
        this.password = password;
        this.ruolo = ruolo;
    }

    public String getEmail() {
        return email;
    }

    public String getRuolo() {
        return ruolo;
    }

    public boolean verificaPassword(String password) {
        return this.password.equals(password);
    }
}