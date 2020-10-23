package example.annotating.model;

import java.awt.Color;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class Model implements Iterable<Annotation> {

	String imagePath 					= "";					// path to the active image
	String annotationsPath 				= "";                   // path the json file with the associated annotations
	ArrayList<Annotation> annotations 	= new ArrayList<>();    // list of annotations
	Annotation selectedAnnotation 		= null;                 // currently selected annotation
	
	private float thicknessBasic		= 2;					// border's thickness of an existing unselected annotation
	private float thicknessActive 		= 4;                    // border's thickness of a selected annotation
	private Color colorBasic 			= Color.green;            // border's color...
	private Color colorActive 			= Color.blue;                
	private String overlapAlert			= "Annotations cannot obscure each other!";
	
	public Model() {
	}
	
	public String getOverlapAlert() { return overlapAlert; }
	
	public void setBasicBorderThickness(float bt) { this.thicknessBasic = bt; }
	public float getBasicBorderThickness() { return this.thicknessBasic; }
	
	public void setSelectedBorderThickness(float bt) { this.thicknessActive = bt; }
	public float getSelectedBorderThickness() { return this.thicknessActive; }	
	
	public void setBasicBorderColor(Color bc) { this.colorBasic = bc; }
	public Color getBasicBorderColor() { return this.colorBasic; }
	
	public void setSelectedBorderColor(Color bc) { this.colorActive = bc; }
	public Color getSelectedBorderColor() { return this.colorActive; }
		
	public void setImagePath(String ip) {
		this.imagePath = ip; 
		this.annotationsPath = this.imagePath.substring(0, imagePath.length() - 4) + "_annotations.json";
	}
	
	public String getImagePath() { return imagePath; }
	public String getAnnotationsPath() { return annotationsPath; }
	
	public void addAnnotation(Annotation a) {
		annotations.add(a);
		writeAnnotationsToFile();
	}		
	
	public void deleteAnnotation(int index) {
		clearSelectedAnnotation();
		annotations.remove(index);
		writeAnnotationsToFile();
	}
	
	public void clearAnnotations() { annotations = new ArrayList<>(); }
	            
	@SuppressWarnings("unchecked")
	private void writeAnnotationsToFile() {
		// create a JSON-array of annotations 
		JSONArray annotationsArrayJSON = new JSONArray();
		
		for(Annotation annotation : annotations) {
			// create a JSON object of annotation fields
			JSONObject annotationFieldsJSON = new JSONObject();
			annotationFieldsJSON.put("imagePath", annotation.getImagePath());
			annotationFieldsJSON.put("content", annotation.getContent());
			annotationFieldsJSON.put("originX", annotation.origin.x);
			annotationFieldsJSON.put("originY", annotation.origin.y);
			annotationFieldsJSON.put("width", annotation.width);
			annotationFieldsJSON.put("height", annotation.height);
			
			// put all the fields describing the annotation into a JSON object
			JSONObject annotationInfoJSON = new JSONObject();
			annotationInfoJSON.put("annotation", annotationFieldsJSON);
			
			// add the description of this annotation into the JSON-array
			annotationsArrayJSON.add(annotationInfoJSON);
		}
		
		// rewrite the .json file, containing the information about annotations, in one shot
		try (FileWriter fileToWrite = new FileWriter(annotationsPath)) {
			fileToWrite.write(annotationsArrayJSON.toString());
			fileToWrite.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void setSelectedAnnotation(Annotation a) { selectedAnnotation = a; }
	public void clearSelectedAnnotation() { selectedAnnotation = null; }
	public Annotation getSelectedAnnotation() { return selectedAnnotation; }
	
	@Override
	/** 
	 * get all the annotations from the model
	 */
	public Iterator<Annotation> iterator() {
		return annotations.iterator();
	}
}
