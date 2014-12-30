package com.nauvalatmaja.SimpleDrawingTool.factories;

import java.awt.image.BufferedImage;
import java.io.IOException;

import com.nauvalatmaja.SimpleDrawingTool.model.shape.*;

/**
 * Interface to create shape factory
 * @author nauval
 *
 */
public interface ShapeFactory {
	/**
	 * Creates shape with the given parameters
	 * @param shape
	 * @param name
	 * @param x
	 * @param y
	 * @param x1
	 * @param y1
	 * @return new Shape
	 */
	public AbstractDrawingShape createShape(ShapeType shape, String name, double x, double y, double x1, double y1);
	
	/**
	 * Create image shape with the specific image parameters
	 * @param shape
	 * @param image
	 * @param name
	 * @param x
	 * @param y
	 * @param x1
	 * @param y1
	 * @return
	 * @throws IOException
	 */
	public AbstractDrawingShape createShape(ShapeType shape, BufferedImage image, String name, double x, double y, double x1, double y1) throws IOException;
}
