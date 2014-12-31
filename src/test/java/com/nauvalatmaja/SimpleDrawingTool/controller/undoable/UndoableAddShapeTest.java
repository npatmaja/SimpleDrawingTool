package com.nauvalatmaja.SimpleDrawingTool.controller.undoable;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;

import com.nauvalatmaja.SimpleDrawingTool.model.DrawingDocument;
import com.nauvalatmaja.SimpleDrawingTool.model.shape.AbstractDrawingShape;

public class UndoableAddShapeTest {
	
	private UndoableAddShape classUnderTest;
	private DrawingDocument mockedDocument;
	private AbstractDrawingShape mockedShape;
	
	@Before
	public void setUp() throws Exception {
		mockedDocument = mock(DrawingDocument.class);
		mockedShape = mock(AbstractDrawingShape.class);
		
		when(mockedShape.toString()).thenReturn("shape");
		
		classUnderTest = new UndoableAddShape(mockedDocument, mockedShape, "Add shape");
	}

	@Test
	public void testUndo() {
		classUnderTest.undo();
		verify(mockedDocument, times(1)).removeShape(mockedShape);
	}

	@Test
	public void testRedo() {
		classUnderTest.redo();
		verify(mockedDocument, times(1)).addShape(mockedShape);
	}

	@Test
	public void testGetActionName() {
		String s = classUnderTest.getActionName();
		assertThat(s, is("Add shape" + " " + mockedShape.toString()));
	}

}
