package com.dorado.ui;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.Border;

import com.dorado.image.ImageModel;
import com.dorado.tool.ToolDirector;
import com.dorado.ui.event.EventManager;
import com.dorado.ui.event.ToolActionAppliedEvent;
import com.dorado.ui.event.ToolActionAppliedListener;
import com.dorado.ui.event.ToolChangedEvent;
import com.dorado.ui.event.ToolChangedListener;
import com.dorado.ui.event.ZoomChangedEvent;

/**
 * Panel for displaying and interacting with the image data.
 */
public class CanvasPanel extends JScrollPane implements ToolChangedListener, ToolActionAppliedListener {
	private static final long serialVersionUID = 1L;
	
	private ZoomableCanvas canvas;
	private EventManager eventManager;
	
	public CanvasPanel(EventManager eventManager, ToolDirector director, ImageModel model) {
		this.eventManager = eventManager;
		
		getViewport().setBackground(UIConstants.EMPTY_COLOR);
		
		setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		
		canvas = new ZoomableCanvas(model, 8);
		
		setViewportView(canvas);
		
		Border border = BorderFactory.createBevelBorder(2);
		setColumnHeaderView(new Ruler.Horizontal(canvas));
		getColumnHeader().setBorder(border);
		setRowHeaderView(new Ruler.Vertical(canvas));
		getRowHeader().setBorder(border);
		
		setCorners();
		
		canvas.bindMouseEvents(director);
		eventManager.addToolChangedListener(this);
		eventManager.addToolActionAppliedListener(this);
	}
	
	@SuppressWarnings("serial")
	private void setCorners() {
		setCorner(JScrollPane.UPPER_LEFT_CORNER, new JPanel() {
			public void paint(Graphics g) {
				int width = getSize().width, height = getSize().height;
				g.setColor(UIConstants.PANEL_COLOR);
				g.fillRect(0, 0, width, height);
				g.setColor(UIConstants.TEXT_COLOR);
				g.drawLine(0, height - 1, width, height - 1);
				g.drawLine(width - 1, 0, width - 1, height);
			}
		});
		
		setCorner(JScrollPane.UPPER_RIGHT_CORNER, new JPanel() {
			public void paint(Graphics g) {
				int width = getSize().width, height = getSize().height;
				g.setColor(UIConstants.PANEL_COLOR);
				g.fillRect(0, 0, width, height);
			}
		});
		
		setCorner(JScrollPane.LOWER_LEFT_CORNER, new JPanel() {
			public void paint(Graphics g) {
				int width = getSize().width, height = getSize().height;
				g.setColor(UIConstants.PANEL_COLOR);
				g.fillRect(0, 0, width, height);
			}
		});
		
		setCorner(JScrollPane.LOWER_RIGHT_CORNER, new JButton() {{
			setText("0");
			addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					resetZoom();
				}
			});
		}});
	}
	
	public int getZoom() {
		return canvas.getZoom();
	}
	
	public void zoomIn() {
		canvas.zoomIn();
		zoomChanged();
	}
	
	public void zoomOut() {
		canvas.zoomOut();
		zoomChanged();
	}
	
	public void resetZoom() {
		canvas.resetZoom();
		zoomChanged();
	}
	
	private void zoomChanged() {
		eventManager.fireEvent(new ZoomChangedEvent(canvas.getZoom()));
		
		forceRerender();
	}
	
	private void forceRerender() {
		// forces scrollbars to recalculate
		setViewportView(canvas);
		// forces rulers and canvas to repaint
		repaint();
	}
	
	@Override
	public void handleToolChanged(ToolChangedEvent e) {
		// TODO uncomment this when we have actual cursor images
		//canvas.setCursor(e.tool.getCursor());
	}
	
	@Override
	public void handleToolActionApplied(ToolActionAppliedEvent e) {
		forceRerender();
	}
}
