package com.dorado.tool;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;

import com.dorado.util.ResourceLoader;

public abstract class Tool {
	protected boolean mouseButtonDown;
	protected Point mousePressedLocation;
	protected Point mouseCurrentLocation;
	protected Color color;
	
	protected Tool() {
		mouseButtonDown = false;
		mousePressedLocation = null;
		mouseCurrentLocation = null;
		color = null;
	}
	
	protected abstract String getCursorImagePath();
	protected abstract String getIconPath();
	protected abstract String getName();
	
	protected Point getCursorHotSpot() {
		return new Point(0, 0);
	}
	
	public Cursor getCursor() {
		Image image = ResourceLoader.loadImage(getCursorImagePath());
		return Toolkit.getDefaultToolkit().createCustomCursor(image, getCursorHotSpot(), getName());
	}
	
	protected void onMouseDown() {
	}
	
	protected void onMouseUp() {
	}
	
	protected void onMouseMoved() {
	}
	
	public void setMouseDown(boolean down) {
		mouseButtonDown = down;
		if (down) {
			mousePressedLocation = mouseCurrentLocation;
			onMouseDown();
		} else {
			onMouseUp();
		}
	}
	
	public void setMouseLocation(Point location) {
		if (!location.equals(mouseCurrentLocation)) {
			mouseCurrentLocation = location;
			onMouseMoved();
		}
	}
	
	public void setColor(Color color) {
		this.color = color;
	}
}
