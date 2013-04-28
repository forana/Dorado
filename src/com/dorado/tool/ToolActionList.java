package com.dorado.tool;

import com.dorado.image.ImageModel;
import com.dorado.ui.event.EventManager;
import com.dorado.ui.event.ToolActionAppliedEvent;

public class ToolActionList {
	private ToolActionNode current;
	private EventManager manager;
	private ImageModel model;
	
	public ToolActionList(EventManager manager, ImageModel model) {
		current = null;
		this.manager = manager;
		this.model = model;
	}
	
	public ToolAction getPrevious() {
		return current == null ? null : current.action;
	}
	
	public ToolAction getNext() {
		return current == null ? null : (current.next == null ? null : current.next.action);
	}
	
	public void addAndApply(ToolAction action) {
		current = new ToolActionNode(current, action);
		action.applyNew(model);
		
		manager.fireEvent(new ToolActionAppliedEvent());
	}
	
	public void stepBack() {
		current.action.applyOriginal(model);
		current = current.previous;
		if (current != null) {
			current.action.applyNew(model);
		}
		
		manager.fireEvent(new ToolActionAppliedEvent());
	}
	
	public void stepForward() {
		current.action.applyOriginal(model);
		current = current.next;
		if (current != null) {
			current.action.applyNew(model);
		}
		
		manager.fireEvent(new ToolActionAppliedEvent());
	}
	
	private static class ToolActionNode {
		public final ToolActionNode previous;
		public ToolActionNode next;
		
		public final ToolAction action;
		
		public ToolActionNode(ToolActionNode previous, ToolAction action) {
			this.previous = previous;
			if (this.previous != null) {
				this.previous.next = this;
			}
			next = null;
			this.action = action;
		}
	}
}
