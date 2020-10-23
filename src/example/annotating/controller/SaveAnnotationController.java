package example.annotating.controller;

import example.annotating.boundary.AnnotationApp;
import example.annotating.model.Model;

public class SaveAnnotationController {
	Model 			model;
	AnnotationApp 	app;
	
	public SaveAnnotationController(Model m, AnnotationApp app) {
		this.model = m;
		this.app = app;
	}

	public void cancelAnnotation() {
		model.clearSelectedAnnotation();
		app.getEnterAnnotationTextArea().setEnabled(false);
		app.getSaveButton().setEnabled(false);
		app.getCancelButton().setEnabled(false);
		app.getEnterAnnotationTextArea().setText("");
	}
	
	public void save() {
		// add content
		String annotationContent = app.getEnterAnnotationTextArea().getText();
		model.getSelectedAnnotation().setContent(annotationContent);
		app.getEnterAnnotationTextArea().setText("");
		app.getDisplayAnnotationTextArea().setText(model.getSelectedAnnotation().getContent());
		model.addAnnotation(model.getSelectedAnnotation());
		app.getEnterAnnotationTextArea().setEnabled(false);
		app.getSaveButton().setEnabled(false);
		app.getCancelButton().setEnabled(false);
	}
}
