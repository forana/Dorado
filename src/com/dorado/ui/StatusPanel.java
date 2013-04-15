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
		this.setBackground(UIConstants.PANEL_COLOR);
		FlowLayout layout = new FlowLayout(FlowLayout.LEFT);
		layout.setHgap(3);
		layout.setVgap(1);
		this.setLayout(layout);
		this.textLabel = new JLabel("Everything's ok down here");
		this.textLabel.setFont(UIConstants.FONT);
		
		this.setBorder(BorderFactory.createLoweredBevelBorder());
		this.add(this.textLabel);
	}
	
	/**
	 * Set the text to display.
	 */
	public void setStatusText(String text) {
		this.textLabel.setText(text);
	}
}
