package dwgfx.model;

import javax.xml.bind.annotation.XmlAttribute;

import javafx.scene.shape.*;

public class DwgCurveTo extends DwgClose {
	@XmlAttribute private double x1;
	@XmlAttribute private double y1;
	@XmlAttribute private double x2;
	@XmlAttribute private double y2;
	@XmlAttribute private double x;
	@XmlAttribute private double y;
	
	public DwgCurveTo() {}
	
	public DwgCurveTo(CubicCurveTo curveto) {
		x1 = curveto.getControlX1();
		y1 = curveto.getControlY1();
		x2 = curveto.getControlX2();
		y2 = curveto.getControlY2();
		x = curveto.getX();
		y = curveto.getY();
	}
	
	@Override public PathElement getElement() {
		return new CubicCurveTo(x1, y1, x2, y2, x, y);
	}
}