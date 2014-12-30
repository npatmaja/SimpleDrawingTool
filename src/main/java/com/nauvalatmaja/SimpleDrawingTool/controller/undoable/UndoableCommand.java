package com.nauvalatmaja.SimpleDrawingTool.controller.undoable;

/**
 * Encapsulates the command invoked by controller
 * @author nauval
 *
 */
public interface UndoableCommand {
	public void undo();
	public void redo();
	public String getActionName();
}
