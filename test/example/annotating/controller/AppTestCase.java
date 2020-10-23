package example.annotating.controller;

import org.junit.*;

import example.annotating.boundary.AnnotationApp;
import example.annotating.model.ModelTestCase;

public abstract class AppTestCase extends ModelTestCase {

	protected AnnotationApp app;
	
	@Before
	public void createApp() {
		app = new AnnotationApp(model);
		app.setVisible(true);
	}
	
	@After
	public void tearDown() throws Exception {
		app.setVisible(false);
	}
	
}
