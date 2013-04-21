package com.dorado.ui.event;

public class ZoomChangedEvent implements Event {
	public final int zoomLevel;
	
	public ZoomChangedEvent(int zoomLevel) {
		this.zoomLevel = zoomLevel;
	}
}
