package dwgfx.model;

import javax.xml.bind.annotation.XmlAttribute;

import javafx.scene.shape.*;

public class DwgRect extends DwgShape {
	@XmlAttribute private double width;
	@XmlAttribute private double height;
	@XmlAttribute private double arcWidth;
	@XmlAttribute private double arcHeight;
	
	public DwgRect() {
		super();
		width = 100.0;
		height = 75.0;
		arcWidth = arcHeight = 0.0;
	}
	
	public DwgRect(Rectangle rect) {
		super(rect);
		width = rect.getWidth();
		height = rect.getHeight();
		arcWidth = rect.getArcWidth();
		arcHeight = rect.getArcHeight();
	}
	
	@Override public Shape getShape() {
		Rectangle rect = new Rectangle(width, height);
		setProperties(rect);
		return rect;
	}
}