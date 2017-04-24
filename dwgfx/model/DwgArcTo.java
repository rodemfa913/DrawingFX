package dwgfx.model;

import javax.xml.bind.annotation.XmlAttribute;

import javafx.scene.shape.*;

public class DwgArcTo extends DwgClose {
	@XmlAttribute private double rx;
	@XmlAttribute private double ry;
	@XmlAttribute private double xRot;
	@XmlAttribute private double x;
	@XmlAttribute private double y;
	@XmlAttribute private boolean large;
	@XmlAttribute private boolean sweep;
	
	public DwgArcTo() {}
	
	public DwgArcTo(ArcTo arcto) {
		rx = arcto.getRadiusX();
		ry = arcto.getRadiusY();
		xRot = arcto.getXAxisRotation();
		x = arcto.getX();
		y = arcto.getY();
		large = arcto.isLargeArcFlag();
		sweep = arcto.isSweepFlag();
	}
	
	@Override public PathElement getElement() {
		return new ArcTo(rx, ry, xRot, x, y, large, sweep);
	}
}