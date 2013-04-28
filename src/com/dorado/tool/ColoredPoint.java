package com.dorado.tool;

import java.awt.Point;

import com.dorado.image.ImageModel;

public class ColoredPoint extends Point {
	private static final long serialVersionUID = 1L;
	
	public final int colorIndex;
	
	public ColoredPoint(int x, int y, int colorIndex) {
		super(x, y);
		this.colorIndex = colorIndex;
	}
	
	public ColoredPoint(int x, int y, ImageModel model) {
		super(x, y);
		colorIndex = model.getColorIndexAt(x, y);
	}
}
