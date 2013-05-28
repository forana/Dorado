package com.dorado.util;

import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

public final class GraphicsUtil {
	/**
	 * Draws a string centered at a certain x-coordinate
	 * 
	 * @param g Graphics instance
	 * @param text The string to draw.
	 * @param baseX The x-coordinate upon which to center.
	 * @param baseY The y-coordinate at which to draw the baseline.
	 */
	public static void drawCenteredString(Graphics g, String text, int baseX, int baseY) {
		FontMetrics metrics = g.getFontMetrics(g.getFont());
		int width = metrics.stringWidth(text);
		
		/* OSX bug here - the letters will render backwards if rotated. The commented-out call below was
		 * meant to get around this, but instead opened up several gallons of worms. So, for now, OSX
		 * can have its bug.
		 */
		//drawBufferedString(g, text, baseX - width/2, baseY, obs);
		g.drawString(text, baseX - width/2, baseY);
	}
	
	/**
	 * Draws a string onto a buffered image, then draws that image onto the provided Graphics context.
	 * 
	 * This function was meant to get around an OSX-specific bug, but unfortunately FontMetrics is either
	 * poorly documented or a very gutsy liar, and the long and short of it is that this is more trouble than it's worth.
	 * 
	 * Should really be deleted, but I'm still sort of hoping we can make this work.
	 */
	public static void drawBufferedString(Graphics g, String text, int x, int y, ImageObserver obs) {
		Rectangle2D bounds = g.getFontMetrics(g.getFont()).getStringBounds(text, g);
		int totalWidth = (int)(bounds.getWidth());
		int totalHeight = (int)(bounds.getHeight());
		
		BufferedImage im = new BufferedImage(totalWidth, totalHeight, BufferedImage.TYPE_INT_ARGB);
		Graphics img = im.getGraphics();
		img.setColor(g.getColor());
		img.drawString(text, 0, totalHeight);
		
		g.drawImage(im, x, y-totalHeight, obs);
	}
	
	/**
	 * Tiles an image across the defined area.
	 */
	public static void tileImage(Graphics g, int x, int y, int width, int height, Image image, ImageObserver obs) {
		int mWidth = image.getWidth(obs);
		int mHeight = image.getHeight(obs);
		if (mWidth > 0 && mHeight > 0) {
			for (int i = x; i < x + width; i += mWidth) {
				for (int j = y; j < y + height; j += mHeight) {
					g.drawImage(image, i, j, obs);
				}
			}
		}
	}
}
