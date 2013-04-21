package com.dorado.ui;

import java.awt.BorderLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.dorado.ui.event.EventManager;
import com.dorado.ui.event.ZoomChangedEvent;
import com.dorado.ui.event.ZoomChangedListener;

/**
 * Panel for showing status messages to the user.
 */
public class StatusPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private JLabel textLabel;
	private JLabel zoomLabel;
	
	public StatusPanel(EventManager eventManager) {
		setBackground(UIConstants.PANEL_COLOR);
		setLayout(new BorderLayout());
		setBorder(BorderFactory.createLoweredBevelBorder());
		
		textLabel = new JLabel(" ");
		textLabel.setFont(UIConstants.FONT_NORMAL);
		add(textLabel, BorderLayout.WEST);
		
		zoomLabel = new JLabel(" ");
		zoomLabel.setFont(UIConstants.FONT_NORMAL);
		add(zoomLabel, BorderLayout.EAST);
		
		eventManager.addZoomChangedListener(new ZoomChangedListener() {
			public void handleZoomChanged(ZoomChangedEvent e) {
				updateZoom(e.zoomLevel);
			}
		});
	}
	
	/**
	 * Set the text to display.
	 */
	public void setText(String text) {
		textLabel.setText(text);
	}
	
	public void updateZoom(int zoomFactor) {
		zoomLabel.setText("Zoom: " + zoomFactor + "x");
	}	
}
