package com.dorado.tool;

import java.util.LinkedList;
import java.util.List;

public class PencilTool extends Tool {
	public PencilTool() {
	}

	@Override
	protected String getCursorImagePath() {
		return "resources/ui/tools/pencil-cursor.png";
	}

	@Override
	protected String getIconPath() {
		return "resources/ui/tools/pencil-button.png";
	}

	@Override
	public String getName() {
		return "Pixel";
	}
	
	private void draw() {
		if (currentLocation.x >= 0 && currentLocation.x < model.getWidth() &&
			currentLocation.y >= 0 && currentLocation.y < model.getHeight()) {
			List<ColoredPoint> original = new LinkedList<ColoredPoint>();
			original.add(new ColoredPoint(currentLocation.x, currentLocation.y, model));
			List<ColoredPoint> affected = new LinkedList<ColoredPoint>();
			affected.add(new ColoredPoint(currentLocation.x, currentLocation.y, colorIndex));
			
			actionList.addAndApply(new RasterToolAction(getName(), original, affected));
		}
	}

	@Override
	protected void onMouseDown() {
		draw();
	}

	@Override
	protected void onMouseUp() {
	}

	@Override
	protected void onMouseMoved() {
		if (mouseDown) {
			draw();
		}
	}

	@Override
	public void cleanUp() {
	}
}
