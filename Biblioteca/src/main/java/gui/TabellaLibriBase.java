package gui;

import java.awt.EventQueue;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.function.Consumer;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import javax.swing.JCheckBox;
import javax.swing.JScrollPane;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JMenu;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.Timer;
import javax.swing.JButton;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import main.GestioneLibri;
import main.Libro;

import java.awt.Component;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.DefaultCellEditor;
import javax.swing.ImageIcon;

import java.awt.Color;
import javax.swing.JTextField;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public abstract class TabellaLibriBase extends JFrame {

	private static final long serialVersionUID = 1L;
	private static InserisciLibro insert;
	private JPanel contentPane;
	private static JTable table;
	private JMenuBar menuBar;
	private boolean amministratore;
	private JPanelPersonalizzato cercaAutore;
	private JPanelPersonalizzato cercaTitolo;
	private JPanelPersonalizzato cercaGenere;

	/**
	 * Create the frame.
	 */
	public TabellaLibriBase(boolean amministratore, boolean[] columnEditables, int row, String azione, ActionListener actionlistener) {
	    this.amministratore=amministratore;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu mnNewMenu = new JMenu("filtra");
		menuBar.add(mnNewMenu);

		JMenu mnNewMenu_1 = new JMenu("Autore");
		mnNewMenu.add(mnNewMenu_1);

		  cercaAutore = new JPanelPersonalizzato( new Consumer<MouseEvent>() {
           
			@Override
			public void accept(MouseEvent t) {
				
				String autore = cercaAutore.getText();
				List<Libro> lista	=GestioneLibri.filtraAutore(autore);
				TabellaLibri.filtraTabella(lista);
			}
		});
		mnNewMenu_1.add(cercaAutore);

		JMenu mnNewMenu_3 = new JMenu("Titolo");
		mnNewMenu.add(mnNewMenu_3);

		cercaTitolo = new JPanelPersonalizzato( new Consumer<MouseEvent>() {

			@Override
			public void accept(MouseEvent t) {

				String titolo = cercaTitolo.getText();
				List<Libro> lista	=GestioneLibri.filtraAutore(titolo);
				TabellaLibri.filtraTabella(lista);
			}
		});
		mnNewMenu_3.add(cercaTitolo);


		JMenu mnNewMenu_4 = new JMenu("Genere");
		mnNewMenu.add(mnNewMenu_4);
		 cercaGenere = new JPanelPersonalizzato( new Consumer<MouseEvent>() {
			@Override
			public void accept(MouseEvent t) {
				String genere = cercaGenere.getText();
				List<Libro> lista	=GestioneLibri.filtraAutore(genere);
				TabellaLibri.filtraTabella(lista);
			}
		});
		mnNewMenu_4.add(cercaGenere);

		JMenuBar menuBar_1 = new JMenuBar();
		mnNewMenu.add(menuBar_1);


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
				},
				new String[] {
						"Autore", "Titolo", "Genere", "Stato", "Azioni"
				}
				) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		
		// Imposta renderer per tooltip nelle celle di testo
		for (int i = 0; i < row; i++) {
			table.getColumnModel().getColumn(i).setCellRenderer(new CustomizedTableCellRenderer());
		}
	
		table.getColumnModel().getColumn(4).setCellRenderer(new CustomizedTableRenderer(azione));
		table.getColumnModel().getColumn(4).setCellEditor(new CustomizedCellEditor(new JButton(azione), actionlistener));
		scrollPane.setViewportView(table);

		Component horizontalGlue = Box.createHorizontalGlue();
		menuBar.add(horizontalGlue);


		Timer timer = new Timer(1000, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String currentTime = new SimpleDateFormat("EEEE, dd MMMM yyyy HH:mm:ss").format(new Date());
				date.setText(currentTime);
			}
		});
		timer.start();
	}

	public JTable getTable() {
		return table;
	}
	public JMenuBar getMenu() {
		return menuBar;
	}
	
	public static  void filtraTabella() {
		
	}

	class CustomizedTableRenderer extends JButton implements TableCellRenderer {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private String tooltip;

		public CustomizedTableRenderer(String tooltip) {
			this.tooltip = tooltip;
			setOpaque(true);
			setToolTipText(tooltip);
		}

		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
			setText((value != null && value instanceof String) ? value.toString() : tooltip);
			return this;
		}
	}

	class CustomizedCellEditor extends DefaultCellEditor {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private JButton button;
		private ActionListener actionListener;

		public CustomizedCellEditor(JButton button, ActionListener actionListener) {
			super(new JCheckBox());
			this.button = button;
			this.actionListener = actionListener;
			button.addActionListener(actionListener);
		}

		@Override
		public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
			button.setText((value != null && value instanceof String) ? value.toString() : "");
			return button;
		}
	}

	class CustomizedTableCellRenderer extends DefaultTableCellRenderer {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
			Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
			if (value != null) {
				setToolTipText(value.toString());
			} else {
				setToolTipText(null);
			}
			return cell;
		}
	}
}