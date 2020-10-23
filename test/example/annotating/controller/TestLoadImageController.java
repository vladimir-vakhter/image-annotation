package example.annotating.controller;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.util.Iterator;

import org.junit.Test;

import example.annotating.model.Annotation;

public class TestLoadImageController extends AppTestCase  {
	
	@Test
	public void testLoadRoutine() {
		LoadImageController lic = new LoadImageController(model, app);

		assertEquals("", model.getImagePath());
		assertFalse(model.iterator().hasNext());

		String imagePath = "C:\\Users\\vladi\\eclipse-workspace\\vladimir_vakhter_hw_1\\test\\images\\image1.jpg";
		lic.fc.setSelectedFile(new File(imagePath));
		
		lic.load_routine();
		
		assertEquals(imagePath, model.getImagePath());
		
		Iterator<Annotation> it = model.iterator();
		int i = 0;
		while(it.hasNext()) {
		    i++;
		    it.next();
		}
		assertEquals(7, i);
		
		imagePath = "test";
		lic.fc.setSelectedFile(new File(imagePath));
		
		lic.load_routine();		
	}
}
