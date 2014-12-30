package com.nauvalatmaja.SimpleDrawingTool;

import java.awt.Color;
import java.awt.Font;
/**
 * Default properties that are used in multiple class
 * @author nauval
 *
 */
public final class Properties {
	/** Application's file extension */
	public static final String FILE_EXTENSION = "drx";
	/** Allowed images extension to be imported into application */
	public static final String[] ALLOWED_IMAGES_EXTENSION = {"JPG", "jpeg", "jpg", "png", "gif"};
	/** Default title for new document */
	public static final String DEFAULT_TITLE = "Untitled";
	/** Frame title for fill colour chooser dialog */
	public static final String FILL_COLOUR_CHOOSER_TITLE = "Select fill colour";
	/** Frame title for line colour chooser dialog */
	public static final String LINE_COLOUR_CHOOSER_TITLE = "Select line colour";
	
	/** Default fill colour */
	public static final Color DEFAULT_FILL_COLOUR = Color.BLUE;
	/** Default line color */
	public static final Color DEFAULT_LINE_COLOUR = Color.BLACK;
	
	/** Default fill opacity */
	public static final float DEFAULT_FILL_OPACITY = 0.8f;
	/** Default line color */
	public static final float DEFAULT_LINE_OPACITY = 0.8f;
	/** Default stroke width */
	public static final int DEFAULT_STROKE_WIDTH = 1;
	
	// Selection lines properties
	/** Default selection line color */
	public static final float SELECTION_LINE_OPACITY = 1f;
	/** Default selection line stroke width */
	public static final int SELECTION_STROKE_WIDTH = 2;
	/** Default selection line color */
	public static final Color SELECTION_LINE_COLOUR = Color.MAGENTA;
	/** Default gap between selection line shape */
	public static final int SELECTION_LINE_GAP = 3;
	
	// Selection nodes
	/** Node fill colour */
	public static final Color NODE_FILL_COLOUR = Color.MAGENTA;
	/** Node selection line color */
	public static final float NODE_LINE_OPACITY = 1f;
	/** Node selection line stroke width */
	public static final int NODE_STROKE_WIDTH = 1;
	/** Default gap between selection line shape */
	public static final double NODE_SIDE_SIZE = 2 * SELECTION_LINE_GAP;
	
	// Main frame's properties
	/** Default font for Menu Bar*/
	public static final Font DEFAULT_MENU_FONT = new Font("Helvetica", Font.BOLD, 12);
	/** Default font */
	public static final Font DEFAULT_FONT= new Font("Helvetica", Font.PLAIN, 12);
	/** Default footer text colour */
	public static final Color DEFAULT_FOOTER_TEXT_COLOUR = Color.DARK_GRAY;
}
