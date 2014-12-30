package com.nauvalatmaja.SimpleDrawingTool.controller.undoable;

public class UndoException extends Exception {
	public UndoException() {
		super("Undo/redo error, can not perform command");
	}
	
	public UndoException(String message) {
		super(message);
	}
}
