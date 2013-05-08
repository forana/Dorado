package com.dorado.image;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.image.BufferedImage;
import java.awt.image.VolatileImage;
import java.io.File;
import java.util.Map;
import java.util.Random;

import org.json.JSONArray;
import org.json.JSONObject;

import com.dorado.selection.Selection;

public class ImageModel {
	private int[][] pixels;
	private Palette palette;
	private Grid grid;
	private Selection selection;
	private boolean dirty;
	private boolean needsRerender;
	
	private VolatileImage image;
	private File source;
	
	public ImageModel(int width, int height, Palette palette) {
		// TODO assert that width and height are positive
		this.palette = palette;
		grid = new Grid();
		
		pixels = new int[width][];
		for (int i=0; i<pixels.length; i++) {
			pixels[i] = new int[height];
			for (int j=0; j<pixels[i].length; j++) {
				pixels[i][j] = Palette.TRANSPARENT_INDEX;
			}
		}
		
		dirty = false;
		needsRerender = true;
		
		image = null;
		source = null;
	}
	
	public int getWidth() {
		return pixels.length;
	}
	
	public int getHeight() {
		return pixels[0].length; 
	}
	
	public File getSource() {
		return source;
	}
	
	public void setSource(File source) {
		this.source = source;
	}
	
	public Palette getPalette() {
		return palette;
	}
	
	public Grid getGrid() {
		return grid;
	}
	
	public void setGrid(Grid grid) {
		this.grid = grid;
	}
	
	public Selection getSelection() {
		return selection;
	}
	
	public void setSelection(Selection selection) {
		this.selection = selection;
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
		image = gc.createCompatibleVolatileImage(getWidth(), getHeight(), VolatileImage.TRANSLUCENT);
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
		
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC));
		
		for (int x = 0; x < pixels.length; x++) {
			for (int y = 0; y < pixels[x].length; y++) {
				g2.setColor(palette.getColor(pixels[x][y]));
				g2.drawLine(x, y, x, y);
			}
		}
	}
	
	public BufferedImage getImage(Component target) {
		prepareImage(target.getGraphicsConfiguration());
		return image.getSnapshot();
	}
	
	public ImageModel(File source, JSONObject obj) {
		this(obj.getInt("width"), obj.getInt("height"), new Palette(obj.getJSONObject("palette")));
		
		JSONArray xarr = obj.getJSONArray("pixels");
		for (int x = 0; x < pixels.length; x++) {
			JSONArray yarr = xarr.getJSONArray(x);
			for (int y = 0; y < pixels[0].length; y++) {
				pixels[x][y] = yarr.getInt(y);
			}
		}
		
		this.source = source;
	}
	
	public JSONObject toJSONObject() {
		JSONObject obj = new JSONObject();
		
		obj.put("width", pixels.length);
		obj.put("height", pixels[0].length);
		
		obj.put("pixels", new JSONArray(pixels));
		
		obj.put("palette", palette.toJSONObject());
		
		return obj;
	}
}
