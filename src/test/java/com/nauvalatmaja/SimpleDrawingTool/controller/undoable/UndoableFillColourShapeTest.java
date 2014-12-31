package com.nauvalatmaja.SimpleDrawingTool.controller.undoable;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;

import java.awt.Color;

import org.junit.Before;
import org.junit.Test;

import com.nauvalatmaja.SimpleDrawingTool.model.shape.AbstractDrawingShape;
import com.nauvalatmaja.SimpleDrawingTool.model.shape.DRectangle;
import com.nauvalatmaja.SimpleDrawingTool.model.shape.Points;

public class UndoableFillColourShapeTest {

	private UndoableFillColourShape classUnderTest;
	private AbstractDrawingShape shape;
	private Color oldColor = Color.cyan;
	private Color newColor = Color.black;
	
	@Before
	public void setUp() throws Exception {
		shape = new DRectangle("shape", new Points(10, 10, 50, 50));
		shape.setFillColor(oldColor);
		
		classUnderTest = new UndoableFillColourShape(shape, Color.black, "Change color");
	}

	@Test
	public void testUndo() {
		classUnderTest.undo();
		assertThat(shape.getFillColor(), is(oldColor));
	}

	@Test
	public void testRedo() {
		classUnderTest.redo();
		assertThat(shape.getFillColor(), is(newColor));
	}

	@Test
	public void testGetActionName() {
		String s = classUnderTest.getActionName();
		assertThat(s, is("Change color" + " " + shape.toString()));
	}
}
