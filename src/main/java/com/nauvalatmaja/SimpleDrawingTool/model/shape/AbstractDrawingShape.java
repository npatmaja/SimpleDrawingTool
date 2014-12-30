package com.nauvalatmaja.SimpleDrawingTool.model.shape;

import java.awt.Color;
import java.awt.Shape;
import java.awt.geom.Rectangle2D;
import java.io.Serializable;

import com.nauvalatmaja.SimpleDrawingTool.Properties;


/**
 * Abstract class of all shapes that containing the base functionality
 * and properties of a shape.
 * 
 * @author nauval
 *
 */
public abstract class AbstractDrawingShape implements Shape, Serializable {
	
	/** Shape name */
	private String name;
	
	/** X anchor coordinate */
	private double xAnchor;
	/** Y anchor coordinate */
	private double yAnchor;
	
	/** X coordinate where bottom-right-most coordinate located */
	private double xEnd;
	/** Y coordinate where bottom-right-most coordinate located */
	private double yEnd;
	
	/** Width */
	private double width;
	/** Height */
	private double height;
	
	/** Minimum value of x between anchor and end coordinate */
	protected double xMin;
	/** Minimum value of y between anchor and end coordinate */
	protected double yMin;
	
	/** Shape's graphics and color properties */
	private boolean filled;
	private boolean drawLine;
	private int strokeWidth;
	private Color fillColour;
	private Color lineColour;
	
	/** Fill opacity level, default 80% */
	private float fillOpacity;
	/** Line opacity level, default 80% */
	private float lineOpacity;
	
	/** Flags whether the shape is selected or not */
	private boolean selected;
	
	/** Nodes that appears at the corners of selection line */
	protected Rectangle2D[] nodes = new Rectangle2D[4];
	
	/** Shape is selection rectangle */
	private boolean selectionRect;
	
	/** Shape side is the same size */
	private boolean square;
	
	public AbstractDrawingShape(String name, double x, double y, double x1, double y1) {
		this.setxAnchor(x);
		this.setyAnchor(y);
		this.setxEnd(x1);
		this.setyEnd(y1);
		this.setFilled(true);
		this.lineColour = Properties.DEFAULT_LINE_COLOUR;
		this.fillColour = Properties.DEFAULT_FILL_COLOUR;
		this.fillOpacity = Properties.DEFAULT_FILL_OPACITY;
		this.lineOpacity = Properties.DEFAULT_LINE_OPACITY;
		this.strokeWidth = Properties.DEFAULT_STROKE_WIDTH;
		this.drawLine = true;
		this.name = name;
		this.selected = false;
		this.setSquare(false);
		this.setSelectionRect(false);
		// define edges
		createNodes();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AbstractDrawingShape other = (AbstractDrawingShape) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	
	/**
	 * @return the fillColour
	 */
	public Color getFillColor() {
		return fillColour;
	}

	/**
	 * Gets shape's fill colour
	 * @return fill colour
	 */
	public Color getFillColour() {
		return fillColour;
	}
	
	/**
	 * Gets opacity value
	 * @return opacity value
	 */
	public float getFillOpacity() {
		// TODO Auto-generated method stub
		return fillOpacity;
	}
	
	/**
	 * @return the height
	 */
	public double getHeight() {
		return height;
	}

	/**
	 * @return the lineColour
	 */
	public Color getLineColor() {
		return lineColour;
	}
	
	/**
	 * Gets shape's line colour
	 * @return
	 */
	public Color getLineColour() {
		return lineColour;
	}
	
	/**
	 * Gets opacity value
	 * @return opacity value
	 */
	public float getLineOpacity() {
		return lineOpacity;
	}
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the nodes
	 */
	public Rectangle2D[] getNodes() {
		return nodes;
	}

	/**
	 * Gets stroke width
	 * @return stroke width
	 */
	public int getStrokeWidth() {
		return strokeWidth;
	}

	/**
	 * Gets the type of the shape
	 * @return type of shape
	 */
	public abstract ShapeType getType();

	/**
	 * @return the width
	 */
	public double getWidth() {
		return width;
	}

	/**
	 * @return the x
	 */
	public double getX() {
		return xMin;
	}

	/**
	 * @return the xEnd
	 */
	public double getxEnd() {
		return xEnd;
	}
	
	/**
	 * @return the y
	 */
	public double getY() {
		return yMin;
	}
	
	/**
	 * @return the yEnd
	 */
	public double getyEnd() {
		return yEnd;
	}
	
	/**
	 * Check whether the line around the shape is drawn
	 * @return true if line is drawn, otherwise false
	 */
	public boolean isDrawLine() {
		return drawLine;
	}
	
	/**
	 * Sets filled information, true to fill the shape
	 * @return boolean filled
	 */
	public boolean isFilled() {
		return filled;
	}
	
	/**
	 * Gets whether the shape is selected or not
	 * @return true if selected, otherwise false
	 */
	public boolean isSelected() {
		return selected;
	}

	/**
	 * @return the selectionRect
	 */
	public boolean isSelectionRect() {
		return selectionRect;
	}

	/**
	 * Releases the shape from selection
	 */
	public void release() {
		selected = false;
	}

	/**
	 * Selects the shape
	 */
	public void select() {
		selected = true;
	}
	
	/**
	 * Assigns whether a line around the shape will be drawn or not
	 * @param draw
	 */
	public void setDrawLine(boolean draw) {
		drawLine = draw;
	}

	/**
	 * @param fillColour the fillColour to set
	 */
	public void setFillColor(Color fillColor) {
		this.fillColour = fillColor;
	}

	/**
	 * Sets fill shape's fill colour
	 * @param colour
	 */
	public void setFillColour(Color colour) {
		this.fillColour = colour;
	}

	/**
	 * Sets filled information, true to fill the shape
	 * @param filled
	 */
	public void setFilled(boolean filled) {
		this.filled = filled;
	}

	/**
	 * Sets opacity level, default will be 80% opaque
	 * @param opacity
	 */
	public void setFillOpacity(float opacity) {
		this.fillOpacity = opacity;
	}

	/**
	 * @param height the height to set
	 */
	public void setHeight(double height) {
		this.height = height;
	}

	/**
	 * @param lineColour the lineColour to set
	 */
	public void setLineColor(Color lineColor) {
		this.lineColour = lineColor;
	}

	/**
	 * Sets shape's line colour
	 * @param colour
	 */
	public void setLineColour(Color colour) {
		this.lineColour = colour;
	}

	/**
	 * Sets opacity level, default will be 80% opaque
	 * @param opacity
	 */
	public void setLineOpacity(float opacity) {
		this.lineOpacity = opacity;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Scales the shape to the appointed x and y
	 * @param x1
	 * @param y1
	 */
	public void setScale(double x1, double y1) {
		performScale(x1, y1);
	}

	/**
	 * @param selectionRect the selectionRect to set
	 */
	public void setSelectionRect(boolean selectionRect) {
		this.selectionRect = selectionRect;
	}

	/**
	 * Sets stroke width 
	 * @param width
	 */
	public void setStrokeWidth(int width) {
		this.strokeWidth = width;
	}
	
	/**
	 * Translate/move shape to the pointed location
	 * @param x
	 * @param y
	 */
	public void setTranslation(double x, double y) {
		performTranslation(x, y);
	}
	
	/**
	 * @param width the width to set
	 */
	public void setWidth(double width) {
		this.width = width;
	}
	
	/**
	 * @param x the x to set
	 */
	public void setX(double x) {
		this.xMin = x;
	}
	
	/**
	 * @param xEnd the xEnd to set
	 */
	public void setxEnd(double xEnd) {
		this.xEnd = xEnd;
	}

	/**
	 * @param y the y to set
	 */
	public void setY(double y) {
		this.xMin = y;
	}

	/**
	 * @param yEnd the yEnd to set
	 */
	public void setyEnd(double yEnd) {
		this.yEnd = yEnd;
	}

	/**
	 * Creates four rectangles to be places in every corner on the selection line
	 */
	protected void createNodes() {
		double xNode = getBounds().getX() - Properties.NODE_SIDE_SIZE;
		double yNode = getBounds().getY() - Properties.NODE_SIDE_SIZE;
		// top left node
		nodes[0] = new Rectangle2D.Double(xNode, yNode, Properties.NODE_SIDE_SIZE, Properties.NODE_SIDE_SIZE);
		
		// top right node
		xNode = getBounds().getMaxX();
		nodes[1] = new Rectangle2D.Double(xNode, yNode, Properties.NODE_SIDE_SIZE, Properties.NODE_SIDE_SIZE);
	
		// bottom right node
		xNode = getBounds().getMaxX();
		yNode = getBounds().getMaxY();
		nodes[2] = new Rectangle2D.Double(xNode, yNode, Properties.NODE_SIDE_SIZE, Properties.NODE_SIDE_SIZE);
		
		// bottom left node
		xNode = getX() - Properties.NODE_SIDE_SIZE;
		yNode = getBounds().getMaxY();
		nodes[3] = new Rectangle2D.Double(xNode, yNode, Properties.NODE_SIDE_SIZE, Properties.NODE_SIDE_SIZE);

	}

	/**
	 * @return the xAnchor
	 */
	protected double getxAnchor() {
		return xAnchor;
	}

	/**
	 * @return the yAnchor
	 */
	protected double getyAnchor() {
		return yAnchor;
	}

	/**
	 * Performs scale to appointed coordinate
	 * @param x1
	 * @param y1
	 */
	protected void performScale(double x1, double y1) {
		setxEnd(x1);
		setyEnd(y1);
		updateNodes();
	}
	
	/**
	 * Performs translation to the pointed coordinate
	 * @param x
	 * @param y
	 */
	protected void performTranslation(double x, double y) {
		setxAnchor(x);
		setyAnchor(y);
		setxEnd(x + getWidth());
		setyEnd(y + getHeight());
		updateNodes();
	}

	/**
	 * @param xAnchor the xAnchor to set
	 */
	protected void setxAnchor(double xAnchor) {
		this.xAnchor = xAnchor;
	}

	/**
	 * @param yAnchor the yAnchor to set
	 */
	protected void setyAnchor(double yAnchor) {
		this.yAnchor = yAnchor;
	}

	/**
	 * Calculates min x, min y, width and height between anchor coordinate 
	 * and end coordinate
	 */
	protected void updateBounds() {
		double width = Math.abs(getxAnchor() - getxEnd());
		double height = Math.abs(getyAnchor() - getyEnd());
		if (isSquare()) {
			width = height;
		}
		setWidth(width);
		setHeight(height);
		setX(Math.min(getxAnchor(), getxEnd()));
		yMin = Math.min(getyAnchor(), getyEnd());
	}

	/**
	 * Updates nodes location
	 */
	protected void updateNodes() {
		double xNode = getBounds().getX() - Properties.NODE_SIDE_SIZE;
		double yNode = getBounds().getY() - Properties.NODE_SIDE_SIZE;
		// top left node
		nodes[0].setRect(xNode, yNode, Properties.NODE_SIDE_SIZE, Properties.NODE_SIDE_SIZE);
		
		// top right node
		xNode = getBounds().getMaxX();
		nodes[1].setRect(xNode, yNode, Properties.NODE_SIDE_SIZE, Properties.NODE_SIDE_SIZE);

		// bottom right node
		xNode = getBounds().getMaxX();
		yNode = getBounds().getMaxY();
		nodes[2].setRect(xNode, yNode, Properties.NODE_SIDE_SIZE, Properties.NODE_SIDE_SIZE);

		// bottom left node
		xNode = getBounds().getX() - Properties.NODE_SIDE_SIZE;
		yNode = getBounds().getMaxY();
		nodes[3].setRect(xNode, yNode, Properties.NODE_SIDE_SIZE, Properties.NODE_SIDE_SIZE);
	}

	/**
	 * @return the square
	 */
	public boolean isSquare() {
		return square;
	}

	/**
	 * @param square the square to set
	 */
	public void setSquare(boolean square) {
		this.square = square;
	}
}
