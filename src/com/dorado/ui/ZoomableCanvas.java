package com.dorado.ui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import com.dorado.image.ImageModel;

public class ZoomableCanvas extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private static final int ZOOM_MIN = 1;
	private static final int ZOOM_MAX = 16;
	
	private ImageModel model;
	private int zoomFactor;
	private int initialZoom;
	
	public ZoomableCanvas(ImageModel model, int zoomFactor) {
		this.model = model;
		this.zoomFactor = zoomFactor;
		initialZoom = zoomFactor;
		setBackground(UIConstants.EMPTY_COLOR);
	}
	
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(zoomFactor * model.getWidth(), zoomFactor * model.getHeight());
	}
	
	@Override
	public void paint(Graphics g) {
		Graphics2D g2 = (Graphics2D)g;
		
		g.setColor(this.getBackground());
		g.fillRect(0, 0, this.getSize().width, this.getSize().height);
		
		int zoomWidth = zoomFactor * model.getWidth();
		int zoomHeight = zoomFactor * model.getHeight();
		if (getSize().width > zoomWidth) {
			g2.translate((getSize().width - zoomWidth) / 2, 0);
		}
		if (getSize().height > zoomHeight) {
			g2.translate(0, (getSize().height - zoomHeight) / 2);
		}
		
		g2.scale(zoomFactor, zoomFactor);
		
		g2.drawImage(model.getImage(this), 0, 0, this);
	}
	
	public void zoomIn() {
		if (zoomFactor < ZOOM_MAX) {
			zoomFactor++;
		}
	}
	
	public void zoomOut() {
		if (zoomFactor > ZOOM_MIN) {
			zoomFactor--;
		}
	}
	
	public void resetZoom() {
		zoomFactor = initialZoom;
	}
	
	public int getZoom() {
		return zoomFactor;
	}
}
