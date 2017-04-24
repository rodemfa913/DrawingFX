package dwgfx.model;

import javax.xml.bind.annotation.XmlAttribute;

import javafx.scene.shape.*;

public class DwgCircle extends DwgShape {
	@XmlAttribute private double radius;
	
	public DwgCircle() {
		super();
		radius = 50.0;
	}
	
	public DwgCircle(Circle circle) {
		super(circle);
		radius = circle.getRadius();
	}
	
	@Override public Shape getShape() {
		Circle circle = new Circle(radius);
		setProperties(circle);
		return circle;
	}
}