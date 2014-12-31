package com.nauvalatmaja.SimpleDrawingTool.controller;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

import java.awt.Color;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;

import org.junit.Before;
import org.junit.Test;

import com.nauvalatmaja.SimpleDrawingTool.controller.undoable.TransformationPoint;
import com.nauvalatmaja.SimpleDrawingTool.controller.undoable.UndoException;
import com.nauvalatmaja.SimpleDrawingTool.controller.undoable.UndoManager;
import com.nauvalatmaja.SimpleDrawingTool.controller.undoable.UndoableAddShape;
import com.nauvalatmaja.SimpleDrawingTool.controller.undoable.UndoableCommand;
import com.nauvalatmaja.SimpleDrawingTool.factories.DrawingShapeFactory;
import com.nauvalatmaja.SimpleDrawingTool.model.DrawingDocument;
import com.nauvalatmaja.SimpleDrawingTool.model.shape.AbstractDrawingShape;
import com.nauvalatmaja.SimpleDrawingTool.model.shape.Points;
import com.nauvalatmaja.SimpleDrawingTool.model.shape.ShapeType;

public class UndoControllerTest {
	private UndoController classUnderTest;
	private AbstractDrawingShape shape = 
			DrawingShapeFactory.getInstance().createShape(ShapeType.RECTANGLE, "rectangle", new Points(10, 10, 110, 110));
	private DrawingDocument document;
	
	@Before
	public void setupBefore() {
		classUnderTest = new UndoController(new UndoManager());
		document = new DrawingDocument();
		classUnderTest.addShape(document, shape);
	}
	
	@Test
	public void testAddShape() {
		assertIsUndoableNotRedoable();
	}

	@Test
	public void testDeleteShape() {
		classUnderTest.deleteShape(document, shape);
		assertIsUndoableNotRedoable();
	}

	@Test
	public void testTrasformShape() {
		Point2D.Double p1 = new Point2D.Double(10, 10);
		Point2D.Double p2 = new Point2D.Double(110, 110);
		Point2D.Double p3 = new Point2D.Double(110, 110);
		Point2D.Double p4 = new Point2D.Double(150, 150);		
		TransformationPoint point = new TransformationPoint(p1, p2, p3, p4);
		
		classUnderTest.trasformShape(document, shape, point);
		assertIsUndoableNotRedoable();
	}

	@Test
	public void testChangeFillColourShape() {
		classUnderTest.changeFillColourShape(shape, Color.black);
		assertIsUndoableNotRedoable();
	}

	@Test
	public void testChangeLineColourShape() {
		classUnderTest.changeLineColourShape(shape, Color.gray);
		assertIsUndoableNotRedoable();
	}

	@Test
	public void testDoUndo() throws UndoException {
		classUnderTest.doUndo();
		
		assertThat(classUnderTest.getUndoManager().isUndoable(), is(false));
		assertThat(classUnderTest.getUndoManager().isRedoable(), is(true));
	}

	@Test
	public void testDoRedo() throws UndoException {
		classUnderTest.deleteShape(document, shape);
		
		classUnderTest.doUndo();
		classUnderTest.doRedo();
		
		assertThat(classUnderTest.getUndoManager().isUndoable(), is(true));
		assertThat(classUnderTest.getUndoManager().isRedoable(), is(false));
	}
	
	private void assertIsUndoableNotRedoable() {
		boolean isUndoable = classUnderTest.getUndoManager().isUndoable();
		boolean isRedoable = classUnderTest.getUndoManager().isRedoable();
		
		assertThat(isUndoable, is(true));
		assertThat(isRedoable, is(false));
	}
}
