package gui;

import java.awt.EventQueue;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JMenuBar;
import javax.swing.JOptionPane;

import javax.swing.JTable;
import javax.swing.RowSorter;
import javax.swing.SortOrder;

import javax.swing.JButton;

import javax.swing.table.DefaultTableModel;

import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import main.GestioneLibri;
import main.GestioneUtenti;
import main.Libro;

import java.awt.Color;

public class TabellaLibriAmministratori extends TabellaLibriBase {

	private static final long serialVersionUID = 1L;
	private static InserisciLibro insert;	
	private static final int row = 3;
	private static final boolean[]  columnEditables = new boolean[] {
			false, false, false, true, true
	}; 
	private static final String azione = "Modifica";
	private static JTable table;
	private JMenuBar menuBar;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TabellaLibriAmministratori frame = new TabellaLibriAmministratori();
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
	public TabellaLibriAmministratori() {
		super( true,columnEditables,row,azione,getActionListener());

		table = super.getTable();
		menuBar = super.getJMenuBar();

		table.getColumnModel().getColumn(3).setCellRenderer(new CustomizedTableRenderer("Visualizza Stato"));
		table.getColumnModel().getColumn(3).setCellEditor(new CustomizedCellEditor(new JButton("Visualizza Stato"), new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int riga  = table.getSelectedRow();
				String autore = (String) table.getValueAt(riga, 0);
				String titolo = (String) table.getValueAt(riga, 1);
				String genere = (String) table.getValueAt(riga, 2);
				List<Libro> lista = GestioneLibri.filtra(titolo, autore, genere);
				    StatoPrestitiAmministratore state = new StatoPrestitiAmministratore(lista);
					state.setVisible(true);
			}}));
		DefaultTableModel model = (DefaultTableModel) table.getModel();		
		List<Libro> lista = GestioneLibri.getListaLibri(true);
		for (int i = 0; i < lista.size(); i++) {
			Libro libro = lista.get(i);
			model.addRow(new Object[]{ libro.getAutore(),libro.getTitolo(),libro.getGenere(), libro.getStato(), "Prenota"});
		}
		TableRowSorter<TableModel> sorter = new TableRowSorter<>((DefaultTableModel) table.getModel());
		table.setRowSorter(sorter);
		sorter.setSortKeys(java.util.Collections.singletonList(
				new RowSorter.SortKey(0, SortOrder.ASCENDING)
				));
		JButton aggiungiRiga = new JButton("Aggiungi Riga");
		aggiungiRiga.setBackground(new Color(192, 192, 192));
		menuBar.add(aggiungiRiga);
		aggiungiRiga.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				insert = new InserisciLibro();
				insert.setVisible(true);
			}});

	}



	public static void inserisciRiga(String autore, String titolo,String genere, int copie) {
		GestioneLibri gestione = new GestioneLibri();
		gestione.aggiungiLibro(titolo, autore, genere, copie);
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		model.addRow(new Object[]{ autore, titolo,  genere, "Visualizza Stato", "Modifica"});
		TableRowSorter<TableModel> sorter = new TableRowSorter<>(model);
		table.setRowSorter(sorter);
		sorter.setSortKeys(java.util.Collections.singletonList(
				new RowSorter.SortKey(0, SortOrder.ASCENDING)
				));

	}

	public static void eliminaRiga(int riga) {
		String autore = (String) table.getValueAt(riga, 0);
		String titolo = (String) table.getValueAt(riga, 1);
		String genere = (String) table.getValueAt(riga, 2);	
		GestioneLibri.eliminaLibri(titolo,autore,genere);
		((DefaultTableModel) table.getModel()).removeRow(riga);
	}

	public static void modificaRiga(String vecchioTitolo, String vecchioAutore,String vecchioGenere,String autore, String titolo,String genere, int copie,int riga) {
		GestioneLibri.modificaLibri(vecchioTitolo, vecchioAutore, vecchioGenere, titolo, autore,genere, copie);
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		String [] dati =  {autore, titolo, genere};
		for(int i=0;i<3;i++) {
			model.setValueAt(dati[i], riga, i);
		}
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
							eliminaRiga(riga);
							JOptionPane.showMessageDialog(null, "Riga eliminata");
						} else if (scelta == 1) {
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
			model.addRow(new Object[]{ libro.getAutore(),libro.getTitolo(),libro.getGenere(), libro.getStato(), "Prenota"});
		}
		TableRowSorter<TableModel> sorter = new TableRowSorter<>((DefaultTableModel) table.getModel());
		table.setRowSorter(sorter);
		sorter.setSortKeys(java.util.Collections.singletonList(
				new RowSorter.SortKey(0, SortOrder.ASCENDING)
				));
		
	}	
}

   