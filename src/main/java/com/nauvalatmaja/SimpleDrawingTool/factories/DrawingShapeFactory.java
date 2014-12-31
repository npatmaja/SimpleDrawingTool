package com.nauvalatmaja.SimpleDrawingTool.factories;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.nauvalatmaja.SimpleDrawingTool.model.shape.*;


/**
 * Creates new shapes from given parameters
 * @author nauval
 *
 */
public class DrawingShapeFactory implements ShapeFactory {
	
	private static DrawingShapeFactory INSTANCE = null;
	
	private DrawingShapeFactory() {}
	
	public static DrawingShapeFactory getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new DrawingShapeFactory();
		}
		return INSTANCE;
	}

	@Override
	public AbstractDrawingShape createShape(ShapeType shape, String name, Points points) {
		switch (shape) {
		case RECTANGLE:
			return new DRectangle(name, points);
		case ELLIPSE:
			return new DEllipse(name, points);
		case LINE:
			return new DLine(name, points);	
		default:
			return new DRectangle(name, points);
		}
	}

	@Override
	public AbstractDrawingShape createShape(ShapeType shape, BufferedImage image,
			String name, Points points) throws IOException {
		
		if (shape == ShapeType.IMAGE) {
			DImage img = new DImage(name, image, points); 
			img.setFillOpacity(1);
			ByteArrayOutputStream bytes = new ByteArrayOutputStream();
			ImageIO.write(image, "png", bytes);
			img.setImageByte(bytes.toByteArray());
			return img;
		} else {
			return createShape(shape, name, points);
		}
	}

}
