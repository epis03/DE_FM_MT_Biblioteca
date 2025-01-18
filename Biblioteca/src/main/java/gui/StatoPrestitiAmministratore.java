package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import gui.TabellaLibriBase.CustomizedCellEditor;
import gui.TabellaLibriBase.CustomizedTableRenderer;

import javax.swing.JScrollPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class StatoPrestitiAmministratore extends StatoPrestiti {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private static JTable table;
	private static String sottotilo= "Libro";
	/**
	 * Create the frame.
	 */
	public StatoPrestitiAmministratore(String titolo) {
		super(sottotilo,titolo);
		table = super.getTable();
		table.setModel(new DefaultTableModel(
				new Object[][] {
				},
				new String[] {
						"Id", "stato", "utente", "scadenza prestito/prenotazione","Cambia Stato"
				}
				) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			public boolean isCellEditable(int row, int column) {
				boolean[]  columnEditables = new boolean[] {
						false, false, false, false,false
				}; 
				return columnEditables[column];
			}
		});
		ActionListener actionListener = getActionListener();
		table.getColumnModel().getColumn(4).setCellRenderer(new CustomizedTableRenderer2("Modifica stato"));
		table.getColumnModel().getColumn(4).setCellEditor(new CustomizedCellEditor2(new JButton("Modifica stato"), actionListener));
	}
	public static ActionListener getActionListener() {
		ActionListener action = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int riga  = table.getSelectedRow();
				if (riga != -1) {
					Object[] options = {"Aggiorna Dati", "Elimina"};
					int choice = javax.swing.JOptionPane.showOptionDialog(
							null,
							"Scegli un'azione:",
							"Azioni",
							javax.swing.JOptionPane.DEFAULT_OPTION,
							javax.swing.JOptionPane.PLAIN_MESSAGE,
							null,
							options,
							options[0]
							);
					if (choice == 0) {
						ModificaRiga update = new ModificaRiga(table.getValueAt(riga, 0),table.getValueAt(riga, 1),table.getValueAt(riga, 2), riga);
						update.setVisible(true);
					} else if (choice == 1) {

						Object[] opzioni = {"SI", "NO"};
						int scelta = javax.swing.JOptionPane.showOptionDialog(
								null,
								"Vuoi cancelalre questa riga?",
								"Elimina riga",
								javax.swing.JOptionPane.DEFAULT_OPTION,
								javax.swing.JOptionPane.PLAIN_MESSAGE,
								null,
								opzioni,
								opzioni[1]
								);
						if (scelta== 0) {
							
							JOptionPane.showMessageDialog(null, "Riga eliminata");
						} else if (scelta == 1) {
							JOptionPane.showMessageDialog(null, "Azione cancellata");
						}
					}
				}
			}};
			return action; 
	}}
