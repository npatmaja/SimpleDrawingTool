package com.nauvalatmaja.SimpleDrawingTool.controller.undoable;

import com.nauvalatmaja.SimpleDrawingTool.model.DocumentModel;
import com.nauvalatmaja.SimpleDrawingTool.model.shape.AbstractDrawingShape;


public class UndoableTransformShape extends AbstractUndoableCommand {

	private TransformationPoint point;
	
	public UndoableTransformShape(DocumentModel document, AbstractDrawingShape shape, 
			TransformationPoint point, String action) {
		super(document, shape, action);
		this.point = point;
	}
	
	@Override
	public void undo() {
		document.translateShape(shape, point.getBeforeTranslationPoint().x, 
				point.getBeforeTranslationPoint().y);
		document.scaleShape(shape, point.getBeforeScallingPoint().x, 
				point.getBeforeScallingPoint().y);
	}

	@Override
	public void redo() {
		document.translateShape(shape, point.getAfterTranslationPoint().x, 
				point.getAfterTranslationPoint().y);
		document.scaleShape(shape, point.getAfterScallingPoint().x, 
				point.getAfterScallingPoint().y);
	}

	@Override
	public String getActionName() {
		return action + " " + shape.toString();
	}

}
