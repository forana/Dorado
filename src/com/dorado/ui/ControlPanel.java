package com.dorado.ui;

import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class ControlPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private static final int WIDTH = 200;

	public ControlPanel() {
		this.setMinimumSize(new Dimension(WIDTH, 0));
		this.setPreferredSize(new Dimension(WIDTH, 0));
		this.setMaximumSize(new Dimension(WIDTH, Integer.MAX_VALUE));
		
		this.add(new JLabel("controls"));
		
		this.setBackground(UIConstants.PANEL_COLOR);
	}
}
