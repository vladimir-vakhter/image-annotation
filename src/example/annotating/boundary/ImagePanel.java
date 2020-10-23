package example.annotating.boundary;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.JPanel;

import example.annotating.controller.CreateSelectAnnotationController;
import example.annotating.model.Annotation;
import example.annotating.model.Model;

/**
 * The Java GUI entity for visual display that supports:
 * 	1. mouse interaction by registering mouse listeners
 * 	2. loading an image
 */

@SuppressWarnings("serial")
public class ImagePanel extends JPanel {
	Image 			image = null;	// the image to be annotated
	Model 			model = null;
	AnnotationApp 	app = null;
	
	public ImagePanel(Model m, AnnotationApp app) {
		this.model 	= m;
		this.app	= app;
		CreateSelectAnnotationController control = new CreateSelectAnnotationController(model, this.app);
		this.addMouseListener(control);
		this.addMouseMotionListener(control);
	}

	public void setImage(Image image) {
		this.image = image;
		repaint();
	}
	
	@Override
	public void paintComponent(Graphics g) {	// updates the screen
		super.paintComponent(g);
		
		Graphics2D g2 = (Graphics2D)g;

		if (model == null) { return; }	// nothing to draw. Only here for WindowBuilder
		if (image == null) { return; }
		
		g.drawImage(image, 0, 0, this);
		
		g.setColor(model.getBasicBorderColor());
		g2.setStroke(new BasicStroke(model.getBasicBorderThickness()));
		for (Annotation a : model) {
			g.drawRect(a.origin.x, a.origin.y, a.width, a.height);
		}
		
		Annotation selected = model.getSelectedAnnotation();
		if (selected != null) {
			g.setColor(model.getSelectedBorderColor());
			g2.setStroke(new BasicStroke(model.getSelectedBorderThickness()));
			g.drawRect(selected.origin.x, selected.origin.y, selected.width, selected.height);
		}
	}
}
