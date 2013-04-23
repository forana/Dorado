package com.dorado.image;

import java.awt.Color;
import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;

import org.json.JSONArray;
import org.json.JSONObject;

public class Palette {
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
					palette.addColor(new Color(r, g, b, 255));
				}
			}
		}
		
		return palette;
	}
	
	public Palette(JSONObject obj) {
		colors = new TreeMap<Integer, Color>();
		
		for (Object okey : obj.keySet()) {
			String key = (String)okey;
			
			JSONArray array = obj.getJSONArray(key);
			Color color = new Color(array.getInt(0), array.getInt(1), array.getInt(2), array.getInt(3));
			
			colors.put(new Integer(key), color);
		}
	}
	
	public JSONObject toJSONObject() {
		JSONObject obj = new JSONObject();
		
		for (Map.Entry<Integer, Color> entry : colors.entrySet()) {
			String key = entry.getKey().toString();
			
			JSONArray rgba = new JSONArray();
			Color color = entry.getValue();
			rgba.put(color.getRed());
			rgba.put(color.getGreen());
			rgba.put(color.getBlue());
			rgba.put(color.getAlpha());
			
			obj.put(key, rgba);
		}
		
		return obj;
	}
}
