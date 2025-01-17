package gui;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Image;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.FlowLayout;
import javax.swing.UIManager;
import java.awt.Font;

public class JPanelPassword extends JPanel {

	private static final long serialVersionUID = 1L;
	private JLabel iconLabel;
	private String defaultText = "Password"; 
	private Icon passwordVisibile;
	private Icon passwordNascosta;
	private boolean visibile = false; 
	private JTextField textField;
	private JPasswordField passwordField;
	/**
	 * Create the panel.
	 */
	public JPanelPassword(boolean password) {
		setBorder(UIManager.getBorder("TextField.border"));
	
	        
		textField = new JTextField();
		textField.setBorder(null);
		textField.setBounds(2, 2, 96, 18);
		textField.setVisible(false);
		textField.setColumns(10);
		addText(textField);
		passwordField = new JPasswordField();	
		passwordField.setBorder(null);
		passwordField.setBounds(2, 2, 96, 18);
		passwordField.setColumns(10);
		passwordField.setEchoChar((char) 0);
		addText(passwordField);
		
	        setLayout(null);
	        add(passwordField);
	        add(textField);
	        
	        if (password) {
	        Image passwordVisibile = new ImageIcon(getClass().getResource("/immagini/MostraPassword.png")).getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH); 
	    	Image passwordNascosta =  new ImageIcon(getClass().getResource(("/immagini/NascondiPassword.png"))).getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH);

			ImageIcon imageIcon = new ImageIcon(passwordNascosta);
	    	iconLabel = new JLabel(imageIcon);
	        iconLabel.setBounds(106, 2, 16, 16);
	        
	        iconLabel.addMouseListener(new MouseAdapter() {
	            @Override
	            public void mouseClicked(MouseEvent e) {
	            	if (visibile) {
	                  
	                    iconLabel.setIcon(new ImageIcon(passwordNascosta));
	                    visibile = false;
	                    passwordField.setText(textField.getText());
	                    textField.setVisible(false);
	                    passwordField.setVisible(true);
	                }  
	                 else {
	                	 textField.setVisible(true);
	                	 passwordField.setVisible(false);
	                    iconLabel.setIcon(new ImageIcon(passwordVisibile));
	                    visibile = true;
	                    textField.setText(new String(passwordField.getPassword()));
	                    
	                }
	            }
	        });
	        add(iconLabel);
	        }
	}

	private void addText(JTextField textField) {
        textField.setText(defaultText);
        textField.setForeground(Color.BLACK);

        textField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (textField.getText().equals(defaultText)) {
                    textField.setText("");
                    textField.setForeground(Color.BLACK);

                    if (textField instanceof JPasswordField) {
                        ((JPasswordField) textField).setEchoChar('●');
                        textField.setFont(new Font("Tahoma", Font.PLAIN, 11));                    }
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (textField.getText().isEmpty()) {
                    textField.setText(defaultText);
                    textField.setForeground(Color.BLACK);

                    if (textField instanceof JPasswordField) {
                        ((JPasswordField) textField).setEchoChar((char) 0); // Rimuove l'echo char
                    }
                }
            }
        });
    }
}
