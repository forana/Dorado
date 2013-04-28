package com.dorado.tool;

import com.dorado.image.ImageModel;

public class ToolAction {
	private String name;
	private Iterable<ColoredPoint> originalPoints;
	private Iterable<ColoredPoint> newPoints;
	
	public ToolAction(String name, Iterable<ColoredPoint> originalPoints, Iterable<ColoredPoint> newPoints) {
		this.name = name;
		this.originalPoints = originalPoints;
		this.newPoints = newPoints;
	}
	
	public void applyNew(ImageModel model) {
		apply(model, newPoints);
	}
	
	public void applyOriginal(ImageModel model) {
		apply(model, originalPoints);
	}
	
	public String getName() {
		return name;
	}
	
	public void apply(ImageModel model, Iterable<ColoredPoint> points) {
		for (ColoredPoint point : points) {
			model.setColorIndexAt(point.x, point.y, point.colorIndex);
		}
	}
}
