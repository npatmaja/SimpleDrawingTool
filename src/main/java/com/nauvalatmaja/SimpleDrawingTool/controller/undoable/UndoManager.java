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
	/** Undoable command's list */
	private List<UndoableCommand> commands;
	/** Index of the linked list to allow moving forward and backward */
	private int index;
	
	public UndoManager() {
		commands = new ArrayList<UndoableCommand>();
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
		if (undoable()) {
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
		if (redoable()) {
			index++;
			UndoableCommand c = commands.get(index);
			c.redo();
			return c.getActionName();
		} else {
			throw new UndoException("No action to perform");
		}
	}
	
	/**
	 * Returns true if there is command(s) in the list
	 * @return
	 */
	public boolean undoable() {
		return (index >= 0);
	}
	
	public boolean redoable() {
		return (index < commands.size() - 1 && commands.size() > 0);
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
	
	
}
