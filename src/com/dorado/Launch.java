package com.dorado;

import com.dorado.image.ImageModel;
import com.dorado.image.Palette;
import com.dorado.ui.AppWindow;
import com.dorado.util.OS;

/**
 * "Main" class for the application.
 */
public class Launch {
	public static void main(String[] args) {
		OS.setLookAndFeel();
		OS.positionMenuBar();
		OS.setGlobalCloseHandler();
		
		// TODO read arguments passed - was a file opened with this application?
		
		// TODO manage open windows so that they can presented in a menu list
		
		// TODO check if another application instance is open and connect to that pipe if it is
		// TODO listen with said pipe
		ImageModel model = new ImageModel(500, 500, Palette.TRANSPARENT_INDEX, Palette.createSteppedPalette(3));
		model.scatter();
		new AppWindow(model);
	}
}
