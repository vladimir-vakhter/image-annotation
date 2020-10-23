package example.annotating.controller;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.awt.Point;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import example.annotating.boundary.AnnotationApp;
import example.annotating.boundary.ImagePanel;
import example.annotating.model.Annotation;
import example.annotating.model.Model;

public class TestCreateSelectAnnotationController extends generic.MouseEventTestCase {

	Model model;
	AnnotationApp app;
	
	@Before
	public void setUp() {
		model = new Model();
		model.clearSelectedAnnotation();
		
		app = new AnnotationApp(model);
		app.getEnterAnnotationTextArea().setEnabled(false);
		app.getSaveButton().setEnabled(false);
		app.getCancelButton().setEnabled(false);
		
		app.setVisible(true);
	}
	
	@After
	public void tearDown() {
		app.setVisible(false);
//		app.dispose();
	}
	
	@Test
	public void testSomething() {
		CreateSelectAnnotationController csac = new CreateSelectAnnotationController(model, app);
		ImagePanel panel = app.getImagePanel();
				
		assertTrue(model.getSelectedAnnotation() == null);
		
		// the image path is not set
		csac.mousePressed(this.createPressed(panel, 30, 80));
		assertNotEquals(new Point(30, 80), csac.origin);
				
		csac.mouseDragged(this.createDragged(panel, 100, 180));
		assertTrue(model.getSelectedAnnotation() == null);
		
		csac.mouseReleased(this.createReleased(panel, 100, 180));
		assertFalse(app.getEnterAnnotationTextArea().isEnabled());
		assertFalse(app.getSaveButton().isEnabled());
		assertFalse(app.getCancelButton().isEnabled());
		
		// the image path is set
		model.setImagePath("C:\\Users\\vladi\\eclipse-workspace\\vladimir_vakhter_hw_1\\test\\images\\image2.png");
		
		// annotation is inside the image field
		csac.mousePressed(this.createPressed(panel, 30, 80));
		assertTrue(app.getDisplayAnnotationTextArea().getText().equals(""));
		assertFalse(app.getDeleteButton().isEnabled());
		assertFalse(app.getSaveButton().isEnabled());
		assertFalse(app.getCancelButton().isEnabled());
		assertFalse(app.getEnterAnnotationTextArea().isEnabled());
		assertFalse(app.getOverlapAlert().getText().equals(model.getOverlapAlert()));
		
		csac.mouseDragged(this.createDragged(panel, 100, 180));
		assertTrue(model.getSelectedAnnotation() != null);
		
		csac.mouseReleased(this.createReleased(panel, 100, 180));
	
		assertTrue(app.getEnterAnnotationTextArea().isEnabled());
		assertTrue(app.getSaveButton().isEnabled());
		assertTrue(app.getCancelButton().isEnabled());
		
		// check that it is impossible to draw annotations outside of the image field
		csac.mousePressed(this.createPressed(panel, 30, 80));
		csac.mouseDragged(this.createDragged(panel, -30, -40));
		Point p = new Point(Math.round(model.getSelectedBorderThickness()),
				 			Math.round(model.getSelectedBorderThickness()));
		
		assertEquals(p.x, model.getSelectedAnnotation().origin.x);
		assertEquals(p.y, model.getSelectedAnnotation().origin.y);
		
		csac.mousePressed(this.createPressed(panel, 30, 80));
		csac.mouseDragged(this.createDragged(panel, app.getImagePanel().getPreferredSize().width,
													app.getImagePanel().getPreferredSize().height));
		
		assertEquals(30, model.getSelectedAnnotation().origin.x);
		assertEquals(80, model.getSelectedAnnotation().origin.y);
		assertEquals(app.getImagePanel().getPreferredSize().width - Math.round(model.getSelectedBorderThickness()) - 30,
					 model.getSelectedAnnotation().width);
		assertEquals(app.getImagePanel().getPreferredSize().height - Math.round(model.getSelectedBorderThickness()) - 80,
					 model.getSelectedAnnotation().height);
		
		csac.mouseReleased(this.createReleased(panel, -10, -10));
		assertEquals(p.x, model.getSelectedAnnotation().origin.x);
		assertEquals(p.y, model.getSelectedAnnotation().origin.y);
		
		csac.mouseReleased(this.createReleased(panel, app.getImagePanel().getPreferredSize().width,
													  app.getImagePanel().getPreferredSize().height));
		assertEquals(app.getImagePanel().getPreferredSize().width - Math.round(model.getSelectedBorderThickness()) - 30,
				 model.getSelectedAnnotation().width);
		assertEquals(app.getImagePanel().getPreferredSize().height - Math.round(model.getSelectedBorderThickness()) - 80,
					 model.getSelectedAnnotation().height);
		
		// test mouse clicked
		Annotation newAnnotation = new Annotation(new Point(40, 90), 20, 30);
		newAnnotation.setContent("test");
		model.addAnnotation(newAnnotation);

		// clicked outside
		csac.mousePressed(this.createPressed(panel, 30, 80));
		csac.mouseReleased(this.createReleased(panel, 30, 80));
		assertTrue(model.getSelectedAnnotation() == null);
		assertTrue(app.getDisplayAnnotationTextArea().getText().equals(""));
		assertFalse(app.getDeleteButton().isEnabled());
		
		// clicked inside
		csac.mousePressed(this.createPressed(panel, 50, 100));
		csac.mouseReleased(this.createReleased(panel, 50, 100));
		assertTrue(app.getDeleteButton().isEnabled());
		assertTrue(app.getDisplayAnnotationTextArea().getText().equals(model.getSelectedAnnotation().getContent()));
		model.clearSelectedAnnotation();
		model.clearAnnotations();
	
		// released with width !=0 or height !=0
		model.addAnnotation(newAnnotation);
		
		// wider and higher than an existing annotation (origin is outside)
		csac.mousePressed(this.createPressed(panel, 30, 80));
		csac.mouseReleased(this.createReleased(panel, 80, 130));
		assertTrue(app.getOverlapAlert().getText().equals(model.getOverlapAlert()));

		// wider and higher than an existing annotation (origins are equal)
		csac.mousePressed(this.createPressed(panel, 40, 90));
		csac.mouseReleased(this.createReleased(panel, 70, 150));
		assertTrue(app.getOverlapAlert().getText().equals(model.getOverlapAlert()));
				
		// inside of an existing annotation 
		csac.mousePressed(this.createPressed(panel, 50, 100));
		csac.mouseReleased(this.createReleased(panel, 55, 105));
		assertTrue(app.getOverlapAlert().getText().equals(model.getOverlapAlert()));
		
		app.repaint();
	}
}
