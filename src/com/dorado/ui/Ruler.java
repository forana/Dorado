package com.dorado.ui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JComponent;

import com.dorado.util.GraphicsUtil;

public class Ruler extends JComponent {
	private static final long serialVersionUID = 1L;
	
	protected static final int BAR_SIZE = 24;
	private static final int MIN_DISTANCE_BETWEEN_TICKS = 8;
	private static final int MINOR_TICKS_PER_MAJOR = 8;
	
	protected ZoomableCanvas canvas;
	
	protected Ruler(ZoomableCanvas canvas) {
		this.canvas = canvas;
	}
	
	protected void renderRuler(Graphics2D g, int ownSize, int observedSize, boolean ltr) {
		int offset = ownSize > observedSize ? (ownSize - observedSize) / 2 : 0;
		
		g.setColor(UIConstants.PANEL_COLOR);
		g.fillRect(0, 0, ownSize, BAR_SIZE);
		g.setColor(UIConstants.TEXT_COLOR);
		g.setFont(UIConstants.FONT_SMALL);
		
		int ppmt = calculatePixelsPerMinorTick();
		
		for (int i=0; i <= observedSize / canvas.getZoom(); i += ppmt) {
			int x = ltr ? i * canvas.getZoom() + offset : ownSize - i * canvas.getZoom() - offset;
			if (i / ppmt % MINOR_TICKS_PER_MAJOR == 0) {
				GraphicsUtil.drawCenteredString(g, "" + i, x, BAR_SIZE / 2);
				g.drawLine(x, BAR_SIZE / 2, x, BAR_SIZE);
			} else {
				g.drawLine(x, BAR_SIZE * 3/4, x, BAR_SIZE);
			}
		}
	}
	
	private int calculatePixelsPerMinorTick() {
		int pixels = 1;
		while (pixels * canvas.getZoom() < MIN_DISTANCE_BETWEEN_TICKS) {
			pixels *= 2;
		}
		return pixels;
	}
	
	public static class Horizontal extends Ruler {
		private static final long serialVersionUID = 1L;
		
		public Horizontal(ZoomableCanvas canvas) {
			super(canvas);
		}
		
		@Override
		public Dimension getPreferredSize() {
			return new Dimension(canvas.getPreferredSize().width, BAR_SIZE);
		}
		
		@Override
		public Dimension getMaximumSize() {
			return new Dimension(Integer.MAX_VALUE, BAR_SIZE);
		}
		
		@Override
		public Dimension getMinimumSize() {
			return new Dimension(0, BAR_SIZE);
		}
		
		@Override
		public void paintComponent(Graphics g) {
			renderRuler((Graphics2D)g, getSize().width, canvas.getPreferredSize().width, true);
		}
	}
	
	public static class Vertical extends Ruler {
		private static final long serialVersionUID = 1L;
		
		public Vertical(ZoomableCanvas canvas) {
			super(canvas);
		}
		
		@Override
		public Dimension getPreferredSize() {
			return new Dimension(BAR_SIZE, canvas.getPreferredSize().height);
		}
		
		@Override
		public Dimension getMaximumSize() {
			return new Dimension(BAR_SIZE, Integer.MAX_VALUE);
		}
		
		@Override
		public Dimension getMinimumSize() {
			return new Dimension(BAR_SIZE, 0);
		}
		
		@Override
		public void paintComponent(Graphics g) {
			Graphics2D g2 = (Graphics2D)g;
			
			// rotate / translate to draw horizontally
			//g2.translate(0, this.getSize().height);
			g2.rotate(-0.5*Math.PI);
			g2.translate(-this.getSize().height, 0);
			
			renderRuler(g2, getSize().height, canvas.getPreferredSize().height, false);
		}
	}
}
