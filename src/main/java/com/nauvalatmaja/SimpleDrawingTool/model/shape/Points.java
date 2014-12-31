package com.nauvalatmaja.SimpleDrawingTool.model.shape;

/**
 * Represents top left corner point and bottom right corner point
 * in a rectangular form.
 * 
 * (xStart,YStart)
 * 	*----------
 * 	|		  |
 * 	|_________*
 * 			(xEnd, yEnd)
 * 
 * @author nauval
 *
 */
public class Points {
	private double xStart;
	private double yStart;
	private double xEnd;
	private double yEnd;
	
	public Points(double xStart, double yStart, double xEnd, double yEnd) {
		this.xStart = xStart;
		this.yStart = yStart;
		this.xEnd = xEnd;
		this.yEnd = yEnd;
	}

	public double getXStart() {
		return xStart;
	}

	public void setXStart(double x) {
		this.xStart = x;
	}

	public double getYStart() {
		return yStart;
	}

	public void setYStart(double y) {
		this.yStart = y;
	}

	public double getXEnd() {
		return xEnd;
	}

	public void setXEnd(double x1) {
		this.xEnd = x1;
	}

	public double getYEnd() {
		return yEnd;
	}

	public void setYEnd(double y1) {
		this.yEnd = y1;
	}
}
