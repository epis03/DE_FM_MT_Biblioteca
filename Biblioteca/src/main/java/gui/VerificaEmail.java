package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;


import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

import main.GestioneEmail;
import main.GestioneUtenti;

import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import java.awt.Component;
import javax.swing.Box;

public class VerificaEmail extends InserimentoCodice {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private  String codice;
	private  static String messaggio = "\r\nSe l'email non è correta, clicca su "
			                         + "cancella per tornare alla schermata di registraizione.";
	private static String titolo = "VERIFICA EMAIL";
	private static String conferma = " registrato correttamente";
	/**
	 * Create the dialog.
	 */
	public VerificaEmail(boolean amministratore,String email,char[] password) {
		super(email,messaggio,titolo);
		codice = GestioneEmail.verificaEmail(email);

		super.getOkButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(Verificacodice(codice)) {
					GestioneUtenti gestione = new GestioneUtenti();
					if(gestione.registraUtente(email, password)) {
					Arrays.fill(password, '\0');
					JOptionPane.showMessageDialog(null, "Utente:" + email +  "registrato correttamente");
					Home.chiudiRegistrazione();
					VerificaEmail.this.dispose();
					}
					else {
						JOptionPane.showMessageDialog(null,"Registrazione non riuscita");
					}
				}}
		});

		super.getNuovoCodice().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				codice =GestioneEmail.verificaEmail(email);				
			}
		});

			
	}
}

