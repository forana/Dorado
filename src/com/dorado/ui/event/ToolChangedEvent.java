package com.dorado.ui.event;

import com.dorado.tool.Tool;

public class ToolChangedEvent implements Event {
	public final Tool tool;
	
	public ToolChangedEvent(Tool tool) {
		this.tool = tool;
	}
}
