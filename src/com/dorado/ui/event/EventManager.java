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
	private Collection<ColorChangedListener> colorChangedListeners = new LinkedList<ColorChangedListener>();
	private Collection<ToolActionAppliedListener> toolActionAppliedListeners = new LinkedList<ToolActionAppliedListener>();

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
						} else if (e instanceof ColorChangedEvent) {
							for (ColorChangedListener l : colorChangedListeners) {
								l.handleColorChanged((ColorChangedEvent)e);
							}
						} else if (e instanceof ToolActionAppliedEvent) {
							for (ToolActionAppliedListener l : toolActionAppliedListeners) {
								l.handleToolActionApplied((ToolActionAppliedEvent)e);
							}
						}
					}
					
					delegating = false;
				}
			}
	}
	
	public void addListener(ZoomChangedListener l) {
		zoomChangedListeners.add(l);
	}
	
	public void addListener(ColorChangedListener l) {
		colorChangedListeners.add(l);
	}
	
	public void addListener(ToolActionAppliedListener l) {
		toolActionAppliedListeners.add(l);
	}
	
	public void fireEvent(Event e) {
		eventQueue.add(e);
		delegateEvents();
	}
}
