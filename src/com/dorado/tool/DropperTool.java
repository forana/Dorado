package com.dorado.tool;

import com.dorado.ui.event.ColorChangedEvent;
import com.dorado.ui.event.EventManager;

public class DropperTool extends Tool {
	private EventManager manager;
	
	public DropperTool(EventManager manager) {
		this.manager = manager;
	}
	
	@Override
	protected String getCursorImagePath() {
		return "resources/ui/tools/dropper-cursor.png";
	}

	@Override
	protected String getIconPath() {
		return "resources/ui/tools/dropper-button.png";
	}

	@Override
	public String getName() {
		return "Dropper";
	}

	@Override
	protected void onMouseDown() {
		pickColor();
	}

	private void pickColor() {
		int index = model.getColorIndexAt(currentLocation.x, currentLocation.y);
		manager.fireEvent(new ColorChangedEvent(index));
	}

	@Override
	protected void onMouseUp() {
	}

	@Override
	protected void onMouseMoved() {
		if (mouseDown) {
			pickColor();
		}
	}

	@Override
	public void cleanUp() {
	}

}
