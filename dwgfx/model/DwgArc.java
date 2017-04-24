package dwgfx.model;

import javax.xml.bind.annotation.XmlAttribute;

import javafx.scene.shape.*;

public class DwgArc extends DwgShape {
	@XmlAttribute private double rx;
	@XmlAttribute private double ry;
	@XmlAttribute private double start;
	@XmlAttribute private double length;
	
	public DwgArc() {
		super();
		rx = 100.0;
		ry = 75.0;
		start = 0.0;
		length = 90.0;
	}
	
	public DwgArc(Arc arc) {
		super(arc);
		rx = arc.getRadiusX();
		ry = arc.getRadiusY();
		start = arc.getStartAngle();
		length = arc.getLength();
	}
	
	@Override public Shape getShape() {
		Arc arc = new javafx.scene.shape.Arc(0, 0, rx, ry, start, length);
		setProperties(arc);
		return arc;
	}
}