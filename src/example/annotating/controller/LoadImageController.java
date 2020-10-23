package example.annotating.controller;

import java.awt.Point;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.JFileChooser;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import example.annotating.boundary.AnnotationApp;
import example.annotating.boundary.ImageFileChooser;
import example.annotating.model.Annotation;
import example.annotating.model.Model;

/**
 * This class handles the request from the user to load an image.
 * The image and any accompanying annotations should be loaded.
 * Allowed formats are JPG, PNG, and GIF.
 */

public class LoadImageController {
	Model 				model;
	AnnotationApp 		app;
	String 				imagePath;
	public ImageFileChooser 	fc;
		
	public LoadImageController(Model m, AnnotationApp app) {
		this.model 	= m;
		this.app 	= app;
		this.fc		= app.getLoadFileChooser();
	}
	
	public void load() {
		if (fc.showOpenDialog(app) == JFileChooser.APPROVE_OPTION) {
			load_routine();
		}
		app.repaint();
	}
	
	@SuppressWarnings("unchecked")
	public void load_routine() {
		// read and set the image path to the model
		imagePath = fc.getSelectedFile().toString();
		model.setImagePath(imagePath);
		
		// clear the annotations stored inside the model
		model.clearSelectedAnnotation();
		model.clearAnnotations();
					
		// load the annotations associated with the image (if any)
		try (FileReader fileToRead = new FileReader(model.getAnnotationsPath())) {
			// parse a JSON file 
			JSONParser jsonParser = new JSONParser();
			Object obj = jsonParser.parse(fileToRead);
			JSONArray annotationsArray = (JSONArray) obj;
			// iterate over annotationsArray
			annotationsArray.forEach(annotation -> parseAnnotationObject((JSONObject) annotation));
		} catch (FileNotFoundException e) {
			System.out.println("There are no annotations for this image.");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (org.json.simple.parser.ParseException e) {
			e.printStackTrace();
		}
	}
	
	private void parseAnnotationObject(JSONObject annotation) {
		// extract the value of the fields describing the annotations 
		JSONObject annotationContent = (JSONObject) annotation.get("annotation");
		// get imagePath, content, originX, originY, width, height
		String ipath	= (String) annotationContent.get("imagePath");
		String content	= (String) annotationContent.get("content");
		int originX		= Integer.parseInt(annotationContent.get("originX").toString());
		int originY		= Integer.parseInt(annotationContent.get("originY").toString());
		Point origin	= new Point(originX, originY);
		int width		= Integer.parseInt(annotationContent.get("width").toString());
		int height		= Integer.parseInt(annotationContent.get("height").toString());
		// create an annotation object
		Annotation a = new Annotation(origin, width, height);
		a.setImagePath(ipath);
		a.setContent(content);
		// add the annotation to the array of annotations in the model
		model.addAnnotation(a);
	}
}
