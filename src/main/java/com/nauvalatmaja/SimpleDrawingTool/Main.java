package com.nauvalatmaja.SimpleDrawingTool;

import com.nauvalatmaja.SimpleDrawingTool.controller.*;
import com.nauvalatmaja.SimpleDrawingTool.model.*;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		DocumentModel model = new DrawingDocument();
		new MainFrameController(model);
	}
}
