package dwgfx.view;

import dialogs.ExceptionDialog;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;

public class Properties implements Props {
	private Node item;
	@FXML private TextField idText;
	@FXML private VBox propsRoot;
	private Props controller;
	
	@Override public void setItem(Node it) throws Exception {
		item = it;
		idText.setText(item.getId());
		FXMLLoader loader = new FXMLLoader();
		String file = item instanceof AnchorPane ? "DrawingProps.fxml" :
			(item instanceof Group ? "LayerProps.fxml" : "shape/ShapeProps.fxml");
		loader.setLocation(getClass().getResource(file));
		Region root = loader.load();
		VBox.setVgrow(root, Priority.ALWAYS);
		propsRoot.getChildren().set(1, root);
		controller = loader.getController();
		controller.setItem(item);
	}
	
	@Override public void handleApply() {
		item.setId(idText.getText());
		try {
			controller.handleApply();
		} catch (Exception ex) {
			(new ExceptionDialog(ex)).showAndWait();
		}
	}
}