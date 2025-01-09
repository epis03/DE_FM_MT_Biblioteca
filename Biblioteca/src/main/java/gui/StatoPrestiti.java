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
import javax.swing.SwingConstants;

public abstract class StatoPrestiti extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table;

	/**
	 * Launch the application.
	 */
	

	/**
	 * Create the frame.
	 */
	public StatoPrestiti(String sottotilo, String oggetto) {       // oggetto = email dell'utente o titolo del libro
		setResizable(false);
		setAlwaysOnTop(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel titolo = new JLabel("Stato Prestiti");
		titolo.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 25));
		titolo.setHorizontalAlignment(SwingConstants.CENTER);
		titolo.setBounds(0, 10, 436, 21);
		contentPane.add(titolo);
		
		JLabel labelSottotitolo = new JLabel(sottotilo + "" + oggetto);
		labelSottotitolo.setFont(new Font("Tahoma", Font.PLAIN, 12));
		labelSottotitolo.setHorizontalAlignment(SwingConstants.CENTER);
		labelSottotitolo.setBounds(0, 40, 436, 26);
		contentPane.add(labelSottotitolo);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(5, 76, 428, 152);
		contentPane.add(scrollPane);
		
		table = new JTable();
		table.setBorder(null);
		scrollPane.setViewportView(table);
		table.setColumnSelectionAllowed(true);
		table.setCellSelectionEnabled(true);
		
		
		JButton closeButton = new JButton("Chiudi");
		closeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				StatoPrestiti.this.dispose();
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

	public JTable getTable() {
		return table;
	}
}