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
	private JLabel accesso;
	private String[] defaultText = {"Email", "Password","Conferma Password"};
	private JPanelPasswordEmail [] infoPanel = new JPanelPasswordEmail[3];
	private boolean passwordfield = false;

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

		for(int i=0;i<3;i++) {
			infoPanel[i] = new JPanelPasswordEmail(passwordfield, defaultText[i]);
			contentPanel.add (infoPanel[i]);
			if(i==0) {
				passwordfield = true;
			}
			infoPanel[i].setFocusable(false);
		} 
		infoPanel[0].setBounds(162, 100, 159, 30);
		infoPanel[1].setBounds(162, 140, 159, 30);
		infoPanel[2].setBounds(162, 180, 159, 30);

		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 30));
		lblNewLabel_2.setBounds(100, 10, 239, 51);
		contentPanel.add(lblNewLabel_2);

		JLabel lblNewLabel_5 = new JLabel("Inserisci email e password");
		lblNewLabel_5.setBounds(110, 50, 252, 36);
		lblNewLabel_5.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 14));
		contentPanel.add(lblNewLabel_5);

		accesso = new JLabel("");
		accesso.setBounds(31, 228, 385, 36);
		accesso.setForeground(new Color(255, 0, 0));
		accesso.setFont(new Font("Nirmala UI", Font.BOLD, 14));
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
						String email = infoPanel[0].getText();
						String password = infoPanel[1].getText();
						String passwordConferma = infoPanel[2].getText();
						if (verificaDati(email, password, passwordConferma)) {
							GestioneUtenti gestioneUtenti = new GestioneUtenti();
							if( gestioneUtenti.emailEsiste(email)) {
								String	codice = GestioneEmail.verificaEmail(email);
								VerificaEmail verifica= new VerificaEmail(true,codice, email,password);
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

		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				for(int i=0;i<3;i++) {
					infoPanel[i].setFocusable(true);
				}
			}
		});
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




	