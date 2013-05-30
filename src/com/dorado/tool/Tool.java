package com.dorado.tool;

import java.awt.Cursor;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import com.dorado.image.ImageModel;
import com.dorado.util.ResourceLoader;

/**
 * 
 * A Tool is a mouse-based way of editing an ImageModel.
 */
public abstract class Tool {
	protected boolean mouseDown;
	protected Point pressedLocation;
	protected Point currentLocation;
	protected int colorIndex;
	protected ImageModel model;
	protected ToolActionList actionList;
	
	protected Tool() {
		mouseDown = false;
		pressedLocation = null;
		currentLocation = null;
		colorIndex = 0;
		model = null;
		actionList = null;
	}
	
	/**
	 * Gets the filepath where the cursor-image for this Tool is found. 
	 */
	protected abstract String getCursorImagePath();
	
	/**
	 * Gets the filepath where the icon for this tool is found. 
	 */
	protected abstract String getIconPath();
	
	/**
	 * Gets the name of this Tool. 
	 */
	public abstract String getName();
	
	/**
	 * Gets the point within the cursor-image where action occurs.
	 */
	protected Point getCursorHotSpot() {
		return new Point(0, 0);
	}
	
	/**
	 * Gets the Cursor displayed where this Tool is editing.
	 */
	public Cursor getCursor() {
		Image image = ResourceLoader.loadImage(getCursorImagePath());
		return Toolkit.getDefaultToolkit().createCustomCursor(image, getCursorHotSpot(), getName());
	}
	
	/**
	 * Gets the Icon used to represent this Tool in a menu.
	 */
	public Icon getIcon() {
		return new ImageIcon(ResourceLoader.loadImage(getIconPath()));
	}
	
	/**
	 * Called when the mouse is pressed when using this Tool.
	 */
	protected abstract void onMouseDown();

	/**
	 * Called when the mouse is released when using this Tool.
	 */
	protected abstract void onMouseUp();

	/**
	 * Called when the mouse moves when using this Tool.
	 */
	protected abstract void onMouseMoved();

	/**
	 * Sets the state of the Tool as to whether the mouse is up or down.
	 */
	public void setMouseDown(boolean down) {
		mouseDown = down;
		if (down) {
			pressedLocation = currentLocation;
			onMouseDown();
		} else {
			onMouseUp();
		}
	}
	
	/**
	 * Sets the location within the ImageModel where the Tool is editing.
	 */
	public void setMouseLocation(Point location) {
		if (!location.equals(currentLocation)) {
			currentLocation = location;
			onMouseMoved();
		}
	}

	/**
	 * Sets the index within a Palette of the color this Tool is using.
	 */
	public void setColorIndex(int colorIndex) {
		this.colorIndex = colorIndex;
	}
	
	/**
	 * Sets the ImageModel that this Tool will act upon.
	 */
	public void setImageModel(ImageModel model) {
		this.model = model;
	}
	
	/**
	 * Sets the ToolActionList this Tool will make ToolActions into.
	 * Some Tools will not have ToolAction side effects; those that do should act on the ToolActionList
	 * to save the ToolAction in history. 
	 */
	public void setActionList(ToolActionList actionList) {
		this.actionList = actionList;
	}
	
	/**
	 * Resets any state maintained by the Tool.
	 * This should be called when a user switches from using this Tool to a different one.
	 */
	public abstract void cleanUp();
}
