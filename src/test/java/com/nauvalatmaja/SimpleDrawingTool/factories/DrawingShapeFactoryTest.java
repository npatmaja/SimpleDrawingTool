package com.nauvalatmaja.SimpleDrawingTool.factories;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.awt.image.BufferedImage;
import java.io.IOException;

import org.junit.Test;

import com.nauvalatmaja.SimpleDrawingTool.model.shape.AbstractDrawingShape;
import com.nauvalatmaja.SimpleDrawingTool.model.shape.DEllipse;
import com.nauvalatmaja.SimpleDrawingTool.model.shape.DImage;
import com.nauvalatmaja.SimpleDrawingTool.model.shape.DLine;
import com.nauvalatmaja.SimpleDrawingTool.model.shape.DRectangle;
import com.nauvalatmaja.SimpleDrawingTool.model.shape.Points;
import com.nauvalatmaja.SimpleDrawingTool.model.shape.ShapeType;

public class DrawingShapeFactoryTest {

	private Points point = new Points(10, 10, 100, 100);
	private AbstractDrawingShape shape;

	@Test
	public void testCreateShapeShapeTypeStringPointsRect() {
		shape = DrawingShapeFactory.getInstance()
			.createShape(ShapeType.RECTANGLE, "test", point);
		assertThat(shape, instanceOf(DRectangle.class));
	}
	
	@Test
	public void testCreateShapeShapeTypeStringPointsLine() {
		shape = DrawingShapeFactory.getInstance()
			.createShape(ShapeType.LINE, "test", point);
		assertThat(shape, instanceOf(DLine.class));
	}
	
	@Test
	public void testCreateShapeShapeTypeStringPointsEllipse() {
		shape = DrawingShapeFactory.getInstance()
			.createShape(ShapeType.ELLIPSE, "test", point);
		assertThat(shape, instanceOf(DEllipse.class));
	}
	
	
	@Test
	public void testCreateShapeShapeTypeBufferedImageStringPoints() throws IOException {
		BufferedImage bi = new BufferedImage(90, 90, BufferedImage.TYPE_INT_RGB);
		shape = DrawingShapeFactory.getInstance()
				.createShape(ShapeType.IMAGE, bi, "test", point);
			assertThat(shape, instanceOf(DImage.class));
	}
}
