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

import com.dorado.image.ImageModel;
import com.dorado.util.OS;
import com.dorado.util.ResourceLoader;

/**
 * Represents both a window and the work-in-progress it contains.
 */
public class AppWindow {
	private ControlPanel controlPanel = null;
	private CanvasPanel canvasPanel = null;
	private StatusPanel statusPanel = null;
	private PalettePanel palettePanel = null;
	
	private final JFrame frame;
	private final ImageModel imageModel;
		
	public AppWindow(ImageModel imageModel) {
		this.imageModel = imageModel;
		
		frame = new JFrame();
		frame.setTitle("Untitled - " + UIConstants.APP_TITLE);
		frame.setIconImage(ResourceLoader.loadImage(UIConstants.ICON_PATH));
		
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
		// TODO why does this always go to 0, 0 on OSX?
		frame.setLocationByPlatform(true);
		// TODO size this better
		frame.setSize(new Dimension(800, 600));
		frame.setMinimumSize(new Dimension(600, 400));
		frame.setJMenuBar(createMenuBar());
		
		// create a root panel to work with
		JPanel container = new JPanel();
		frame.add(container);
		container.setLayout(new BoxLayout(container, BoxLayout.X_AXIS));
		
		// need a container for the two middle components so they can be arranged properly
		JPanel middlePanel = new JPanel();
		middlePanel.setLayout(new BorderLayout());
		
		controlPanel = new ControlPanel();
		canvasPanel = new CanvasPanel(imageModel);
		statusPanel = new StatusPanel();
		palettePanel = new PalettePanel(imageModel.getPalette());
		
		container.add(controlPanel);
		middlePanel.add(canvasPanel, BorderLayout.CENTER);
		middlePanel.add(statusPanel, BorderLayout.SOUTH);
		container.add(middlePanel);
		container.add(palettePanel);
		
		// tie close method to event
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				closeWindow();
			}
		});
		
		// show window and attempt to give the user control
		frame.setVisible(true);
		frame.requestFocus();
	}
	
	/**
	 * Attempt to close this window. If the image data has been changed, the user will be prompted to confirm
	 * the closing. If they do not confirm this, the window will not be closed. In all other cases, it will
	 * be disposed.
	 */
	private void closeWindow() {
		if (!imageModel.isDirty() || confirmCloseWindow()) {
			frame.dispose();
		}
	}
	
	/**
	 * Show confirmation to user, and return true if the user accepts.
	 */
	private boolean confirmCloseWindow() {
		int result = JOptionPane.showConfirmDialog(frame,
			"Do you really want to close this window? Your changes will not be saved.",
			"Really close?",
			JOptionPane.YES_NO_OPTION);
		return result == JOptionPane.YES_OPTION;
	}
	
	/**
	 * Builds up and returns the application menu bar.
	 */
	@SuppressWarnings("serial")
	private JMenuBar createMenuBar() {
		JMenuBar bar = new JMenuBar();
		
		// platform-specific hotkey assignments
		final int mainKey = Toolkit.getDefaultToolkit().getMenuShortcutKeyMask();
		final int mainPlusShiftKey = Toolkit.getDefaultToolkit().getMenuShortcutKeyMask() | InputEvent.SHIFT_MASK;
		
		bar.add(buildFileMenu(mainKey, mainPlusShiftKey));
		bar.add(buildEditMenu(mainKey, mainPlusShiftKey));
		bar.add(buildViewMenu(mainKey));
		bar.add(buildWindowMenu(mainKey));
		bar.add(buildHelpMenu());
		
		return bar;
	}
	
	@SuppressWarnings("serial")
	private JMenu buildFileMenu(final int mainKey, final int mainPlusShiftKey) {
		final JMenu fileMenu = new JMenu("File");
		fileMenu.add(new JMenuItem("New") {{
			setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, mainKey));
		}});
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
		fileMenu.add(new JMenuItem("Export") {{
			setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, mainKey));
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
				setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4, InputEvent.ALT_MASK));
			}});
		}
		return fileMenu;
	}

	@SuppressWarnings("serial")
	private JMenu buildEditMenu(final int mainKey, final int mainPlusShiftKey) {
		final JMenu editMenu = new JMenu("Edit");
		editMenu.add(new JMenuItem("Undo") {{
			setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, mainKey));
		}});
		editMenu.add(new JMenuItem("Redo") {{
			setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, mainPlusShiftKey));
		}});
		editMenu.add(new JSeparator());
		editMenu.add(new JMenuItem("Copy") {{
			setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, mainKey));
		}});
		editMenu.add(new JMenuItem("Cut") {{
			setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, mainKey));
		}});
		editMenu.add(new JMenuItem("Paste") {{
			setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, mainKey));
		}});
		editMenu.add(new JSeparator());
		editMenu.add(new JMenuItem("Select all") {{
			setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, mainKey));
		}});
		editMenu.add(new JMenuItem("Select none") {{
			setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, mainPlusShiftKey));
		}});
		return editMenu;
	}

	@SuppressWarnings("serial")
	private JMenu buildViewMenu(final int mainKey) {
		final JMenu viewMenu = new JMenu("View");
		viewMenu.add(new JMenuItem("Zoom in") {{
			setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_EQUALS, mainKey));
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
		return viewMenu;
	}

	@SuppressWarnings("serial")
	private JMenu buildWindowMenu(final int mainKey) {
		final JMenu windowMenu = new JMenu("Window");
		windowMenu.add(new JMenuItem("1. Untitled") {{
			setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_1, mainKey));
		}});
		return windowMenu;
	}
	
	@SuppressWarnings("serial")
	private JMenu buildHelpMenu() {
		final JMenu helpMenu = new JMenu("Help");
		helpMenu.add(new JMenuItem("Website...") {{
			setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0));
		}});
		return helpMenu;
	}
}
