package com.dorado.util;

import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

public final class GraphicsUtil {
	public static void drawCenteredString(Graphics g, String text, int baseX, int baseY) {
		FontMetrics metrics = g.getFontMetrics(g.getFont());
		int width = metrics.stringWidth(text);
		
		//drawBufferedString(g, text, baseX - width/2, baseY, obs);
		g.drawString(text, baseX - width/2, baseY);
	}
	
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
