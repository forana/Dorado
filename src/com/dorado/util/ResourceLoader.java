package com.dorado.util;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public final class ResourceLoader {
	private ResourceLoader() {
	}
	
	// seems very hacky, but when in a jar, URLs will start with jar:/ - this is an easy way to check
	private static final boolean IS_IN_JAR = ResourceLoader.class.getResource("ResourceLoader.class").toString().startsWith("jar");
	
	/**
	 * Load an image at a given path relative to the project root.
	 */
	public static Image loadImage(String path) {
		try {
			return IS_IN_JAR ?
				ImageIO.read(ResourceLoader.class.getResource("/" + path)) :
				ImageIO.read(new File(path));
		} catch (IOException e) {
			return null;
		}
	}
}
