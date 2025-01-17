package gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import main.GestioneEmail;
import main.GestioneUtenti;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;




public class Registrazione extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private final JLabel lblNewLabel_2 = new JLabel("Registrazione");
	private JTextField passwordVisibile;
	private JPasswordField passwordField;
	private JToggleButton nascondi;
	private boolean mostraPassword = false;
	private JTextField confermaVisibile;
	private JPasswordField passwordFieldConferma;
	private JToggleButton nascondiConferma;
	private boolean mostraConferma = false;
	private JTextField emailField;
	private JLabel accesso;


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			Registrazione dialog = new Registrazione();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public Registrazione() {
		setResizable(false);
		setBounds(100, 100, 471, 360);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(new Color(240, 248, 255));
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		JToggleButton 	mostra = new JToggleButton("Mostra password");
		mostra.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				passwordVisibile.setText(( new String(passwordField.getPassword())));
				passwordField.setVisible(false);
				passwordVisibile.setVisible(true);
				mostra.setVisible(false);
				nascondi.setVisible(true);
				mostraPassword=true;
			}
		});
		mostra.setBounds(304, 140, 132, 21);
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
		nascondi.setBounds(304, 140, 132, 21);
		contentPanel.add(nascondi);

		passwordVisibile = new JTextField();
		passwordVisibile.setVisible(false);
		passwordVisibile.setBounds(162, 140, 132, 19);
		contentPanel.add(passwordVisibile);
		passwordVisibile.setColumns(10);

		passwordField = new JPasswordField();
		passwordField.setBounds(162, 140, 132, 19);
		contentPanel.add(passwordField);

		JToggleButton 	mostraConfermaPassword = new JToggleButton("Mostra password");
		mostraConfermaPassword.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				confermaVisibile.setText(( new String(passwordFieldConferma.getPassword())));
				passwordFieldConferma.setVisible(false);
				confermaVisibile.setVisible(true);
				mostraConfermaPassword.setVisible(false);
				nascondiConferma.setVisible(true);
				mostraConferma=true;
			}
		});
		mostraConfermaPassword.setBounds(304, 180, 132, 21);
		contentPanel.add(mostraConfermaPassword);

		nascondiConferma = new JToggleButton("Nascondi Password");
		nascondiConferma.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				passwordFieldConferma.setText((confermaVisibile.getText()));
				confermaVisibile.setVisible(false);
				passwordFieldConferma.setVisible(true);
				nascondiConferma.setVisible(false);
				mostraConfermaPassword.setVisible(true);
				mostraConferma=false;
			}
		});
		nascondiConferma.setSelected(true);
		nascondiConferma.setVisible(false);
		nascondiConferma.setBounds(304, 180, 132, 21);
		contentPanel.add(nascondiConferma);

		confermaVisibile = new JTextField();
		confermaVisibile.setVisible(false);
		confermaVisibile.setBounds(162, 180, 132, 19);
		contentPanel.add(confermaVisibile);
		confermaVisibile.setColumns(10);

		passwordFieldConferma = new JPasswordField();
		passwordFieldConferma.setBounds(162, 180, 132, 19);
		contentPanel.add(passwordFieldConferma);


		JLabel lblNewLabel = new JLabel("Password:");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel.setBounds(75, 140, 76, 13);
		contentPanel.add(lblNewLabel);


		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 30));
		lblNewLabel_2.setBounds(100, 10, 239, 51);
		contentPanel.add(lblNewLabel_2);


		JLabel conferma = new JLabel("Conferma password:");
		conferma.setFont(new Font("Tahoma", Font.PLAIN, 15));
		conferma.setBounds(6, 180, 142, 22);
		contentPanel.add(conferma);

		JLabel lblNewLabel_1 = new JLabel("Email");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel_1.setBounds(70, 90, 76, 18);
		contentPanel.add(lblNewLabel_1);



		JLabel lblNewLabel_5 = new JLabel("Inserisci email e password");
		lblNewLabel_5.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 14));
		lblNewLabel_5.setBounds(110, 50, 252, 36);
		contentPanel.add(lblNewLabel_5);

		emailField = new JTextField();
		emailField.setBounds(162, 90, 132, 19);
		contentPanel.add(emailField);
		emailField.setColumns(10);


		accesso = new JLabel("");
		accesso.setForeground(new Color(255, 0, 0));
		accesso.setFont(new Font("Nirmala UI", Font.BOLD, 14));
		accesso.setBounds(31, 225, 385, 36);
		contentPanel.add(accesso);




		{
			JPanel buttonPane = new JPanel();
			FlowLayout fl_buttonPane = new FlowLayout(FlowLayout.LEFT);
			fl_buttonPane.setVgap(10);
			buttonPane.setLayout(fl_buttonPane);
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			JLabel date = new JLabel();
			date.setBounds(10, -3, 406, 22);
			buttonPane.add(date);
			Timer timer = new Timer(1000, new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					String currentTime = new SimpleDateFormat("EEEE, dd MMMM yyyy HH:mm:ss").format(new Date());
					date.setText(currentTime);
				}
			});
			timer.start();
			Component horizontalStrut = Box.createHorizontalStrut(70);
			buttonPane.add(horizontalStrut);
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
						Home.chiudiRegistrazione();
					}
				});
				buttonPane.add(cancelButton);
			}
			{
				JButton okButton = new JButton("Conferma");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						String email = new String();
						email= emailField.getText();
						String password = new String();
						String passwordConferma = new String();
						password = acquisisciPassword(mostraPassword,passwordVisibile,passwordField);
						passwordConferma = acquisisciPassword(mostraConferma, confermaVisibile, passwordFieldConferma);
						if (verificaDati(email, password, passwordConferma)) {
							GestioneUtenti gestioneUtenti = new GestioneUtenti();
							if( gestioneUtenti.registraUtente(email, password, "amministratore")) {
								String	codice = GestioneEmail.verificaEmail(email);
								VerificaEmail verifica= new VerificaEmail(true,codice, email);
								verifica.setVisible(true);
							}
							else{
								accesso.setText("L'utente " + email + " è già registrato" ); 
							}
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

	public  String acquisisciPassword(boolean mostra,JTextField passwordVisibile, JPasswordField passwordNascosta) {
		String password = new  String();
		if(mostra) {
			password = passwordVisibile.getText();
		}
		else {
			password = new String(passwordNascosta.getPassword());
		}
		return password;
	}

	public boolean verificaDati(String email,String password, String conferma) {
		boolean condition=true;
		if(password.isBlank() && email.isBlank()) {
			accesso.setText("Inserire email e password");
			condition = false;
		}
		if(condition && email.isBlank()){
			accesso.setText("Inserire email");
			condition = false;
		}
		if(condition && !email.matches(("^[^@]+@[^@]+\\.[^@]+$"))) {
			accesso.setText("Formato email errato");
			condition = false;
		}
		if(condition && password.isBlank()) {
			accesso.setText("Inserire password");
			condition=false;
		}		
		if(condition && conferma.isBlank()) {
			accesso.setText("Confermare password");
			condition=false;
		}
		if(condition && !password.equals(conferma)) {
			accesso.setText("Le password non corrispondono");
			condition=false;
		}
		return condition;
	}
}




	