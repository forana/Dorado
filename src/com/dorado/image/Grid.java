package com.dorado.image;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Stroke;

public class Grid implements Cloneable {
	public static final int STROKE_SOLID = 0;
	public static final int STROKE_DASHED = 2;
	
	private static final float[] DASH_PATTERN = {8f, 8f};
	private static final Stroke[] STROKES = {
		new BasicStroke(3f),
		new BasicStroke(1f),
		new BasicStroke(3f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_BEVEL, 1f, DASH_PATTERN, 0f),
		new BasicStroke(1f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_BEVEL, 1f, DASH_PATTERN, 0f)
	};

	private static final int DEFAULT_X_SPACING = 16;
	private static final int DEFAULT_Y_SPACING = 16;
	private static final int DEFAULT_X_OFFSET = 0;
	private static final int DEFAULT_Y_OFFSET = 0;
	private static final boolean DEFAULT_ENABLED = false;
	
	private static final Color DEFAULT_HIGH_COLOR = new Color(0xCCCCCC);
	private static final Color DEFAULT_LOW_COLOR = new Color(0x333333);
	
	private int xSpacing;
	private int ySpacing;
	private int xOffset;
	private int yOffset;
	private boolean enabled;
	private Color highColor;
	private Color lowColor;
	private int strokeStyleIndex;
	
	public Grid() {
		xSpacing = DEFAULT_X_SPACING;
		ySpacing = DEFAULT_Y_SPACING;
		xOffset = DEFAULT_X_OFFSET;
		yOffset = DEFAULT_Y_OFFSET;
		enabled = DEFAULT_ENABLED;
		highColor = DEFAULT_HIGH_COLOR;
		lowColor = DEFAULT_LOW_COLOR;
		strokeStyleIndex = STROKE_DASHED;
	}
	
	public int getXSpacing() {
		return xSpacing;
	}
	
	public void setXSpacing(int xSpacing) {
		this.xSpacing = xSpacing;
	}
	
	public int getYSpacing() {
		return ySpacing;
	}
	
	public void setYSpacing(int ySpacing) {
		this.ySpacing = ySpacing;
	}
	
	public int getXOffset() {
		return xOffset;
	}
	
	public void setXOffset(int xOffset) {
		this.xOffset = xOffset;
	}
	
	public int getYOffset() {
		return yOffset;
	}
	
	public void setYOffset(int yOffset) {
		this.yOffset = yOffset;
	}
	
	public boolean isEnabled() {
		return enabled;
	}
	
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
	public int getStrokeStyleIndex() {
		return strokeStyleIndex;
	}
	
	public void setStrokeStyleIndex(int strokeStyleIndex) {
		this.strokeStyleIndex = strokeStyleIndex;
	}
	
	public Color getHighColor() {
		return highColor;
	}
	
	public void setHighColor(Color highColor) {
		this.highColor = highColor;
	}
	
	public Color getLowColor() {
		return lowColor;
	}
	
	public void setLowColor(Color lowColor) {
		this.lowColor = lowColor;
	}
	
	public void render(Graphics2D g2, int width, int height, int scale) {
		if (enabled) {
			g2.setStroke(STROKES[strokeStyleIndex]);
			g2.setColor(highColor);
			renderLines(g2, width, height, scale);
			
			g2.setStroke(STROKES[strokeStyleIndex + 1]);
			g2.setColor(lowColor);
			renderLines(g2, width, height, scale);
		}
	}
	
	private void renderLines(Graphics2D g2, int width, int height, int scale) {
		for (int x = xOffset; x <= width; x += xSpacing) {
			g2.drawLine(x * scale, 0, x * scale, height * scale);
		}
		for (int y = yOffset; y <= height; y += ySpacing) {
			g2.drawLine(0, y * scale, width * scale, y * scale);
		}
	}
}
