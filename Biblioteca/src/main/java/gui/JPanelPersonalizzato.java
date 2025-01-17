package gui;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.function.Consumer;

public class JPanelPersonalizzato extends JPanel {

	private static final long serialVersionUID = 1L;


	/**
	 * Create the panel.
	 */
	public JPanelPersonalizzato( Consumer<MouseEvent> onClick) {

		setLayout(new BorderLayout());
		JTextField textField = new JTextField(10); 
		add(textField, BorderLayout.CENTER);
		Image immagini = new ImageIcon(getClass().getResource("/immagini/Cerca.png")).getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH);
		ImageIcon imageIcon = new ImageIcon(immagini);
		JLabel iconLabel = new JLabel(imageIcon);
		iconLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				onClick.accept(e);
			}
		});
		add(iconLabel, BorderLayout.EAST);
	}
}

