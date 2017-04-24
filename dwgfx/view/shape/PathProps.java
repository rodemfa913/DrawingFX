package dwgfx.view.shape;

import dwgfx.util.PathConverter;
import dwgfx.view.Props;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.shape.Path;

public class PathProps implements Props {
	private Path path;
	@FXML private Spinner<Double> xSpin;
	@FXML private Spinner<Double> ySpin;
	@FXML private TextArea dataText;
	
	@Override public void setItem(Node item) {
		path = (Path) item;
		xSpin.getValueFactory().setValue(path.getLayoutX());
		ySpin.getValueFactory().setValue(path.getLayoutY());
		dataText.setText(PathConverter.toString(path.getElements()));
	}
	
	@Override public void handleApply() throws Exception{
		path.setLayoutX(xSpin.getValue());
		path.setLayoutY(ySpin.getValue());
		path.getElements().setAll(PathConverter.parseElements(dataText.getText()));
	}
}
