package com.nauvalatmaja.SimpleDrawingTool.controller.undoable;

import com.nauvalatmaja.SimpleDrawingTool.model.DocumentModel;
import com.nauvalatmaja.SimpleDrawingTool.model.shape.AbstractDrawingShape;


public class UndoableDeleteShape extends AbstractUndoableCommand {
	
	public UndoableDeleteShape(DocumentModel document, AbstractDrawingShape shape, String action) {
		super(document, shape, action);
	}
	
	@Override
	public void undo() {
		document.addShape(shape);
	}

	@Override
	public void redo() {
		document.removeShape(shape);
	}

	@Override
	public String getActionName() {
		return action + " " + shape.toString();
	}

}
