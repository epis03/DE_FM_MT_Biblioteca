package main;

import java.time.LocalDate;

public class Libro {
    private int id;
    private String titolo;
	private String autore;
    private String genere;
    private Stato stato;
    private int copie;
    private  LocalDate inizioPrestito;
    private  LocalDate finePrestito ;

    public Libro(int id, String titolo, String autore, String genere, Stato stato, int copie,LocalDate inizioPrestito, LocalDate finePrestito) {
        this.id = id;
        this.titolo = titolo;
        this.autore = autore;
        this.genere = genere;
        this.stato = stato;
        this.copie = copie;
        this.inizioPrestito = inizioPrestito;
        this.finePrestito = finePrestito;
    }
    
    public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitolo() {
		return titolo;
	}

	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}

	public String getAutore() {
		return autore;
	}

	public void setAutore(String autore) {
		this.autore = autore;
	}

	public String getGenere() {
		return genere;
	}

	public void setGenere(String genere) {
		this.genere = genere;
	}

	public Stato getStato() {
		return stato;
	}

	public void setStato(Stato stato) {
		this.stato = stato;
	}

	public int getCopie() {
		return copie;
	}

	public void setCopie(int copie) {
		this.copie = copie;
	}

	public LocalDate getInizioPrestito() {
		return inizioPrestito;
	}

	public void setInizioPrestito(LocalDate inizioPrestito) {
		this.inizioPrestito = inizioPrestito;
	}

	public LocalDate getFinePrestito() {
		return finePrestito;
	}

	public void setFinePrestito(LocalDate finePrestito) {
		this.finePrestito = finePrestito;
	}


}
