package com.nauvalatmaja.SimpleDrawingTool.model.shape;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import org.junit.Before;
import org.junit.Test;

public class DImageTest {

	private DImage classUnderTest;
	private BufferedImage mockedBI;
	
	@Before
	public void setup() {
		mockedBI = mock(BufferedImage.class);
		
		classUnderTest = new DImage ("Test", mockedBI, new Points(0, 0, 200, 100));
	}
	
	@Test
	public void testContainsDoubleDouble() {
		double x = 50, y = 10;
		boolean result = classUnderTest.contains(x, y);
		assertThat(result, is(true));
	}
	
	@Test
	public void testContainsDoubleDoubleFalse() {
		double x = 200, y = 10;
		boolean result = classUnderTest.contains(x, y);
		assertThat(result, is(false));
	}
	
	@Test
	public void testContainsPoint2D() {
		boolean result = classUnderTest.contains(createPoint2D(50, 10));
		assertThat(result, is(true));
	}
	
	@Test
	public void testContainsPoint2DFalse() {
		boolean result = classUnderTest.contains(createPoint2D(10, 300));
		assertThat(result, is(false));
	}

	@Test
	public void testIntersectsDoubleDoubleDoubleDouble() {
		boolean result = classUnderTest.intersects(50, 50, 200, 200);
		assertThat(result, is(true));
	}
	
	@Test
	public void testIntersectsDoubleDoubleDoubleDoubleFalse() {
		boolean result = classUnderTest.intersects(90, 101, 200, 200);
		assertThat(result, is(false));
	}

	@Test
	public void testIntersectsRectangle2D() {
		boolean result = classUnderTest.intersects(createRectangle2D(50, 50, 20, 10));
		assertThat(result, is(true));
	}
	
	@Test
	public void testIntersectsRectangle2DFalse() {
		boolean result = classUnderTest.intersects(createRectangle2D(90, 101, 120, 120));
		assertThat(result, is(false));
	}

	@Test
	public void testContainsDoubleDoubleDoubleDouble() {
		boolean result = classUnderTest.contains(40, 40, 10, 10);
		assertThat(result, is(true));
	}
	
	@Test
	public void testContainsDoubleDoubleDoubleDoubleFalse() {
		boolean result = classUnderTest.contains(10, 101, 80, 100);
		assertThat(result, is(false));
	}

	@Test
	public void testContainsRectangle2D() {
		boolean result = classUnderTest.contains(createRectangle2D(40, 40, 20, 20));
		assertThat(result, is(true));
	}
	
	@Test
	public void testContainsRectangle2DFalse() {
		boolean result = classUnderTest.contains(createRectangle2D(200, 10, 50, 50));
		assertThat(result, is(false));
	}
	
	@Test
	public void testEqualTrue() {
		DImage e = new DImage("Test", mockedBI,new Points(0, 0, 100, 100));
		assertThat(classUnderTest.equals(e), is(true));
	}
	
	@Test
	public void testEqualFalse() {
		DEllipse e = new DEllipse("Test 2", new Points(0, 0, 100, 100));
		assertThat(classUnderTest.equals(e), is(false));
	}
	
	@Test
	public void testSquare() {
		classUnderTest.setSquare(true);
		assertThat(classUnderTest.getHeight(), is(classUnderTest.getWidth()));
	}
	
	@Test
	public void testToString() {
		String string = String.format("IMAGE [x:%s y:%s w:%s h:%s]", 
				classUnderTest.getX(), 
				classUnderTest.getY(), 
				classUnderTest.getWidth(), 
				classUnderTest.getHeight());
		
		assertThat(classUnderTest.toString(), is(string));
	}
	
	private Point2D.Double createPoint2D(double x, double y) {
		Point2D.Double point = new Point2D.Double(x, y);
		return point;
	}
	
	private Rectangle2D.Double createRectangle2D(double x, double y, double x1, double y1) {
		Rectangle2D.Double rect = new Rectangle2D.Double(x, y, x1, y1);
		return rect;
	}
}
