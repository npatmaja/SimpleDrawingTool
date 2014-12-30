package com.nauvalatmaja.SimpleDrawingTool.controller.undoable;

import com.nauvalatmaja.SimpleDrawingTool.model.DrawingDocument;
import com.nauvalatmaja.SimpleDrawingTool.model.shape.AbstractDrawingShape;


public class UndoableDeleteShape implements UndoableCommand {

	private DrawingDocument document;
	private AbstractDrawingShape shape;
	private String action;
	
	public UndoableDeleteShape(DrawingDocument document, AbstractDrawingShape shape, String action) {
		this.document = document;
		this.shape = shape;
		this.action = action;
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
