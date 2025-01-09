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

import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import java.awt.Component;
import javax.swing.Box;

public class VerificaEmail extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField inserisciCodice;
	private String email;
	private  String codice;
	private JLabel accesso;
	

	/**
	 * Create the dialog.
	 */
	public VerificaEmail(boolean amministratore, String codice, String email) {
		this.codice = codice;
		this.email=email;
		setAlwaysOnTop(true);
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		inserisciCodice = new JTextField();
		inserisciCodice.setFont(new Font("Tahoma", Font.PLAIN, 17));
		inserisciCodice.setDocument(new JTextFieldsetLimite()); 
		inserisciCodice.setBounds(134, 131, 114, 36);
		contentPanel.add(inserisciCodice);
		inserisciCodice.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Inserisci codice");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel.setBounds(10, 134, 114, 31);
		contentPanel.add(lblNewLabel);
		
		accesso = new JLabel("");
	    accesso.setForeground(new Color(255, 0, 0));
	    accesso.setFont(new Font("Nirmala UI", Font.BOLD, 14));
		accesso.setBounds(10, 167, 385, 36);
		contentPanel.add(accesso);
			
			JLabel lblNewLabel_1 = new JLabel("VERIFICA EMAIL");
			lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 30));
			lblNewLabel_1.setBounds(84, 0, 261, 36);
			contentPanel.add(lblNewLabel_1);
			
			JTextArea sottotilo = new JTextArea();
			sottotilo.setBackground(new Color(240, 240, 240));
			sottotilo.setWrapStyleWord(true);
			sottotilo.setFont(new Font("Verdana Pro", Font.PLAIN, 12));
			sottotilo.setText("Inserisci il codice inviato a: null \r\nSe l'email non è correta, clicca su cancella per tornare alla schermata di registraizione. \r\nSe non hai ricevuto il codice, clicca \"Nuovo codice\" per riceverne un altro. ");
			sottotilo.setEditable(false);
			sottotilo.setLineWrap(true);
			sottotilo.setSelectedTextColor(new Color(0, 0, 0));
			sottotilo.setToolTipText("");
			sottotilo.setRows(2);
			sottotilo.setBounds(10, 43, 416, 82);
			contentPanel.add(sottotilo);
		{
			JPanel buttonPane = new JPanel();
			FlowLayout fl_buttonPane = new FlowLayout(FlowLayout.RIGHT);
			fl_buttonPane.setVgap(10);
			buttonPane.setLayout(fl_buttonPane);
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.setMinimumSize(new Dimension(70, 30));
				cancelButton.setMaximumSize(new Dimension(70, 30));
				cancelButton.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 11));
				cancelButton.setBackground(new Color(240, 240, 240));
				cancelButton.setForeground(new Color(255, 0, 0));
				cancelButton.setPreferredSize(new Dimension(77, 30));
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						VerificaEmail.this.dispose();
						Home.chiudiRegistrazione();
					}
				});
				
				JButton nuovoCodice = new JButton("Nuovo codice");
				nuovoCodice.setMinimumSize(new Dimension(70, 30));
				nuovoCodice.setMaximumSize(new Dimension(170, 30));
				nuovoCodice.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 11));
				nuovoCodice.setBackground(new Color(240, 240, 240));
				nuovoCodice.setForeground(new Color(0, 0, 0));
				nuovoCodice.setPreferredSize(new Dimension(110, 30));
				buttonPane.add(nuovoCodice);
				nuovoCodice.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						VerificaEmail.this.codice =GestioneEmail.verificaEmail(VerificaEmail.this.email);
						JOptionPane.showMessageDialog(null, "Ti abbiamo inviato un nuovo codice a: " + VerificaEmail.this.email);
					}
				});
				
				Component horizontalStrut = Box.createHorizontalStrut(145);
				buttonPane.add(horizontalStrut);
				buttonPane.add(cancelButton);
				
			}
			{
				JButton okButton = new JButton("Conferma");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if (inserisciCodice.getText().equals(VerificaEmail.this.codice)) {
							accesso.setText("Utente " + VerificaEmail.this.email + " registrato correttamente");
							Timer timer = new Timer(5000, new ActionListener() {
								public void actionPerformed(ActionEvent e) {
							VerificaEmail.this.dispose();
							
								}});
							timer.setRepeats(false); 
					        timer.start();
						}
						else {
							accesso.setText("Il codice inserito non è corretto!");
						}
							}
							
								}
				);
				
				okButton.setMargin(new Insets(2, 8, 2, 14));
				okButton.setForeground(new Color(0, 0, 255));
				okButton.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 9));
				okButton.setPreferredSize(new Dimension(77, 30));
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
		}
	}
}

class JTextFieldsetLimite extends PlainDocument {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int limite =6;

    public JTextFieldsetLimite() {
        super();
        
    }
        @Override
    public void insertString(int offset, String str, AttributeSet attr) throws BadLocationException {
        if (str == null)
            return;
        
        if ((getLength() + str.length()) <= limite) {
            super.insertString(offset, str, attr);
        }
    }}
