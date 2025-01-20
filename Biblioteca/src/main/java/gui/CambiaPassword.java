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
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;

import main.GestioneUtenti;

public class CambiaPassword extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private final JLabel lblNewLabel_2 = new JLabel("Cambia Password");
	private JLabel accesso;
	private String[] defaultText = { "Nuova Password","Conferma Password"};
	private JPanelPasswordEmail [] infoPanel = new JPanelPasswordEmail[2];
	private boolean passwordfield = true;

	

	/**
	 * Create the dialog.
	 */
	public CambiaPassword(String email) {

		setAlwaysOnTop(true);
		setResizable(false);
		setBounds(100, 100, 471, 360);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(new Color(240, 248, 255));
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		for(int i=0;i<2;i++) {
			infoPanel[i] = new JPanelPasswordEmail(passwordfield, defaultText[i]);
			contentPanel.add (infoPanel[i]);
		} 
		infoPanel[0].setBounds(140, 130, 159, 30);
		infoPanel[1].setBounds(140, 170, 159, 30);
		
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 30));
		lblNewLabel_2.setBounds(109, 10, 219, 51);
		contentPanel.add(lblNewLabel_2);

		JLabel lblNewLabel_5 = new JLabel("Inserisci una nuova password");
		lblNewLabel_5.setBounds(121, 52, 191, 36);
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
						CambiaPassword.this.dispose();					}
				});
				buttonPane.add(cancelButton);
			}
			{
				JButton okButton = new JButton("Conferma");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						String password = infoPanel[0].getText();
						String passwordConferma = infoPanel[1].getText();
						if (verificaDati( password, passwordConferma)) {
							//metodo per cambiare password
							JOptionPane.showMessageDialog(null, "Password cambiata corettamente,clicca ok oer tornare alla schermata di Login");
							CambiaPassword.this.dispose();
							}
							else{
								accesso.setText("Tentativo di cambio password fallito" ); 
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
				for(int i=0;i<2;i++) {
					infoPanel[i].setFocusable(true);
				}
			}
		});
	}

	public boolean verificaDati(String password, String conferma) {
		boolean condition=true;

		
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




	