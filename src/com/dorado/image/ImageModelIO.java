package com.dorado.image;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.json.JSONObject;

public final class ImageModelIO {
	public static ImageModel readImage(File file) throws IOException {
		char[] buffer = new char[(int)file.length()];
		FileReader reader = new FileReader(file);
		reader.read(buffer);
		reader.close();
		
		return new ImageModel(file, new JSONObject(new String(buffer)));
	}
	
	public static void writeImage(File file, ImageModel model) throws IOException {
		FileWriter writer = new FileWriter(file);
		writer.write(model.toJSONObject().toString());
		writer.close();
	}
	
	public static ImageModel importImage(File file) throws IOException {
		BufferedImage image = ImageIO.read(file);
		
		int width = image.getWidth();
		int height = image.getHeight();
		Palette palette = new Palette();
		ImageModel model = new ImageModel(width, height, palette);
		
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				Color color = new Color(image.getRGB(x, y));
				model.setColorIndexAt(x, y, palette.addColor(color));
			}
		}
		
		return model;
	}
}
