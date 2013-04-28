package com.dorado.ui.event;

public class ColorChangedEvent implements Event {
	public final int colorIndex;
	
	public ColorChangedEvent(int colorIndex) {
		this.colorIndex = colorIndex;
	}
}
