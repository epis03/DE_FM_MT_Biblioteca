package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class StatoPrestitiAmministratore extends StatoPrestiti {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table;
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
					"Id", "stato", "utente", "scadenza prestito"
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
	}
}
