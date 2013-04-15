package com.dorado.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.KeyStroke;

import com.dorado.osutil.OS;

/**
 * Represents both a window and the work-in-progress it contains.
 */
public class AppWindow {
	private ControlPanel controlPanel = null;
	private CanvasPanel canvasPanel = null;
	private StatusPanel statusPanel = null;
	private PalettePanel palettePanel = null;
	
	private boolean dataChanged;
	
	private final JFrame frame;
		
	public AppWindow() {
		this.frame = new JFrame();
		this.frame.setTitle("Untitled - " + UIConstants.APP_TITLE);
		this.frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
		this.frame.setLocationByPlatform(true);
		this.frame.setMinimumSize(new Dimension(800, 600));
		this.frame.setJMenuBar(createMenuBar());
		
		JPanel container = new JPanel();
		this.frame.add(container);
		container.setLayout(new BoxLayout(container, BoxLayout.X_AXIS));
		
		JPanel middlePanel = new JPanel();
		middlePanel.setLayout(new BorderLayout());
		
		this.controlPanel = new ControlPanel();
		this.canvasPanel = new CanvasPanel();
		this.statusPanel = new StatusPanel();
		this.palettePanel = new PalettePanel();
		
		container.add(this.controlPanel);
		middlePanel.add(this.canvasPanel, BorderLayout.CENTER);
		middlePanel.add(this.statusPanel, BorderLayout.SOUTH);
		container.add(middlePanel);
		container.add(this.palettePanel);
		
		dataChanged = true;
		
		this.frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				closeWindow();
			}
		});
		this.frame.setVisible(true);
		this.frame.requestFocus();
	}
	
	private void closeWindow() {
		if (!dataChanged || confirmCloseWindow()) {
			frame.dispose();
		}
	}
	
	private boolean confirmCloseWindow() {
		int result = JOptionPane.showConfirmDialog(this.frame,
			"Do you really want to close this window? Your changes will not be saved.",
			"Really close?",
			JOptionPane.YES_NO_OPTION);
		return result == JOptionPane.YES_OPTION;
	}
	
	@SuppressWarnings("serial")
	private JMenuBar createMenuBar() {
		JMenuBar bar = new JMenuBar();
		
		final int mainKey = Toolkit.getDefaultToolkit().getMenuShortcutKeyMask();
		final int mainPlusShiftKey = Toolkit.getDefaultToolkit().getMenuShortcutKeyMask() | InputEvent.SHIFT_MASK;
		final int noKey = 0;
		
		final JMenu fileMenu = new JMenu("File");
		fileMenu.add(new JMenuItem("Open") {{
			setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, mainKey));
			addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					//
				}
			});
		}});
		fileMenu.add(new JMenuItem("Revert"));
		fileMenu.add(new JSeparator());
		fileMenu.add(new JMenuItem("Save") {{
			setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, mainKey));
		}});
		fileMenu.add(new JMenuItem("Save as...") {{
			setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, mainPlusShiftKey));
		}});
		fileMenu.add(new JSeparator());
		fileMenu.add(new JMenuItem("Close Window") {{
			setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W, mainKey));
			addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					closeWindow();
				}
			});
		}});
		// OSX contains this in the application menu
		if (!OS.IS_OSX) {
			fileMenu.add(new JMenuItem("Exit") {{
				setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, mainPlusShiftKey));
			}});
		}
		bar.add(fileMenu);
		
		final JMenu editMenu = new JMenu("Edit");
		editMenu.add(new JMenuItem("Undo") {{
			setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, mainKey));
		}});
		editMenu.add(new JMenuItem("Redo") {{
			setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, mainPlusShiftKey));
		}});
		editMenu.add(new JSeparator());
		editMenu.add(new JMenuItem("Select all") {{
			setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, mainKey));
		}});
		editMenu.add(new JMenuItem("Select none") {{
			setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, mainPlusShiftKey));
		}});
		bar.add(editMenu);
		
		final JMenu viewMenu = new JMenu("View");
		viewMenu.add(new JMenuItem("Zoom in") {{
			setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_PLUS, mainKey));
		}});
		viewMenu.add(new JMenuItem("Zoom out") {{
			setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_MINUS, mainKey));
		}});
		viewMenu.add(new JMenuItem("Normal Zoom") {{
			setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_0, mainKey));
		}});
		viewMenu.add(new JSeparator());
		viewMenu.add(new JMenuItem("Show grid"));
		viewMenu.add(new JMenuItem("Configure grid"));
		bar.add(viewMenu);
		
		final JMenu windowMenu = new JMenu("Window");
		windowMenu.add(new JMenuItem("1. Untitled") {{
			setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_1, mainKey));
		}});
		bar.add(windowMenu);
		
		final JMenu helpMenu = new JMenu("Help");
		helpMenu.add(new JMenuItem("Website...") {{
			setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F1, noKey));
		}});
		bar.add(helpMenu);
		
		return bar;
	}
}
