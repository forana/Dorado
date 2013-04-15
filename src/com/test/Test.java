package com.test;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;

import com.dorado.ui.CanvasPanel;
import com.dorado.ui.ControlPanel;
import com.dorado.ui.PalettePanel;
import com.dorado.ui.StatusPanel;

public class Test {
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			//TODO set some default
		}
		
		JFrame f = new JFrame();
		f.setTitle("Test");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		f.setLocationByPlatform(true);
		f.setMinimumSize(new Dimension(800, 600));
		
		JPanel container = new JPanel();
		f.add(container);
		container.setLayout(new BoxLayout(container, BoxLayout.X_AXIS));
		
		JPanel middlePanel = new JPanel();
		middlePanel.setLayout(new BorderLayout());
		
		ControlPanel controlPanel = new ControlPanel();
		CanvasPanel canvasPanel = new CanvasPanel();
		StatusPanel statusPanel = new StatusPanel();
		PalettePanel palettePanel = new PalettePanel();
		
		container.add(controlPanel);
		middlePanel.add(canvasPanel, BorderLayout.CENTER);
		middlePanel.add(statusPanel, BorderLayout.SOUTH);
		container.add(middlePanel);
		container.add(palettePanel);
		
		f.setVisible(true);
	}
}
