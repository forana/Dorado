package com.dorado.tool;

public class PencilTool extends Tool {
	public PencilTool() {
	}

	@Override
	protected String getCursorImagePath() {
		return "resources/icons/pencil-icon.png";
	}

	@Override
	protected String getIconPath() {
		return "resources/icons/pencil-icon.png";
	}

	@Override
	public String getName() {
		return "Pixel";
	}
	
	private void draw() {
		model.setColorIndexAt(currentLocation.x, currentLocation.y, colorIndex);
		triggerToolActionAppliedEvent();
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
