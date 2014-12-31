package com.nauvalatmaja.SimpleDrawingTool.model.shape;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * Wrapper class of image being imported into the application. The image will be stored in byte array
 * so it can be serialized (imageByte class is not serializable). BufferedImage is used to store the conversion 
 * result from byte to image and will not be serialized (transient).
 * 
 * @author nauval
 *
 */
public class DImage extends AbstractDrawingShape{
	
	private byte[] imageByte;
	private transient BufferedImage image;
	
	public DImage(String name, BufferedImage image, Points points) {
		super(name, points);
		this.image = image;
	}

	@Override
	public Rectangle getBounds() {
		updateBounds();
		return new Rectangle((int) getX(), (int) minYValue, (int) getWidth(), (int) getHeight());
	}

	@Override
	public Rectangle2D getBounds2D() {
		updateBounds();
		return new Rectangle2D.Double(getX(), minYValue, getWidth(), getHeight());
	}

	@Override
	public boolean contains(double x, double y) {
		return getBounds().contains(x, y);
	}

	@Override
	public boolean contains(Point2D p) {
		return getBounds().contains(p);
	}

	@Override
	public boolean intersects(double x, double y, double w, double h) {
		return getBounds().intersects(x, y, w, h);
	}

	@Override
	public boolean intersects(Rectangle2D r) {
		return getBounds().intersects(r);
	}

	@Override
	public boolean contains(double x, double y, double w, double h) {
		return getBounds().contains(x, y, w, h);
	}

	@Override
	public boolean contains(Rectangle2D r) {
		return getBounds().contains(r);
	}

	@Override
	public PathIterator getPathIterator(AffineTransform at) {
		// TODO Auto-generated method stub
		return getBounds().getPathIterator(at);
	}

	@Override
	public PathIterator getPathIterator(AffineTransform at, double flatness) {
		// TODO Auto-generated method stub
		return getBounds().getPathIterator(at, flatness);
	}

	public String toString() {
		return String.format("Image x:%s y:%s w:%s h:%s", getBounds().getX(), getBounds().getY(), getBounds().getWidth(), getBounds().getHeight());	
	}

	@Override
	public ShapeType getType() {
		return ShapeType.IMAGE;
	}	
	
	public void setImage(BufferedImage image) {
		this.image = image;
	}
	
	public BufferedImage getImage() {
		return image;
	}
	
	public void setImageByte(byte[] bytes) {
		imageByte = bytes;
	}
	
	public byte[] getImageByte() {
		return imageByte;
	}
}
