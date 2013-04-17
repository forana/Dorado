package com.dorado.util;

import java.awt.Image;
import java.awt.Toolkit;

public final class ResourceLoader {
	private ResourceLoader() {
	}
	
	// seems very hacky, but when in a jar, URLs will start with jar:/ - this is an easy way to check
	private static final boolean IS_IN_JAR = ResourceLoader.class.getResource("ResourceLoader.class").toString().startsWith("jar");
	
	/**
	 * Load an image at a given path relative to the project root.
	 */
	public static Image loadImage(String path) {
		return IS_IN_JAR ?
			Toolkit.getDefaultToolkit().getImage(ResourceLoader.class.getResource("/" + path)) :
			Toolkit.getDefaultToolkit().getImage(path);
	}
}
