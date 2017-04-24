package dwgfx.model;

import javax.xml.bind.annotation.*;

import javafx.scene.shape.Shape;
import javafx.scene.text.*;

public class DwgText extends DwgShape {
	@XmlAttribute private String fontFamily;
	@XmlAttribute private double fontSize;
	@XmlAttribute private String fontStyle;
	@XmlElement private String body;
	
	public DwgText() {
		super();
		fontFamily = "Monospaced";
		fontSize = 12.0;
		fontStyle = "Regular";
		body = "";
	}
	
	public DwgText(Text text) {
		super(text);
		Font font = text.getFont();
		fontFamily = font.getFamily();
		fontSize = font.getSize();
		fontStyle = font.getStyle();
		body = text.getText().replace("\\", "\\\\").replace("\n", "\\n");
	}
	
	@Override public Shape getShape() {
		Text text = new Text(body.replace("\\n", "\n").replace("\\\\", "\\"));
		FontWeight weight = fontStyle.contains("Bold") ? FontWeight.BOLD : FontWeight.NORMAL;
		FontPosture posture = fontStyle.contains("Italic") ? FontPosture.ITALIC : FontPosture.REGULAR;
		text.setFont(Font.font(fontFamily, weight, posture, fontSize));
		setProperties(text);
		return text;
	}
}