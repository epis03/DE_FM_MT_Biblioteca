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
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;

public class VerificaEmail extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField inserisciEmail;
	private String email;
	private  String codice;
	private JLabel accesso;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			VerificaEmail dialog = new VerificaEmail();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public VerificaEmail() {
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		inserisciEmail = new JTextField();
		inserisciEmail.setFont(new Font("Tahoma", Font.PLAIN, 15));
		inserisciEmail.setDocument(new JTextFieldsetLimite()); 
		inserisciEmail.setBounds(148, 107, 92, 25);
		contentPanel.add(inserisciEmail);
		inserisciEmail.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Inserisci codice");
		lblNewLabel.setBounds(10, 112, 102, 13);
		contentPanel.add(lblNewLabel);
		
		    accesso = new JLabel("");
		    accesso.setForeground(new Color(255, 0, 0));
		    accesso.setFont(new Font("Nirmala UI", Font.BOLD, 14));
			accesso.setBounds(31, 225, 385, 36);
			contentPanel.add(accesso);
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
					}
				});
				buttonPane.add(cancelButton);
			}
			{
				JButton okButton = new JButton("Conferma");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if (inserisciEmail.getText().equals(codice)) {
							accesso.setText("Utente " + email + " registrato correttamente");
							Timer timer = new Timer(5000, new ActionListener() {
								public void actionPerformed(ActionEvent e) {
							VerificaEmail.this.dispose();
								}});
							timer.setRepeats(false); 
					        timer.start();
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

	public  void verfica(boolean amministratore, String codice, String email) {
		VerificaEmail.this.setVisible(true);
		this.codice = codice;
		this.email = email; 
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
