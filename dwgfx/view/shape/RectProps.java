package dwgfx.view.shape;

import dwgfx.view.Props;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Spinner;
import javafx.scene.shape.Rectangle;

public class RectProps implements Props {
	private Rectangle rect;
	@FXML private Spinner<Double> xSpin;
	@FXML private Spinner<Double> ySpin;
	@FXML private Spinner<Double> widthSpin;
	@FXML private Spinner<Double> heightSpin;
	@FXML private Spinner<Double> arcWidthSpin;
	@FXML private Spinner<Double> arcHeightSpin;
	
	@Override public void setItem(Node item) {
		rect = (Rectangle) item;
		xSpin.getValueFactory().setValue(rect.getLayoutX());
		ySpin.getValueFactory().setValue(rect.getLayoutY());
		widthSpin.getValueFactory().setValue(rect.getWidth());
		heightSpin.getValueFactory().setValue(rect.getHeight());
		arcWidthSpin.getValueFactory().setValue(rect.getArcWidth());
		arcHeightSpin.getValueFactory().setValue(rect.getArcHeight());
	}
	
	@Override public void handleApply() {
		rect.setLayoutX(xSpin.getValue());
		rect.setLayoutY(ySpin.getValue());
		rect.setWidth(widthSpin.getValue());
		rect.setHeight(heightSpin.getValue());
		rect.setArcWidth(arcWidthSpin.getValue());
		rect.setArcHeight(arcHeightSpin.getValue());
	}
}