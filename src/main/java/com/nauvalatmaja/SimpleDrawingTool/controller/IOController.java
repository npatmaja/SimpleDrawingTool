package com.nauvalatmaja.SimpleDrawingTool.controller;

import static com.nauvalatmaja.SimpleDrawingTool.Properties.*;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.imageio.ImageIO;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.nauvalatmaja.SimpleDrawingTool.Exception.NoFileSelectedException;
import com.nauvalatmaja.SimpleDrawingTool.model.DrawingDocument;
import com.nauvalatmaja.SimpleDrawingTool.view.MainFrame;

public class IOController {
	
	private FileFilter imageFilter;
	private FileFilter fileFilter;
	private MainFrame view;
	
	private ObjectOutputStream objectOS;
	private ObjectInputStream objectIS;
	
	public IOController(MainFrame view) {
		this.view = view;
		imageFilter = new FileNameExtensionFilter("Image files", ALLOWED_IMAGES_EXTENSION);
		fileFilter = new FileNameExtensionFilter("Image files", FILE_EXTENSION);
	}
	
	public BufferedImage openImage() throws IOException {
		String filepath = view.showFileChooser("Insert", imageFilter);
		BufferedImage bi = ImageIO.read(new File(filepath));
		return bi;
	}
	
	public void save(DrawingDocument model, String documentPath) throws IOException, NoFileSelectedException {
		if (isNotYetSaved(documentPath)) {
			documentPath = getFullPathFilename(showFileChooser());	
		}
		
		objectOS = new ObjectOutputStream(new FileOutputStream(documentPath));
		objectOS.writeObject(model);
		objectOS.flush();
		objectOS.close();
	}

	private boolean isNotYetSaved(String activeDocumentPath) {
		return activeDocumentPath.length() <= 0;
	}
	
	public DrawingDocument openDocument() throws NoFileSelectedException, FileNotFoundException, IOException, ClassNotFoundException {
		DrawingDocument model = null;
		String documentPath = getFullPathFilename(showFileChooser());
		
		objectIS = new ObjectInputStream(new FileInputStream(documentPath));
		model = (DrawingDocument) objectIS.readObject();
		objectIS.close();
		
		return model;
	}

	private String showFileChooser() throws NoFileSelectedException {
		String documentPath = view.showFileChooser("Open", fileFilter);
		if (documentPath.length() <= 0) {
			throw new NoFileSelectedException();
		}
		return documentPath;
	}
	
	protected String getFullPathFilename(String name) {
		if (name.lastIndexOf(".") < 0) {
			return name + "." + FILE_EXTENSION;
		} else {
			return name;
		}
	}

	protected MainFrame getView() {
		return view;
	}

	protected void setView(MainFrame view) {
		this.view = view;
	}

	protected ObjectOutputStream getObjectOS() {
		return objectOS;
	}

	protected void setObjectOS(ObjectOutputStream objectOS) {
		this.objectOS = objectOS;
	}

	protected ObjectInputStream getObjectIS() {
		return objectIS;
	}

	protected void setObjectIS(ObjectInputStream objectIS) {
		this.objectIS = objectIS;
	}
}
