package com.nauvalatmaja.SimpleDrawingTool.controller.undoable;

import java.awt.Color;

import com.nauvalatmaja.SimpleDrawingTool.model.shape.AbstractDrawingShape;

/**
 * Undo command for change shape's fill colour
 * @author nauval
 *
 */
public class UndoableFillColourShape implements UndoableCommand {
	private Color before;
	private Color after;
	private AbstractDrawingShape shape;
	private String action;
	public UndoableFillColourShape(AbstractDrawingShape shape, Color newColour, String action) {
		this.shape = shape;
		this.before = new Color(shape.getFillColour().getRGB());
		this.after = newColour;
		this.action = action;
	}
	@Override
	public void undo() {
		shape.setFillColour(before);
	}

	@Override
	public void redo() {
		shape.setFillColour(after);
	}

	@Override
	public String getActionName() {
		return action + " " + shape.toString();
	}
}
