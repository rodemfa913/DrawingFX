package dwgfx.view;

import java.io.File;
import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

public class DrawingProps implements Props {
	private AnchorPane drawing;
	@FXML private Spinner<Double> widthSpin;
	@FXML private Spinner<Double> heightSpin;
	@FXML private ColorPicker bgColorPicker;
	private File styleFile;
	@FXML private Button styleButton;
	
	@Override public void setItem(Node item) {
		drawing = (AnchorPane) item;
		widthSpin.getValueFactory().setValue(drawing.getMinWidth());
		heightSpin.getValueFactory().setValue(drawing.getMinHeight());
		bgColorPicker.setValue((Color) drawing.getBackground().getFills().get(0).getFill());
		List<String> stylesheets = drawing.getStylesheets();
		if (!stylesheets.isEmpty()) {
			styleFile = new File(stylesheets.get(0));
			styleButton.setText(styleFile.getName());
		}
	}
	
	@FXML private void handleStyle() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().add(new ExtensionFilter("CSS files (*.css)", "*.css"));
		styleFile = fileChooser.showOpenDialog(styleButton.getScene().getWindow());
		if (styleFile == null) {
			styleButton.setText("Choose...");
		} else {
			styleButton.setText(styleFile.getName());
		}
	}
	
	@Override public void handleApply() throws Exception {
		drawing.setMinWidth(widthSpin.getValue());
		drawing.setMinHeight(heightSpin.getValue());
		drawing.setBackground(new Background(new BackgroundFill(bgColorPicker.getValue(), null, null)));
		List<String> stylesheets = drawing.getStylesheets();
		stylesheets.clear();
		if (styleFile != null) {
			stylesheets.add(styleFile.toURI().toURL().toString());
		}
	}
}