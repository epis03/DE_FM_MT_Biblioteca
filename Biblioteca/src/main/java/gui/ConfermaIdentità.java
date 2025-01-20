package gui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.Timer;

import main.GestioneEmail;
import main.GestioneUtenti;

public class ConfermaIdentità extends InserimentoCodice {

	private static final long serialVersionUID = 1L;
	private static String titolo = "IDENTIFICAZIONE";
	private static String messaggio = " per confermare la tua identità.";
	private static String codice;
	


	/**
	 * Create the dialog.
	 */
	public ConfermaIdentità(String email) {
		super(email,messaggio,titolo);
		codice = GestioneEmail.identificaUtente(email);



		super.getOkButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(Verificacodice(codice)) {
					CambiaPassword cambia = new CambiaPassword(email);
					ConfermaIdentità.this.dispose();
					JOptionPane.showMessageDialog(null, "Identificazione riuscita, per l'utente " + email +  "\rClicca su ok per procedere al cambio della Password");
				}}
		});






		super.getNuovoCodice().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ConfermaIdentità.this.codice =GestioneEmail.identificaUtente(email);
				JOptionPane.showMessageDialog(null, "Ti abbiamo inviato un nuovo codice a: " + email);
			}
		});
	}


}

