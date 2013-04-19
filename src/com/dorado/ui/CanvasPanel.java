package com.dorado.ui;

import javax.swing.BorderFactory;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.Border;

import com.dorado.image.ImageModel;

/**
 * Panel for displaying and interacting with the image data.
 */
public class CanvasPanel extends JScrollPane {
	private static final long serialVersionUID = 1L;
	
	private ZoomableCanvas canvas;
	
	public CanvasPanel(ImageModel model) {
		getViewport().setBackground(UIConstants.EMPTY_COLOR);
		
		setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		
		canvas = new ZoomableCanvas(model, 8);
		
		setViewportView(canvas);
		
		Border border = BorderFactory.createBevelBorder(2);
		setColumnHeaderView(new Ruler.Horizontal(canvas));
		getColumnHeader().setBorder(border);
		setRowHeaderView(new Ruler.Vertical(canvas));
		getRowHeader().setBorder(border);
	}
}
