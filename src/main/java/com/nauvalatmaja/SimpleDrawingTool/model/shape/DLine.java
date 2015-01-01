package com.nauvalatmaja.SimpleDrawingTool.model.shape;

import static com.nauvalatmaja.SimpleDrawingTool.Properties.*;

import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;


public class DLine extends AbstractDrawingShape {
	
	public DLine(String name, Points points) {
		super(name, points);
	}

	@Override
	public Rectangle getBounds() {
		updateBounds();
		return new Rectangle((int) getxAnchor(), (int) getyAnchor(), (int) getWidth(), (int) getHeight());
	}

	@Override
	public Rectangle2D getBounds2D() {
		return new Line2D.Double(getxAnchor(), getyAnchor(), getxEnd(), getyEnd()).getBounds2D();
	}

	@Override
	public boolean contains(double x, double y) {
		return getBounds2D().contains(x, y);
	}

	@Override
	public boolean contains(Point2D p) {
		return getBounds2D().contains(p);
	}

	@Override
	public boolean intersects(double x, double y, double w, double h) {
		return new Line2D.Double(getxAnchor(), getyAnchor(), getxEnd(), getyEnd()).intersects(x, y, w, h);
	}

	@Override
	public boolean intersects(Rectangle2D r) {
		return getBounds2D().contains(r);
	}

	@Override
	public boolean contains(double x, double y, double w, double h) {
		return getBounds2D().contains(x, y, w, h); 
	}

	@Override
	public boolean contains(Rectangle2D r) {
		return getBounds2D().contains(r);
	}

	@Override
	public PathIterator getPathIterator(AffineTransform at) {
		return new Line2D.Double(getxAnchor(), getyAnchor(), getxEnd(), getyEnd()).getPathIterator(at);
	}

	@Override
	public PathIterator getPathIterator(AffineTransform at, double flatness) {
		return new Line2D.Double(getxAnchor(), getyAnchor(), getxEnd(), getyEnd()).getPathIterator(at, flatness);
	}
	
	@Override
	public ShapeType getType() {
		return ShapeType.LINE;
	}	
	
	/**
	 * Performs translation to the pointed coordinate
	 * @param x
	 * @param y
	 */
	protected void performTranslation(double x, double y) {
		double deltax = getxAnchor() - x;
		double deltay = getyAnchor() - y;
		setxAnchor(x);
		setyAnchor(y);
		setxEnd(getxEnd() - deltax);
		setyEnd(getyEnd() - deltay);
		updateNodes();
	}
	
	protected void createNodes() {
		double xNode = getX() - NODE_SIDE_SIZE;
		double yNode = getY() - NODE_SIDE_SIZE;

		nodes[0] = new Rectangle2D.Double(xNode, yNode, NODE_SIDE_SIZE, NODE_SIDE_SIZE);
		
		// top right node
		xNode = getBounds().getMaxX();
		nodes[1] = new Rectangle2D.Double(xNode, yNode, NODE_SIDE_SIZE, NODE_SIDE_SIZE);
	
		// bottom right node
		yNode = Math.max(getBounds().getMinY(), getyEnd());
		nodes[2] = new Rectangle2D.Double(xNode, yNode, NODE_SIDE_SIZE, NODE_SIDE_SIZE);
		
		// bottom left node
		xNode = getBounds().getMinX() - NODE_SIDE_SIZE;
		yNode = Math.max(getBounds().getMinY(), getyEnd());
		nodes[3] = new Rectangle2D.Double(xNode, yNode, NODE_SIDE_SIZE, NODE_SIDE_SIZE);

	}
	
	protected void updateNodes() {
		double xNode = getX() - NODE_SIDE_SIZE;
		double yNode = getY() - NODE_SIDE_SIZE;
		
		// top left node
		nodes[0].setRect(xNode, yNode, NODE_SIDE_SIZE, NODE_SIDE_SIZE);
		
		// top right node
		xNode = getBounds().getMaxX();
		nodes[1].setRect(xNode, yNode, NODE_SIDE_SIZE, NODE_SIDE_SIZE);

		// bottom right node
		yNode = Math.max(getBounds().getMinY(), getyEnd());
		nodes[2].setRect(xNode, yNode, NODE_SIDE_SIZE, NODE_SIDE_SIZE);

		// bottom left node
		xNode = getBounds().getMinX() - NODE_SIDE_SIZE;
		yNode = Math.max(getBounds().getMinY(), getyEnd());
		nodes[3].setRect(xNode, yNode, NODE_SIDE_SIZE, NODE_SIDE_SIZE);
	}
}
