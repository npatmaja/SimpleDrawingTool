package com.nauvalatmaja.SimpleDrawingTool.Exception;

public class NoFileSelectedException extends IOAppException {
	public NoFileSelectedException() {
		super("No file selected");
	}
	
	public NoFileSelectedException(String message) {
		super(message);
	}
}
