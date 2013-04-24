package com.dorado.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.dorado.image.ImageModel;
import com.dorado.image.ImageModelIO;
import com.dorado.image.Palette;

public final class SimpleDialogs {
	private static final String MODEL_EXTENSION = "ped";
	private static final String EXPORT_EXTENSION = "png";
	
	@SuppressWarnings("serial")
	public static void showNewImageDialog(JFrame owner) {
		final JDialog dialog = new JDialog(owner, "New Image", true);
		dialog.setLocationByPlatform(true);
		dialog.setSize(200, 60);
		dialog.add(new JButton() {{
			setText("TODO options");
			addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					dialog.dispose();
					new AppWindow(new ImageModel(64, 64, Palette.createSteppedPalette(3)));
				}
			});
		}});
		
		dialog.setVisible(true);
	}
	
	public static void showSaveImageDialog(JFrame owner, ImageModel model) throws IOException {
		JFileChooser chooser = model.getSource() != null ? new JFileChooser(model.getSource().getParentFile()) : new JFileChooser();
		chooser.setFileFilter(new FileNameExtensionFilter("Dorado Image File", MODEL_EXTENSION));
		if (chooser.showSaveDialog(owner) == JFileChooser.APPROVE_OPTION) {
			File file = chooser.getSelectedFile();
			ImageModelIO.writeImage(file, model);
			model.setSource(file);
			model.setNotDirty();
		}
	}
	
	public static void showOpenImageDialog(JFrame owner) throws IOException {
		JFileChooser chooser = new JFileChooser();
		chooser.setFileFilter(new FileNameExtensionFilter("All accepted images", MODEL_EXTENSION, EXPORT_EXTENSION));
		if (chooser.showOpenDialog(owner) == JFileChooser.APPROVE_OPTION) {
			File file = chooser.getSelectedFile();
			ImageModel model;
			if (file.getName().toLowerCase().endsWith(MODEL_EXTENSION)) {
				model = ImageModelIO.readImage(file);
			} else {
				model = ImageModelIO.importImage(file);
			}
			new AppWindow(model);
		}
	}
}
