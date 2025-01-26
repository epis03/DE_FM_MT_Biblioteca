package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import main.GestioneLibri;
import main.GestionePrestiti;
import main.Libro;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class PrestitiAttivi extends StatoPrestiti {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table;
	private static String sottotitolo = "Utente";

	/**
	 * Create the frame.
	 */
	public PrestitiAttivi(String email) {
		super(sottotitolo,email);
		table = super.getTable();
		table.setModel(new DefaultTableModel(
				new Object[][] {
				},
				new String[] {
						"Autore", "Titolo", "Genere", "scadenza prestito"
				}
				) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			public boolean isCellEditable(int row, int column) {
				boolean[]  columnEditables = new boolean[] {
						false, false, false, false
				}; 
				return columnEditables[column];
			}
		});
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		List <Integer> id = GestionePrestiti.getLibriPrenotati(email);
		List <Libro> lista = new ArrayList<Libro>();
		for(int i=0;i<id.size();i++) {
			lista.add(GestioneLibri.getLibroId(id.get(i)));
		}
		
		for (int i = 0; i < lista.size(); i++) {
			Libro libro = lista.get(i);
			model.addRow(new Object[]{ libro.getAutore(),libro.getTitolo(),libro.getGenere(), libro.getFinePrestito()});
		}
		TableRowSorter<TableModel> sorter = new TableRowSorter<>((DefaultTableModel) table.getModel());
		table.setRowSorter(sorter);
		sorter.setSortKeys(java.util.Collections.singletonList(
				new RowSorter.SortKey(0, SortOrder.ASCENDING)
				));
	}
}


