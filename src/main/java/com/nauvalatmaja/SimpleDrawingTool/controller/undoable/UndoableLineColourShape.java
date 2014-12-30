package com.nauvalatmaja.SimpleDrawingTool.controller.undoable;

import java.awt.Color;

import com.nauvalatmaja.SimpleDrawingTool.model.shape.AbstractDrawingShape;



/**
 * Undo command for change shape's line colour
 * @author nauval
 *
 */
public class UndoableLineColourShape implements UndoableCommand {
	
	private Color before;
	private Color after;
	private AbstractDrawingShape shape;
	private String action;
	
	public UndoableLineColourShape(AbstractDrawingShape shape, Color newColour, String action) {
		this.shape = shape;
		this.before = new Color(shape.getFillColour().getRGB());
		this.after = newColour;
		this.action = action;
	}
	@Override
	public void undo() {
		shape.setLineColour(before);
	}

	@Override
	public void redo() {
		shape.setLineColour(after);
	}

	@Override
	public String getActionName() {
		return action + " " + shape.toString();
	}


}
