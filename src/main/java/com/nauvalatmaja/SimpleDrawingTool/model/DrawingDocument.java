package com.nauvalatmaja.SimpleDrawingTool.model;

import java.awt.Color;
import java.awt.geom.Point2D.Double;
import java.awt.geom.Rectangle2D;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import com.nauvalatmaja.SimpleDrawingTool.Properties;
import com.nauvalatmaja.SimpleDrawingTool.model.shape.AbstractDrawingShape;



public class DrawingDocument extends Observable implements DocumentModel, Serializable {
	private boolean dirty;
	private String name;
	private List<AbstractDrawingShape> shapes;
	
	public DrawingDocument() {
		shapes = new ArrayList<AbstractDrawingShape>();
		dirty = false;
		name = Properties.DEFAULT_TITLE;
	}
	
	/**
	 * Add shape to model
	 * @param shape
	 */
	public void addShape(AbstractDrawingShape shape) {
		shapes.add(shape);
		markDirty();
		update();
	}
	
	/**
	 * Translate/move shape to the pointed coordinate
	 * @param shape
	 * @param x
	 * @param y
	 */
	public void translateShape(AbstractDrawingShape shape, double x, double y) {
		if (doesnotContain(shape)) {
			return;
		}
		shape.setTranslation(x, y);
		markDirty();
		update();
	}
	
	/** 
	 * Gets newly created shape
	 * @return draw-able shape
	 */
	public AbstractDrawingShape getTopShape() {
		if (shapes.size() > 0) {
			return shapes.get(shapes.size() - 1);
		} else {
			return null;
		}
	}
	
	/**
	 * Gets top most of the shapes within pointed coordinate
	 * @param x
	 * @param y
	 * @return draw-able shape
	 */
	public AbstractDrawingShape getTopShape(double x, double y) {
		return getTopShape(getPointerRectangle(x, y));
	}
	
	/**
	 * Gets top most of the shapes within pointed coordinate
	 * @param point
	 * @return draw-able shape
	 */
	public AbstractDrawingShape getTopShape(Double point) {
		return getTopShape(point.x, point.y);
	}
	
	/**
	 * Gets top most of the shapes within rectangle
	 * @param point
	 * @return draw-able shape
	 */
	public AbstractDrawingShape getTopShape(Rectangle2D r) {
		AbstractDrawingShape shape = null;
		AbstractDrawingShape s = null;
		for (int i = shapes.size() - 1; i >= 0; i--) {	
			s = shapes.get(i);
			if (r.intersects(s.getBounds2D()) && !s.isSelectionRect()) {
				shape = s;
				break;
			}
		}
		return shape;
	}
	
	/**
	 * Creates 5x5 rectangle around the pointed coordinate
	 * @param x
	 * @param y
	 * @return rectangle
	 */
	private Rectangle2D getPointerRectangle(double x, double y) {
		double side = 8;
		double x0 = x - side / 2;
		double y0 = y - side / 2;
		return new Rectangle2D.Double(x0, y0, side, side);
	}
	
	@Override
	public void markDirty() {
		dirty = true;
		update();
	}
	
	@Override
	public boolean isDirty() {
		return dirty;
	}
	
	@Override
	public void markClean() {
		dirty = false;
		update();
	}
	
	private void update() {
		this.setChanged();
		this.notifyObservers();
	}
	
	/**
	 * Returns the shapes
	 * @return list of draw-able shapes
	 */
	public List<AbstractDrawingShape> getShapes() {
		return shapes;
	}

	/**
	 * Scales shape to the pointed x and y coordinate
	 * @param shape
	 * @param x
	 * @param y
	 */
	public void scaleShape(AbstractDrawingShape shape, double x, double y) {
		if (doesnotContain(shape)) {
			return;
		}
		shape.setScale(x, y);
		markDirty();
	}

	private boolean doesnotContain(AbstractDrawingShape shape) {
		return !shapes.contains(shape);
	}

	/**
	 * Removes shape
	 * @param shape
	 */
	public void removeShape(AbstractDrawingShape shape) {
		shapes.remove(shape);
		markDirty();
	}

	
	
	/**
	 * Sets shape's fill colour
	 * @param shape
	 * @param colour
	 */
	public void setFillColour(AbstractDrawingShape shape, Color colour) {
		shape.setFillColour(colour);
		markDirty();
	}
	
	/**
	 * Gets selected shape's fill colour
	 * @param x
	 * @param y
	 * @return colour
	 */
	public Color getFillColour(double x, double y) {
		return getTopShape(x, y).getFillColour();
	}
	
	/**
	 * Sets shape's line colour
	 * @param shape
	 * @param colour
	 */
	public void setLineColour(AbstractDrawingShape shape, Color colour) {
		shape.setLineColour(colour);
		markDirty();
	}
	
	/**
	 * Gets selected shape's line colour
	 * @param x
	 * @param y
	 * @return colour
	 */
	public Color getLineColour(double x, double y) {
		return getTopShape(x, y).getLineColour();
	}
	
	/**
	 * Sets shape's fill opacity
	 * @param shape
	 * @param colour
	 */
	public void setFillOpacity(AbstractDrawingShape shape, float opacity) {
		shape.setFillOpacity(opacity);
		markDirty();
	}
	
	/**
	 * Gets selected shape's fill opacity
	 * @param x
	 * @param y
	 * @return opacity
	 */
	public float getFillOpacity(double x, double y) {
		return getTopShape(x, y).getFillOpacity();
	}
	
	/**
	 * Sets shape's line opacity
	 * @param shape
	 * @param colour
	 */
	public void setLineOpacity(AbstractDrawingShape shape, float opacity) {
		shape.setLineOpacity(opacity);
		markDirty();
	}
	
	/**
	 * Gets selected shape's line opacity
	 * @param x
	 * @param y
	 * @return opacity
	 */
	public float getLineOpacity(double x, double y) {
		return getTopShape(x, y).getFillOpacity();
	}

	/**
	 * Sets shape's line width
	 * @param shape
	 * @param width
	 */
	public void setLineWidth(AbstractDrawingShape shape, int width) {
		shape.setStrokeWidth(width);
		markDirty();
	}

	/**
	 * Gets selected shape's fill opacity
	 * @param x
	 * @param y
	 * @return line width
	 */
	public int getLineWidth(double x, double y) {
		return getTopShape(x, y).getStrokeWidth();
	}

	
	public void setName(String name) {
		this.name = name;
		update();
	}

	@Override
	public String getName() {
		return name;
	}

	/**
	 * Selects the shape
	 * @param shape
	 */
	public void selectShape(AbstractDrawingShape shape) {
		if (shape != null) {
			shape.select();
			update();
		}
	}

	/**
	 * Releases the shape
	 * @param shape
	 */
	public void releaseShape(AbstractDrawingShape shape) {
		if (shape != null) {
			shape.release();
		}
		update();
	}
	
	/**
	 * Get selected shape
	 * @return selected shape
	 */
	public AbstractDrawingShape getSelectedhape() {
		AbstractDrawingShape shape = null;
		for (AbstractDrawingShape s : shapes) {
			if (s.isSelected()) {
				shape = s;
				break;
			}
		}
		return shape;
	}
	
	public AbstractDrawingShape getShapeInDocument(AbstractDrawingShape shape) {
		int index = shapes.indexOf(shape);
		return shapes.get(index);
	}
}
