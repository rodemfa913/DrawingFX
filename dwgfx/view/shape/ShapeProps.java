package dwgfx.view.shape;

import java.net.URL;
import java.util.ResourceBundle;

import dwgfx.util.*;
import dwgfx.view.Props;
import javafx.fxml.*;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.text.Text;
import javafx.scene.transform.Affine;

public class ShapeProps implements Initializable, Props {
	@FXML private Accordion accordion;
	@FXML private TitledPane genTitledPane;
	private Shape shape;
	@FXML private TextField classText;
	@FXML private ColorPicker fillColorPicker;
	@FXML private ColorPicker strokeColorPicker;
	@FXML private Spinner<Double> strokeWidthSpin;
	@FXML private ComboBox<StrokeLineCap> lineCapCombo;
	@FXML private ComboBox<StrokeLineJoin> lineJoinCombo;
	@FXML private TextArea transText;
	@FXML private AnchorPane genAnchorPane;
	private Props controller;
	
	@Override public void initialize(URL location, ResourceBundle resources) {
		accordion.setExpandedPane(genTitledPane);
	}
	
	@Override public void setItem(Node item) throws Exception {
		shape = (Shape) item;
		classText.setText(StyleClassConverter.toString(shape.getStyleClass()));
		fillColorPicker.setValue((Color) shape.getFill());
		strokeColorPicker.setValue((Color) shape.getStroke());
		strokeWidthSpin.getValueFactory().setValue(shape.getStrokeWidth());
		lineCapCombo.setValue(shape.getStrokeLineCap());
		lineJoinCombo.setValue(shape.getStrokeLineJoin());
		transText.setText(TransConverter.toString((Affine) shape.getTransforms().get(0)));
		FXMLLoader loader = new FXMLLoader();
		String file = shape instanceof Text ? "TextProps.fxml" : (shape instanceof Rectangle ? "RectProps.fxml" :
			(shape instanceof Path ? "PathProps.fxml" : "ArcProps.fxml"));
		loader.setLocation(getClass().getResource(file));
		Pane root = loader.load();
		AnchorPane.setLeftAnchor(root, 0.0);
		AnchorPane.setRightAnchor(root, 0.0);
		AnchorPane.setTopAnchor(root, 0.0);
		genAnchorPane.getChildren().add(root);
		controller = loader.getController();
		controller.setItem(shape);
	}
	
	@Override public void handleApply() throws Exception {
		controller.handleApply();
		shape.getStyleClass().setAll(StyleClassConverter.parseClasses(classText.getText()));
		shape.setFill(fillColorPicker.getValue());
		shape.setStroke(strokeColorPicker.getValue());
		shape.setStrokeWidth(strokeWidthSpin.getValue());
		shape.setStrokeLineCap(lineCapCombo.getValue());
		shape.setStrokeLineJoin(lineJoinCombo.getValue());
		shape.getTransforms().set(0, TransConverter.parseTransform(transText.getText()));
	}
}
