package com.nauvalatmaja.SimpleDrawingTool.Exception;

public class DocumentException extends Exception {

	public DocumentException() {
		super("Exception while doing operation in the Document");
	}

	public DocumentException(String message) {
		super(message);
	}

	public DocumentException(Throwable arg0) {
		super(arg0);
	}

	public DocumentException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

}
