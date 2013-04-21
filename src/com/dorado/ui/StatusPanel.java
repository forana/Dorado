package com.dorado.ui;

import java.awt.BorderLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Panel for showing status messages to the user.
 */
public class StatusPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private JLabel textLabel;
	private JLabel zoomLabel;
	
	public StatusPanel() {
		setBackground(UIConstants.PANEL_COLOR);
		setLayout(new BorderLayout());
		setBorder(BorderFactory.createLoweredBevelBorder());
		
		textLabel = new JLabel(" ");
		textLabel.setFont(UIConstants.FONT_NORMAL);
		add(textLabel, BorderLayout.WEST);
		
		zoomLabel = new JLabel(" ");
		zoomLabel.setFont(UIConstants.FONT_NORMAL);
		add(zoomLabel, BorderLayout.EAST);
	}
	
	/**
	 * Set the text to display.
	 */
	public void setText(String text) {
		textLabel.setText(text);
	}
	
	public void updateZoom(int zoomFactor) {
		zoomLabel.setText("Zoom: " + zoomFactor + "x");
	}	
}
