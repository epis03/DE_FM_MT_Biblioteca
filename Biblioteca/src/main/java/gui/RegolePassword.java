package gui;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class RegolePassword extends JPanel {

	private JTextArea textArea;
	private JLabel label;
	private static JLabel accesso;
	public RegolePassword(JLabel accesso) {
		this.accesso=accesso;
		setBackground(new Color(240, 248, 255));
		Image info = new ImageIcon(getClass().getResource("/immagini/Info.png")).getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH); 
		ImageIcon imageIcon = new ImageIcon(info);
		setLayout(null);

		JLabel label = new JLabel(imageIcon);
		label.setBounds(0, 0, 29, 30);
		add(label);

		label.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {

				textArea.setVisible(true);  
				revalidate();  
				repaint();
			}

			@Override
			public void mouseExited(MouseEvent e) {     
				textArea.setVisible(false);
				String testo = RegolePassword.accesso.getText();
				RegolePassword.accesso.setText(" ");
				revalidate();  
				RegolePassword.accesso.setText(testo);
				repaint();
				
			}
		});

		textArea = new JTextArea(10, 30);
		textArea.setFont(new Font("Dialog", Font.PLAIN, 12));		
		textArea.setLineWrap(true);
		textArea.setForeground(new Color(128, 128, 128));
		textArea.setBackground(new Color(240, 248, 255));
		textArea.setBounds(27, 5, 253, 91);
		textArea.setVisible(false);
		add(textArea);
		textArea.setEditable(false);  
		textArea.setText("La password per essere valida deve:\n" +
				"  - Contenere almeno 8 caratteri.\n" +
				"  - Contenere almeno una lettera maiuscola.\n" +
				"  - Contenere almeno un numero.\n" +
				"  - Contenere almeno un carattere speciale.");





	}
}
