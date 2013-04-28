package com.dorado.tool;

import com.dorado.image.ImageModel;

public abstract class ToolAction {
	protected String name;
	
	public String getName() {
		return name;
	}
	
	public abstract void applyOriginal(ImageModel model);
	public abstract void applyNew(ImageModel model);
}
