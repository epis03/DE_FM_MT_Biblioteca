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

public class Stato extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Stato frame = new Stato();
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
	public Stato() {
		setResizable(false);
		setAlwaysOnTop(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel titolo = new JLabel("Stato");
		titolo.setBounds(134, 10, 149, 21);
		contentPane.add(titolo);
		
		JLabel sottotiolo = new JLabel("Libro: ");
		sottotiolo.setBounds(29, 33, 358, 26);
		contentPane.add(sottotiolo);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(5, 76, 428, 152);
		contentPane.add(scrollPane);
		
		table = new JTable();
		table.setBorder(null);
		scrollPane.setViewportView(table);
		table.setColumnSelectionAllowed(true);
		table.setCellSelectionEnabled(true);
		table.setModel(new DefaultTableModel(
			new Object[][] {
				{null, "", null, null},
			},
			new String[] {
				"Id", "stato", "utente", "scadenza prestito"
			}
		) {
			boolean[] columnEditables = new boolean[] {
				false, false, false, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		
		JButton closeButton = new JButton("Chiudi");
		closeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Stato.this.dispose();
			}		
		});
		closeButton.setBounds(345, 238, 85, 21);
		closeButton.setMinimumSize(new Dimension(70, 30));
		closeButton.setMaximumSize(new Dimension(70, 30));
		closeButton.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 11));
		closeButton.setBackground(new Color(240, 240, 240));
		closeButton.setForeground(new Color(0, 0, 0));
		closeButton.setPreferredSize(new Dimension(77, 30));
		contentPane.add(closeButton);
	}
}
