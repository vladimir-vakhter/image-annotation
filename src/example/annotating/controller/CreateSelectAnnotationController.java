package example.annotating.controller;

import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import example.annotating.boundary.AnnotationApp;
import example.annotating.model.Annotation;
import example.annotating.model.Model;

/**
 * This class:
 * 	1. 	Selects an annotation, i.e. when the user presses the mouse on/inside the border of an annotation,
 * 		(1) the annotation is highlighted and (2) the annotation content is displayed in the comment text area.
 * 	2. 	Creates an annotation, i.e. when the image is loaded, the user selects a rectangle field on the image,
 * 		the user types the text, and the user confirms that the content of the annotation should be saved, 
 * 		the annotation should be saved onto the disk and the display is updated.
 *  3.	Deletes annotation, i.e. when the user selects an annotation and requests to remove it, this class removes
 *  	it from the disk and updated the display.
 *  
 *  All the annotations are saved once they are created/deleted.
 *  It means that when the user relaunches the application and loads exact the same image,
 *  all the created annotations will be shown on the display.
 *  
 */

public class CreateSelectAnnotationController extends MouseAdapter {
	Model 			model;
	AnnotationApp 	app;
	public Point 	origin = null;	
		
	public CreateSelectAnnotationController(Model m, AnnotationApp app) {
		this.model 	= m;
		this.app 	= app;
	}
	
	@Override
	public void mousePressed(MouseEvent me) {
		if (model.getImagePath() != "") {		// cannot annotate an empty panel
			origin = me.getPoint();	
			app.getDisplayAnnotationTextArea().setText("");
			app.getDeleteButton().setEnabled(false);
			app.getSaveButton().setEnabled(false);
			app.getCancelButton().setEnabled(false);
			app.getEnterAnnotationTextArea().setEnabled(false);
			app.clearOverlapAlert();
		}
	}

	@Override
	public void mouseDragged(MouseEvent me) {
		if (model.getImagePath() != "") {
			int currentX = me.getX();
			int currentY = me.getY();
			
			// check the border conditions
			if (currentX <= 0) { currentX = Math.round(model.getSelectedBorderThickness()); }
			if (currentY <= 0) { currentY = Math.round(model.getSelectedBorderThickness()); }
			
			if (currentX >= app.getImagePanel().getPreferredSize().width) {
				currentX = app.getImagePanel().getPreferredSize().width - Math.round(model.getSelectedBorderThickness());
			}
			if (currentY >= app.getImagePanel().getPreferredSize().height) {
				currentY = app.getImagePanel().getPreferredSize().height - Math.round(model.getSelectedBorderThickness());
			}

			int minX = Math.min(currentX, origin.x);
			int minY = Math.min(currentY, origin.y);
			int maxX = Math.max(currentX, origin.x);
			int maxY = Math.max(currentY, origin.y);
			
			int width 	= maxX - minX;
			int height 	= maxY - minY;
			
			Point anchor = new Point(minX, minY);
			
			model.setSelectedAnnotation(new Annotation(anchor, width, height));
			app.getImagePanel().repaint();
		}
	}
	
	@Override
	public void mouseReleased(MouseEvent me) {
		if (model.getImagePath() != "") {
			int currentX = me.getX();
			int currentY = me.getY();
			
			// check the border conditions
			if (currentX <= 0) { currentX = Math.round(model.getSelectedBorderThickness()); }
			if (currentY <= 0) { currentY = Math.round(model.getSelectedBorderThickness()); }
			
			if (currentX >= app.getImagePanel().getPreferredSize().width) {
				currentX = app.getImagePanel().getPreferredSize().width - Math.round(model.getSelectedBorderThickness());
			}
			if (currentY >= app.getImagePanel().getPreferredSize().height) {
				currentY = app.getImagePanel().getPreferredSize().height - Math.round(model.getSelectedBorderThickness());
			}
			
			int minX = Math.min(currentX, origin.x);
			int minY = Math.min(currentY, origin.y);
			int maxX = Math.max(currentX, origin.x);
			int maxY = Math.max(currentY, origin.y);
			
			int width 	= maxX - minX;
			int height 	= maxY - minY;
			
			Point anchor = new Point(minX, minY);
		
			// if mouse was clicked once
			if (width == 0 && height == 0) {					
				boolean selected = false;
				for (Annotation a : model) {
					// if it was clicked inside an existing annotation, select
					if(a.contains(anchor)) {
						model.clearSelectedAnnotation();
						model.setSelectedAnnotation(a);
						app.getDisplayAnnotationTextArea().setText(a.getContent());
						app.getDeleteButton().setEnabled(true);
						selected = true;
					}
				}
				if (!selected) {
					model.clearSelectedAnnotation();
					app.getDisplayAnnotationTextArea().setText("");
					app.getDeleteButton().setEnabled(false);
				}			
			} else {
				boolean discarded = false;
				
				for (Annotation a : model) {
					// if the origin of new annotation is inside of any existing one
					if(a.contains(anchor)) {										
						// is the new annotation is completely shadowed by an existing one?
						if (((anchor.x + width <= a.origin.x + a.width) && (anchor.y + height <= a.origin.y + a.height)) ||
							((anchor.x == a.origin.x) && (anchor.y == a.origin.y) && (width >= a.width) && (height >= a.height))) {														
							// show a pop-up alert message
							app.setOverlapAlert();
							model.clearSelectedAnnotation();
							discarded = true;
							break;
						} 
					// the origin of the new annotation is outside of the existing one
					} else {
						// does the new one shadow the existing annotation?
						Annotation newAnnotation = new Annotation(anchor, width, height);
						if (newAnnotation.contains(new Point(a.origin.x, a.origin.y))) {
							if ((anchor.x + width >= a.origin.x + a.width) && (anchor.y + height >= a.origin.y + a.height)) {
								// show a pop-up alert message
								app.setOverlapAlert();
								model.clearSelectedAnnotation();
								discarded = true;
								break;
							}			
						}
					}
				}			
				if (!discarded) {
					// add the new annotation to the model
					model.setSelectedAnnotation(null);
					// activate the input annotationTextField
					app.getEnterAnnotationTextArea().setEnabled(true);
					// activate the save button
					app.getSaveButton().setEnabled(true);
					// activate the cancel button
					app.getCancelButton().setEnabled(true);
					// pass the information about new annotation to SaveAnnotationController
					Annotation newAnnotation = new Annotation(anchor, width, height);
					newAnnotation.setImagePath(model.getImagePath());					
					model.setSelectedAnnotation(newAnnotation);
				}
			}			
			app.getImagePanel().repaint();
		}
	}
}
