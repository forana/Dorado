package com.dorado.ui.event;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Queue;

// TODO come up with a better name
public class EventManager {
	private Object processingMutex;
	private boolean delegating;
	private Queue<Event> eventQueue;
	
	private Collection<ZoomChangedListener> zoomChangedListeners = new LinkedList<ZoomChangedListener>();
	private Collection<ToolChangedListener> toolChangedListeners = new LinkedList<ToolChangedListener>();
	private Collection<ToolActionAppliedListener> toolActionAppliedListeners = new LinkedList<ToolActionAppliedListener>();
	private Collection<ColorChangedListener> colorChangedListeners = new LinkedList<ColorChangedListener>();

	public EventManager() {
		processingMutex = new Object();
		delegating = false;
		eventQueue = new LinkedList<Event>();
	}
	
	private void delegateEvents() {
		if (!delegating) {
			synchronized (processingMutex) {
				if (!delegating)
					delegating = true;
					
					Event e;
					while ((e = eventQueue.poll()) != null) {
						if (e instanceof ZoomChangedEvent) {
							for (ZoomChangedListener l : zoomChangedListeners) {
								l.handleZoomChanged((ZoomChangedEvent)e);
							}
						} else if (e instanceof ToolChangedEvent) {
							for (ToolChangedListener l : toolChangedListeners) {
								l.handleToolChanged((ToolChangedEvent)e);
							}
						} else if (e instanceof ToolActionAppliedEvent) {
							for (ToolActionAppliedListener l : toolActionAppliedListeners) {
								l.handleToolActionApplied((ToolActionAppliedEvent)e);
							}
						} else if (e instanceof ColorChangedEvent) {
							for (ColorChangedListener l : colorChangedListeners) {
								l.handleColorChanged((ColorChangedEvent)e);
							}
						}
					}
					
					delegating = false;
				}
			}
	}
	
	public void addZoomChangedListener(ZoomChangedListener l) {
		zoomChangedListeners.add(l);
	}
	
	public void addToolChangedListener(ToolChangedListener l) {
		toolChangedListeners.add(l);
	}
	
	public void addToolActionAppliedListener(ToolActionAppliedListener l) {
		toolActionAppliedListeners.add(l);
	}
	
	public void addColorChangedListener(ColorChangedListener l) {
		colorChangedListeners.add(l);
	}
	
	public void fireEvent(Event e) {
		eventQueue.add(e);
		delegateEvents();
	}
}
