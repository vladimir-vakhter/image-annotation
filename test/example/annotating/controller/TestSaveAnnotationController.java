package example.annotating.controller;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.awt.Point;

import org.junit.Test;

import example.annotating.model.Annotation;

public class TestSaveAnnotationController extends AppTestCase {
	@Test
	public void testCancel() {
		SaveAnnotationController sac = new SaveAnnotationController(model, app);
		
		// set the selected annotation to the model
		Annotation a1 = new Annotation(new Point(1, 2), 3, 4);
		a1.setContent("test content 1");
		a1.setImagePath("test_image_1.png");
		
		model.addAnnotation(a1);
		
		model.setSelectedAnnotation(a1);
		assertEquals(a1, model.getSelectedAnnotation());
		
		// enable controls and fields
		app.getEnterAnnotationTextArea().setEnabled(true);
		assertTrue(app.getEnterAnnotationTextArea().isEnabled());
		
		app.getSaveButton().setEnabled(true);
		assertTrue(app.getSaveButton().isEnabled());
		
		app.getCancelButton().setEnabled(true);
		assertTrue(app.getCancelButton().isEnabled());
		
		app.getEnterAnnotationTextArea().setText(model.getSelectedAnnotation().getContent());
		assertEquals(a1.getContent(), app.getEnterAnnotationTextArea().getText());
		
		// cancel
		sac.cancelAnnotation();
		assertFalse(app.getEnterAnnotationTextArea().isEnabled());
		assertFalse(app.getSaveButton().isEnabled());
		assertFalse(app.getCancelButton().isEnabled());
		assertEquals("", app.getEnterAnnotationTextArea().getText());
	}
	
	@Test
	public void testSave() {
		SaveAnnotationController sac = new SaveAnnotationController(model, app);
		
		// set the selected annotation to the model
		Annotation a1 = new Annotation(new Point(1, 2), 3, 4);
		a1.setImagePath("test_image_1.png");
		
//		model.addAnnotation(a1);
		
		assertFalse(model.iterator().hasNext());
		
		model.setSelectedAnnotation(a1);
		assertEquals(a1, model.getSelectedAnnotation());
		
		// enable controls and fields
		app.getEnterAnnotationTextArea().setEnabled(true);
		assertTrue(app.getEnterAnnotationTextArea().isEnabled());
		
		app.getSaveButton().setEnabled(true);
		assertTrue(app.getSaveButton().isEnabled());
		
		app.getCancelButton().setEnabled(true);
		assertTrue(app.getCancelButton().isEnabled());
		
		app.getEnterAnnotationTextArea().setText("test");
		assertEquals("test", app.getEnterAnnotationTextArea().getText());
		app.getDisplayAnnotationTextArea().setText("");
		assertEquals("", app.getDisplayAnnotationTextArea().getText());
		
		assertFalse(model.iterator().hasNext());
		
		sac.save();
		assertEquals("test", model.getSelectedAnnotation().getContent());
		assertEquals("", app.getEnterAnnotationTextArea().getText());
		assertEquals(model.getSelectedAnnotation().getContent(), app.getDisplayAnnotationTextArea().getText());
		
		assertTrue(model.iterator().hasNext());
		
		assertFalse(app.getEnterAnnotationTextArea().isEnabled());
		assertFalse(app.getSaveButton().isEnabled());
		assertFalse(app.getCancelButton().isEnabled());
	}
}
