package example.annotating.controller;

import javax.swing.JOptionPane;

import example.annotating.boundary.AnnotationApp;

/**
 * Confirms the request to exist the application.
 */
public class QuitApplicationController {

	AnnotationApp app;

	public QuitApplicationController(AnnotationApp app) {
		this.app = app;
	}

	public void quit() {
		int c = JOptionPane.showConfirmDialog (app, "Do you want to quit?");

		if (c == JOptionPane.OK_OPTION) {
			app.setVisible(false);
			app.dispose();
		}
	}
}
