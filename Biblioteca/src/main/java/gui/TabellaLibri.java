package gui;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;

import javax.swing.JTable;
import javax.swing.SwingConstants;


public class TabellaLibri extends TabellaLibriBase{

	private static final long serialVersionUID = 1L;
	private static final int row = 4;
	private static final boolean[]  columnEditables = new boolean[] {
			false, false, false, false, true
	}; 
	private static JTable table;
	private static final String azione = "Prenota";



	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TabellaLibri frame = new TabellaLibri();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public TabellaLibri() {

		super(columnEditables,row,azione,getActionListener());
		JButton info = new JButton("Prestiti Attivi");
		info.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// PrestitiAttivi prestiti = new PrestitiAttivi();
				// prestiti.setVisible(true);
			}
		});
		info.setBackground(new Color(192, 192, 192));
		JMenuBar menuBar =  super.getMenu();
		menuBar.add(info);
	}

	public static ActionListener getActionListener() {
		ActionListener action = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedRow = table.getSelectedRow();
				if (selectedRow != -1) {
					Object[] options = {"SI", "NO"};
					int choice = javax.swing.JOptionPane.showOptionDialog(
							null,
							"Vuoi prenotare questo libro:",
							"Prenotazione",
							javax.swing.JOptionPane.DEFAULT_OPTION,
							javax.swing.JOptionPane.PLAIN_MESSAGE,
							null,
							options,
							options[1]
							);
					if (choice == 0) {
						JOptionPane.showMessageDialog(null, "Libro prenotato con successo");
					} else if (choice == 1) {
						JOptionPane.showMessageDialog(null, "Azione cancellata");
					}
				}
			}};

			return action;
	}
}


