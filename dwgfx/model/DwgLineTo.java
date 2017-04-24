package dwgfx.model;

import javax.xml.bind.annotation.XmlAttribute;

import javafx.scene.shape.*;

public class DwgLineTo extends DwgClose {
	@XmlAttribute private double x;
	@XmlAttribute private double y;
	
	public DwgLineTo() {}
	
	public DwgLineTo(LineTo lineto) {
		x = lineto.getX();
		y = lineto.getY();
	}
	
	@Override public PathElement getElement() {
		return new LineTo(x, y);
	}
}