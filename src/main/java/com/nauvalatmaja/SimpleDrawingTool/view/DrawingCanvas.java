package com.nauvalatmaja.SimpleDrawingTool.view;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.JOptionPane;

import com.nauvalatmaja.SimpleDrawingTool.Properties;
import com.nauvalatmaja.SimpleDrawingTool.model.shape.AbstractDrawingShape;
import com.nauvalatmaja.SimpleDrawingTool.model.shape.DImage;
import com.nauvalatmaja.SimpleDrawingTool.model.shape.ShapeType;



/**
 * Extension of JComponents to draw shapes and images.
 * @author nauval
 *
 */
public class DrawingCanvas extends JComponent {
	
	private final int DEF_GRID_SIZE = 10;
	private boolean showGrid;
	private int gridSize = DEF_GRID_SIZE;
	
	private List<AbstractDrawingShape> shapes;

	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		try {
			paintBackground(g2);
			if (showGrid) {
				printBackgroundGrid(g2);
			}
			if (shapes != null) {
				for (AbstractDrawingShape s : getShapes()) {
					drawShape(g2, s);
					drawSelectionBorder(g2, s);
				}
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	/**
	 * Draw shape and image with given stroke width and opacity
	 * @param g2
	 * @param s
	 * @throws IOException
	 */
	private void drawShape(Graphics2D g2, AbstractDrawingShape s) throws IOException {
		g2.setStroke(new BasicStroke(s.getStrokeWidth()));
		
		// if shape is image then convert byte data stored in the object to 
		// BufferedImage then display draw it opaquely
		if (s.getType() == ShapeType.IMAGE) {
			DImage img = (DImage) s;
			if (img.getImage() == null) {
				img.setImage(ImageIO.read(new ByteArrayInputStream(img.getImageByte())));
			}
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, img.getFillOpacity()));
			g2.drawImage(img.getImage(), (int) img.getBounds2D().getX(), (int) img.getBounds2D().getY(), 
					(int) img.getBounds2D().getWidth(), (int) img.getBounds2D().getHeight(), null);
		} else {
			if (s.isFilled()) {
			    g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, s.getFillOpacity()));
				g2.setColor(s.getFillColour());
				g2.fill(s);
			} 
		    if (s.isDrawLine()) {
		    	g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, s.getLineOpacity()));
				g2.setColor(s.getLineColour());
				g2.draw(s);
			}
		}
	}

	/**
	 * Draws selection rectangle outside the selected shape
	 * @param g2
	 * @param shape
	 */
	private void drawSelectionBorder(Graphics2D g2, AbstractDrawingShape shape) {
		if (shape.isSelected()) {
			int x = (int) shape.getX() - Properties.SELECTION_LINE_GAP;
			int y = (int) shape.getY() - Properties.SELECTION_LINE_GAP;
			int width = (int) shape.getWidth() + 2 * Properties.SELECTION_LINE_GAP;
			int height = (int) shape.getHeight() + 2 * Properties.SELECTION_LINE_GAP;
			
			g2.setStroke(new BasicStroke(Properties.SELECTION_STROKE_WIDTH));
			g2.setColor(Properties.SELECTION_LINE_COLOUR);
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, Properties.SELECTION_LINE_OPACITY));
			
			g2.drawRect(x, y, width, height);
			
			// nodes
			drawSelectionNodes(g2, shape);
		}
	}

	/**
	 * Draws selection nodes
	 * @param g2
	 * @param shape
	 */
	private void drawSelectionNodes(Graphics2D g2, AbstractDrawingShape shape) {
		g2.setStroke(new BasicStroke(Properties.NODE_STROKE_WIDTH));
		g2.setColor(Properties.NODE_FILL_COLOUR);
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, Properties.NODE_LINE_OPACITY));
		
		for (Rectangle2D node : shape.getNodes()) {
			g2.fill(node);
		}
	}
	
	@Override
	public void setBackground(Color bg) {
		super.setBackground(bg);
	}
	
	/** Adds shape to the list */
	public void addShape(AbstractDrawingShape shape) {
		getShapes().add(shape);
	}
	
	/** Paint background color */
	private void paintBackground(Graphics2D g2) {
		// Gets background color
		g2.setPaint(getBackground());
		// Colors the background
		g2.fillRect(0, 0, getWidth(), getHeight());
	}
	
	/** print grid with light gray color */
	private void printBackgroundGrid(Graphics2D g2) {
		g2.setPaint(Color.LIGHT_GRAY);
		for (int i = 0; i < getWidth(); i += gridSize) {
			Shape line = new Line2D.Double(i, 0, i, getHeight());
			g2.draw(line);
		}
		for (int i = 0; i < getHeight(); i += gridSize) {
			Shape line = new Line2D.Double(0, i, getWidth(), i);
			g2.draw(line);
		}
	}
	
	/**
	 * @return the showGrid
	 */
	public boolean isShowGrid() {
		return showGrid;
	}

	/**
	 * @param showGrid the showGrid to set
	 */
	public void setShowGrid(boolean showGrid) {
		this.showGrid = showGrid;
	}

	/**
	 * @return the shapes
	 */
	public List<AbstractDrawingShape> getShapes() {
		return shapes;
	}

	/**
	 * @param shapes the shapes to set
	 */
	public void setShapes(List<AbstractDrawingShape> shapes) {
		this.shapes = shapes;
	}
}
