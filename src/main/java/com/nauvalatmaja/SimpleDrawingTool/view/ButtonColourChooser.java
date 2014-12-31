package com.nauvalatmaja.SimpleDrawingTool.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.MatteBorder;

/**
 * Colour chooser button which show what colour currently selected as it's background
 * @author nauval
 *
 */
public class ButtonColourChooser extends JPanel {
	
	private final Border DEFAULT_BORDER = new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY);
	private final Border PRESSED_BORDER = new MatteBorder(1, 1, 1, 1, (Color) Color.DARK_GRAY);
	private final double GAP_FACTOR = 0.1;
	private Color initColour;
	
	private JButton button;
	
	/**
	 * Constructor with initial color that will be shown in the button
	 * @param initColour
	 */
	public ButtonColourChooser(Color initColour) {
		this.initColour = initColour;
		initComponents();
		addButtonMouseListener();
	}
	
	/**
	 * Adds mouse listener to button to change its border when mouse pressed and change back
	 * to default border when mouse released
	 */
	private void addButtonMouseListener() {
		button.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				button.setBorder(PRESSED_BORDER);
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				button.setBorder(DEFAULT_BORDER);
			}
		});
	}

	/**
	 * Create and setup components
	 */
	private void initComponents() {
		setLayout(new BorderLayout(0, 0));
		button = new JButton();
		button.setBorder(DEFAULT_BORDER);
		button.setOpaque(true);
		button.setBackground(initColour);
		add(button, BorderLayout.CENTER);
	}

	/**
	 * Add gaps around the button
	 */
	private void addButtonGaps() {
		int width = (int) (getPreferredSize().width * GAP_FACTOR); 
		int height = (int) (getPreferredSize().height * GAP_FACTOR);
		// add gaps
		JPanel north = new JPanel();
		north.setPreferredSize(new Dimension(width, height));
		add(north, BorderLayout.NORTH);

		JPanel west = new JPanel();
		west.setPreferredSize(new Dimension(width, height));
		add(west, BorderLayout.WEST);

		JPanel east = new JPanel();
		east.setPreferredSize(new Dimension(width, height));
		add(east, BorderLayout.EAST);

		JPanel south = new JPanel();
		south.setPreferredSize(new Dimension(width, height));
		add(south, BorderLayout.SOUTH);
	}
	
	@Override
	public void setPreferredSize(Dimension preferredSize) {
		super.setPreferredSize(preferredSize);
		addButtonGaps();
	}
	
	/**
	 * Adds action listener to the button
	 * @param listener
	 */
	public void addButtonActionListener(ActionListener listener) {
		button.addActionListener(listener);
	}
	
	/**
	 * Sets button's background colour
	 * @param colour
	 */
	public void setButtonColour(Color colour) {
		button.setBackground(colour);
	}
	
	/**
	 * Gets active colour
	 * @return colour
	 */
	public Color getButtonColour() {
		return button.getBackground();
	}
	
	@Override
	public void setName(String name) {
		button.setName(name);
	}
	
	@Override
	public String getName() {
		return button.getName();
	}
}
