package com.dorado.image;

public class ImageModel {
	private int[][] pixels;
	private Palette palette;
	private boolean dirty;
	
	public ImageModel(int width, int height, int fill, Palette palette) {
		this.palette = palette;
		
		pixels = new int[width][];
		for (int i=0; i<pixels.length; i++) {
			pixels[i] = new int[height];
			for (int j=0; j<pixels[i].length; j++) {
				pixels[i][j] = fill;
			}
		}
		
		dirty = false;
	}
	
	public Palette getPalette() {
		return palette;
	}
	
	public int getColorIndexAt(int x, int y) {
		return pixels[x][y];
	}
	
	public int setColorIndexAt(int x, int y, int index) {
		dirty = true;
		return pixels[x][y];
	}
	
	public boolean isDirty() {
		return dirty;
	}
	
	public void setNotDirty() {
		dirty = false;
	}
}
