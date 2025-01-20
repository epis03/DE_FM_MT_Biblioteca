package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.PlainDocument;

import main.GestioneEmail;

public class InserimentoCodice extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField inserisciCodice;
	private String email;
	private JLabel accesso;
	private JLabel lblNewLabel_1;
	private JButton cancelButton;
	private JButton nuovoCodice;
	private JButton okButton;
	private Document doc;


	/**
	 * Create the dialog.
	 */
	public InserimentoCodice(String email, String messaggio, String titolo) {
		this.email=email;
		
		setAlwaysOnTop(true);
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		inserisciCodice = new JTextField();
		inserisciCodice.setFont(new Font("Tahoma", Font.PLAIN, 17));
		doc = inserisciCodice.getDocument();
	    addText(inserisciCodice);
		inserisciCodice.setBounds(143, 121, 125, 36);
		contentPanel.add(inserisciCodice);
		inserisciCodice.setColumns(10);

		accesso = new JLabel("");
		accesso.setForeground(new Color(255, 0, 0));
		accesso.setFont(new Font("Nirmala UI", Font.BOLD, 14));
		accesso.setBounds(10, 167, 385, 36);
		contentPanel.add(accesso);

		JLabel lblNewLabel_1 = new JLabel(titolo);
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 30));
		lblNewLabel_1.setBounds(84, 0, 261, 36);
		contentPanel.add(lblNewLabel_1);

		JTextArea sottotilo = new JTextArea();
		sottotilo.setBackground(new Color(240, 240, 240));
		sottotilo.setWrapStyleWord(true);
		sottotilo.setFont(new Font("Verdana Pro", Font.PLAIN, 12));
		sottotilo.setText("Inserisci il codice inviato a " + this.email + messaggio + "\r\nSe non hai ricevuto il codice, clicca \"Nuovo codice\" per riceverne un altro. ");
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
				cancelButton = new JButton("Cancel");
				cancelButton.setMinimumSize(new Dimension(70, 30));
				cancelButton.setMaximumSize(new Dimension(70, 30));
				cancelButton.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 11));
				cancelButton.setBackground(new Color(240, 240, 240));
				cancelButton.setForeground(new Color(255, 0, 0));
				cancelButton.setPreferredSize(new Dimension(77, 30));
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						InserimentoCodice.this.dispose();
					}
				});

				nuovoCodice = new JButton("Nuovo codice");
				nuovoCodice.setMinimumSize(new Dimension(70, 30));
				nuovoCodice.setMaximumSize(new Dimension(170, 30));
				nuovoCodice.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 11));
				nuovoCodice.setBackground(new Color(240, 240, 240));
				nuovoCodice.setForeground(new Color(0, 0, 0));
				nuovoCodice.setPreferredSize(new Dimension(110, 30));
				buttonPane.add(nuovoCodice);

				Component horizontalStrut = Box.createHorizontalStrut(145);
				buttonPane.add(horizontalStrut);
				buttonPane.add(cancelButton);

			}
			{
				okButton = new JButton("Conferma");									
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

	public JButton getNuovoCodice() {
		return nuovoCodice;
	}



	public JButton getOkButton() {
		return okButton;
	}
	
	public JLabel getAccesso() {
		return accesso;
	}
	
	private void addText(JTextField textField) {
		String defaultText = "Inserisci codice";
		textField.setText( defaultText);
		textField.setForeground(Color.GRAY);

		textField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if (textField.getText().equals( defaultText)) {
					textField.setText("");
					textField.setForeground(Color.BLACK);
					inserisciCodice.setDocument(new JTextFieldsetLimite()); 
				}
			}

			@Override
			public void focusLost(FocusEvent e) {
				if (textField.getText().isEmpty()) {          
					textField.setText(defaultText);
					textField.setForeground(Color.GRAY);
					textField.setDocument(doc);
					}
				}
		});
	}
	
	public boolean Verificacodice(String codice) {
		if(inserisciCodice.getText().equals(codice)) {
			
			return true;
		}
		else{
			accesso.setText("Il codice inserito non è corretto!");
			return false;
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
