package dwgfx.model;

import java.util.*;

import javax.xml.bind.annotation.*;

import javafx.scene.shape.*;

public class DwgPath extends DwgShape {
	@XmlElementWrapper @XmlElements({
		@XmlElement(name = "arcto", type = DwgArcTo.class), @XmlElement(name = "close", type = DwgClose.class),
		@XmlElement(name = "curveto", type = DwgCurveTo.class), @XmlElement(name = "lineto", type = DwgLineTo.class),
		@XmlElement(name = "moveto", type = DwgMoveTo.class)
	})
	private List<DwgClose> elements;
	
	public DwgPath() {
		super();
	}
	
	public DwgPath(Path path) {
		super(path);
		elements = new ArrayList<>();
		for (PathElement element : path.getElements()) {
			if (element instanceof ArcTo) {
				elements.add(new DwgArcTo((ArcTo) element));
			} else if (element instanceof CubicCurveTo) {
				elements.add(new DwgCurveTo((CubicCurveTo) element));
			} else if (element instanceof LineTo) {
				elements.add(new DwgLineTo((LineTo) element));
			} else if (element instanceof MoveTo) {
				elements.add(new DwgMoveTo((MoveTo) element));
			} else {
				elements.add(new DwgClose());
			}
		}
	}
	
	@Override public Shape getShape() {
		Path path = new Path();
		for (DwgClose element : elements) {
			path.getElements().add(element.getElement());
		}
		setProperties(path);
		return path;
	}
}