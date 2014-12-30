package com.nauvalatmaja.SimpleDrawingTool.model.shape;

import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

public class DEllipse extends AbstractDrawingShape{
	
	public DEllipse(String name, double x, double y, double x1, double y1) {
		super(name, x, y, x1, y1);
	}

	@Override
	public Rectangle getBounds() {
		updateBounds();
		return new Rectangle((int) getX(), (int) yMin, (int) getWidth(), (int) getHeight());
	}

	@Override
	public Rectangle2D getBounds2D() {
		updateBounds();
		return new Rectangle2D.Double(getX(), yMin, getWidth(), getHeight());
	}

	@Override
	public boolean contains(double x, double y) {
		updateBounds();
		return new Ellipse2D.Double(getX(), yMin, getWidth(), getHeight()).contains(x, y);
	}

	@Override
	public boolean contains(Point2D p) {
		updateBounds();
		return new Ellipse2D.Double(getX(), yMin, getWidth(), getHeight()).contains(p);
	}

	@Override
	public boolean intersects(double x, double y, double w, double h) {
		updateBounds();
		return new Ellipse2D.Double(getX(), yMin, getWidth(), getHeight()).intersects(x, y, w, h);
	}

	@Override
	public boolean intersects(Rectangle2D r) {
		updateBounds();
		return new Ellipse2D.Double(getX(), yMin, getWidth(), getHeight()).contains(r);
	}

	@Override
	public boolean contains(double x, double y, double w, double h) {
		updateBounds();
		return new Ellipse2D.Double(getX(), yMin, getWidth(), getHeight()).contains(x, y, w, h);
	}

	@Override
	public boolean contains(Rectangle2D r) {
		updateBounds();
		return new Ellipse2D.Double(getX(), yMin, getWidth(), getHeight()).contains(r);
	}

	@Override
	public PathIterator getPathIterator(AffineTransform at) {
		updateBounds();
		return new Ellipse2D.Double(getX(), yMin, getWidth(), getHeight()).getPathIterator(at);
	}

	@Override
	public PathIterator getPathIterator(AffineTransform at, double flatness) {
		updateBounds();
		return new Ellipse2D.Double(getX(), yMin, getWidth(), getHeight()).getPathIterator(at, flatness);
	}
	
	@Override
	public String toString() {
		return String.format("Ellipse [x:%s y:%s w:%s h:%s]", getBounds().getX(), getBounds().getY(), getBounds().getWidth(), getBounds().getHeight());
	}

	@Override
	public ShapeType getType() {
		return ShapeType.ELLIPSE;
	}	
}
