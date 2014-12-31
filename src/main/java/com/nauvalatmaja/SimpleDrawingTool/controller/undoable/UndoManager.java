package com.nauvalatmaja.SimpleDrawingTool.controller.undoable;

import java.util.ArrayList;
import java.util.List;

/**
 * Undo manager that manage undo(s) and redo(s).
 * Based on article by Greg Cope http://www.algosome.com/articles/implementing-undo-redo-java.html
 * @author nauval
 *
 */
public class UndoManager {
	
	private List<UndoableCommand> commands;
	private int index;
	
	public UndoManager() {
		init();
	}
	
	private void init() {
		if (commands == null) {
			commands = new ArrayList<UndoableCommand>();
		} else {
			commands.clear();
		}
		index = -1;
	}
	
	/**
	 * Puts undoable command to the list and remove any commands after
	 * @param command
	 */
	public void putCommand(UndoableCommand command) {
		index++;
		commands.add(index, command);
		removeCommandsAfter();
	}

	/**
	 * Executes undo command
	 * @throws UndoException 
	 */
	public String doUndo() throws UndoException {
		if (isUndoable()) {
			UndoableCommand c = commands.get(index);
			c.undo();
			index--;
			return c.getActionName();
		} else {
			throw new UndoException("No action to perform");
		}
	}
	
	/**
	 * Executes redo command
	 * @throws UndoException 
	 */
	public String doRedo() throws UndoException {
		if (isRedoable()) {
			index++;
			UndoableCommand c = commands.get(index);
			c.redo();
			return c.getActionName();
		} else {
			throw new UndoException("No action to perform");
		}
	}
	
	public boolean isUndoable() {
		return (index >= 0);
	}
	
	public boolean isRedoable() {
		return (index < commands.size() - 1 && commands.size() > 0);
	}
	
	public void reset() {
		init();
	}
	
	/**
	 * Removes commands from list
	 */
	private void removeCommandsAfter() {
		int start = index + 1;
		int end = commands.size();
		if (start <= end) {
			commands.subList(start, end).clear();
		}
	}

	protected List<UndoableCommand> getCommands() {
		return commands;
	}

	protected void setCommands(List<UndoableCommand> commands) {
		this.commands = commands;
	}

	protected int getIndex() {
		return index;
	}

	protected void setIndex(int index) {
		this.index = index;
	}
}
