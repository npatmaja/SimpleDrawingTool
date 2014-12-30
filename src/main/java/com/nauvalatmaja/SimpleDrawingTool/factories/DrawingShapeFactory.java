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

	@Override
	public AbstractDrawingShape createShape(ShapeType shape, String name, double x, double y, double x1,
			double y1) {
		switch (shape) {
		case RECTANGLE:
			return new DRectangle(name, x, y, x1, y1);
		case ELLIPSE:
			return new DEllipse(name, x, y, x1, y1);
		case LINE:
			return new DLine(name, x, y, x1, y1);	
		default:
			return new DRectangle(name, x, y, x1, y1);
		}
	}

	@Override
	public AbstractDrawingShape createShape(ShapeType shape, BufferedImage image,
			String name, double x, double y, double x1, double y1) throws IOException {
		if (shape == ShapeType.IMAGE) {
			DImage img = new DImage(name, image, x, y, x1, y1); 
			img.setFillOpacity(1);
			ByteArrayOutputStream bytes = new ByteArrayOutputStream();
			ImageIO.write(image, "png", bytes);
			img.setImageByte(bytes.toByteArray());
			return img;
		} else {
			return createShape(shape, name, x, y, x1, y1);
		}
	}

}
