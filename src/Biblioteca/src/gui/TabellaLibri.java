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
import java.awt.Component;
import javax.swing.Box;
import javax.swing.DefaultCellEditor;

import java.awt.Color;

public class TabellaLibri extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private static JTable table;

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
			},
			new String[] {
				"Autore", "Titolo", "Genere", "Stato", "Azioni"
			}
		) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			boolean[] columnEditables = new boolean[] {
				false, false, false, false, true
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		
		// Imposta renderer per tooltip nelle celle di testo
        for (int i = 0; i < 4; i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(new CustomizedTableCellRenderer());
        }
			TableRowSorter<TableModel> sorter = new TableRowSorter<>((DefaultTableModel) table.getModel());
		      table.setRowSorter(sorter);
		      sorter.setSortKeys(java.util.Collections.singletonList(
		              new RowSorter.SortKey(0, SortOrder.ASCENDING)
		          ));
      
		

      table.getColumnModel().getColumn(4).setCellRenderer(new CustomizedTableRenderer("Prenota"));
      table.getColumnModel().getColumn(4).setCellEditor(new CustomizedCellEditor(new JButton("Prenota"),  new ActionListener() {
			public void actionPerformed(ActionEvent e) {
          int selectedRow = table.getSelectedRow();
          if (selectedRow != -1) {
              Object[] options = {"SI", "NO"};
              int choice = javax.swing.JOptionPane.showOptionDialog(
                  null,
                  "Vuoi prenotare questo libro:",
                  "Prenotazione",
                  javax.swing.JOptionPane.DEFAULT_OPTION,
                  javax.swing.JOptionPane.PLAIN_MESSAGE,
                  null,
                  options,
                  options[1]
              );
              if (choice == 0) {
            	  JOptionPane.showMessageDialog(null, "Libro prenotato con successo");
              } else if (choice == 1) {
            	  JOptionPane.showMessageDialog(null, "Azione cancellata");
              }
          }
      }}));
		scrollPane.setViewportView(table);
		
		
			
		Timer timer = new Timer(1000, new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		         String currentTime = new SimpleDateFormat("EEEE, dd MMMM yyyy HH:mm:ss").format(new Date());
		        date.setText(currentTime);
		    }
		});
		timer.start();
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