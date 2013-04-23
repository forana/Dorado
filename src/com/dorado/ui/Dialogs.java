package com.dorado.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;

import com.dorado.image.ImageModel;
import com.dorado.image.Palette;

public final class Dialogs {
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
					new AppWindow(new ImageModel(64, 64, Palette.TRANSPARENT_INDEX, Palette.createSteppedPalette(3)));
				}
			});
		}});
		
		dialog.setVisible(true);
	}
}
