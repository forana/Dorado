package com.dorado.ui;

import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class StatusPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private JLabel textLabel;
	
	public StatusPanel() {
		this.setBackground(UIConstants.PANEL_COLOR);
		FlowLayout layout = new FlowLayout(FlowLayout.LEFT);
		layout.setHgap(3);
		layout.setVgap(1);
		this.setLayout(layout);
		this.textLabel = new JLabel("asdlfjhalksjhkdf");
		this.textLabel.setFont(UIConstants.FONT);
		
		this.setBorder(BorderFactory.createLoweredBevelBorder());
		this.add(this.textLabel);
	}
	
	public void setStatusText(String text) {
		this.textLabel.setText(text);
	}
}
