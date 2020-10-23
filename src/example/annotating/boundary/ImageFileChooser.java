package example.annotating.boundary;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import example.annotating.model.Model;

@SuppressWarnings("serial")
public class ImageFileChooser extends JFileChooser {
	Model model;
	
	String dialogTitle = "Load New Image";
	String defaultPath = ".\\images";
	FileNameExtensionFilter filter = new FileNameExtensionFilter("JPG, PNG, GIF", "jpg", "JPG",
			 													 "png", "PNG", "gif", "GIF");
	
	public ImageFileChooser(Model m) {
		this.model = m;
		this.setDialogTitle(dialogTitle);
		this.setCurrentDirectory(new File(defaultPath));
		this.setFileSelectionMode(JFileChooser.FILES_ONLY);
		this.setFileFilter(filter);
		this.setAcceptAllFileFilterUsed(false);
	}
}
