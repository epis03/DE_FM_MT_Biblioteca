package gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;




public class Login extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField passwordVisibile;
	private JToggleButton  nascondi;
	private JPasswordField passwordField;
	private JTextField codFiscale;
	private final JLabel lblNewLabel_2 = new JLabel("Login");
	private JLabel accesso;
	private boolean mostraPassword = false;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			Login dialog = new Login();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public Login() {
		setBounds(100, 100, 433, 359);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(new Color(240, 248, 255));
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JToggleButton 	mostra = new JToggleButton("Mostra password");
		mostra.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				passwordVisibile.setText((passwordField.getText()));
				passwordField.setVisible(false);
				passwordVisibile.setVisible(true);
				mostra.setVisible(false);
				nascondi.setVisible(true);
				mostraPassword=true;
				}
		});
		mostra.setBounds(277, 164, 115, 21);
		contentPanel.add(mostra);
		
		nascondi = new JToggleButton("Nascondi Password");
	    nascondi.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		passwordField.setText((passwordVisibile.getText()));
	    		passwordVisibile.setVisible(false);
				passwordField.setVisible(true);
				nascondi.setVisible(false);
				mostra.setVisible(true);
				mostraPassword=false;
			}
	    });
		nascondi.setSelected(true);
		nascondi.setVisible(false);
		nascondi.setBounds(277, 164, 115, 21);
		contentPanel.add(nascondi);
		
		passwordVisibile = new JTextField();
		passwordVisibile.setVisible(false);
		passwordVisibile.setBounds(135, 165, 115, 19);
		contentPanel.add(passwordVisibile);
		passwordVisibile.setColumns(10);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(135, 165, 115, 19);
		contentPanel.add(passwordField);
		
		JLabel lblNewLabel = new JLabel("Password");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel.setBounds(41, 166, 87, 13);
		contentPanel.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Codice Fiscale");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel_1.setBounds(31, 100, 163, 56);
		contentPanel.add(lblNewLabel_1);
		
		codFiscale = new JTextField();
		codFiscale.addKeyListener(new KeyAdapter() {
			
			public void keyReleased(KeyEvent e) {
				codFiscale.setText(codFiscale.getText().toUpperCase());
			}
		});
		codFiscale.setBounds(135, 121, 115, 19);
		contentPanel.add(codFiscale);
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 30));
		lblNewLabel_2.setBounds(152, 0, 115, 50);
		contentPanel.add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("Inserisci le tue credenziali");
		lblNewLabel_3.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNewLabel_3.setBounds(95, 60, 225, 30);
		contentPanel.add(lblNewLabel_3);
		
	    accesso = new JLabel("");
	    accesso.setForeground(new Color(255, 0, 0));
	    accesso.setFont(new Font("Nirmala UI", Font.BOLD, 14));
		accesso.setBounds(31, 211, 385, 36);
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
						Home.chiudiLogin();
					}
				});
				buttonPane.add(cancelButton);
			}
			{
				JButton okButton = new JButton("Conferma");
				okButton.setMargin(new Insets(2, 8, 2, 14));
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						String codiceFiscale = new String();
						codiceFiscale= codFiscale.getText();
						String password = new String();
						password = acquisisciPassword();
						verificaDati(codiceFiscale, password);
					}
				});
				okButton.setForeground(new Color(0, 0, 255));
				okButton.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 9));
				okButton.setPreferredSize(new Dimension(77, 30));
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
		}
		
	}
	
	public  String acquisisciPassword() {
		String password = new  String();
		if(mostraPassword) {
			password = passwordVisibile.getText();
		}
		else {
			password = passwordField.getText();
		}
		return password;
	}
	
	public void verificaDati(String codicefiscale,String password) {
		boolean condition= true;
		if(password.isBlank() && codicefiscale.isBlank()) {
			 accesso.setText("ACCESSO NEGATO: Inserire codice fiscale e password");
			 condition = false;
		}
		if(condition && password.isBlank()) {
			accesso.setText("ACCESSO NEGATO: Inserire password");
			condition=false;
		}
		if(condition && codicefiscale.isBlank()){
			accesso.setText("ACCESSO NEGATO: Inserire codice fiscale");
			 condition = false;
		}
		if(condition && codicefiscale.length() != 16) {
			accesso.setText("ACCESSO NEGATO: Codice fiscale errato");
		}
	}
}
