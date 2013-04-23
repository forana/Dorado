package com.dorado.image;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

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
}
