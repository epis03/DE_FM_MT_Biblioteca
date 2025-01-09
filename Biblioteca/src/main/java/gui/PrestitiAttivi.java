package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
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
	}
	}


