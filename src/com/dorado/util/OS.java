package com.dorado.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import javax.swing.UIManager;

/**
 * Utility class for checking / dealing with OS-specific behavior.
 */
public final class OS {
	/**
	 * For checking whether or not the operating system is OSX.
	 */
	public static final boolean IS_OSX = System.getProperty("os.name").contains("OS X");
	
	/**
	 * Declare the behavior that happens when all windows are closed.
	 * 
	 * Unlike other windowings systems, on OSX, a UI application can be running with no
	 * windows open - only the menu bar will show. Therefore, this function does some
	 * settings only for OSX.
	 */
	public static void setGlobalCloseHandler() {
		if (IS_OSX) {
			try {
				Class<?> strategyEnum = Class.forName("com.apple.eawt.QuitStrategy");
				Field enumField = strategyEnum.getField("CLOSE_ALL_WINDOWS");
				Object enumValue = enumField.get(strategyEnum);
				
				Class<?> appClass = Class.forName("com.apple.eawt.Application");
				Object appObj = appClass.getMethod("getApplication").invoke(appClass);
				Method setStrategyMethod = appClass.getMethod("setQuitStrategy", strategyEnum);
				
				setStrategyMethod.invoke(appObj, enumValue);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Tell the OS where to place menu bars for this application.
	 * 
	 * On OSX, there are two options - inside the window's frame, as it is on other systems - or
	 * at the top of the window. Apple's style guides ask that OSX applications use the latter,
	 * so this function sets the proper setting to do so.
	 */
	public static void positionMenuBar() {
		// tell OSX to put the menu bars in the normal place
		if (IS_OSX) {
			System.setProperty("apple.laf.useScreenMenuBar", "true");
		}
	}
	
	/**
	 * Set the look and feel to match the platform's.
	 * 
	 * If there is an error, java's default is used.
	 */
	public static void setLookAndFeel() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			// just use the default
		}
	}
}
