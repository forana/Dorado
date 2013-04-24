package com.dorado.ui;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.dorado.image.Grid;
import com.dorado.image.ImageModel;

public class GridUpdateDialog extends JDialog {
	private static final long serialVersionUID = 1L;
	
	private Grid clone;
	
	public GridUpdateDialog(JFrame owner, ImageModel model, Component canvas) {
		super(owner, "Configure Grid", true);
		setLocationByPlatform(true);
		
		clone = (Grid)model.getGrid().clone();
		buildUI(model, canvas);
		
		setVisible(true);
	}
	
	@SuppressWarnings("serial")
	private void buildUI(final ImageModel imageModel, final Component canvas) {
		setSize(200, 250);
		setResizable(false);
		GridBagLayout bag = new GridBagLayout();
		setLayout(bag);
		GridBagConstraints c = new GridBagConstraints();
		
		c.fill = GridBagConstraints.NONE;
		c.anchor = GridBagConstraints.LINE_START;
		c.insets = new Insets(2, 2, 2, 2);
		
		c.gridx = 0;
		c.gridy = 0;
		add(new JLabel("X Spacing"), c);
		
		c.gridx = 1;
		add(new JSpinner(
			new SpinnerNumberModel(imageModel.getGrid().getXSpacing(), 1, imageModel.getWidth(), 1)) {{
				addChangeListener(new ChangeListener() {
					public void stateChanged(ChangeEvent e) {
						imageModel.getGrid().setXSpacing((Integer)getValue());
						canvas.repaint();
					}
				});
			}}, c);
		
		c.gridx = 0;
		c.gridy = 1;
		add(new JLabel("Y Spacing"), c);
		
		c.gridx = 1;
		add(new JSpinner(
			new SpinnerNumberModel(imageModel.getGrid().getYSpacing(), 1, imageModel.getHeight(), 1)) {{
				addChangeListener(new ChangeListener() {
					public void stateChanged(ChangeEvent e) {
						imageModel.getGrid().setYSpacing((Integer)getValue());
						canvas.repaint();
					}
				});
			}}, c);
		
		c.gridx = 0;
		c.gridy = 2;
		add(new JLabel("X Offset"), c);
		
		c.gridx = 1;
		add(new JSpinner(
			new SpinnerNumberModel(imageModel.getGrid().getXOffset(), 0, imageModel.getWidth() - 1, 1)) {{
				addChangeListener(new ChangeListener() {
					public void stateChanged(ChangeEvent e) {
						imageModel.getGrid().setXOffset((Integer)getValue());
						canvas.repaint();
					}
				});
			}}, c);
		
		c.gridx = 0;
		c.gridy = 3;
		add(new JLabel("Y Offset"), c);
		
		c.gridx = 1;
		add(new JSpinner(
			new SpinnerNumberModel(imageModel.getGrid().getYOffset(), 0, imageModel.getHeight() - 1, 1)) {{
				addChangeListener(new ChangeListener() {
					public void stateChanged(ChangeEvent e) {
						imageModel.getGrid().setYOffset((Integer)getValue());
						canvas.repaint();
					}
				});
			}}, c);
		
		c.gridx = 0;
		c.gridy = 4;
		add(new JLabel("Line Style"), c);
		
		c.gridx = 1;
		add(new JComboBox<StrokeStyle>(new StrokeStyle[] {StrokeStyle.SOLID, StrokeStyle.DASHED}) {{
			setSelectedIndex(imageModel.getGrid().getStrokeStyleIndex() == StrokeStyle.SOLID.index ? 0 : 1);
			addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					imageModel.getGrid().setStrokeStyleIndex(getItemAt(getSelectedIndex()).index);
					canvas.repaint();
				}
			});
		}}, c);
		
		c.gridx = 0;
		c.gridy = 5;
		add(new JButton("Ok") {{
			addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					dispose();
				}
			});
		}}, c);
		
		c.gridx = 1;
		add(new JButton("Cancel") {{
			addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					imageModel.setGrid(clone);
					canvas.repaint();
					dispose();
				}
			});
		}}, c);
	}
	
	private static class StrokeStyle {
		public final int index;
		private String name;
		
		public static StrokeStyle SOLID = new StrokeStyle(Grid.STROKE_SOLID, "Solid");
		public static StrokeStyle DASHED = new StrokeStyle(Grid.STROKE_DASHED, "Dashed");
		
		private StrokeStyle(int index, String name) {
			this.index = index;
			this.name = name;
		}
		
		public String toString() {
			return name;
		}
	}
}
