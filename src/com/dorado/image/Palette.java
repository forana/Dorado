package com.dorado.image;

import java.awt.Color;
import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;

public class Palette {
	public static final int TRANSPARENT_INDEX = 0;
	public static final Color TRANSPARENT = new Color(0, 0, 0, 0);
	
	private Map<Integer, Color> colors;
	
	public Palette() {
		this.colors = new TreeMap<Integer, Color>();
		//this.colors.put(TRANSPARENT_INDEX, TRANSPARENT);
	}
	
	public void addColor(Color newColor) {
		this.colors.put(this.colors.size(), newColor);
	}
	
	public Color getColor(int index) {
		return this.colors.get(index);
	}
	
	public void updateColor(int index, Color newColor) {
		this.colors.put(index, newColor);
	}
	
	public Collection<Map.Entry<Integer, Color>> getAllColors() {
		return this.colors.entrySet();
	}
	
	public static Palette createSteppedPalette(int steps) {
		int[] indices = new int[steps + 1];
		for (int i=0; i<indices.length; i++) {
			indices[i] = Math.max((256 / steps * i) - 1, 0);
		}
		
		Palette palette = new Palette();
		
		for (int r : indices) {
			for (int g : indices) {
				for (int b : indices) {
					palette.addColor(new Color(r, g, b));
				}
			}
		}
		
		return palette;
	}
}
