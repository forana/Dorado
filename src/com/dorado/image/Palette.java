package com.dorado.image;

import java.awt.Color;
import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;

import org.json.JSONArray;
import org.json.JSONString;

public class Palette implements JSONString {
	public static final int TRANSPARENT_INDEX = 0;
	public static final Color TRANSPARENT = new Color(0, 0, 0, 0);
	
	private Map<Integer, Color> colors;
	
	public Palette() {
		colors = new TreeMap<Integer, Color>();
		colors.put(TRANSPARENT_INDEX, TRANSPARENT);
	}
	
	public void addColor(Color newColor) {
		colors.put(colors.size(), newColor);
	}
	
	public Color getColor(int index) {
		return colors.get(index);
	}
	
	public void updateColor(int index, Color newColor) {
		colors.put(index, newColor);
	}
	
	public Collection<Map.Entry<Integer, Color>> getAllColors() {
		return colors.entrySet();
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
	
	public Palette(JSONArray array) {
	}
	
	public String toJSONString() {
		// TODO
		return null;
	}
}
