package com.nauvalatmaja.SimpleDrawingTool.controller.undoable;

import com.nauvalatmaja.SimpleDrawingTool.model.DocumentModel;
import com.nauvalatmaja.SimpleDrawingTool.model.DrawingDocument;
import com.nauvalatmaja.SimpleDrawingTool.model.shape.AbstractDrawingShape;


public class UndoableAddShape implements UndoableCommand {
	private String action = "Add shape";
	private AbstractDrawingShape shape;
	private DrawingDocument document;
	
	public UndoableAddShape(DocumentModel document, AbstractDrawingShape shape, String action) {
		this.document = (DrawingDocument) document;
		this.shape = shape;
		this.action = action;
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
