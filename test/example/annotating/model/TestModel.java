package example.annotating.model;

import org.junit.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Iterator;

public class TestModel extends ModelTestCase {

	@Test
	public void testSetGetImagePath() {
		model.setImagePath("test.jpg");
		assertEquals("test.jpg", model.getImagePath());
		assertEquals("test_annotations.json", model.getAnnotationsPath());
	}
	
	@Test
	public void testAddGetAnnotation() {
		model.addAnnotation(new Annotation(new Point(1, 2), 3, 4));
		model.addAnnotation(new Annotation(new Point(5, 6), 7, 8));
		
		Iterator<Annotation> it = model.iterator();
		int i = 0;
		while(it.hasNext()) {
		    i++;
		    it.next();
		}
		assertEquals(2, i);
	}
	
	@Test
	public void testSetGetClearAnnotation() {
		Annotation a = new Annotation(new Point(1, 2), 3, 4); 
		model.setSelectedAnnotation(a);
		assertEquals(a, model.getSelectedAnnotation());
		model.clearSelectedAnnotation();
		assertEquals(null, model.getSelectedAnnotation());
	}
	
	@Test
	public void testSetGetBasicBorderThickness() {
		model.setBasicBorderThickness(4);
		assertEquals(4, model.getBasicBorderThickness());
	}

	@Test
	public void testSetGetActiveBorderThickness() {
		model.setSelectedBorderThickness(4);
		assertEquals(4, model.getSelectedBorderThickness());
	}

	@Test
	public void testSetGetBasicBorderColor() {
		model.setBasicBorderColor(Color.black);
		assertEquals(Color.black, model.getBasicBorderColor());
	}

	@Test
	public void testSetGetActiveBorderColor() {
		model.setSelectedBorderColor(Color.black);
		assertEquals(Color.black, model.getSelectedBorderColor());
	}
	
	@Test
	public void testClearAnnotations() {
		model.addAnnotation(new Annotation(new Point(1, 2), 3, 4));
		model.addAnnotation(new Annotation(new Point(5, 6), 7, 8));
		
		Iterator<Annotation> it = model.iterator();
		int i = 0;
		while(it.hasNext()) {
		    i++;
		    it.next();
		}
		assertEquals(2, i);
		
		model.clearAnnotations();
		assertEquals(false, it.hasNext());
	}
	
	@Test
	public void testDeleteAnnotation() {
		assertEquals(null, model.getSelectedAnnotation());
		model.addAnnotation(new Annotation(new Point(1, 2), 3, 4));
		model.addAnnotation(new Annotation(new Point(5, 6), 7, 8));
		model.addAnnotation(new Annotation(new Point(7, 3), 5, 6));
		
		Iterator<Annotation> it = model.iterator();
		int i = 0;
		while(it.hasNext()) {
		    i++;
		    it.next();
		}
		assertEquals(3, i);
		
		Annotation a = new Annotation(new Point(1, 2), 3, 4);
		model.setSelectedAnnotation(a);
		assertEquals(a, model.getSelectedAnnotation());

		ArrayList<Annotation> annotations_1 = new ArrayList<>();
		annotations_1.add(new Annotation(new Point(5, 6), 7, 8));
		annotations_1.add(new Annotation(new Point(7, 3), 5, 6));
		
		model.deleteAnnotation(0);

		ArrayList<Annotation> annotations_2 = new ArrayList<>();
		
		it = model.iterator();
		i = 0;
		while(it.hasNext()) {
		    i++;
		    annotations_2.add(it.next());
		}
		assertEquals(2, i);
		assertEquals(annotations_1, annotations_2);
	}
}
