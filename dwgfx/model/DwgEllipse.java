package dwgfx.model;

import javax.xml.bind.annotation.XmlAttribute;

import javafx.scene.shape.*;

public class DwgEllipse extends DwgShape {
	@XmlAttribute private double rx;
	@XmlAttribute private double ry;
	
	public DwgEllipse() {
		super();
		rx = 100.0;
		ry = 75.0;
	}
	
	public DwgEllipse(Ellipse ellipse) {
		super(ellipse);
		rx = ellipse.getRadiusX();
		ry = ellipse.getRadiusY();
	}
	
	@Override public Shape getShape() {
		Ellipse ellipse = new Ellipse(rx, ry);
		setProperties(ellipse);
		return ellipse;
	}
}