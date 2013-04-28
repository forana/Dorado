package com.dorado.tool;

import com.dorado.image.ImageModel;

public class RasterToolAction extends ToolAction {
	private Iterable<ColoredPoint> originalPoints;
	private Iterable<ColoredPoint> newPoints;
	
	public RasterToolAction(String name, Iterable<ColoredPoint> originalPoints, Iterable<ColoredPoint> newPoints) {
		this.name = name;
		this.originalPoints = originalPoints;
		this.newPoints = newPoints;
	}
	
	@Override
	public void applyNew(ImageModel model) {
		apply(model, newPoints);
	}
	
	@Override
	public void applyOriginal(ImageModel model) {
		apply(model, originalPoints);
	}
	
	public void apply(ImageModel model, Iterable<ColoredPoint> points) {
		for (ColoredPoint point : points) {
			model.setColorIndexAt(point.x, point.y, point.colorIndex);
		}
	}
}
