package dwgfx.model;

import java.util.*;

import javax.xml.bind.annotation.*;

import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

@XmlRootElement(name = "drawing") @XmlAccessorType(XmlAccessType.FIELD)
public class Drawing {
	@XmlAttribute private String id;
	@XmlAttribute private double width;
	@XmlAttribute private double height;
	@XmlElement private DwgColor background;
	@XmlAttribute private String stylesheet;
	@XmlElementWrapper @XmlElement(name = "layer")
	private List<Layer> layers = new ArrayList<>();
	
	public Drawing() {
		id = "drawing";
		width = height = 400.0;
		background = new DwgColor();
		stylesheet = "";
	}
	
	public Drawing(AnchorPane drawing) {
		id = drawing.getId();
		width = drawing.getMinWidth();
		height = drawing.getMinHeight();
		background = new DwgColor((Color) drawing.getBackground().getFills().get(0).getFill());
		List<String> stylesheets = drawing.getStylesheets();
		stylesheet = stylesheets.isEmpty() ? "" : stylesheets.get(0);
		for (Node layer : drawing.getChildren()) {
			layers.add(new Layer((Group) layer));
		}
	}
	
	public void loadDrawing(AnchorPane drawing) {
		drawing.setId(id);
		drawing.setMinWidth(width);
		drawing.setMinHeight(height);
		drawing.setBackground(new Background(new BackgroundFill(background.getColor(), null, null)));
		List<String> stylesheets = drawing.getStylesheets();
		stylesheets.clear();
		stylesheets.add(stylesheet);
		List<Node> nodeList = drawing.getChildren();
		nodeList.clear();
		for (Layer layer : layers) {
			nodeList.add(layer.getLayer());
		}
	}
}
