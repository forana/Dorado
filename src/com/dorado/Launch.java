package com.dorado;

import javax.swing.UIManager;

import com.dorado.ui.AppWindow;
import com.dorado.util.OS;

/**
 * "Main" class for the application.
 */
public class Launch {
	public static void main(String[] args) {
		if (OS.IS_OSX) {
			System.setProperty("apple.laf.useScreenMenuBar", "true");
		}
		
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			// just use the default
		}
		
		OS.setGlobalCloseHandler();
		
		// TODO read arguments passed - was a file opened with this application?
		
		// TODO manage open windows so that they can presented in a menu list
		
		// TODO check if another application instance is open and connect to that pipe if it is
		// TODO listen with said pipe
		new AppWindow();
	}
}
