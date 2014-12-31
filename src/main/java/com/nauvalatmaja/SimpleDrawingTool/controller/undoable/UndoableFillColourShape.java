package com.nauvalatmaja.SimpleDrawingTool.controller.undoable;

import java.awt.Color;

import com.nauvalatmaja.SimpleDrawingTool.model.shape.AbstractDrawingShape;

/**
 * Undo command for change shape's fill colour
 * @author nauval
 *
 */
public class UndoableFillColourShape extends AbstractUndoableCommand {
	private Color before;
	private Color after;
	
	public UndoableFillColourShape(AbstractDrawingShape shape, Color newColour, String action) {
		super(null, shape, action);
		this.before = new Color(shape.getFillColour().getRGB());
		this.after = newColour;
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
