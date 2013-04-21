package com.dorado.image;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.Image;
import java.awt.image.VolatileImage;
import java.util.Map;
import java.util.Random;

import org.json.JSONObject;
import org.json.JSONString;

public class ImageModel implements JSONString {
	private int[][] pixels;
	private Palette palette;
	private boolean dirty;
	private boolean needsRerender;
	
	private VolatileImage image;
	
	public ImageModel(int width, int height, int fill, Palette palette) {
		// TODO assert that width and height are positive
		this.palette = palette;
		
		pixels = new int[width][];
		for (int i=0; i<pixels.length; i++) {
			pixels[i] = new int[height];
			for (int j=0; j<pixels[i].length; j++) {
				pixels[i][j] = fill;
			}
		}
		
		dirty = false;
		needsRerender = false;
		
		image = null;
	}
	
	public int getWidth() {
		return pixels.length;
	}
	
	public int getHeight() {
		return pixels[0].length; 
	}
	
	public Palette getPalette() {
		return palette;
	}
	
	public int getColorIndexAt(int x, int y) {
		return pixels[x][y];
	}
	
	public void setColorIndexAt(int x, int y, int index) {
		dirty = true;
		needsRerender = true;
		pixels[x][y] = index;
	}
	
	public boolean isDirty() {
		return dirty;
	}
	
	public void setNotDirty() {
		dirty = false;
	}
	
	public void scatter() {
		Object[] entries = palette.getAllColors().toArray();
		int[] indices = new int[entries.length];
		for (int i = 0; i<indices.length; i++) {
			@SuppressWarnings("unchecked")
			Map.Entry<Integer, Color> entry = (Map.Entry<Integer, Color>)entries[i];
			indices[i] = entry.getKey();
		}
		
		Random r = new Random();
		
		for (int x = 0; x < pixels.length; x++) {
			for (int y = 0; y < pixels[x].length; y++) {
				pixels[x][y] = indices[r.nextInt(indices.length)];
			}
		}
		
		dirty = true;
		needsRerender = true;
	}
	
	private void createImage(GraphicsConfiguration gc) {
		image = gc.createCompatibleVolatileImage(getWidth(), getHeight());
		image.setAccelerationPriority(1f);
	}
	
	private void prepareImage(GraphicsConfiguration gc) {
		if (image == null) {
			createImage(gc);
		}
		
		do {
			int res = image.validate(gc);
			if (res != VolatileImage.IMAGE_OK || needsRerender) {
				if (res == VolatileImage.IMAGE_INCOMPATIBLE) {
					createImage(gc);
				}
				renderImage();
			}
		} while (image.contentsLost());
		
		needsRerender = false;
	}
	
	private void renderImage() {
		Graphics2D g2 = (Graphics2D)image.getGraphics();
		
		for (int x = 0; x < pixels.length; x++) {
			for (int y = 0; y < pixels[x].length; y++) {
				g2.setColor(palette.getColor(pixels[x][y]));
				g2.drawLine(x, y, x, y);
			}
		}
	}
	
	public Image getImage(Component target) {
		prepareImage(target.getGraphicsConfiguration());
		return image.getSnapshot();
	}
	
	public ImageModel(JSONObject obj) {
	}
	
	public String toJSONString() {
		// TODO
		return null;
	}
}
