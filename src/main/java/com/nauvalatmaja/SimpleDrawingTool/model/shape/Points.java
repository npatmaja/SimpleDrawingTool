package com.nauvalatmaja.SimpleDrawingTool.model.shape;

/**
 * Represents top left corner point and bottom right corner point
 * in a rectangular form.
 * 
 * (x,Y)
 * 	*----------
 * 	|		  |
 * 	|_________*
 * 			(x1, y1)
 * 
 * @author nauval
 *
 */
public class Points {
	private double x;
	private double y;
	private double x1;
	private double y1;
	
	public Points(double x, double y, double x1, double y1) {
		this.x = x;
		this.y = y;
		this.x1 = x1;
		this.y1 = y1;
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public double getX1() {
		return x1;
	}

	public void setX1(double x1) {
		this.x1 = x1;
	}

	public double getY1() {
		return y1;
	}

	public void setY1(double y1) {
		this.y1 = y1;
	}
}
