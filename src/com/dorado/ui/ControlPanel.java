package com.dorado.ui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.List;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JToggleButton;

import com.dorado.tool.DropperTool;
import com.dorado.tool.FillTool;
import com.dorado.tool.PencilTool;
import com.dorado.tool.Tool;
import com.dorado.tool.ToolDirector;
import com.dorado.ui.event.EventManager;
import com.forana.layout.BlockLayout;

/**
 * Panel for displaying tools to the user.
 */
public class ControlPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private static final int WIDTH = 50;

	public ControlPanel(EventManager manager, ToolDirector director) {
		setMinimumSize(new Dimension(WIDTH, 0));
		//setPreferredSize(new Dimension(WIDTH, 0));
		setMaximumSize(new Dimension(WIDTH, Integer.MAX_VALUE));
		setBackground(UIConstants.PANEL_COLOR);
		
		//setLayout(new FlowLayout(FlowLayout.CENTER, 10, 2));
		setLayout(new BlockLayout());
		
		this.populateToolButtons(manager, director);
	}
	
	private void populateToolButtons(EventManager manager, ToolDirector director) {
		List<AbstractButton> buttons = new LinkedList<AbstractButton>();
		
		/*buttons.add(new JToggleButton() {{
			setIcon(new ImageIcon(ResourceLoader.loadImage("resources/icons/select-icon.png")));
			setToolTipText("Select rectangle");
		}});
		buttons.add(new JToggleButton() {{
			setIcon(new ImageIcon(ResourceLoader.loadImage("resources/icons/select-continuous-area-icon.png")));
			setToolTipText("Select color region");
		}});
		buttons.add(new JToggleButton() {{
			setIcon(new ImageIcon(ResourceLoader.loadImage("resources/icons/select-by-color-icon.png")));
			setToolTipText("Select all of color");
		}});
		buttons.add(new JToggleButton() {{
			setIcon(new ImageIcon(ResourceLoader.loadImage("resources/icons/transform-move-icon.png")));
			setToolTipText("Move");
		}});
		buttons.add(new JToggleButton() {{
			setIcon(new ImageIcon(ResourceLoader.loadImage("resources/icons/paintcan-icon.png")));
			setToolTipText("Fill");
		}});
		*/
		
		Tool pencilTool = new PencilTool();
		director.setTool(pencilTool);
		ToolButton toolButton = new ToolButton(pencilTool, director);
		toolButton.setSelected(true);
		buttons.add(toolButton);
		
		buttons.add(new ToolButton(new FillTool(), director));
		buttons.add(new ToolButton(new DropperTool(manager), director));
		
		ButtonGroup group = new ButtonGroup();
		
		for (AbstractButton button : buttons) {
			add(button);
			group.add(button);
		}
	}
	
	private static class ToolButton extends JToggleButton {
		private static final long serialVersionUID = 1L;
		
		public ToolButton(final Tool tool, final ToolDirector director) {
			setIcon(tool.getIcon());
			setToolTipText(tool.getName());
			addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					director.setTool(tool);
				}
			});
		}
	}
}
