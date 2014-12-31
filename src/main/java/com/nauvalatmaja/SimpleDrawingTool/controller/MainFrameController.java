package com.nauvalatmaja.SimpleDrawingTool.controller;

import static com.nauvalatmaja.SimpleDrawingTool.Properties.*;

import java.awt.Color;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.IOException;

import com.nauvalatmaja.SimpleDrawingTool.Exception.NoFileSelectedException;
import com.nauvalatmaja.SimpleDrawingTool.controller.undoable.TransformationPoint;
import com.nauvalatmaja.SimpleDrawingTool.controller.undoable.UndoException;
import com.nauvalatmaja.SimpleDrawingTool.controller.undoable.UndoManager;
import com.nauvalatmaja.SimpleDrawingTool.factories.DrawingShapeFactory;
import com.nauvalatmaja.SimpleDrawingTool.model.DocumentModel;
import com.nauvalatmaja.SimpleDrawingTool.model.DrawingDocument;
import com.nauvalatmaja.SimpleDrawingTool.model.shape.AbstractDrawingShape;
import com.nauvalatmaja.SimpleDrawingTool.model.shape.Points;
import com.nauvalatmaja.SimpleDrawingTool.model.shape.ShapeType;
import com.nauvalatmaja.SimpleDrawingTool.view.MainFrame;



public class MainFrameController implements Controller{
	private static final String NEW_DOC_NOTIFICATION = "New document created";
	
	private final String DRAW_RECT_STATUS = "Status: Rectangel tool selected";
	private final String DRAW_ELLIPSE_STATUS = "Status: Ellipse tool selected";
	private final String DRAW_LINE_STATUS = "Status: Line tool selected";
	private final String PAN_STATUS = "Status: Pan tool selected";
	private final String SCALSE_STATUS = "Status: Scale tool selected";
	
	private DrawingDocument model;
	private MainFrame view;
	
	private boolean scale = false;
	private boolean pan = false;
	private boolean addShape = true;
	private boolean square = false;
	
	private Point2D.Double offset;
	private Point2D.Double startDragPosition;
	private Point2D.Double currentPosition;
	private Point2D.Double anchor1;
	private Point2D.Double end1;
	private Point2D.Double anchor2;
	private Point2D.Double end2;
	
	private AbstractDrawingShape draggedShape;
	private AbstractDrawingShape selectedShape;
	private AbstractDrawingShape newShape;
	
	private ShapeType shapeType = ShapeType.RECTANGLE;
	private String activeDocumentPath = "";
	
	private IOController ioController;
	private UndoController undoController;
	
	public MainFrameController(DocumentModel document) {
		init(document);
		registerViewToModel();
		initPoint2D();
	}

	private void init(DocumentModel document) {
		this.model = (DrawingDocument) document;
		
		view = new MainFrame(this, model);
		ioController = new IOController(view);
		undoController = new UndoController(new UndoManager());
	}
	
	/**
	 * Deletes current selected file and create undo command of the performed action
	 */
	public void delete() {
		model.releaseShape(model.getSelectedhape());
		if (selectedShape != null) {
			model.removeShape(selectedShape);
			undoController.deleteShape(model, selectedShape);	
		}
		selectedShape = null;
		newShape = null;
		draggedShape = null;
	}

	/**
	 * Exits application
	 */
	public void exit() {
		System.exit(0);
	}
	
	/**
	 * Insert image file into canvas
	 */
	public void insertImage() {
		try {
			// set shapeType to Image
			shapeType = ShapeType.IMAGE;
			BufferedImage bi = ioController.openImage();
			
			Points points = createImagePoints(bi);
			
			model.addShape(DrawingShapeFactory.getInstance()
					.createShape(shapeType, bi, createShapeName(), points));
			
			undoController.addShape(model, model.getTopShape());
			
			// Set newly created shape as selected shape
			selectedShape = model.getTopShape();
			model.selectShape(model.getTopShape());
			setToDefaultShape();
		} catch (IOException e) {
			view.showErrorDialog(e.getMessage());
		}
	}

	private Points createImagePoints(BufferedImage bi) {
		double ratio = calculateImageRatio(bi.getWidth(), view.getCanvasWidth());
		double width = bi.getWidth() / ratio;
		double height = bi.getHeight() / ratio;
		
		double x = (view.getCanvasBound().getWidth() - width) / 2;
		double y = (view.getCanvasBound().getHeight() - height) / 2;
		double x1 = x + width;
		double y1 = y + height;
		
		return new Points(x, y, x1, y1);
	}
	
	/**
	 * Creates new document
	 */
	public void newDocument() {
		model = new DrawingDocument();
		registerViewToModel();
		model.markClean();
		view.setFooterNotification(NEW_DOC_NOTIFICATION);
	}
	
	/**
	 * OPens a document
	 */
	public void open() {
		try {
			model = ioController.openDocument();
			
			registerViewToModel();
			String name = getFilename(activeDocumentPath);
			model.setName(name);
			model.markClean();
			
			view.setFooterNotification(name + " opened");
		} catch (FileNotFoundException e) {
			showErrorOnView("Can not open file:", e);
		} catch (IOException e) {
			showErrorOnView("Can not open file:", e);
		} catch (ClassNotFoundException e) {
			showErrorOnView("Can not open file:", e);
		} catch (NoFileSelectedException e) {
			// do nothing when no file selected
		}
	}

	private void showErrorOnView(String shortDescription, Exception e) {
		view.setFooterNotification("Can not open file:" + e.getMessage());
		view.showErrorDialog("Can not open file:" + e.getMessage());
	}
	
	@Override
	public void redo() {
		try {
			view.setFooterNotification("Redo " + undoController.doRedo());
			model.markDirty();
		} catch (UndoException e) {
			view.setFooterNotification(e.getMessage());
		}
	}

	/**
	 * Save active document, when the document have not be saved before then a file chooser dialog
	 * will appears and ask for the path and file name for the new file.
	 */
	public void save() {
		try {
			ioController.save(model, activeDocumentPath);
			String fileName = ioController.getFullPathFilename(activeDocumentPath);
			view.setFooterStatus("Saving " + fileName + "." + FILE_EXTENSION);
			
			model.setName(fileName);
			model.markClean();
			
			// Updates view statuses
			view.setFooterNotification(fileName + " saved");
			view.setFooterStatus("Status: ");
			view.showInfoDialog(fileName + " saved");
			
		} catch (FileNotFoundException e) {
			view.setFooterNotification("Can not save file:" + e.getMessage());
			view.showErrorDialog("Can not save file:" + e.getMessage());
		} catch (IOException e) {
			view.setFooterNotification("Can not save file:" + e.getMessage());
			view.showErrorDialog("Can not save file:" + e.getMessage());
		} catch (NoFileSelectedException e) {
			// do nothing when user cancel the operation
		} 
		
	}
	
	/** 
	 * Selects shape's fill colour
	 * @param colour
	 */
	public void selectShapeFillColour() {
		Color colour = view.showColourChooser(FILL_COLOUR_CHOOSER_TITLE, DEFAULT_FILL_COLOUR);
		if (selectedShape != null) {
			undoController.changeFillColourShape(selectedShape, colour);
			model.setFillColour(selectedShape, colour);
		}
		view.setShapeFillColor(colour);
	}

	/** 
	 * Selects shape's line colour
	 * @param colour
	 */
	public void selectShapeLineColour() {
		Color colour = view.showColourChooser(FILL_COLOUR_CHOOSER_TITLE, DEFAULT_FILL_COLOUR);
		if (selectedShape != null) {
			undoController.changeLineColourShape(selectedShape, colour);
			model.setLineColour(selectedShape, colour);
		}
		view.setShapeLineColor(colour);
	}
	
	/** 
	 * Sets shape's line width
	 * @param width
	 */
	public void setLineWidth(int width) {
		if (selectedShape != null) {
			model.setLineWidth(selectedShape, width);
		}
	}
	
	/**
	 * Set "Pan" mode off
	 */
	public void setPanOff() {
		pan = false;
	}
	
	/**
	 * Set "Pan" mode on
	 */
	public void setPanOn() {
		pan = true;
		view.setFooterStatus(PAN_STATUS);
	}

	/**
	 * Set current mouse location
	 * @param x
	 * @param y
	 */
	public void setPointer(double x, double y) {
		currentPosition.x = x;
		currentPosition.y = y;
		view.setFooterPointerLocation(currentPosition.x, currentPosition.y);
	}

	/**
	 * Sets "Scale" mode of
	 */
	public void setScaleOff() {
		scale = false;
	}
	
	/**
	 * Sets "Scale" mode on
	 */
	public void setScaleOn() {
		scale = true;
		view.setFooterStatus(SCALSE_STATUS);
	}
	
	/**
	 * Sets the square mode on, where the sides are in the same size
	 */
	public void setSquareOn() {
		square = true;
	}
	
	/**
	 * Sets the square mode off
	 */
	public void setSquareOff() {
		square = false;
	}
	
	/** 
	 * Sets shape's fill opacity
	 * @param opacity between 0 (transparent) and 1 (opaque)
	 */
	public void setSelectedShapeFillOpacity(float opacity) {
		if (selectedShape != null) {
			model.setFillOpacity(selectedShape, opacity);
		}
	}

	/** 
	 * Sets shape's line opacity
	 * @param opacity between 0 (transparent) and 1 (opaque)
	 */
	public void setSelectedShapeLineOpacity(float opacity) {
		if (selectedShape != null) {
			model.setLineOpacity(selectedShape, opacity);			
		}
	}
	
	/**
	 * Set shape mode, so every basic operation will be based on the shape mode
	 * @param shape
	 */
	public void setShape(ShapeType shape) {
		this.shapeType = shape;
		setViewFooterShapeStatus();
	}
	
	/**
	 * Marks controller to start dragging Shape that currently pointed by pointer
	 */
	public void startDragging() {
		// Checks if pointer is pointing on a shape, current active mode and perform accordingly
		AbstractDrawingShape s = model.getTopShape(currentPosition);
		startDragPosition = getCurrentPointerPosition();
		
		selectedShape = null;
		if ((pan || scale) && s != null) {
			startDragPanScaleShape(s);
		} else if (!pan && !scale) {
			startDraggAddShape();
		} else {
			startDragSelectionShape();
		}
		
	}

	/**
	 * Do dragging
	 */
	public void doDragging() {
		if (pan && draggedShape != null) {
			model.translateShape(draggedShape, currentPosition.x + offset.x, currentPosition.y + offset.y);
			view.setFooterNotification("Moving " + draggedShape.getName());
		} else if (scale) { 
			if (newShape != null) {
				newShape.setSquare(square);
				model.scaleShape(newShape, currentPosition.x, currentPosition.y);
			} else if (draggedShape != null) {
				draggedShape.setSquare(square);
				model.scaleShape(draggedShape, currentPosition.x, currentPosition.y);
				view.setFooterNotification("Scaling " + draggedShape.getName());
			}
		}
	}
	
	/**
	 * Marks controller to stop dragging
	 */
	public void stopDragging() {
		if (addShape) {
			stopDraggingAddShape();
		} else if (draggedShape != null) {
			stopDraggingDragShape();
		} else {
			stopDraggingSelectShape();
		}
	}


	@Override
	public void undo() {
		try {
			view.setFooterNotification("Undo " + undoController.doUndo());
			model.markDirty();
		} catch (UndoException e) {
			view.setFooterNotification(e.getMessage());
		}
	}

	/**
	 * Calculates image width ratio against canvas's width
	 * @param imageWidth
	 * @param canvasWidth
	 * @return
	 */
	private double calculateImageRatio(double imageWidth, double canvasWidth) {
		double ratio = 1;
		if (imageWidth >= canvasWidth / 2) {
			ratio = imageWidth / canvasWidth / 0.65;
		}
		return ratio;
	}

	/**
	 * Calculates offset value of the shape
	 * @param s
	 */
	private void calculateOffset(AbstractDrawingShape s) {
		offset.x = s.getBounds().getX() - currentPosition.x;
		offset.y = s.getBounds().getY() - currentPosition.y;
	}

	/**
	 * Creates selection rectangle
	 * @param point1
	 * @param point2
	 * @return rectangle selection
	 */
	private AbstractDrawingShape createSelectionRectangle(Point2D.Double point1, Point2D.Double point2) {
		Points points = new Points(point1.x, point1.y, point2.x, point2.y);
		AbstractDrawingShape shape = DrawingShapeFactory.getInstance()
				.createShape(ShapeType.RECTANGLE, createShapeName() , points);
		shape.setLineOpacity(0.6f);
		shape.setFilled(false);
		shape.setStrokeWidth(2);
		shape.setLineColour(Color.DARK_GRAY);
		shape.setSelectionRect(true);
		return shape;
	}

	/**
	 * Creates new shape from point1 to point2
	 * @param point1
	 * @param point2
	 * @return new shape
	 */
	private AbstractDrawingShape createShape(Point2D.Double point1, Point2D.Double point2) {
		Points points = new Points(point1.x, point1.y, point2.x, point2.y);
		AbstractDrawingShape shape = DrawingShapeFactory.getInstance()
				.createShape(shapeType, createShapeName(), points);
		shape.setFillColour(view.getSelectedFillColor());
		shape.setFillOpacity(view.getSelectedFillOpacity());
		shape.setLineColour(view.getSelectedLineColor());
		shape.setLineOpacity(view.getSelectedLineOpacity());
		return shape;
	}
	
	/**
	 * Creates name for new shape consisting of the type of the shape and its number
	 * @return name of new shape
	 */
	private String createShapeName() {
		return shapeType.name().toLowerCase() + " " + (model.getShapes().size() + 1);
	}

	/**
	 * Gets anchor point of the shape
	 * @param shape
	 * @return new instance of anchor point of the shape
	 */
	private Point2D.Double getAnchorFromShape(AbstractDrawingShape shape) {
		return new Point2D.Double(shape.getBounds2D().getX(), shape.getBounds2D().getY());
	}

	/**
	 * Gets current pointer possition
	 * @return new instance of current pointer position
	 */
	private Point2D.Double getCurrentPointerPosition() {
		return new Point2D.Double(currentPosition.x, currentPosition.y);
	}

	/**
	 * Gets end point of the shape
	 * @param shape
	 * @return new instance of end point of the shape
	 */
	private Point2D.Double getEndPointFromShape(AbstractDrawingShape shape) {
		return new Point2D.Double(shape.getBounds2D().getMaxX(), shape.getBounds2D().getMaxY());
	}

	/**
	 * Gets file name with extension from given file path
	 * @param fullPath
	 * @return filename
	 */
	private String getFilename(String fullPath) {
		return fullPath.substring(fullPath.lastIndexOf("/") + 1, fullPath.length());
	}

	/**
	 * Instantiate points
	 */
	private void initPoint2D() {
		offset = new Point2D.Double();
		currentPosition = new Point2D.Double();
	}
	
	/**
	 * Registers view to model, set the model into view and reset undo manager, 
	 * this method will be called when there is a re-instantiation of the model
	 */
	private void registerViewToModel() {
		model.addObserver(view);
		view.setModel(model);
		undoController.clearCommands();
	};
	
	/**
	 * Release current selected shape and select the given shape
	 * @param shape
	 */
	private void reselectShape(AbstractDrawingShape shape) {
		model.releaseShape(model.getSelectedhape());
		model.selectShape(shape);
	}
	
	/**
	 * Change shapeType to the default value which is rectangle
	 */
	private void setToDefaultShape() {
		shapeType = ShapeType.RECTANGLE;
	}
	
	private void setViewFooterShapeStatus() {
		switch (shapeType) {
		case RECTANGLE:
			view.setFooterStatus(DRAW_RECT_STATUS);
			break;
		case ELLIPSE:
			view.setFooterStatus(DRAW_ELLIPSE_STATUS);
			break;
		case LINE:
			view.setFooterStatus(DRAW_LINE_STATUS);
			break;
		}
	}

	/**
	 * Create shape from the point where the dragging occurred to the 
	 * current pointer location
	 */
	private void startDraggAddShape() {
		// Create a new drag-able shape
		scale = true;
		if (newShape == null) {
			addShape = true;
			newShape = createShape(startDragPosition, currentPosition);
			model.addShape(newShape);
		}
	}

	/**
	 * Initiates initial conditions prior to translating/moving or scaling shape
	 * @param shape
	 */
	private void startDragPanScaleShape(AbstractDrawingShape shape) {
		// release previous selected shape and set current selection to current pointed shape
		// Put selected shape in the temporary shape and perform scaling or translation
		reselectShape(shape);
		draggedShape = shape;
		selectedShape = shape;
		anchor1 = getAnchorFromShape(shape);
		end1 = getEndPointFromShape(shape);
		calculateOffset(shape);
	}
	
	/**
	 * Starts to create selection area (rectangle) 
	 */
	private void startDragSelectionShape() {
		scale = true;
		if (newShape == null) {
			newShape = createSelectionRectangle(startDragPosition, currentPosition);
			model.addShape(newShape);
		}
		reselectShape(selectedShape);
	}
	
	/**
	 * Removes non line shape which has no width or height from the model's list
	 * and removes line shape which has no width and height from the model's list
	 */
	private void stopDraggingAddShape() {
		boolean added = true;
		scale = false;
		model.releaseShape(model.getSelectedhape());
		selectedShape = null;
		if (shapeType != ShapeType.LINE && (newShape.getBounds().getWidth() == 0 || newShape.getBounds().getHeight() == 0)) {
			model.removeShape(newShape);
			added = false;
		} else if (shapeType == ShapeType.LINE && (newShape.getBounds().getWidth() == 0 && newShape.getBounds().getHeight() == 0)){
			model.removeShape(newShape);
			added = false;
		}
		// Record to undo
		if (added) {
			undoController.addShape(model, model.getTopShape());
			view.setFooterNotification(model.getTopShape().getName() + " added to canvas");
			selectedShape = model.getTopShape();
			model.selectShape(model.getTopShape());
		}
		selectedShape = null;
		newShape = null;
		startDragPosition = null;
		addShape = false;
	}
	
	/**
	 * Finishes scaling and translating a shape
	 */
	private void stopDraggingDragShape() {
		String action = scale ? " Scaled" : " Moved";
		AbstractDrawingShape undoableShape = model.getShapes().get(model.getShapes().indexOf(draggedShape));
		
		anchor2 = getAnchorFromShape(undoableShape);
		end2 = getEndPointFromShape(undoableShape);
		TransformationPoint point = new TransformationPoint(anchor1, end1, anchor2, end2);
		undoController.trasformShape(model, undoableShape, point);
		view.setFooterNotification(undoableShape.getName() + action);
		draggedShape = null;
		newShape = null;
	}
	
	/**
	 * Selects shape inside the selection rectangle and remove the selection 
	 * rectangle
	 */
	private void stopDraggingSelectShape() {
		model.releaseShape(model.getSelectedhape());
		if (newShape != null) {
			reselectShape(model.getTopShape(newShape.getBounds2D()));
			model.removeShape(newShape);
			selectedShape = model.getSelectedhape();
			newShape = null;
		}
	}
}
