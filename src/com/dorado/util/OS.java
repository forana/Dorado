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
	
	public static void positionMenuBar() {
		// tell OSX to put the menu bars in the normal place
		if (IS_OSX) {
			System.setProperty("apple.laf.useScreenMenuBar", "true");
		}
	}
	
	public static void setLookAndFeel() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			// just use the default
		}
	}
}
