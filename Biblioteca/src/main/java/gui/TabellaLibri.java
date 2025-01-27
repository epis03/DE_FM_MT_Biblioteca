package gui;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;

import javax.swing.JTable;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import main.GestioneEmail;
import main.GestioneLibri;
import main.GestionePrestiti;
import main.Libro;
import main.Stato;


public class TabellaLibri extends TabellaLibriBase{

	private static final long serialVersionUID = 1L;
	private static final int row = 4;
	private static final boolean[]  columnEditables = new boolean[] {
			false, false, false, false, true
	}; 
	private static JTable table;
	private static final String azione = "Prenota";
	private static String email;
	private static boolean scaduto;
	
	/**
	 * Create the frame.
	 */
	public TabellaLibri(String email, boolean scaduto) {
		super(false,columnEditables,row,azione,getActionListener());
		this.email=email;
		this.scaduto=scaduto;
		table = super.getTable();
		DefaultTableModel model = (DefaultTableModel) table.getModel();		
		List<Libro> lista = GestioneLibri.getListaLibri(true);
		for (int i = 0; i < lista.size(); i++) {
			Libro libro = lista.get(i);
			Stato stato;
			if(libro.getCopie()==0) {
				stato = Stato.NON_DISPONIBILE;
			}
			else {
				stato = Stato.DISPONIBILE;
			}
			model.addRow(new Object[]{ libro.getAutore(),libro.getTitolo(),libro.getGenere(), stato, "Prenota"});
		}
		TableRowSorter<TableModel> sorter = new TableRowSorter<>((DefaultTableModel) table.getModel());
		table.setRowSorter(sorter);
		sorter.setSortKeys(java.util.Collections.singletonList(
				new RowSorter.SortKey(0, SortOrder.ASCENDING)
				));

		JButton info = new JButton("Prestiti Attivi");
		info.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PrestitiAttivi prestiti = new PrestitiAttivi(email);
				prestiti.setVisible(true);
			}
		});
		info.setBackground(new Color(192, 192, 192));
		JMenuBar menuBar =  super.getMenu();
		menuBar.add(info);
	}

	public static ActionListener getActionListener() {
		ActionListener action = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(scaduto) {
					JOptionPane.showMessageDialog(null, "Hai una prenotazione scaduta, impossibile prenotare altri libri");
				}
				else{
				int riga = table.getSelectedRow();
				if (riga != -1) {
					Object[] options = {"SI", "NO"};
					DefaultTableModel model = (DefaultTableModel) table.getModel();
					int choice = javax.swing.JOptionPane.showOptionDialog(
							null,
							"Vuoi prenotare questo libro: " + (String)model.getValueAt(riga, 0),
							"Prenotazione",
							javax.swing.JOptionPane.DEFAULT_OPTION,
							javax.swing.JOptionPane.PLAIN_MESSAGE,
							null,
							options,
							options[1]
							);
					if (choice == 0) {
						int id=GestioneLibri.prenotaLibro((String)model.getValueAt(riga, 1), (String)model.getValueAt(riga, 0));
						if(model.getValueAt( riga, 3)==Stato.DISPONIBILE && id!=-1) {
						GestionePrestiti.prenotaLibro(email, id);
						GestioneEmail.prenotazione(email, GestioneLibri.getLibroId(id));
						if(GestioneLibri.getLibroId(id).getCopie()==0) {
							model.setValueAt("NON_DISPONIBILE", riga, 3);
						}
						JOptionPane.showMessageDialog(null, "Libro prenotato con successo");
						}
						else{
							JOptionPane.showMessageDialog(null, "Impossibile effettuare la prenotazione, libro esaurito");
						}
					} else if (choice == 1) {
						JOptionPane.showMessageDialog(null, "Azione cancellata");
					}
				}
				}
			}};
		
			return action;
	}
	
	public static void filtraTabella(List<Libro> lista) {
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		model.setRowCount(0);
		for (int i = 0; i < lista.size(); i++) {
			Libro libro = lista.get(i);
			Stato stato;
			if(libro.getCopie()==0) {
				stato = Stato.NON_DISPONIBILE;
			}
			else {
				stato=Stato.DISPONIBILE;
			}
			model.addRow(new Object[]{ libro.getAutore(),libro.getTitolo(),libro.getGenere(), stato, "Prenota"});
		}
		TableRowSorter<TableModel> sorter = new TableRowSorter<>((DefaultTableModel) table.getModel());
		table.setRowSorter(sorter);
		sorter.setSortKeys(java.util.Collections.singletonList(
				new RowSorter.SortKey(0, SortOrder.ASCENDING)
				));
		
	}
}


