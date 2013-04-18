package com.dorado.ui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JComponent;

import com.dorado.util.GraphicsUtil;

public class Ruler extends JComponent {
	private static final long serialVersionUID = 1L;
	
	protected static final int BAR_SIZE = 24;
	
	protected Component observedComponent;
	
	protected Ruler(Component observedComponent) {
		this.observedComponent = observedComponent;
	}
	
	protected void renderRuler(Graphics2D g, int ownSize, int observedSize, boolean ltr) {
		int offset = ownSize > observedSize ? (ownSize - observedSize) / 2 : 0;
		
		g.setColor(UIConstants.PANEL_COLOR);
		g.fillRect(0, 0, ownSize, BAR_SIZE);
		g.setColor(UIConstants.TEXT_COLOR);
		g.setFont(UIConstants.FONT_SMALL);
		for (int i=0; i <= observedSize; i += 50) {
			GraphicsUtil.drawCenteredString(g, "" + i, ltr ? offset + i : ownSize - i - offset, BAR_SIZE / 2, this);
		}
	}
	
	public static class Horizontal extends Ruler {
		private static final long serialVersionUID = 1L;
		
		public Horizontal(Component observedComponent) {
			super(observedComponent);
		}
		
		@Override
		public Dimension getPreferredSize() {
			return new Dimension(observedComponent.getPreferredSize().width, BAR_SIZE);
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
			renderRuler((Graphics2D)g, getSize().width, observedComponent.getPreferredSize().width, true);
		}
	}
	
	public static class Vertical extends Ruler {
		private static final long serialVersionUID = 1L;
		
		public Vertical(Component observedComponent) {
			super(observedComponent);
		}
		
		@Override
		public Dimension getPreferredSize() {
			return new Dimension(BAR_SIZE, observedComponent.getPreferredSize().height);
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
			
			renderRuler(g2, getSize().height, observedComponent.getPreferredSize().height, false);
		}
	}
}
