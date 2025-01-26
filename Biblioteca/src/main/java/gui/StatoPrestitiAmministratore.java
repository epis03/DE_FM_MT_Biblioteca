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
import main.GestioneLibri;
import main.GestionePrestiti;
import main.Libro;
import main.Stato;

import javax.swing.JScrollPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.List;
import java.awt.event.ActionEvent;

public class StatoPrestitiAmministratore extends StatoPrestiti {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private static JTable table;
	private static String sottotilo= "Libro";

	/**
	 * Create the frame.
	 */
	public StatoPrestitiAmministratore(List<Libro> lista) {
		super(sottotilo,lista.get(0).getTitolo());
		table = super.getTable();
		table.setModel(new DefaultTableModel(
				new Object[][] {
				},
				new String[] {
						"Id", "stato", "scadenza prestito/prenotazione", "Cambia Stato"
				}
				) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			public boolean isCellEditable(int row, int column) {
				boolean[]  columnEditables = new boolean[] {
						false, false, false,true
				}; 
				return columnEditables[column];
			}
		});
		DefaultTableModel model = (DefaultTableModel) table.getModel();		
		for (int i = 0; i < lista.size(); i++) {
			Libro libro = lista.get(i);
			model.addRow(new Object[]{ libro.getId(),libro.getStato(),libro.getFinePrestito() != null ? libro.getFinePrestito() : "", "Cambia Stato"});
		}
		ActionListener actionListener = getActionListener();
		table.getColumnModel().getColumn(4).setCellRenderer(new CustomizedTableRenderer2("Modifica stato"));
		table.getColumnModel().getColumn(4).setCellEditor(new CustomizedCellEditor2(new JButton("Modifica stato"), actionListener));
	}
	public static ActionListener getActionListener() {
		ActionListener action = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DefaultTableModel model = (DefaultTableModel) table.getModel();	
				int riga  = table.getSelectedRow();
				if(model.getValueAt(riga, 1)!=Stato.DISPONIBILE) {
					if (riga != -1) {
						Object[] options = {"Disponibile","Ritirato",};
						int choice = javax.swing.JOptionPane.showOptionDialog(
								null,
								"Seleziona lo stato",
								"Cambia stato",
								javax.swing.JOptionPane.DEFAULT_OPTION,
								javax.swing.JOptionPane.PLAIN_MESSAGE,
								null,
								options,
								options[0]
								);
						if (choice == 0) {
							int id = (int) model.getValueAt( riga, 0);
							model.setValueAt("DISPONIBILE", riga, 1);
							GestioneLibri.cambiaStatoInDisponibile(id);
							GestionePrestiti.eliminaPrenotazione(id);
							JOptionPane.showMessageDialog(null, "Stato aggiornato");
						} 
						if (choice == 1) {									
							int id = (int) model.getValueAt( riga, 0);
							model.setValueAt("RITIRATO", riga, 1);
							GestioneLibri.cambiaStatoInRitirato(id);		
							GestionePrestiti.aggiornaPrestito(id);
							JOptionPane.showMessageDialog(null, "Stato aggiornato");
						}	
					}
				}else {
					JOptionPane.showMessageDialog(null, "Lo stato non può essere cambiato");
				}
			}};
			return action; 
	}}
