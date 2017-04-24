package dwgfx.view.shape;

import java.net.URL;
import java.util.*;

import dwgfx.view.Props;
import javafx.collections.*;
import javafx.fxml.*;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.text.*;

public class TextProps implements Initializable, Props {
	private Text text;
	@FXML private Spinner<Double> xSpin;
	@FXML private Spinner<Double> ySpin;
	@FXML private ComboBox<String> familyCombo;
	@FXML private Spinner<Double> sizeSpin;
	@FXML private CheckBox boldCheck;
	@FXML private CheckBox italicCheck;
	@FXML private TextArea bodyText;
	
	@Override public void initialize(URL location, ResourceBundle resources) {
		List<String> famList = Font.getFamilies();
		familyCombo.setItems(FXCollections.observableArrayList(famList));
	}
	
	@Override public void setItem(Node item) {
		text = (Text) item;
		xSpin.getValueFactory().setValue(text.getLayoutX());
		ySpin.getValueFactory().setValue(text.getLayoutY());
		Font font = text.getFont();
		familyCombo.setValue(font.getFamily());
		sizeSpin.getValueFactory().setValue(font.getSize());
		boldCheck.setSelected(font.getStyle().contains("Bold"));
		italicCheck.setSelected(font.getStyle().contains("Italic"));
		bodyText.setText(text.getText());
	}
	
	@Override public void handleApply() {
		text.setLayoutX(xSpin.getValue());
		text.setLayoutY(ySpin.getValue());
		String family = familyCombo.getValue();
		FontWeight weight =	boldCheck.isSelected() ? FontWeight.BOLD : FontWeight.NORMAL;
		FontPosture posture = italicCheck.isSelected() ? FontPosture.ITALIC : FontPosture.REGULAR;
		Double size = sizeSpin.getValue();
		text.setFont(Font.font(family, weight, posture, size));
		text.setText(bodyText.getText());
	}
}