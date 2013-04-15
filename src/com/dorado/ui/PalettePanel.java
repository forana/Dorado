package com.dorado.ui;

import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Panel for displaying the palette to the user.
 */
public class PalettePanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private static final int WIDTH = 150;

	public PalettePanel() {
		this.setMinimumSize(new Dimension(WIDTH, 0));
		this.setPreferredSize(new Dimension(WIDTH, 0));
		this.setMaximumSize(new Dimension(WIDTH, Integer.MAX_VALUE));
		
		this.add(new JLabel("palette"));
		
		this.setBackground(UIConstants.PANEL_COLOR);
	}
}
