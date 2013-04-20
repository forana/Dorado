package com.dorado.util;

import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

public final class GraphicsUtil {
	public static void drawCenteredString(Graphics g, String text, int baseX, int baseY, ImageObserver obs) {
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
}
