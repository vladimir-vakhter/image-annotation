package example.annotating.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import java.awt.Point;

class TestAnnotation {

	@Test
	void testConstruction() {
		Annotation annotation = new Annotation(new Point(3,4), 1, 2);
		assertEquals(1, annotation.width);
		assertEquals(2, annotation.height);
		assertEquals(3, annotation.origin.x);
		assertEquals(4, annotation.origin.y);
	}
	
	@Test
	void testSetGetImagePath() {
		Annotation annotation = new Annotation(new Point(3,4), 1, 2);
		annotation.setImagePath("test");
		assertEquals("test", annotation.getImagePath());
	}
	
	@Test
	void testSetGetContent() {
		Annotation annotation = new Annotation(new Point(3,4), 1, 2);
		annotation.setContent("test");
		assertEquals("test", annotation.getContent());
	}	
	
	@Test
	void testContains() {
		Annotation a = new Annotation(new Point(1,1), 10, 7);
		assertTrue(a.contains(new Point(5, 3)));
		assertFalse(a.contains(new Point(11,1)));
		assertFalse(a.contains(new Point(1,8)));
	}
	
	@Test
	void testEquals() {
		Annotation a1 = new Annotation(new Point(1,1), 10, 7);
		a1.setImagePath("test_path");
		a1.setContent("test_content");
		assertFalse(a1.equals(null));	
		assertFalse(a1.equals(1));
		
		Annotation a2 = new Annotation(new Point(1,1), 10, 7);
		a2.setImagePath("test_path");
		a2.setContent("test_content");
		assertTrue(a1.equals(a2));
		
		Annotation a3 = new Annotation(new Point(1,1), 10, 7);
		a3.setImagePath("test_path1");
		a3.setContent("test_content");
		assertFalse(a3.equals(a1));
		
		a3.setImagePath("test_path");
		a3.setContent("test_content1");
		assertFalse(a3.equals(a1));
		
		Annotation a4 = new Annotation(new Point(2,1), 10, 7);
		a4.setImagePath("test_path");
		a4.setContent("test_content");
		assertFalse(a4.equals(a1));
		
		Annotation a5 = new Annotation(new Point(1,2), 10, 7);
		a5.setImagePath("test_path");
		a5.setContent("test_content");
		assertFalse(a5.equals(a1));
		
		Annotation a6 = new Annotation(new Point(1,1), 11, 7);
		a6.setImagePath("test_path");
		a6.setContent("test_content");
		assertFalse(a6.equals(a1));
		
		Annotation a7 = new Annotation(new Point(1,1), 10, 8);
		a7.setImagePath("test_path");
		a7.setContent("test_content");
		assertFalse(a7.equals(a1));		
	}		
}
