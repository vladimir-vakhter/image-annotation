package example.annotating.controller;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.awt.Point;

import org.junit.Test;

import example.annotating.model.Annotation;

public class TestDeleteAnnotationController extends AppTestCase {

	@Test
	public void testDelete() {
		DeleteAnnotationController dac = new DeleteAnnotationController(model, app);
		
		dac.delete();
		
		// add annotations to the model
		Annotation a1 = new Annotation(new Point(1, 2), 3, 4);
		a1.setContent("test content 1");
		a1.setImagePath("test_image_1.png");
		
		Annotation a2 = new Annotation(new Point(5, 6), 7, 8);
		a2.setContent("test content 2");
		a2.setImagePath("test_image_2.png");
		
		Annotation a3 = new Annotation(new Point(3, 3), 5, 1);
		a3.setContent("test content 3");
		a3.setImagePath("test_image_3.png");
		
		model.addAnnotation(a1);
		model.addAnnotation(a2);
		model.addAnnotation(a3);
		
		// set selected annotation
		model.setSelectedAnnotation(a2);
		assertEquals(a2, model.getSelectedAnnotation());
		
		// set the content text area
		app.getDisplayAnnotationTextArea().setText(a2.getContent());
		assertEquals(a2.getContent(), app.getDisplayAnnotationTextArea().getText());
		
		// enable delete button
		app.getDeleteButton().setEnabled(true);
		assertTrue(app.getDeleteButton().isEnabled());

		dac.delete();
		assertEquals(null, model.getSelectedAnnotation());
		assertEquals("", app.getDisplayAnnotationTextArea().getText());
		assertFalse(app.getDeleteButton().isEnabled());
	}
}
