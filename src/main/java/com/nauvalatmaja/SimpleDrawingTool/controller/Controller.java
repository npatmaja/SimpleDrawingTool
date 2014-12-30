package com.nauvalatmaja.SimpleDrawingTool.controller;


public interface Controller {
	/**
	 * Undo change
	 */
	public void undo();
	
	/**
	 * Redo change
	 */
	public void redo();
}
