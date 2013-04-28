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
import java.io.IOException;

import javax.swing.BoxLayout;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.KeyStroke;

import com.dorado.image.ImageModel;
import com.dorado.image.ImageModelIO;
import com.dorado.tool.ToolAction;
import com.dorado.tool.ToolActionList;
import com.dorado.tool.ToolDirector;
import com.dorado.ui.event.EventManager;
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
	private final EventManager eventManager;
	private final ToolActionList actionList;
		
	public AppWindow(ImageModel imageModel) {
		this.imageModel = imageModel;
		
		eventManager = new EventManager();
		actionList = new ToolActionList(eventManager, this.imageModel);
		
		frame = new JFrame();
		frame.setIconImage(ResourceLoader.loadImage(UIConstants.ICON_PATH));
		setTitle();
		
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
		
		ToolDirector director = new ToolDirector(eventManager, actionList, imageModel);
		
		controlPanel = new ControlPanel(director);
		canvasPanel = new CanvasPanel(eventManager, director, imageModel);
		statusPanel = new StatusPanel(eventManager);
		palettePanel = new PalettePanel(director, imageModel.getPalette());
		
		statusPanel.updateZoom(canvasPanel.getZoom());
		
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
		
		WindowManager.getInstance().register(this);
		
		// show window and attempt to give the user control
		frame.setVisible(true);
		frame.requestFocus();
	}
	
	public JFrame getFrame() {
		return frame;
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
	
	private void setTitle() {
		String title = imageModel.getSource() == null ? "Untitled" : imageModel.getSource().getName();
		if (imageModel.isDirty()) {
			title += "*";
		}
		title += " - " + imageModel.getWidth() + " x " + imageModel.getHeight();
		
		frame.setTitle(title);
	}
	
	/**
	 * Builds up and returns the application menu bar.
	 */
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
			addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					SimpleDialogs.showNewImageDialog(frame);
				}
			});
		}});
		fileMenu.add(new JMenuItem("Open") {{
			setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, mainKey));
			addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						SimpleDialogs.showOpenImageDialog(frame);
					} catch (IOException e2) {
						// TODO what to do here?
						e2.printStackTrace();
					}
				}
			});
		}});
		fileMenu.add(new JMenuItem("Revert"));
		fileMenu.add(new JSeparator());
		fileMenu.add(new JMenuItem("Save") {{
			setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, mainKey));
			addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						if (imageModel.getSource() != null) {
							ImageModelIO.writeImage(imageModel.getSource(), imageModel);
						} else {
							SimpleDialogs.showSaveImageDialog(frame, imageModel);
						}
						setTitle();
					} catch (IOException e2) {
						// TODO what to do here?
						e2.printStackTrace();
					}
				}
			});
		}});
		fileMenu.add(new JMenuItem("Save as...") {{
			setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, mainPlusShiftKey));
			addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						SimpleDialogs.showSaveImageDialog(frame, imageModel);
						setTitle();
					} catch (IOException e2) {
						// TODO what to do here?
						e2.printStackTrace();
					}
				}
			});
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
		editMenu.add(new JMenuItem() {
			@Override
			public String getText() {
				String text = "Undo";
				ToolAction action = actionList.getPrevious();
				if (action != null) {
					text += " " + action.getName();
				}
				return text;
			}
			
			@Override
			public boolean isEnabled() {
				return actionList.getPrevious() != null;
			}
			
			{
				setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, mainKey));
				addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						actionList.stepBack();
					}
				});
		}});
		editMenu.add(new JMenuItem() {
			@Override
			public String getText() {
				String text = "Redo";
				ToolAction action = actionList.getNext();
				if (action != null) {
					text += " " + action.getName();
				}
				return text;
			}
			
			@Override
			public boolean isEnabled() {
				return actionList.getNext() != null;
			}
			
			{
				setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, mainPlusShiftKey));
				addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						actionList.stepForward();
					}
				});
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
			addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					canvasPanel.zoomIn();
				}
			});
		}});
		viewMenu.add(new JMenuItem("Zoom out") {{
			setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_MINUS, mainKey));
			addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					canvasPanel.zoomOut();
				}
			});
		}});
		viewMenu.add(new JMenuItem("Normal Zoom") {{
			setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_0, mainKey));
			addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					canvasPanel.resetZoom();
				}
			});
		}});
		viewMenu.add(new JSeparator());
		viewMenu.add(new JCheckBoxMenuItem("Show grid", imageModel.getGrid().isEnabled()) {{
			addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					boolean newState = !imageModel.getGrid().isEnabled();
					imageModel.getGrid().setEnabled(newState);
					setState(newState);
					canvasPanel.repaint();
				}
			});
		}});
		viewMenu.add(new JMenuItem("Configure grid") {{
			addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					new GridUpdateDialog(frame, imageModel, canvasPanel);
				}
			});
		}});
		return viewMenu;
	}

	private JMenu buildWindowMenu(final int mainKey) {
		return new WindowMenu("Window", this);
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
