package com.nauvalatmaja.SimpleDrawingTool.controller.undoable;

import com.nauvalatmaja.SimpleDrawingTool.model.DocumentModel;
import com.nauvalatmaja.SimpleDrawingTool.model.shape.AbstractDrawingShape;


public class UndoableAddShape extends AbstractUndoableCommand {
	public UndoableAddShape(DocumentModel document, AbstractDrawingShape shape, String action) {
		super(document, shape, action);
	}
	
	@Override
	public void undo() {
		document.removeShape(shape);
	}

	@Override
	public void redo() {
		document.addShape(shape);
	}

	@Override
	public String getActionName() {
		return action + " " + shape.toString();
	}

}
