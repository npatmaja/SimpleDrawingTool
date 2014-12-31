package com.nauvalatmaja.SimpleDrawingTool.controller.undoable;

import java.awt.geom.Point2D;

public class TransformationPoint {
	private Point2D.Double beforeTranslationPoint;
	private Point2D.Double beforeScallingPoint;
	private Point2D.Double afterTranslationPoint;
	private Point2D.Double afterScallingPoint;
	
	public TransformationPoint(Point2D.Double beforeTranslation, Point2D.Double beforeScalling,
			Point2D.Double afterTranslation, Point2D.Double afterScalling) {
		this.beforeTranslationPoint = beforeTranslation;
		this.beforeScallingPoint = beforeScalling;
		this.afterTranslationPoint = afterTranslation;
		this.afterScallingPoint = afterScalling;
	}

	public Point2D.Double getBeforeTranslationPoint() {
		return beforeTranslationPoint;
	}

	public void setBeforeTranslationPoint(Point2D.Double beforeTranslationPoint) {
		this.beforeTranslationPoint = beforeTranslationPoint;
	}

	public Point2D.Double getBeforeScallingPoint() {
		return beforeScallingPoint;
	}

	public void setBeforeScallingPoint(Point2D.Double beforeScallingPoint) {
		this.beforeScallingPoint = beforeScallingPoint;
	}

	public Point2D.Double getAfterTranslationPoint() {
		return afterTranslationPoint;
	}

	public void setAfterTranslationPoint(Point2D.Double afterTranslationPoint) {
		this.afterTranslationPoint = afterTranslationPoint;
	}

	public Point2D.Double getAfterScallingPoint() {
		return afterScallingPoint;
	}

	public void setAfterScallingPoint(Point2D.Double afterScallingPoint) {
		this.afterScallingPoint = afterScallingPoint;
	}
}
