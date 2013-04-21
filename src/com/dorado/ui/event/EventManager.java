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
						}
					}
					
					delegating = false;
				}
			}
	}
	
	public void addZoomChangedListener(ZoomChangedListener l) {
		zoomChangedListeners.add(l);
	}
	
	public void fireZoomChangedEvent(ZoomChangedEvent e) {
		eventQueue.add(e);
		delegateEvents();
	}
}
