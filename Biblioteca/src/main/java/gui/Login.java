package gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import main.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.SimpleDateFormat;
import java.util.Date;



public class Login extends JDialog {

	private static final long serialVersionUID = 1L;
	private static Login dialog;
	private final JPanel contentPanel = new JPanel();
	private JTextField passwordVisibile;
	private JToggleButton  nascondi;
	private JPasswordField passwordField;
	private JTextField emailField;
	private final JLabel lblNewLabel_2 = new JLabel("Login");
	private JLabel accesso;
	private boolean mostraPassword = false;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			dialog = new Login();
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
				passwordVisibile.setText(( new String(passwordField.getPassword())));
				passwordField.setVisible(false);
				passwordVisibile.setVisible(true);
				mostra.setVisible(false);
				nascondi.setVisible(true);
				mostraPassword=true;
			}
		});
		mostra.setBounds(260, 178, 132, 21);
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
		nascondi.setBounds(277, 178, 115, 21);
		contentPanel.add(nascondi);

		passwordVisibile = new JTextField();
		passwordVisibile.setVisible(false);
		passwordVisibile.setBounds(135, 179, 115, 19);
		contentPanel.add(passwordVisibile);
		passwordVisibile.setColumns(10);

		passwordField = new JPasswordField();
		passwordField.setBounds(135, 179, 115, 19);
		contentPanel.add(passwordField);

		JLabel lblNewLabel = new JLabel("Password");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel.setBounds(31, 180, 87, 13);
		contentPanel.add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("Email");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel_1.setBounds(31, 134, 71, 21);
		contentPanel.add(lblNewLabel_1);

		emailField = new JTextField();
		emailField.setBounds(135, 134, 115, 19);
		contentPanel.add(emailField);
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 30));
		lblNewLabel_2.setBounds(147, 10, 115, 50);
		contentPanel.add(lblNewLabel_2);

		JLabel lblNewLabel_3 = new JLabel("Inserisci le tue credenziali");
		lblNewLabel_3.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNewLabel_3.setBounds(95, 70, 225, 30);
		contentPanel.add(lblNewLabel_3);

		accesso = new JLabel("");
		accesso.setForeground(new Color(255, 0, 0));
		accesso.setFont(new Font("Nirmala UI", Font.BOLD, 14));
		accesso.setBounds(31, 214, 385, 36);
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
			Component horizontalStrut = Box.createHorizontalStrut(35);
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
						String email = new String();
						email= emailField.getText();
						String password = new String();
						password = acquisisciPassword();
						if (verificaDati(email, password)) {
							GestioneUtenti gestioneUtenti = new GestioneUtenti();
							if( gestioneUtenti.autenticaUtente(email, password)) {
								accesso.setText("Autenticazione avvenuta con successo");
								Timer timer = new Timer(5000, new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										TabellaLibriAmministratori table = new TabellaLibriAmministratori();
										table.setVisible(true);
										Login.this.dispose();
									}});
								timer.setRepeats(false); 
								timer.start();
							} else {
								accesso.setText("ACCESSO NEGATO: Autenticazione fallita" );
							}
						}
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
			password = new String(passwordField.getPassword());
		}
		return password;
	}

	public boolean verificaDati(String email,String password) {
		GestioneUtenti gestioneUtenti = new GestioneUtenti();
		boolean condition= true;
		if(password.isBlank() && email.isBlank()) {
			accesso.setText("ACCESSO NEGATO: Inserire email e password");
			condition = false;
		}
		if(condition && password.isBlank()) {
			accesso.setText("ACCESSO NEGATO: Inserire password");
			condition=false;
		}
		if(condition && email.isBlank()){
			accesso.setText("ACCESSO NEGATO: Inserire email");
			condition = false;
		}	
		return condition;
	}
}
