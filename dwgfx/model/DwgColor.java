package dwgfx.model;

import javax.xml.bind.annotation.*;

import javafx.scene.paint.Color;

@XmlAccessorType(XmlAccessType.FIELD)
public class DwgColor {
	@XmlAttribute private double red;
	@XmlAttribute private double green;
	@XmlAttribute private double blue;
	@XmlAttribute private double alpha;
	
	public DwgColor() {
		this(null);
	}
	
	public DwgColor(Color color) {
		if (color == null) {
			color = Color.color(0, 0, 0, 1);
		}
		red = color.getRed();
		green = color.getGreen();
		blue = color.getBlue();
		alpha = color.getOpacity();
	}
	
	public Color getColor() {
		return new Color(red, green, blue, alpha);
	}
}