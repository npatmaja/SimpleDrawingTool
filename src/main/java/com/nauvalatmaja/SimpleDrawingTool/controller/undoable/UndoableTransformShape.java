package com.nauvalatmaja.SimpleDrawingTool.controller.undoable;

import java.awt.geom.Point2D;

import com.nauvalatmaja.SimpleDrawingTool.model.DocumentModel;
import com.nauvalatmaja.SimpleDrawingTool.model.DrawingDocument;
import com.nauvalatmaja.SimpleDrawingTool.model.shape.AbstractDrawingShape;


public class UndoableTransformShape implements UndoableCommand{

	/** Action name */
	private final String ACTION = "Transform shape";
	/** Shape in action */
	private AbstractDrawingShape shape;
	/** Anchor point before transformation */
	private Point2D.Double anchor1;
	/** End point before transformation */
	private Point2D.Double end1;
	/** Anchor point after transformation */
	private Point2D.Double anchor2;
	/** End point after transformation */
	private Point2D.Double end2;
	/** Model */
	private DrawingDocument document;
	
	public UndoableTransformShape(DocumentModel document, AbstractDrawingShape shape, Point2D.Double anchor1, 
			Point2D.Double end1, Point2D.Double anchor2, Point2D.Double end2) {
		this.document = (DrawingDocument) document;
		this.shape = shape;
		this.anchor1 = anchor1;
		this.end1 = end1;
		this.anchor2 = anchor2;
		this.end2 = end2;
	}
	
	@Override
	public void undo() {
		document.translateShape(shape, anchor1.x, anchor1.y);
		document.scaleShape(shape, end1.x, end1.y);
	}

	@Override
	public void redo() {
		document.translateShape(shape, anchor2.x, anchor2.y);
		document.scaleShape(shape, end2.x, end2.y);
	}

	@Override
	public String getActionName() {
		return ACTION + " " + shape.toString();
	}

}
