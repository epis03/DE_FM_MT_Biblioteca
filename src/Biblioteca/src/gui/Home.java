package gui;


import java.awt.EventQueue;
import java.awt.event.ActionEvent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;


public class Home  {

	private static JFrame frame;
	private JLabel lblNewLabel;
	private static Registrazione reg ;
	private static Login log;
	 
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Home window = new Home();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Home() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setResizable(false);
		frame.getContentPane().setForeground(new Color(0, 0, 0));
		frame.getContentPane().setBackground(SystemColor.activeCaption);
		frame.setBounds(100, 100, 607, 445);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JButton btnNewButton = new JButton("Registrazione");
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 reg = new Registrazione();
				 frame.setVisible(false);
				reg.setVisible(true);
				
				}
		});
		btnNewButton.setBounds(85, 232, 135, 70);
		frame.getContentPane().add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Login");
		btnNewButton_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				  log = new Login();
				frame.setVisible(false);
				log.setVisible(true);
			}
		});
		btnNewButton_1.setBounds(364, 232, 135, 70);
		frame.getContentPane().add(btnNewButton_1);
		
		lblNewLabel = new JLabel("Nome Biblioteca");
		lblNewLabel.setForeground(new Color(64, 128, 128));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Verdana", lblNewLabel.getFont().getStyle() | Font.BOLD | Font.ITALIC, 50));
		lblNewLabel.setBounds(0, 34, 583, 153);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel Sfondo = new JLabel("");
		Sfondo.setLabelFor(frame);
		Image immagini = new ImageIcon(this.getClass().getResource("/ImmagineBiblioteca.png")).getImage();
		Sfondo.setIcon(new ImageIcon(immagini));
		Sfondo.setBounds(0, -49, 593, 504);
		frame.getContentPane().add(Sfondo);
	}

	public static void chiudiRegistrazione() {
		
		reg.dispose();
		frame.setVisible(true);
		
		}
	
public static void chiudiLogin() {
		
		log.dispose();
		frame.setVisible(true);
		}
	
}
