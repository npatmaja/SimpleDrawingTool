package com.nauvalatmaja.SimpleDrawingTool.Exception;

public class IOAppException extends Exception {
	public IOAppException() {
		super("Error while performing IO operations");
	}
	
	public IOAppException(String message) {
		super(message);
	}
}
