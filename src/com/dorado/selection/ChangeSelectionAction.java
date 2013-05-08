package com.dorado.selection;

import com.dorado.image.ImageModel;
import com.dorado.tool.ToolAction;

public class ChangeSelectionAction extends ToolAction {
	private Selection oldSelection;
	private Selection newSelection;
	
	public ChangeSelectionAction(Selection oldSelection, Selection newSelection) {
		this.oldSelection = oldSelection;
		this.newSelection = newSelection;
		this.name = "Change Selection";
	}

	@Override
	public void applyOriginal(ImageModel model) {
		model.setSelection(oldSelection);
	}

	@Override
	public void applyNew(ImageModel model) {
		model.setSelection(newSelection);
	}
	
}
