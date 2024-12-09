package gui;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import javax.swing.JScrollPane;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.Timer;
import javax.swing.JButton;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.Component;
import javax.swing.Box;
import java.awt.Color;

public class TabellaLibri extends JFrame {

	private static final long serialVersionUID = 1L;
	private static JFrame frame;
	private static InserisciLibro insert;
	private JPanel contentPane;
	private JTable table;

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
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnNewMenu = new JMenu("filtra");
		menuBar.add(mnNewMenu);
		
		JMenu mnNewMenu_2 = new JMenu("info");
		menuBar.add(mnNewMenu_2);
		
		Component horizontalGlue = Box.createHorizontalGlue();
		menuBar.add(horizontalGlue);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JLabel date = new JLabel("");
		contentPane.add(date, BorderLayout.SOUTH);
		
		JScrollPane scrollPane = new JScrollPane();
		contentPane.add(scrollPane, BorderLayout.CENTER);
		
		table = new JTable();
		table.setModel(new DefaultTableModel(
			new Object[][] {
				{null, null, null, null, null},
			},
			new String[] {
				"Autore", "Libro", "Genere", "Stato", ""
			}
		) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			boolean[] columnEditables = new boolean[] {
				false, false, false, false, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		TableRowSorter<TableModel> sorter = new TableRowSorter<>((DefaultTableModel) table.getModel());
      table.setRowSorter(sorter);
      sorter.setSortKeys(java.util.Collections.singletonList(
              new RowSorter.SortKey(0, SortOrder.ASCENDING)
          ));
		scrollPane.setViewportView(table);
		
		JButton addRow = new JButton("Aggiungi Riga");
		addRow.setBackground(new Color(192, 192, 192));
		menuBar.add(addRow);
		addRow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				insert = new InserisciLibro();
				insert.setVisible(true);
				if(InserisciLibro.verificaDatiInseriti()){
				DefaultTableModel model = (DefaultTableModel) table.getModel();
				 model.addRow(new Object[]{"Column 1", "Column 2", "Column 3", "Colum 4"});
				 sorter.setSortKeys(java.util.Collections.singletonList(
			              new RowSorter.SortKey(0, SortOrder.ASCENDING)
			          ));
}
			}
			});
		Timer timer = new Timer(1000, new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		         String currentTime = new SimpleDateFormat("EEEE, dd MMMM yyyy HH:mm:ss").format(new Date());
		        date.setText(currentTime);
		    }
		});
		timer.start();
	}

	public static void chiudiInserimento() {
		insert.setVisible(false);
		
	}
}
