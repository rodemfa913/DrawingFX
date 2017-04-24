package dwgfx.model;

import java.util.*;

import javax.xml.bind.annotation.*;

import javafx.scene.*;
import javafx.scene.shape.*;
import javafx.scene.text.Text;

@XmlAccessorType(XmlAccessType.FIELD)
public class Layer {
	@XmlAttribute private String id;
	@XmlAttribute private double opacity;
	@XmlAttribute private boolean visible;
	@XmlElementWrapper @XmlElements({
		@XmlElement(name = "arc", type = DwgArc.class), @XmlElement(name = "circle", type = DwgCircle.class),
		@XmlElement(name = "ellipse", type = DwgEllipse.class), @XmlElement(name = "path", type = DwgPath.class),
		@XmlElement(name = "rect", type = DwgRect.class), @XmlElement(name = "text", type = DwgText.class)
	})
	private List<DwgShape> items = new ArrayList<>();
	
	public Layer() {
		id = "layer";
		opacity = 1.0;
		visible = true;
	}
	
	public Layer(Group layer) {
		id = layer.getId();
		opacity = layer.getOpacity();
		visible = layer.isVisible();
		for (Node item : layer.getChildren()) {
			if (item instanceof Arc) {
				items.add(new DwgArc((Arc) item));
			} else if (item instanceof Circle) {
				items.add(new DwgCircle((Circle) item));
			} else if(item instanceof Ellipse) {
				items.add(new DwgEllipse((Ellipse) item));
			} else if(item instanceof Path) {
				items.add(new DwgPath((Path) item));
			} else if(item instanceof Text) {
				items.add(new DwgText((Text) item));
			} else {
				items.add(new DwgRect((Rectangle) item));
			}
		}
	}
	
	public Group getLayer() {
		Group layer = new Group();
		layer.setId(id);
		layer.setOpacity(opacity);
		layer.setVisible(visible);
		for (DwgShape it : items) {
			layer.getChildren().add(it.getShape());
		}
		return layer;
	}
}