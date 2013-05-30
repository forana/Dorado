package com.dorado.tool;

import com.dorado.image.ImageModel;
import com.dorado.ui.event.EventManager;
import com.dorado.ui.event.ToolActionAppliedEvent;

/**
 * Represents a stack of ToolActions that have been applied to an ImageModel over time.
 * The main purpose of this is to have a history of actions that can be undone and redone by the user. 
 *
 */
public class ToolActionList {
	private ToolActionNode current;
	private EventManager manager;
	private ImageModel model;
	
	public ToolActionList(EventManager manager, ImageModel model) {
		current = new ToolActionNode(null, new NullAction());
		this.manager = manager;
		this.model = model;
	}
	
	public ToolAction getPrevious() {
		return current.action instanceof NullAction ? null : current.action;
	}
	
	public ToolAction getNext() {
		return current.next == null ? null : current.next.action;
	}
	
	/**
	 * Adds a new ToolAction to the top of the stack, and applies its effect to the ImageModel
	 */
	public void addAndApply(ToolAction action) {
		current = new ToolActionNode(current, action);
		action.applyNew(model);
		
		manager.fireEvent(new ToolActionAppliedEvent());
	}
	
	/**
	 * Undoes the last ToolAction performed.
	 * Moves back one ToolAction from the history, but remembers that ToolAction so it can be redone later.
	 */
	public void stepBack() {
		current.action.applyOriginal(model);
		current = current.previous;

		manager.fireEvent(new ToolActionAppliedEvent());
	}
	
	/**
	 * Executes a redo of the last ToolAction that was undone.
	 */
	public void stepForward() {
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
	
	private static class NullAction extends ToolAction {

		@Override
		public void applyOriginal(ImageModel model) {
		}

		@Override
		public void applyNew(ImageModel model) {
		}
	}
}
