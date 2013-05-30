package com.dorado.tool;

import com.dorado.image.ImageModel;

/**
 * A ToolAction encapsulates one drawing-action a user performs with a Tool on an ImageModel.
 * An editing history can easily be maintained as a list of ToolActions, which can get undone and redone.
 *
 */
public abstract class ToolAction {
	protected String name;
	
	public String getName() {
		return name;
	}
	
	/**
	 * Restores the ImageModel to its original condition, before this ToolAction was applied
	 */
	public abstract void applyOriginal(ImageModel model);
	
	/**
	 * Applies this ToolAction to an ImageModel.
	 */
	public abstract void applyNew(ImageModel model);
}
