package dwgfx.model;

import javax.xml.bind.annotation.*;

import javafx.scene.shape.*;

@XmlAccessorType(XmlAccessType.FIELD)
public class DwgClose {
	public PathElement getElement() {
		return new ClosePath();
	}
}