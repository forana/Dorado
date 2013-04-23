package com.dorado.ui;

import java.awt.Dimension;
import java.util.LinkedList;
import java.util.List;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JToggleButton;

import com.dorado.ui.event.EventManager;
import com.dorado.util.ResourceLoader;
import com.forana.layout.BlockLayout;

/**
 * Panel for displaying tools to the user.
 */
public class ControlPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private static final int WIDTH = 50;

	public ControlPanel(EventManager eventManager) {
		setMinimumSize(new Dimension(WIDTH, 0));
		//setPreferredSize(new Dimension(WIDTH, 0));
		setMaximumSize(new Dimension(WIDTH, Integer.MAX_VALUE));
		setBackground(UIConstants.PANEL_COLOR);
		
		//setLayout(new FlowLayout(FlowLayout.CENTER, 10, 2));
		setLayout(new BlockLayout());
		
		this.populateToolButtons();
	}
	
	@SuppressWarnings("serial")
	private void populateToolButtons() {
		List<AbstractButton> buttons = new LinkedList<AbstractButton>();
		
		buttons.add(new JToggleButton() {{
			setIcon(new ImageIcon(ResourceLoader.loadImage("resources/icons/test24.png")));
			setToolTipText("Select rectangle");
		}});
		buttons.add(new JToggleButton() {{
			setIcon(new ImageIcon(ResourceLoader.loadImage("resources/icons/test24.png")));
			setToolTipText("Select color region");
		}});
		buttons.add(new JToggleButton() {{
			setIcon(new ImageIcon(ResourceLoader.loadImage("resources/icons/test24.png")));
			setToolTipText("Select all of color");
		}});
		buttons.add(new JToggleButton() {{
			setIcon(new ImageIcon(ResourceLoader.loadImage("resources/icons/test24.png")));
			setToolTipText("Move");
		}});
		buttons.add(new JToggleButton() {{
			setIcon(new ImageIcon(ResourceLoader.loadImage("resources/icons/test24.png")));
			setToolTipText("Color picker");
		}});
		buttons.add(new JToggleButton() {{
			setIcon(new ImageIcon(ResourceLoader.loadImage("resources/icons/test24.png")));
			setToolTipText("Pixel");
			setSelected(true);
		}});
		buttons.add(new JToggleButton() {{
			setIcon(new ImageIcon(ResourceLoader.loadImage("resources/icons/test24.png")));
			setToolTipText("Fill");
		}});
		buttons.add(new JToggleButton() {{
			setIcon(new ImageIcon(ResourceLoader.loadImage("resources/icons/test24.png")));
			setToolTipText("Erase");
		}});
		
		ButtonGroup group = new ButtonGroup();
		
		for (AbstractButton button : buttons) {
			add(button);
			group.add(button);
		}
	}
}
