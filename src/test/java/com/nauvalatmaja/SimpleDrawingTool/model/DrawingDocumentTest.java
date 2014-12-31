package com.nauvalatmaja.SimpleDrawingTool.model;

import static org.junit.Assert.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import org.hamcrest.core.IsNull;
import org.junit.Before;
import org.junit.Test;

import com.nauvalatmaja.SimpleDrawingTool.model.shape.AbstractDrawingShape;
import com.nauvalatmaja.SimpleDrawingTool.model.shape.DRectangle;
import com.nauvalatmaja.SimpleDrawingTool.model.shape.Points;

public class DrawingDocumentTest {
	
	private DrawingDocument classUnderTest;
	private AbstractDrawingShape shape;

	@Before
	public void setUp() throws Exception {
		shape = new DRectangle("test", new Points(0, 0, 100, 100));
		classUnderTest = new DrawingDocument();
	}

	@Test
	public void testAddShape() {
		classUnderTest.addShape(shape);
		assertThat(classUnderTest.getShapes().size(), is(1));
	}

	@Test
	public void testTranslateShape() {
		classUnderTest.addShape(shape);
		classUnderTest.translateShape(shape, 10, 10);
		assertThat(shape.getX(), is(10.));
		assertThat(shape.getY(), is(10.));
	}
	
	@Test
	public void testTranslateShapeNotInList() {
		classUnderTest.translateShape(shape, 10, 10);
		assertThat(shape.getX(), is(0.));
		assertThat(shape.getY(), is(0.));
	}

	@Test
	public void testGetTopShape() {
		AbstractDrawingShape s = new DRectangle("s", new Points(10, 10, 50, 50));
		classUnderTest.addShape(shape);
		classUnderTest.addShape(s);
		assertThat(classUnderTest.getTopShape(), is(s));
	}

	@Test
	public void testGetTopShapeDoubleDouble() {
		AbstractDrawingShape s = new DRectangle("s", new Points(10, 10, 50, 50));
		classUnderTest.addShape(shape);
		classUnderTest.addShape(s);
		assertThat(classUnderTest.getTopShape(5,5), is(shape));
	}

	@Test
	public void testGetTopShapeDouble() {
		AbstractDrawingShape s = new DRectangle("s", new Points(10, 10, 50, 50));
		classUnderTest.addShape(shape);
		classUnderTest.addShape(s);
		assertThat(classUnderTest.getTopShape(new Point2D.Double(1, 1)), is(shape));
	}

	@Test
	public void testGetTopShapeRectangle2D() {
		AbstractDrawingShape s = new DRectangle("s", new Points(10, 10, 50, 50));
		classUnderTest.addShape(shape);
		classUnderTest.addShape(s);
		Rectangle2D.Double rect = new Rectangle2D.Double(5, 5, 50, 50);
		assertThat(classUnderTest.getTopShape(rect), is(s));
	}

	@Test
	public void testScaleShape() {
		classUnderTest.addShape(shape);
		classUnderTest.scaleShape(shape, 30, 30);
		assertThat(shape.getxEnd(), is(30.));
		assertThat(shape.getyEnd(), is(30.));
	}
	
	@Test
	public void testScaleShapeNotInList() {
		classUnderTest.scaleShape(shape, 30, 30);
		assertThat(shape.getxEnd(), is(100.));
		assertThat(shape.getyEnd(), is(100.));
	}

	@Test
	public void testRemoveShape() {
		classUnderTest.addShape(shape);
		classUnderTest.removeShape(shape);
		assertThat(classUnderTest.getShapes().size(), is(0));
	}

	@Test
	public void testSelectShape() {
		classUnderTest.addShape(shape);
		classUnderTest.selectShape(shape);
		assertThat(shape.isSelected(), is(true));
	}

	@Test
	public void testReleaseShape() {
		classUnderTest.addShape(shape);
		classUnderTest.selectShape(shape);
		classUnderTest.releaseShape(shape);
		assertThat(shape.isSelected(), is(false));
	}

	@Test
	public void testGetSelectedhape() {
		classUnderTest.addShape(shape);
		classUnderTest.selectShape(shape);
		assertThat(classUnderTest.getSelectedhape(), is(shape));
	}

	@Test
	public void testGetShapeInDocument() {
		classUnderTest.addShape(shape);
		assertThat(classUnderTest.getShapeInDocument(shape), is(shape));
	}

}
