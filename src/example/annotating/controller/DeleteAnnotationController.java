package example.annotating.controller;

import example.annotating.boundary.AnnotationApp;
import example.annotating.model.Annotation;
import example.annotating.model.Model;

public class DeleteAnnotationController {
	Model 			model;
	AnnotationApp 	app;
	
	public DeleteAnnotationController(Model m, AnnotationApp app) {
		this.model 	= m;
		this.app 	= app;
	}

	public void delete() {
		int annotationIndex = 0;
		for(Annotation a : model) {
			if (a == model.getSelectedAnnotation()) {
				model.deleteAnnotation(annotationIndex);
				app.getDeleteButton().setEnabled(false);
				app.getDisplayAnnotationTextArea().setText("");
				return;
			} else {
				annotationIndex++;
			}
		}
	}
}
