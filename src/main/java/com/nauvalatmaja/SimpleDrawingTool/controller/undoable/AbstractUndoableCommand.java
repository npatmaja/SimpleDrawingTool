package com.nauvalatmaja.SimpleDrawingTool.controller.undoable;

import com.nauvalatmaja.SimpleDrawingTool.model.DocumentModel;
import com.nauvalatmaja.SimpleDrawingTool.model.DrawingDocument;
import com.nauvalatmaja.SimpleDrawingTool.model.shape.AbstractDrawingShape;

public abstract class AbstractUndoableCommand implements UndoableCommand{
	String action = "";
	AbstractDrawingShape shape;
	DrawingDocument document;
	
	public AbstractUndoableCommand(DocumentModel document, AbstractDrawingShape shape, String action) {
		this.document = (DrawingDocument) document;
		this.shape = shape;
		this.action = action;
	}
}
