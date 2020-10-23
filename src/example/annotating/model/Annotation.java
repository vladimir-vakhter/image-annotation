package example.annotating.model;

import java.awt.Point;

public class Annotation {
	
	String imagePath	= "";
	String content 		= "";
	public final Point origin;
	public final int width, height;
	
	public Annotation(Point origin, int width, int height) {
		this.origin 	= origin;
		this.width		= width;
		this.height 	= height;
	}
	
	public void		setImagePath(String ip) { this.imagePath = ip; }
	public String 	getImagePath() { return this.imagePath; }
	
	public void		setContent(String s) { this.content = s; }
	public String 	getContent() { return this.content; }

	public boolean 	contains(Point p) {
		return (p.x >= origin.x && p.x < origin.x + width && p.y >= origin.y && p.y < origin.y + height);
	}
	
	@Override
	public boolean equals(Object o) {
		if (o == null) { return false; }
		if (o == this) { return true; } 
		if (!(o instanceof Annotation)) { return false; }
				
		Annotation other = (Annotation) o;
		return imagePath 	== other.getImagePath() &&
			   content 		== other.getContent() &&
			   origin.x 	== other.origin.x &&
			   origin.y		== other.origin.y &&
			   width 		== other.width &&
			   height 		== other.height;
	}
}
