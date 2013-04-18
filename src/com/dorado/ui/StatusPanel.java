package com.dorado.ui;

import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Panel for showing status messages to the user.
 */
public class StatusPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private JLabel textLabel;
	
	public StatusPanel() {
		setBackground(UIConstants.PANEL_COLOR);
		setLayout(new FlowLayout(FlowLayout.LEFT, 3, 1));
		setBorder(BorderFactory.createLoweredBevelBorder());
		
		textLabel = new JLabel(" ");
		textLabel.setFont(UIConstants.FONT_NORMAL);
		add(this.textLabel);
	}
	
	/**
	 * Set the text to display.
	 */
	public void setStatusText(String text) {
		textLabel.setText(text);
	}
}
