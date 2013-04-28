package com.dorado.ui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import javax.swing.JPanel;

import com.dorado.image.ImageModel;
import com.dorado.tool.ToolDirector;
import com.dorado.util.GraphicsUtil;

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
		
		GraphicsUtil.tileImage(g, 0, 0, zoomWidth, zoomHeight, UIConstants.TRANSPARENT_TILE, this);
		
		g2.scale(zoomFactor, zoomFactor);
		
		g2.drawImage(model.getImage(this), 0, 0, this);
		
		g2.scale(1f/zoomFactor, 1f/zoomFactor);
		
		model.getGrid().render(g2, model.getWidth(), model.getHeight(), zoomFactor);
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
	
	public void bindMouseEvents(final ToolDirector director) {
		addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				director.getTool().setMouseDown(true);
			}
			
			@Override
			public void mouseReleased(MouseEvent e) {
				director.getTool().setMouseDown(false);
			}
		});
		
		addMouseMotionListener(new MouseMotionListener() {
			public void mouseMoved(MouseEvent e) {
				handleMouseMoved(director, e);
			}
			
			public void mouseDragged(MouseEvent e) {
				handleMouseMoved(director, e);
			}
		});
	}
	
	private void handleMouseMoved(ToolDirector director, MouseEvent e) {
		Dimension aSize = getSize();
		Dimension pSize = getPreferredSize();
		int offsetX = pSize.width < aSize.width ? (aSize.width - pSize.width) / 2 : 0;
		int offsetY = pSize.height < aSize.height ? (aSize.height - pSize.height) / 2 : 0;
		
		int iX = (e.getX() - offsetX) / zoomFactor;
		int iY = (e.getY() - offsetY) / zoomFactor;
		
		if (iX >= 0 && iX < model.getWidth() &&
			iY >= 0 && iY < model.getHeight()) {
			director.getTool().setMouseLocation(new Point(iX, iY));
		}
	}
}
