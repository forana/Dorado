package com.dorado.ui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import com.dorado.image.ImageModel;
import com.dorado.image.Palette;

public class NewImageDialog extends JDialog {
	private static final long serialVersionUID = 1L;
	
	public NewImageDialog(JFrame owner) {
		super(owner, "New Image", true);
		setLocationByPlatform(true);
		
		buildUI();
		
		setVisible(true);
	}
	
	@SuppressWarnings("serial")
	private void buildUI() {
		setSize(200, 200);
		
		final JCheckBox prepopulateBox = new JCheckBox("Prepopulate palette", true);
		final JSpinner widthSpinner = new JSpinner(new SpinnerNumberModel(64, 1, 9999, 1));
		final JSpinner heightSpinner = new JSpinner(new SpinnerNumberModel(64, 1, 9999, 1));
		
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.gridwidth = 1;
		c.gridheight = 1;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.LINE_START;
		c.weightx = 0;
		c.weighty = 0;
		c.insets = new Insets(10, 10, 10, 10);
		
		c.gridx = 0;
		c.gridy = 0;
		add(new JLabel("Width"), c);
		
		c.gridx = 1;
		add(widthSpinner);
		
		c.gridx = 0;
		c.gridy = 1;
		add(new JLabel("Height"), c);
		
		c.gridx = 1;
		add(heightSpinner, c);
		
		c.gridx = 0;
		c.gridy = 2;
		c.gridwidth = 2;
		add(prepopulateBox, c);
		
		c.gridy = 3;
		c.gridwidth = 1;
		add(new JButton() {{
			setText("Create");
			addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					dispose();
					int width = (Integer)widthSpinner.getValue();
					int height = (Integer)heightSpinner.getValue();
					Palette palette = prepopulateBox.isSelected() ? Palette.createSteppedPalette(3) : new Palette();
					new AppWindow(new ImageModel(width, height, palette));
				}
			});
		}}, c);
		
		c.gridx = 1;
		add(new JButton() {{
			setText("Cancel");
			addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					dispose();
				}
			});
		}}, c);
	}
}
