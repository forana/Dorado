package com.dorado.image;

public class ImageModel {
	private int[][] pixels;
	
	private Palette palette;
	
	public ImageModel(int width, int height, int fill, Palette palette) {
		this.palette = palette;
		
		this.pixels = new int[width][];
		for (int i=0; i<this.pixels.length; i++) {
			this.pixels[i] = new int[height];
			for (int j=0; j<this.pixels[i].length; j++) {
				this.pixels[i][j] = fill;
			}
		}
	}
	
	public Palette getPalette() {
		return this.palette;
	}
	
	public int getColorIndexAt(int x, int y) {
		return this.pixels[x][y];
	}
	
	public int setColorIndexAt(int x, int y, int index) {
		return this.pixels[x][y];
	}
}
