package gui;

import java.awt.*;
import javax.swing.*;

@SuppressWarnings("serial")
public class Banner extends JPanel {

	// -----------------------------------------------------------------
	// Attributes
	// -----------------------------------------------------------------

	/**
	 * Banner image
	 */
	private JLabel image;

	// -----------------------------------------------------------------
	// Constructor
	// -----------------------------------------------------------------

	/**
	 * The constructor of the banner panel
	 */

	public Banner() {
		setLayout(new GridLayout(1, 1));
		setBackground(Color.WHITE);
		ImageIcon icon = new ImageIcon("futbol.jpg");

		image = new JLabel("");
		image.setIcon(icon);
		image.setHorizontalAlignment(SwingConstants.CENTER);
		image.setVerticalAlignment(SwingConstants.CENTER);
		add(image);
	}

}