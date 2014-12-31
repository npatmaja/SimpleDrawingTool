package com.nauvalatmaja.SimpleDrawingTool.controller.undoable;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;

import com.nauvalatmaja.SimpleDrawingTool.model.DocumentModel;
import com.nauvalatmaja.SimpleDrawingTool.model.DrawingDocument;
import com.nauvalatmaja.SimpleDrawingTool.model.shape.AbstractDrawingShape;

public class UndoManagerTest {
	private UndoManager classUnderTest;
	private DocumentModel mockedDocument = mock(DrawingDocument.class);
	private AbstractDrawingShape mockedShape = mock(AbstractDrawingShape.class);
	private AbstractUndoableCommand command = new UndoableAddShape(mockedDocument, mockedShape, "Command");
	
	@Before
	public void setUp() throws Exception {
		classUnderTest = new UndoManager();
		classUnderTest.putCommand(command);
	}

	@Test
	public void testPutCommand() {
		int size = classUnderTest.getCommands().size();
		assertThat(size, is(1));
		assertThat(classUnderTest.isUndoable(), is(true));
	}

	@Test
	public void testDoUndo() throws UndoException {
		classUnderTest.doUndo();
		int size = classUnderTest.getCommands().size();
		assertThat(size, is(1));
		assertThat(classUnderTest.getIndex(), lessThan(1));
		assertThat(classUnderTest.isUndoable(), is(false));
	}
	
	@Test(expected = UndoException.class)
	public void testDoUndoWhenNotUndoable() throws UndoException {
		classUnderTest.doUndo();
		classUnderTest.doUndo();
	}

	@Test
	public void testDoRedo() throws UndoException {
		classUnderTest.doUndo();
		classUnderTest.doRedo();
		
		int size = classUnderTest.getCommands().size();
		assertThat(size, is(1));
		assertThat(classUnderTest.getIndex(), is(0));
		assertThat(classUnderTest.isRedoable(), is(false));
		assertThat(classUnderTest.isUndoable(), is(true));
	}
	
	@Test(expected = UndoException.class)
	public void testDoUndoWhenNotRedoable() throws UndoException {
		classUnderTest.doRedo();
	}

	@Test
	public void testReset() {
		classUnderTest.reset();
		assertThat(classUnderTest.getCommands().size(), is(0));
		assertThat(classUnderTest.getIndex(), is(-1));
	}

}
