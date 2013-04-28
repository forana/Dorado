package com.dorado.tool;

import java.awt.Cursor;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import com.dorado.image.ImageModel;
import com.dorado.ui.event.EventManager;
import com.dorado.ui.event.ToolActionAppliedEvent;
import com.dorado.util.ResourceLoader;

public abstract class Tool {
	protected boolean mouseDown;
	protected Point pressedLocation;
	protected Point currentLocation;
	protected int colorIndex;
	protected ImageModel model;
	
	private EventManager manager;
	
	protected Tool() {
		mouseDown = false;
		pressedLocation = null;
		currentLocation = null;
		colorIndex = 0;
		model = null;
		manager = null;
	}
	
	protected abstract String getCursorImagePath();
	protected abstract String getIconPath();
	public abstract String getName();
	
	protected Point getCursorHotSpot() {
		return new Point(0, 0);
	}
	
	public Cursor getCursor() {
		Image image = ResourceLoader.loadImage(getCursorImagePath());
		return Toolkit.getDefaultToolkit().createCustomCursor(image, getCursorHotSpot(), getName());
	}
	
	public Icon getIcon() {
		return new ImageIcon(ResourceLoader.loadImage(getIconPath()));
	}
	
	protected abstract void onMouseDown();
	protected abstract void onMouseUp();
	protected abstract void onMouseMoved();
	
	public void setMouseDown(boolean down) {
		mouseDown = down;
		if (down) {
			pressedLocation = currentLocation;
			onMouseDown();
		} else {
			onMouseUp();
		}
	}
	
	public void setMouseLocation(Point location) {
		if (!location.equals(currentLocation)) {
			currentLocation = location;
			onMouseMoved();
		}
	}
	
	public void setColorIndex(int colorIndex) {
		this.colorIndex = colorIndex;
	}
	
	public void setImageModel(ImageModel model) {
		this.model = model;
	}
	
	public void setManager(EventManager manager) {
		this.manager = manager;
	}
	
	public abstract void cleanUp();
	
	protected final void triggerToolActionAppliedEvent() {
		manager.fireEvent(new ToolActionAppliedEvent());
	}
}
