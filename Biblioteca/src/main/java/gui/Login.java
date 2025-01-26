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
import java.util.Arrays;
import java.util.Date;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;



public class Login extends JDialog {

	private static final long serialVersionUID = 1L;
	private static Login dialog;
	private final JPanel contentPanel = new JPanel();
	private final JLabel lblNewLabel_2 = new JLabel("Login");
	private JLabel accesso;
	private String[] defaultText = {"Email", "Password"};
	private JPanelPasswordEmail [] infoPanel = new JPanelPasswordEmail[2];
	private boolean passwordfield = false;


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
		setAlwaysOnTop(true);
		setBounds(100, 100, 433, 359);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(new Color(240, 248, 255));
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		for(int i=0;i<2;i++) {
			infoPanel[i] = new JPanelPasswordEmail(passwordfield, defaultText[i]);
			contentPanel.add (infoPanel[i]);
			if(i==0) {
				passwordfield = true;
			}
			infoPanel[i].setFocusable(false);
		} 
		infoPanel[0].setBounds(120, 120, 159, 30);
		infoPanel[1].setBounds(120, 160, 159, 30);

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
		accesso.setBounds(31, 218, 385, 36);
		contentPanel.add(accesso);

		JLabel passwordDimenticata = new JLabel("Password dimenticata?");
		passwordDimenticata.setToolTipText("Clicca qui per cambiare la password");
		passwordDimenticata.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String email = new String(infoPanel[0].getText());
				if(email.isBlank()){
					accesso.setText("Inserire email e poi cliccare 'Password dimenticata?'");
				}
				else {
					GestioneUtenti gestione = new GestioneUtenti();
					if(gestione.emailEsiste(email)) {
						ConfermaIdentità identificazione = new ConfermaIdentità(email);
						identificazione.setVisible(true);
					}
				}}});
		passwordDimenticata.setForeground(new Color(0, 0, 255));
		passwordDimenticata.setBounds(120, 199, 142, 13);
		contentPanel.add(passwordDimenticata);




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
						String email = new String(infoPanel[0].getText());
						char[] password = infoPanel[1].getText();
						if (verificaDati(email, password)) {
							GestioneUtenti gestioneUtenti = new GestioneUtenti();
							if( gestioneUtenti.autenticaUtente(email, password)) {
								Arrays.fill(password, '\0');
								accesso.setForeground(new Color(0, 255, 0));
								accesso.setText("Autenticazione avvenuta con successo");
								Timer timer = new Timer(5000, new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										String ruolo = gestioneUtenti.getUserRole(email);
										if(ruolo.equals("amministratore")){
											GestionePrestiti.controllaPreenotazioni();
											GestionePrestiti.controllaPrestiti();
											TabellaLibriAmministratori table = new TabellaLibriAmministratori();
											table.setVisible(true);
										}
										else {
											Boolean scaduto = GestioneUtenti.verificaPrestitoScaduto(email);
											TabellaLibri table= new TabellaLibri(email,scaduto);
											table.setVisible(true);
										}										
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
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				for(int i=0;i<2;i++) {
					infoPanel[i].setFocusable(true);
				}
			}
		});
	}


	public boolean verificaDati(String email,char[] password) {
		GestioneUtenti gestioneUtenti = new GestioneUtenti();
		boolean condition= true;
		if(password.length==0 && email.isBlank()) {
			accesso.setText("ACCESSO NEGATO: Inserire email e password");
			condition = false;
		}
		if(condition && password.length==0) {
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
