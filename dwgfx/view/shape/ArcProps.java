package dwgfx.view.shape;

import dwgfx.view.Props;
import javafx.fxml.*;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.shape.*;

public class ArcProps implements Props {
	private Shape shape;
	@FXML private Spinner<Double> cxSpin;
	@FXML private Spinner<Double> cySpin;
	@FXML private Label rxLabel;
	@FXML private Spinner<Double> rxSpin;
	@FXML private Label ryLabel;
	@FXML private Spinner<Double> rySpin;
	@FXML private Label startLabel;
	@FXML private Spinner<Double> startSpin;
	@FXML private Label lengthLabel;
	@FXML private Spinner<Double> lengthSpin;
	
	@Override public void setItem(Node item) {
		rxLabel.setText("Radius:");
		ryLabel.setVisible(false);
		rySpin.setVisible(false);
		startLabel.setVisible(false);
		startSpin.setVisible(false);
		lengthLabel.setVisible(false);
		lengthSpin.setVisible(false);
		shape = (Shape) item;
		cxSpin.getValueFactory().setValue(shape.getLayoutX());
		cySpin.getValueFactory().setValue(shape.getLayoutY());
		if (shape instanceof Circle) {
			Circle circle = (Circle) shape;
			rxSpin.getValueFactory().setValue(circle.getRadius());
		} else {
			rxLabel.setText("Radius X:");
			ryLabel.setVisible(true);
			rySpin.setVisible(true);
			if (shape instanceof Ellipse) {
				Ellipse ellipse = (Ellipse) shape;
				rxSpin.getValueFactory().setValue(ellipse.getRadiusX());
				rySpin.getValueFactory().setValue(ellipse.getRadiusY());
			} else {
				startLabel.setVisible(true);
				startSpin.setVisible(true);
				lengthLabel.setVisible(true);
				lengthSpin.setVisible(true);
				Arc arc = (Arc) shape;
				rxSpin.getValueFactory().setValue(arc.getRadiusX());
				rySpin.getValueFactory().setValue(arc.getRadiusY());
				startSpin.getValueFactory().setValue(arc.getStartAngle());
				lengthSpin.getValueFactory().setValue(arc.getLength());
			}
		}
	}
	
	@Override public void handleApply() {
		shape.setLayoutX(cxSpin.getValue());
		shape.setLayoutY(cySpin.getValue());
		if (shape instanceof Circle) {
			Circle circle = (Circle) shape;
			circle.setRadius(rxSpin.getValue());
		} else if (shape instanceof Ellipse) {
			Ellipse ellipse = (Ellipse) shape;
			ellipse.setRadiusX(rxSpin.getValue());
			ellipse.setRadiusY(rySpin.getValue());
		} else {
			Arc arc = (Arc) shape;
			arc.setRadiusX(rxSpin.getValue());
			arc.setRadiusY(rySpin.getValue());
			arc.setStartAngle(startSpin.getValue());
			arc.setLength(lengthSpin.getValue());
		}
	}
}