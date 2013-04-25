package com.dorado.ui.event;

import java.awt.Color;

public class ColorChangedEvent implements Event {
	public final Color color;
	
	public ColorChangedEvent(Color color) {
		this.color = color;
	}
}
