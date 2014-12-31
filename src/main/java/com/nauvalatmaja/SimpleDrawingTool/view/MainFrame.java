package com.nauvalatmaja.SimpleDrawingTool.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.util.Observable;
import java.util.Observer;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JSpinner;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.MatteBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileFilter;

import com.nauvalatmaja.SimpleDrawingTool.Properties;
import com.nauvalatmaja.SimpleDrawingTool.controller.Controller;
import com.nauvalatmaja.SimpleDrawingTool.controller.MainFrameController;
import com.nauvalatmaja.SimpleDrawingTool.model.DocumentModel;
import com.nauvalatmaja.SimpleDrawingTool.model.DrawingDocument;
import com.nauvalatmaja.SimpleDrawingTool.model.shape.AbstractDrawingShape;
import com.nauvalatmaja.SimpleDrawingTool.model.shape.ShapeType;


public class MainFrame extends JFrame implements Observer {

	/** Frame width */
	private final int FRAME_WIDTH = 960;
	/** Frame height */
	private final int FRAME_HEIGHT = 640;
	/** Starting x position of the frame */
	private final int X_POS = (Toolkit.getDefaultToolkit().getScreenSize().width - FRAME_WIDTH) / 2;
	/** Starting y position of the frame */
	private final int Y_POS = (Toolkit.getDefaultToolkit().getScreenSize().height - FRAME_HEIGHT) / 2;


	/** Selected fill colour */
	private Color selectedFillColor;
	/** Selected line color */
	private Color selectedLineColor;


	/** Controller */
	private MainFrameController controller;
	/** Model */
	private DrawingDocument model;


	/** GUI Components */
	// Root panel, parents of all components in the frame
	private JPanel pnlRoot;

	// Menu bar and items
	private JMenuBar menuBar;
	// File menu and its children: New, Open, Save and Close
	private JMenu mnFile;
	private JMenuItem mnItemNew;
	private JMenuItem mnItemOpen;
	private JMenuItem mnItemSave;
	private JMenuItem mnItemClose;
	// Insert menu and its children: insert image
	private JMenu mnInsert;
	private JMenuItem mnItemInsertImage;

	// Toolbar1
	private JPanel pnlToolbar;
	private JToolBar toolBar;
	private JButton btnDrawRectangle;
	private JButton btnDrawEllipse;
	private JButton btnDrawLine;
	private JButton btnPan;
	private JButton btnScale;
	private JButton btnDelete;
	private JButton btnUndo;
	private JButton btnRedo;

	//Toolbar2
	private JToolBar toolBar2;
	private JLabel lblFillColour;
	private JLabel lblLineColour;
	private JLabel lblFillOpacity;
	private JLabel lblLineOpacity;
	private JSpinner spnrFillOpacity;
	private JSpinner spnrLineOpacity;
	private SpinnerModel fillOpacityModel;
	private SpinnerModel lineOpacityModel;
	private JLabel lblLineWidth;
	private SpinnerNumberModel lineWidthModel;
	private JSpinner spnrLineWidth;
	private ButtonColourChooser btnFillColourChooser;
	private ButtonColourChooser btnLineColourChooser;

	// Main panel
	/** Custom components to draw shapes */
	private DrawingCanvas canvas;
	private JPanel pnlDocument;
	private JPanel northPanel;
	private JPanel westPanel;
	private JPanel eastPanel;
	private JPanel southPanel;

	// Footer
	private JPanel pnlFooter;
	private JPanel pnlFooterWest;
	private JPanel pnlFooterCenter;
	private JPanel pnlFooterEast;
	private JLabel lblNotification;
	private JLabel lblStatus;
	private JLabel lblY;
	private JLabel lblX;
	private JLabel lblXPoint;
	private JLabel lblYPoint;
	private JSeparator sprtFooter1;
	private JSeparator sprtFooter2;

	public MainFrame(Controller controller, DocumentModel model) {
		this.controller = (MainFrameController) controller;
		this.model = (DrawingDocument) model;

		selectedFillColor = Properties.DEFAULT_FILL_COLOUR;
		selectedLineColor = Properties.DEFAULT_LINE_COLOUR;

		initGuiComponents();

		setListeners();

		setVisible(true);
	}

	private void setListeners() {
		setMenubarButtonListener();
		setToolbarButtonListener();
		setToolbarSpinnerListener();
		setCanvasListener();
	}

	private void setCanvasListener() {
		CanvasMouseListener mouseListener = new CanvasMouseListener();
		CanvasMouseMotionListener motionListener = new CanvasMouseMotionListener();
		FrameKeyListener keyListener = new FrameKeyListener();
		canvas.addMouseListener(mouseListener);
		canvas.addMouseMotionListener(motionListener);

		// Add key binding to activate the square mode when shift is pressed and turn it off when shift key is released
		canvas.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
				KeyStroke.getKeyStroke(KeyEvent.VK_SHIFT, KeyEvent.SHIFT_DOWN_MASK), "square on");
		canvas.getActionMap().put("square on", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.setSquareOn();
			}
		});

		canvas.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("released SHIFT"), "square off");
		canvas.getActionMap().put("square off", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.setSquareOff();
			}
		});
	}

	private void setToolbarSpinnerListener() {
		SpinnerListener listener = new SpinnerListener();
		spnrFillOpacity.addChangeListener(listener);
		spnrLineOpacity.addChangeListener(listener);
		spnrLineWidth.addChangeListener(listener);
	}

	private void setToolbarButtonListener() {
		ToolbarButtonListener listener = new ToolbarButtonListener();
		btnDrawRectangle.addActionListener(listener);
		btnDrawEllipse.addActionListener(listener);
		btnDrawLine.addActionListener(listener);
		btnPan.addActionListener(listener);
		btnScale.addActionListener(listener);
		btnDelete.addActionListener(listener);
		btnUndo.addActionListener(listener);
		btnRedo.addActionListener(listener);
		btnFillColourChooser.addButtonActionListener(listener);
		btnLineColourChooser.addButtonActionListener(listener);
	}

	private void setMenubarButtonListener() {
		MenuBarButtonListener listener = new MenuBarButtonListener();
		mnItemNew.addActionListener(listener);
		mnItemOpen.addActionListener(listener);
		mnItemSave.addActionListener(listener); 
		mnItemClose.addActionListener(listener);
		mnItemInsertImage.addActionListener(listener);
	}

	/**
	 * Create GUI components
	 */
	private void initGuiComponents() {
		// JFrame
		initFrame();

		// Menubar
		initMenuBar();

		// Toolbars
		initToolbar();

		// Main panels
		initMainPanel();

		// Footer
		initFooter();
	}

	/**
	 * Creates and configures footer components. Footer consists of labels to show what tool is
	 * currently active, notifications, and current pointer location
	 */
	private void initFooter() {
		pnlFooter = new JPanel();
		pnlRoot.add(pnlFooter, BorderLayout.SOUTH);
		pnlFooter.setLayout(new BorderLayout());

		// West side, Status
		pnlFooterWest = new JPanel();
		pnlFooter.add(pnlFooterWest, BorderLayout.WEST);

		lblStatus = new JLabel("Status:");
		lblStatus.setForeground(Properties.DEFAULT_FOOTER_TEXT_COLOUR);
		lblStatus.setFont(Properties.DEFAULT_FONT);
		lblStatus.setPreferredSize(new Dimension(200, 10));
		pnlFooterWest.add(lblStatus);

		// Center side, Notification
		pnlFooterCenter = new JPanel();
		pnlFooter.add(pnlFooterCenter, BorderLayout.CENTER);
		pnlFooterCenter.setLayout(new BorderLayout());

		// Add separator
		sprtFooter1 = new JSeparator();
		sprtFooter1.setOrientation(SwingConstants.VERTICAL);
		pnlFooterCenter.add(sprtFooter1, BorderLayout.WEST);

		// Notification label
		lblNotification = new JLabel();
		lblNotification.setForeground(Properties.DEFAULT_FOOTER_TEXT_COLOUR);
		lblNotification.setFont(Properties.DEFAULT_FONT);
		pnlFooterCenter.add(lblNotification, BorderLayout.CENTER);

		// Add separator
		sprtFooter2 = new JSeparator();
		sprtFooter2.setOrientation(SwingConstants.VERTICAL);
		pnlFooterCenter.add(sprtFooter2, BorderLayout.EAST);

		// East side, current pointer location
		pnlFooterEast = new JPanel();
		pnlFooter.add(pnlFooterEast, BorderLayout.EAST);

		lblX = new JLabel("x:");
		lblX.setForeground(Properties.DEFAULT_FOOTER_TEXT_COLOUR);
		lblX.setFont(Properties.DEFAULT_FONT);
		pnlFooterEast.add(lblX);

		lblXPoint = new JLabel("00.00");
		lblXPoint.setForeground(Properties.DEFAULT_FOOTER_TEXT_COLOUR);
		lblXPoint.setFont(Properties.DEFAULT_FONT);
		pnlFooterEast.add(lblXPoint);

		lblY = new JLabel("y:");
		lblY.setForeground(Properties.DEFAULT_FOOTER_TEXT_COLOUR);
		lblY.setFont(Properties.DEFAULT_FONT);
		pnlFooterEast.add(lblY);

		lblYPoint = new JLabel("00.00");
		lblYPoint.setForeground(Properties.DEFAULT_FOOTER_TEXT_COLOUR);
		lblYPoint.setFont(Properties.DEFAULT_FONT);
		pnlFooterEast.add(lblYPoint);

	}

	/** 
	 * Create and configure main panel
	 */
	private void initMainPanel() {
		// Create root panel for drawing panel with 1px top border and bottom border
		// to separate between toolbar and drawing panel
		pnlDocument = new JPanel();
		pnlDocument.setBorder(new MatteBorder(1, 0, 1, 0, Color.DARK_GRAY));
		pnlRoot.add(pnlDocument);
		pnlDocument.setLayout(new BorderLayout());

		// Create canvas component as drawing panel with grid
		canvas = new DrawingCanvas();
		pnlDocument.add(canvas);
		canvas.setShowGrid(true);
		canvas.setBackground(Color.WHITE);

		// Additional panels to create gap around the drawing panel
		northPanel = new JPanel();
		pnlDocument.add(northPanel, BorderLayout.NORTH);

		westPanel = new JPanel();
		pnlDocument.add(westPanel, BorderLayout.WEST);

		eastPanel = new JPanel();
		pnlDocument.add(eastPanel, BorderLayout.EAST);

		southPanel = new JPanel();
		pnlDocument.add(southPanel, BorderLayout.SOUTH);
	}

	/**
	 * Create and configure toolbars
	 */
	private void initToolbar() {
		// Setup panel for toolbars
		pnlToolbar = new JPanel();
		pnlRoot.add(pnlToolbar, BorderLayout.NORTH);
		pnlToolbar.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));

		// toolbar1, for drawing tools, undo and redo
		toolBar = new JToolBar();
		pnlToolbar.add(toolBar);

		btnDrawRectangle = new JButton();
		btnDrawRectangle.setName("Draw Rectangle Tool");
		btnDrawRectangle.setBorderPainted(false);
		btnDrawRectangle.setToolTipText("Draw New Rectangle");
		btnDrawRectangle.setIcon(new ImageIcon(MainFrame.class.getResource("/resources/draw-square.png")));
		toolBar.add(btnDrawRectangle);

		btnDrawEllipse = new JButton();
		btnDrawEllipse.setName("Draw Ellipse Tool");
		btnDrawEllipse.setBorderPainted(false);
		btnDrawEllipse.setToolTipText("Draw New Ellipse");
		btnDrawEllipse.setIcon(new ImageIcon(MainFrame.class.getResource("/resources/draw-ellipse.png")));
		toolBar.add(btnDrawEllipse);

		btnDrawLine = new JButton();
		btnDrawLine.setName("Draw Line Tool");
		btnDrawLine.setBorderPainted(false);
		btnDrawLine.setIcon(new ImageIcon(MainFrame.class.getResource("/resources/draw-line.png")));
		btnDrawLine.setToolTipText("Draw New Line");
		toolBar.add(btnDrawLine);

		btnPan = new JButton();
		btnPan.setName("Pan Tool");
		btnPan.setBorderPainted(false);
		btnPan.setToolTipText("Move Shape");
		btnPan.setIcon(new ImageIcon(MainFrame.class.getResource("/resources/pan.png")));
		toolBar.add(btnPan);

		btnScale = new JButton();
		btnScale.setName("Scale Tool");
		btnScale.setBorderPainted(false);
		btnScale.setToolTipText("Scale shape");
		btnScale.setIcon(new ImageIcon(MainFrame.class.getResource("/resources/arrows.png")));
		toolBar.add(btnScale);

		btnDelete = new JButton();
		btnDelete.setName("Delete Shape");
		btnDelete.setBorderPainted(false);
		btnDelete.setToolTipText("Delete shape");
		btnDelete.setIcon(new ImageIcon(MainFrame.class.getResource("/resources/delete.png")));
		toolBar.add(btnDelete);

		btnUndo = new JButton();
		btnUndo.setName("Undo");
		btnUndo.setBorderPainted(false);
		btnUndo.setIcon(new ImageIcon(MainFrame.class.getResource("/resources/undo.png")));
		btnUndo.setToolTipText("Undo");
		toolBar.add(btnUndo);

		btnRedo = new JButton();
		btnRedo.setName("Redo");
		btnRedo.setBorderPainted(false);
		btnRedo.setIcon(new ImageIcon(MainFrame.class.getResource("/resources/redo.png")));
		btnRedo.setToolTipText("Redo");
		toolBar.add(btnRedo);

		// toolbar2, shape's properties
		toolBar2 = new JToolBar();
		pnlToolbar.add(toolBar2);

		lblFillColour = new JLabel("Fill colour:");
		lblFillColour.setFont(Properties.DEFAULT_FONT);
		toolBar2.add(lblFillColour);

		/* put button color chooser here */
		btnFillColourChooser = new ButtonColourChooser(selectedFillColor);
		btnFillColourChooser.setName("Choose Fill Colour");
		btnFillColourChooser.setPreferredSize(new Dimension(30, 30));
		toolBar2.add(btnFillColourChooser);

		lblFillOpacity = new JLabel("Fill opacity:");
		lblFillOpacity.setFont(Properties.DEFAULT_FONT);
		toolBar2.add(lblFillOpacity);

		fillOpacityModel = new SpinnerNumberModel(80, 0, 100, 1);
		spnrFillOpacity = new JSpinner(fillOpacityModel);
		spnrFillOpacity.setName("Fill Opacity");
		spnrFillOpacity.setPreferredSize(new Dimension(60, 30));
		spnrFillOpacity.setFont(Properties.DEFAULT_FONT);
		toolBar2.add(spnrFillOpacity);

		lblLineColour = new JLabel("Line colour:");
		lblLineColour.setFont(Properties.DEFAULT_FONT);
		toolBar2.add(lblLineColour);

		/* button colour choose */
		btnLineColourChooser = new ButtonColourChooser(selectedLineColor);
		btnLineColourChooser.setName("Choose Line Colour");
		btnLineColourChooser.setPreferredSize(new Dimension(30, 30));
		toolBar2.add(btnLineColourChooser);

		lblLineOpacity = new JLabel("Line opacity:");
		lblLineOpacity.setFont(Properties.DEFAULT_FONT);
		toolBar2.add(lblLineOpacity);

		lineOpacityModel = new SpinnerNumberModel(80, 0, 100, 1);
		spnrLineOpacity = new JSpinner(lineOpacityModel);
		spnrLineOpacity.setName("Line Opacity");
		spnrLineOpacity.setPreferredSize(new Dimension(60, 30));
		toolBar2.add(spnrLineOpacity);

		lblLineWidth = new JLabel("Line width:");
		lblLineWidth.setFont(Properties.DEFAULT_FONT);
		toolBar2.add(lblLineWidth);

		lineWidthModel = new SpinnerNumberModel(1, 1, 10, 1);
		spnrLineWidth = new JSpinner(lineWidthModel);
		spnrLineWidth.setName("Line Width");
		spnrLineWidth.setPreferredSize(new Dimension(50, 30));
		spnrLineWidth.setFont(Properties.DEFAULT_FONT);
		toolBar2.add(spnrLineWidth);
	}

	/**
	 * Configure JFrame and root panel
	 */
	private void initFrame() {
		setBounds(X_POS, Y_POS, FRAME_WIDTH,FRAME_HEIGHT);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Create root panel and attach it to frame
		pnlRoot = new JPanel();
		getContentPane().add(pnlRoot, BorderLayout.CENTER);
		pnlRoot.setLayout(new BorderLayout());

		// Set title
		setTitle(model.getName());
	}

	/** 
	 * Create and configure menu bar
	 */
	private void initMenuBar() {
		menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		// File menu
		mnFile = new JMenu("File");
		mnFile.setFont(Properties.DEFAULT_MENU_FONT);
		menuBar.add(mnFile);

		// Menu item New
		mnItemNew = new JMenuItem("New");
		mnItemNew.setName("New");
		mnItemNew.setFont(Properties.DEFAULT_FONT);
		mnFile.add(mnItemNew);

		// Menu item open
		mnItemOpen = new JMenuItem("Open");
		mnItemOpen.setName("Open");
		mnItemOpen.setFont(Properties.DEFAULT_FONT);
		mnFile.add(mnItemOpen);

		// Menu item save
		mnItemSave = new JMenuItem("Save");
		mnItemSave.setName("Save");
		mnItemSave.setFont(Properties.DEFAULT_FONT);
		mnFile.add(mnItemSave);

		// Menu item close
		mnItemClose = new JMenuItem("Close");
		mnItemClose.setName("Close");
		mnItemClose.setFont(Properties.DEFAULT_FONT);
		mnFile.add(mnItemClose);

		// Insert menu
		mnInsert = new JMenu("Insert");
		mnInsert.setFont(Properties.DEFAULT_MENU_FONT);
		menuBar.add(mnInsert);

		// Menu item insert image
		mnItemInsertImage = new JMenuItem("Image");
		mnItemInsertImage.setName("Insert Image");
		mnItemInsertImage.setFont(Properties.DEFAULT_FONT);
		mnInsert.add(mnItemInsertImage);
	}

	@Override
	public void update(Observable o, Object arg) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				// Get shapes and repaint shapes to canvas
				canvas.setShapes(model.getShapes());
				MainFrame.this.invalidate();
				canvas.repaint();

				// Set title if there is a change in documents
				if (model.isDirty()) {
					MainFrame.this.setTitle(model.getName() + "*");
				} else {
					MainFrame.this.setTitle(model.getName());
				}

				// updates shape's properties
				AbstractDrawingShape s = model.getSelectedhape();
				if (s != null) {
					setShapeFillOpacity(s.getFillOpacity());
					setShapeLineOpacity(s.getLineOpacity());
					setShapeFillColor(s.getFillColour());
					setShapeLineColor(s.getLineColour());
					setShapeLineWidth(s.getStrokeWidth());
				} else {
					setShapeFillOpacity(Properties.DEFAULT_FILL_OPACITY);
					setShapeLineOpacity(Properties.DEFAULT_LINE_OPACITY);
					setShapeFillColor(Properties.DEFAULT_FILL_COLOUR);
					setShapeLineColor(Properties.DEFAULT_LINE_COLOUR);
					setShapeLineWidth(Properties.DEFAULT_STROKE_WIDTH);
				}
			}
		});
	}

	public void setModel(DocumentModel model) {
		this.model = (DrawingDocument) model;
	}

	/**
	 * Sets footer status
	 * @param status
	 * @return
	 */
	public void setFooterStatus(String status) {
		lblStatus.setText(status);
	}

	/**
	 * Sets notification message
	 * @param notification
	 */
	public void setFooterNotification(String notification) {
		lblNotification.setText(notification);
	}

	public void setFooterPointerLocation(double x, double y) {
		lblXPoint.setText(String.format("%.2f", x));
		lblYPoint.setText(String.format("%.2f", y));
	}

	/**
	 * Sets fill opacity spinner to reflect shape's fill opacity
	 * @param opacity
	 */
	public void setShapeFillOpacity(float opacity) {
		fillOpacityModel.setValue(opacity * 100);
	}

	/**
	 * Sets fill opacity spinner to reflect shape's fill opacity
	 * @param opacity
	 */
	public void setShapeLineOpacity(float opacity) {
		lineOpacityModel.setValue(opacity * 100);
	}

	/**
	 * Sets line width spinner to reflect shape's line width
	 * @param width
	 */
	public void setShapeLineWidth(int width) {
		lineWidthModel.setValue(width);
	}

	/**
	 * Sets button fill colour background to reflect shape's fill colour
	 * @param colour the selectedFillColor to set
	 */
	public void setShapeFillColor(Color colour) {
		this.selectedFillColor = colour;
		btnFillColourChooser.setButtonColour(selectedFillColor);
	}

	/**
	 * Sets button line colour background to reflect shape's line colour
	 * @param colour the selectedLineColor to set
	 */
	public void setShapeLineColor(Color colour) {
		this.selectedLineColor = colour;
		btnLineColourChooser.setButtonColour(selectedLineColor);
	}

	/**
	 * Gets current selected fill colour
	 * @return the selectedFillColor
	 */
	public Color getSelectedFillColor() {
		return selectedFillColor;
	}

	/**
	 * Gets current selected line colour
	 * @return the selectedFillColor	
	 */
	public Color getSelectedLineColor() {
		return selectedLineColor;
	}

	/**
	 * Gets current selected fill opacity
	 * @return the selectedFillColor
	 */
	public float getSelectedFillOpacity() {
		return Float.parseFloat(fillOpacityModel.getValue().toString()) / 100;
	}

	/**
	 * Gets current selected line opacity
	 * @return the selectedFillColor	
	 */
	public float getSelectedLineOpacity() {
		return Float.parseFloat(lineOpacityModel.getValue().toString()) / 100;
	}

	/**
	 * Shows info dialog to the user
	 * @param message
	 */
	public void showInfoDialog(String message) {
		JOptionPane.showMessageDialog(this, message);
	}

	/**
	 * Shows warning dialog to the user
	 * @param message
	 */
	public void showWarningDialog(String message) {
		JOptionPane.showMessageDialog(this ,message, "Warning", JOptionPane.WARNING_MESSAGE);
	}

	/**
	 * Shows error dialog to the user
	 * @param message
	 */
	public void showErrorDialog(String message) {
		JOptionPane.showMessageDialog(this ,message, "Error", JOptionPane.ERROR_MESSAGE);
	}

	/**
	 * Gets canvas size
	 * @return
	 */
	public Rectangle2D getCanvasBound() {
		return canvas.getBounds();
	}
	
	public double getCanvasWidth() {
		return getCanvasBound().getWidth();
	}
	
	public double getCanvasHeight() {
		return getCanvasBound().getHeight();
	}

	/**
	 * Shows file chooser dialog box
	 * @param approveText
	 * @param filter
	 * @return file selected
	 */
	public String showFileChooser(String approveText, FileFilter filter) {
		JFileChooser fc = new JFileChooser();
		fc.setFileFilter(filter);
		fc.showDialog(MainFrame.this, approveText);
		File file = fc.getSelectedFile();
		if (file != null) {
			return file.getAbsolutePath();
		} else {
			return "";
		}
	}

	/**
	 * Shows colour chooser dialog
	 * @param title
	 * @param colour
	 * @return
	 */
	public Color showColourChooser(String title, Color colour) {
		Color tmp = JColorChooser.showDialog(MainFrame.this, title, colour);
		return tmp != null? tmp : colour;
	}

	/**
	 * Button listener
	 * @author nauval
	 *
	 */
	private class ToolbarButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			JComponent source = (JComponent) e.getSource();
			if (source.getName().equals(btnDrawRectangle.getName())) {
				controller.setShape(ShapeType.RECTANGLE);
			} else if (source.getName().equals(btnDrawEllipse.getName())) {
				controller.setShape(ShapeType.ELLIPSE);
			} else if (source.getName().equals(btnDrawLine.getName())) {
				controller.setShape(ShapeType.LINE);
			} else if (source.getName().equals(btnPan.getName())) {
				controller.setPanOn();
			} else if (source.getName().equals(btnScale.getName())) {
				controller.setScaleOn();
			} else if (source.getName().equals(btnDelete.getName())) {
				controller.delete();
			} else if (source.getName().equals(btnUndo.getName())) {
				controller.undo();
			} else if (source.getName().equals(btnRedo.getName())) {
				controller.redo();
			} else if (source.getName().equals(btnFillColourChooser.getName())) {
				controller.selectShapeFillColour();
			} else if (source.getName().equals(btnLineColourChooser.getName())) {
				controller.selectShapeLineColour();
			} 
			postButtonClickedRoutine(source);
		}

		/**
		 * Actions after a button clicked
		 * @param button
		 */
		private void postButtonClickedRoutine(JComponent button) {
			if (button.getName() != "Pan Tool") {
				controller.setPanOff();
			}
			if (button.getName() != "Scale Tool") {
				controller.setScaleOff();
			}
			((JButton) button).setBorderPainted(true);
			setButtonsDefaultBackground(button);
		}

		private void setButtonsDefaultBackground(Component caller) {
			for (Component component : toolBar.getComponents()) {
				if (component instanceof JButton && !component.getName().equals(caller.getName())) {
					((JButton) component).setBorderPainted(false);
				}
			}
		}

	}

	/**
	 * Listener class for spinners
	 * @author nauval
	 *
	 */
	private class SpinnerListener implements ChangeListener {

		@Override
		public void stateChanged(ChangeEvent e) {
			JComponent source = (JComponent) e.getSource();
			if (source.getName().equals(spnrFillOpacity.getName())) {
				float opacity = Float.parseFloat(fillOpacityModel.getValue().toString())/100;
				controller.setSelectedShapeFillOpacity(opacity);
			} else if (source.getName().equals(spnrLineOpacity.getName())) {
				float opacity = Float.parseFloat(lineOpacityModel.getValue().toString())/100;
				controller.setSelectedShapeLineOpacity(opacity);
			} else if (source.getName().equals(spnrLineWidth.getName())) {
				int width = Integer.parseInt(lineWidthModel.getValue().toString());
				controller.setLineWidth(width);
			}
		}

	}

	/**
	 * Listener for menu bar buttons
	 * @author nauval
	 *
	 */
	private class MenuBarButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			JComponent source = (JComponent) e.getSource();

			if (source.getName().equals(mnItemNew.getName())) {
				controller.newDocument();
			} else if (source.getName().equals(mnItemOpen.getName())) {
				controller.open();
			} else if (source.getName().equals(mnItemSave.getName())) {
				controller.save();
			} else if (source.getName().equals(mnItemClose.getName())) {
				controller.exit();
			} else if (source.getName().equals(mnItemInsertImage.getName())) {
				controller.insertImage();
			}
		}

	}

	/**
	 * Mouse listener for canvas
	 * @author nauval
	 *
	 */
	private class CanvasMouseListener extends MouseAdapter {
		@Override
		public void mousePressed(MouseEvent e) {
			controller.setPointer(e.getX(), e.getY());
			controller.startDragging();
		}
		@Override
		public void mouseReleased(MouseEvent e) {
			controller.setPointer(e.getX(), e.getY());
			controller.stopDragging();
		}
	}

	/**
	 * Mouse motion listener for canvas
	 * @author nauval
	 *
	 */
	private class CanvasMouseMotionListener extends MouseMotionAdapter {
		@Override
		public void mouseDragged(MouseEvent e) {
			controller.setPointer(e.getX(), e.getY());
			controller.doDragging();
		}
		@Override
		public void mouseMoved(MouseEvent e) {
			controller.setPointer(e.getX(), e.getY());
		}
	}

	private class FrameKeyListener extends KeyAdapter {
		@Override
		public void keyPressed(KeyEvent e) {

			System.out.println("key pressed");
			if (e.isShiftDown()) {
				System.out.println("shift pressed");
				controller.setSquareOn();
			}
		}

		@Override
		public void keyReleased(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_SHIFT) {

				System.out.println("shift released");
				controller.setSquareOff();
			}
		}
	}
}
