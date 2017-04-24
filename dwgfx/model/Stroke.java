package dwgfx.model;

import javax.xml.bind.annotation.*;

import javafx.scene.paint.Color;
import javafx.scene.shape.*;

@XmlAccessorType(XmlAccessType.FIELD)
public class Stroke {
	@XmlElement private DwgColor color;
	@XmlAttribute private double width;
	@XmlAttribute private StrokeLineCap lineCap;
	@XmlAttribute private StrokeLineJoin lineJoin;
	
	public Stroke() {
		color = new DwgColor();
		width = 1.0;
		lineCap = StrokeLineCap.BUTT;
		lineJoin = StrokeLineJoin.MITER;
	}
	
	public Stroke(Color clr, double w, StrokeLineCap lc, StrokeLineJoin lj) {
		color = new DwgColor(clr);
		width = w;
		lineCap = lc;
		lineJoin = lj;
	}
	
	public Color getColor() {
		return color.getColor();
	}
	
	public double getWidth() {
		return width;
	}
	
	public StrokeLineCap getLineCap() {
		return lineCap;
	}
	
	public StrokeLineJoin getLineJoin() {
		return lineJoin;
	}
}