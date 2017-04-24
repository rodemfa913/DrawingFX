package dwgfx.model;

import javax.xml.bind.annotation.*;

import dwgfx.util.StyleClassConverter;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import javafx.scene.transform.Affine;

@XmlAccessorType(XmlAccessType.FIELD)
public abstract class DwgShape {
	@XmlAttribute private String id;
	@XmlAttribute(name = "class") private String styleClass;
	@XmlElement private DwgColor fill;
	@XmlElement private Stroke stroke;
	@XmlElement private DwgAffine transform;
	@XmlAttribute private double x;
	@XmlAttribute private double y;
	
	public DwgShape() {
		id = "item";
		styleClass = "";
		fill = new DwgColor();
		stroke = new Stroke();
		transform = new DwgAffine();
		x = y = 20.0;
	}
	
	public DwgShape(Shape shape) {
		id = shape.getId();
		styleClass = StyleClassConverter.toString(shape.getStyleClass());
		fill = new DwgColor((Color) shape.getFill());
		stroke = new Stroke((Color) shape.getStroke(), shape.getStrokeWidth(),
				shape.getStrokeLineCap(), shape.getStrokeLineJoin());
		transform = new DwgAffine((Affine) shape.getTransforms().get(0));
		x = shape.getLayoutX();
		y = shape.getLayoutY();
	}
	
	public abstract Shape getShape();
	
	protected void setProperties(Shape shape) {
		shape.setId(id);
		shape.getStyleClass().setAll(StyleClassConverter.parseClasses(styleClass));
		shape.setFill(fill.getColor());
		shape.setStroke(stroke.getColor());
		shape.setStrokeWidth(stroke.getWidth());
		shape.setStrokeLineCap(stroke.getLineCap());
		shape.setStrokeLineJoin(stroke.getLineJoin());
		shape.getTransforms().add(transform.getTransform());
		shape.setLayoutX(x);
		shape.setLayoutY(y);
	}
}