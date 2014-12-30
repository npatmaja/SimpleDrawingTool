package com.nauvalatmaja.SimpleDrawingTool.model;



public interface DocumentModel {
	/**
	 * Flags that there is change(s) within document's shape
	 */
	public void markDirty();
	
	/**
	 * Flags that there is no change(s) within document
	 */
	public void markClean();
	
	/**
	 * Gets the dirty state of the documents, returns true if dirty and false if no changes
	 * @return dirty condition
	 */
	public boolean isDirty();
	
	/**
	 * Sets document name
	 * @param name
	 */
	public void setName(String name);
	
	/**
	 * Gets document name
	 * @return name
	 */
	public String getName();
}
