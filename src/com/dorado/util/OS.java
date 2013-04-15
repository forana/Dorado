package com.dorado.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Utility class for checking / dealing with OS-specific behavior.
 */
public final class OS {
	/**
	 * For checking whether or not the operating system is OSX.
	 */
	public static final boolean IS_OSX = System.getProperty("os.name").contains("OS X");
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void setGlobalCloseHandler() {
		if (IS_OSX) {
			try {
				Class strategyEnum = Class.forName("com.apple.eawt.QuitStrategy");
				Field enumField = strategyEnum.getField("CLOSE_ALL_WINDOWS");
				Object enumValue = enumField.get(strategyEnum);
				
				Class appClass = Class.forName("com.apple.eawt.Application");
				Object appObj = appClass.getMethod("getApplication").invoke(appClass);
				Method setStrategyMethod = appClass.getMethod("setQuitStrategy", strategyEnum);
				
				setStrategyMethod.invoke(appObj, enumValue);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
