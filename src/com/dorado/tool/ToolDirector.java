package com.dorado.tool;

import com.dorado.image.ImageModel;
import com.dorado.ui.event.EventManager;
import com.dorado.ui.event.ToolChangedEvent;

public class ToolDirector {
	private Tool tool;
	private int colorIndex;
	private ImageModel model;
	
	private EventManager manager;
	
	public ToolDirector(EventManager manager, ImageModel model) {
		tool = null;
		colorIndex = 0;
		this.model = model;
		this.manager = manager;
	}
	
	public void setColor(int index) {
		colorIndex = index;
		if (tool != null) {
			tool.setColorIndex(colorIndex);
		}
	}
	
	public void setTool(Tool tool) {
		if (this.tool != null) {
			this.tool.cleanUp();
		}
		
		this.tool = tool;
		this.tool.setColorIndex(colorIndex);
		this.tool.setImageModel(model);
		this.tool.setManager(manager);
		
		manager.fireEvent(new ToolChangedEvent(tool));
	}
	
	public Tool getTool() {
		return tool;
	}
}
