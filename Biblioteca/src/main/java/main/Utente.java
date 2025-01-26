package main;

public class Utente {
    private String email;
    private String password;
    private String ruolo; 
    private boolean prestitoScaduto;

    public Utente(String email, String password, String ruolo) {
        this.email = email;
        this.password = password;
        this.ruolo = ruolo;
        this.prestitoScaduto=false;
    }

    

    public String getEmail() {
		return email;
	}



	public void setEmail(String email) {
		this.email = email;
	}



	public String getPassword() {
		return password;
	}



	public void setPassword(String password) {
		this.password = password;
	}



	public String getRuolo() {
		return ruolo;
	}



	public void setRuolo(String ruolo) {
		this.ruolo = ruolo;
	}



	public boolean isPrestitoScaduto() {
		return prestitoScaduto;
	}



	public void setPrestitoScaduto(boolean prestitoScaduto) {
		this.prestitoScaduto = prestitoScaduto;
	}



	public boolean verificaPassword(String password) {
        return this.password.equals(password);
    }
}