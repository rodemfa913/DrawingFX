package dwgfx.model;

import javax.xml.bind.annotation.XmlAttribute;

import javafx.scene.shape.*;

public class DwgMoveTo extends DwgClose {
	@XmlAttribute private double x;
	@XmlAttribute private double y;
	
	public DwgMoveTo() {}
	
	public DwgMoveTo(MoveTo moveto) {
		x = moveto.getX();
		y = moveto.getY();
	}
	
	@Override public PathElement getElement() {
		return new MoveTo(x, y);
	}
}