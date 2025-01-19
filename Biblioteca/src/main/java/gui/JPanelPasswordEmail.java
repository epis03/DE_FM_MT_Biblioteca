package gui;

import javax.swing.DropMode;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

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

public class JPanelPasswordEmail extends JPanel {

	private static final long serialVersionUID = 1L;
	private JLabel iconLabel;
	private String defaultText; 
	private Icon passwordVisibile;
	private Icon passwordNascosta;
	private boolean visibile = true; 
	private JTextField textField;
	private JPasswordField passwordField;
	/**
	 * Create the panel.
	 */
	public JPanelPasswordEmail(boolean password, String defaultText) {
		this.defaultText = defaultText;		
				
		setBorder(UIManager.getBorder("TextField.border"));
	   
		textField = new JTextField();
		textField.setBorder(null);
		textField.setBounds(2, 2, 154, 24);
		textField.setColumns(10);
		addText(textField);

	        
		setLayout(null);
	        
	        add(textField);
	        
	       if(password) {
	    	  textField.setBounds(2, 2, 132, 24);
	          textField.setVisible(false);
	          visibile = false;
	          passwordField = new JPasswordField();	
	    	  passwordField.setBorder(null);
	    	  passwordField.setBounds(2, 2, 132, 24);
	    		passwordField.setColumns(10);
	    		passwordField.setEchoChar((char) 0);
	    		addText(passwordField);
	    		add(passwordField);
	    		
	        Image passwordVisibile = new ImageIcon(getClass().getResource("/immagini/MostraPassword.png")).getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH); 
	    	Image passwordNascosta =  new ImageIcon(getClass().getResource(("/immagini/NascondiPassword.png"))).getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH);

			ImageIcon imageIcon = new ImageIcon(passwordNascosta);
	    	iconLabel = new JLabel(imageIcon);
	        iconLabel.setBounds(135, 2, 20, 24);
	        
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
	
	public String getText() {
		String testo;
		if(visibile) {
	        testo = this.textField.getText();
		}
		else {
			testo = new String(this.passwordField.getPassword());
		}
		
	return testo;
	}
	
}
