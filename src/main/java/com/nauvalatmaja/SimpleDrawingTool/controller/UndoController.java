package com.nauvalatmaja.SimpleDrawingTool.controller;


import java.awt.Color;

import com.nauvalatmaja.SimpleDrawingTool.controller.undoable.*;
import com.nauvalatmaja.SimpleDrawingTool.model.*;
import com.nauvalatmaja.SimpleDrawingTool.model.shape.*;

public class UndoController {
	
	private UndoManager undoManager;
	
	public UndoController(UndoManager manager) {
		undoManager = manager;
	}
	
	public void addShape(DocumentModel document, AbstractDrawingShape shape) {
		undoManager.putCommand(new UndoableAddShape(document, shape, "Add shape"));
	}
	
	public void deleteShape(DocumentModel document, AbstractDrawingShape shape) {
		undoManager.putCommand(new UndoableDeleteShape(document, shape, "Delete shape"));
	}
	
	public void trasformShape(DocumentModel document, AbstractDrawingShape shape, 
			TransformationPoint point) {
		undoManager.putCommand(new UndoableTransformShape(document, shape, point, 
				"Transform shape"));
	}
	
	public void changeFillColourShape(AbstractDrawingShape shape, Color newColour) {
		undoManager.putCommand(new UndoableFillColourShape(shape, newColour, 
				"Change shape's colour"));
	}
	
	public void changeLineColourShape(AbstractDrawingShape shape, Color newColour) {
		undoManager.putCommand(new UndoableLineColourShape(shape, newColour, 
				"Change shape's line colour"));
	}
	
	public void clearCommands() {
		undoManager.reset();
	}
	
	public String doUndo() throws UndoException {
		return undoManager.doUndo();
	};
	
	public String doRedo() throws UndoException {
		return undoManager.doRedo();
	}

	protected UndoManager getUndoManager() {
		return undoManager;
	}

	protected void setUndoManager(UndoManager undoManager) {
		this.undoManager = undoManager;
	};
}
