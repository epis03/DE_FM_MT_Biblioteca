package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;


public class InserisciLibro extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField titoloTextField;
	private JTextField autoreTextField;
	private JTextField genereTextField;
	private JTextField copieTextField;
	private JTextArea avviso;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			InserisciLibro dialog = new InserisciLibro();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public InserisciLibro() {
		setResizable(false);
		setAlwaysOnTop(true);
		setBounds(100, 100, 489, 358);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		JLabel date = new JLabel("");
		date.setBounds(10, 0, 406, 22);
		contentPanel.add(date);

		JLabel titoloJDialog = new JLabel("Inserisci un nuovo libro");
		titoloJDialog.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 20));
		titoloJDialog.setBounds(90, 9, 292, 63);
		contentPanel.add(titoloJDialog);
		{
			JLabel titolo = new JLabel("Titolo");
			titolo.setFont(new Font("Tahoma", Font.PLAIN, 15));
			titolo.setBounds(90, 82, 45, 13);
			contentPanel.add(titolo);
		}
		{
			JLabel autore = new JLabel("Autore");
			autore.setFont(new Font("Tahoma", Font.PLAIN, 15));
			autore.setBounds(90, 119, 45, 13);
			contentPanel.add(autore);
		}
		{
			JLabel genere = new JLabel("Genere");
			genere.setFont(new Font("Tahoma", Font.PLAIN, 15));
			genere.setBounds(90, 158, 66, 13);
			contentPanel.add(genere);
		}
		{
			JLabel copie = new JLabel("Numero di copie");
			copie.setFont(new Font("Tahoma", Font.PLAIN, 15));
			copie.setBounds(90, 190, 110, 17);
			contentPanel.add(copie);
		}

		titoloTextField = new JTextField();
		titoloTextField.setBounds(197, 156, 150, 22);
		contentPanel.add(titoloTextField);
		titoloTextField.setColumns(10);

		autoreTextField = new JTextField();
		autoreTextField.setBounds(197, 117, 150, 22);
		contentPanel.add(autoreTextField);
		autoreTextField.setColumns(10);

		genereTextField = new JTextField();
		genereTextField.setBounds(197, 77, 153, 22);
		contentPanel.add(genereTextField);
		genereTextField.setColumns(10);

		copieTextField = new JTextField();
		copieTextField.setBounds(257, 190, 30, 22);
		contentPanel.add(copieTextField);
		copieTextField.setColumns(10);
		{
			avviso = new JTextArea("");
			avviso.setBackground(new Color(240, 240, 240));
			avviso.setEditable(false);
			avviso.setWrapStyleWord(true);
			avviso.setLineWrap(true);
			avviso.setForeground(new Color(255, 0, 0));
			avviso.setFont(new Font("Nirmala UI", Font.BOLD, 14));
			avviso.setBounds(5, 226, 450, 41);
			contentPanel.add(avviso);
		}


		Timer timer = new Timer(1000, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String currentTime = new SimpleDateFormat("EEEE, dd MMMM yyyy HH:mm:ss").format(new Date());
				date.setText(currentTime);
			}
		});
		timer.start();

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
						InserisciLibro.this.dispose();
					}
				});
				buttonPane.add(cancelButton);
			}
			{
				JButton okButton = new JButton("Conferma");
				okButton.setMargin(new Insets(2, 8, 2, 14));
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if(verificadati()) {
							TabellaLibriAmministratori.inserisciRiga(autoreTextField.getText().trim(), titoloTextField.getText().trim().replaceAll( "  ", " "), genereTextField.getText().trim(),Integer.parseInt( copieTextField.getText()));
							JOptionPane.showMessageDialog(null, "Libro inserito correttamente");
							InserisciLibro.this.dispose();
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
	public boolean verificadati() {
		boolean condition = true;
		if ( !copieTextField.getText().matches("\\d+")) {
			avviso.setText("ERRORE: Il numero di copie deve essere espresso tramite un numero intero");
			condition = false;
		}
		return condition;
	}

}
