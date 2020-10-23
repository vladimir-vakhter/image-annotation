package example.annotating;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import example.annotating.boundary.AnnotationApp;
import example.annotating.controller.QuitApplicationController;
import example.annotating.model.Model;

// this class launches everything
public class Main {
	public static void main(String[] args) {
		Model m = new Model();
		AnnotationApp app = new AnnotationApp(m);
		app.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent we) {
				new QuitApplicationController(app).quit();
			}
		});
		app.setVisible(true);
	}
}
