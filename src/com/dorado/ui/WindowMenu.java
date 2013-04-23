package com.dorado.ui;

import java.awt.Component;
import java.awt.Toolkit;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

public class WindowMenu extends JMenu {
	private static final long serialVersionUID = 1L;
	
	private AppWindow home;
	
	public WindowMenu(String title, AppWindow home) {
		super(title);
		this.home = home;
	}
	
	@Override
	public int getItemCount() {
		return WindowManager.getInstance().getWindows().size();
	}
	
	@Override
	public JMenuItem getItem(int pos) {
		AppWindow window = WindowManager.getInstance().getWindows().get(pos);
		return new WindowItem(window, home == window, pos + 1);
	}
	
	@Override
	public int getMenuComponentCount() {
		return getItemCount();
	}
	
	@Override
	public Component getMenuComponent(int pos) {
		return getItem(pos);
	}
	
	@Override
	public Component[] getMenuComponents() {
		Component[] ret = new Component[WindowManager.getInstance().getWindows().size()];
		for (int i=0; i < ret.length; i++) {
			ret[i] = getItem(i);
		}
		return ret;	
	}
	
	private static class WindowItem extends JCheckBoxMenuItem {
		private static final long serialVersionUID = 1L;
		
		public WindowItem(AppWindow w, boolean selected, int index) {
			super(w.getFrame().getTitle());
			setState(selected);
			if (index < 10) {
				setAccelerator(KeyStroke.getKeyStroke(Integer.toString(index).charAt(0), Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
			}
		}
	}
}
