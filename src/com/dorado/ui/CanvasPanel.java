package com.dorado.ui;

import java.awt.Canvas;
import java.awt.Color;

import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import com.dorado.image.ImageModel;

/**
 * Panel for displaying and interacting with the image data.
 */
public class CanvasPanel extends JScrollPane {
	private static final long serialVersionUID = 1L;
	
	private Canvas canvas;
	
	public CanvasPanel(ImageModel model) {
		this.getViewport().setBackground(UIConstants.EMPTY_COLOR);
		
		this.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		this.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		
		this.canvas = new Canvas();
		this.canvas.setBackground(Color.WHITE);
		this.canvas.setBounds(0, 0, 100, 100);
		
		this.setViewportView(this.canvas);
	}
}
