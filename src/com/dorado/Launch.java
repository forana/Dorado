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
		
		// TODO figure out how to get file associations working for Launch4j and AppBundler
		// (for AppBundler, that's going to be a hassle - there's a fork that can do it but
		// it seems very sketchy. no idea about Launch4j -- Alex)
		
		// TODO manage open windows so that they can presented in a menu list
		
		// TODO check if another application instance is open and connect to that pipe if it is
		// TODO listen with said pipe
		ImageModel model = new ImageModel(64, 64, Palette.createSteppedPalette(3));
		//model.scatter();
		new AppWindow(model);
	}
}
