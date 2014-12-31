package com.nauvalatmaja.SimpleDrawingTool.controller.undoable;

import java.awt.Color;

import com.nauvalatmaja.SimpleDrawingTool.model.shape.AbstractDrawingShape;

/**
 * Undo command for change shape's line colour
 * @author nauval
 *
 */
public class UndoableLineColourShape extends AbstractUndoableCommand {
	
	private Color before;
	private Color after;
	
	public UndoableLineColourShape(AbstractDrawingShape shape, Color newColour, String action) {
		super(null, shape, action);
		this.before = new Color(shape.getLineColour().getRGB());
		this.after = newColour;
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
