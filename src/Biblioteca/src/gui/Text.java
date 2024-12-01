package gui;

import java.awt.TextField;

import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;

public class Text extends JTextField {

	void insertUpdate (DocumentEvent e) {
		
		this.setText(this.getText().toUpperCase());
	}
}
