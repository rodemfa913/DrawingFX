package dwgfx.view;

import java.io.File;
import java.net.URL;
import java.util.*;

import javax.xml.bind.*;

import dialogs.ExceptionDialog;
import dwgfx.model.Drawing;
import dwgfx.util.TreeCellFactory;
import javafx.beans.value.*;
import javafx.event.*;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.effect.InnerShadow;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.text.*;
import javafx.scene.transform.Affine;
import javafx.stage.*;
import javafx.stage.FileChooser.ExtensionFilter;

/**
 * Primary scene controller.
 * @author rodemfa
public class PrimaryScene implements Initializable {
	@FXML private TreeView<Node> nodeTree;
	@FXML private AnchorPane canvas;
	private ExtensionFilter extFilter;
	private IdListener idListener;
	private ShapeHandler shapeHandler;
	private Stage primaryStage, propsStage;
	@FXML private ComboBox<String> typeCombo;
	@FXML private Slider zoomSlider;
	@FXML private ScrollPane canvasScroll;
	
	private class TreeSelectionListener implements ChangeListener<TreeItem<Node>> {
		@Override public void changed(ObservableValue<? extends TreeItem<Node>> observable,
				TreeItem<Node> oldValue, TreeItem<Node> newValue) {
			InnerShadow effect;
			if (oldValue == null || oldValue.getParent() == null) {
				effect = new InnerShadow();
			} else {
				effect = (InnerShadow) oldValue.getValue().getEffect();
				oldValue.getValue().setEffect(null);
			}
			if (newValue != null && newValue.getParent() != null) {
				newValue.getValue().setEffect(effect);
			}
		}
	}
	
	private class IdListener implements ChangeListener<String> {
		@Override public void changed(ObservableValue<? extends String> observable,
				String oldValue, String newValue) {
			nodeTree.refresh();
		}
	}
	
	private class ShapeHandler implements EventHandler<MouseEvent> {
		@Override public void handle(MouseEvent event) {
			Node selected = (Node) event.getSource();
			Group layer = (Group) selected.getParent();
			int j = layer.getChildren().indexOf(selected);
			int i = canvas.getChildren().indexOf(layer);
			TreeItem<Node> layItem = nodeTree.getRoot().getChildren().get(i);
			TreeItem<Node> item = layItem.getChildren().get(j);
			nodeTree.getSelectionModel().select(item);
		}
	}
	
	@Override public void initialize(URL location, ResourceBundle resources) {
		extFilter = new ExtensionFilter("XML files (*.xml)", "*.xml");
		nodeTree.setCellFactory(new TreeCellFactory());
		nodeTree.getSelectionModel().selectedItemProperty().addListener(new TreeSelectionListener());
		canvas.setId("drawing");
		idListener = new IdListener();
		canvas.idProperty().addListener(idListener);
		canvas.setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));
		nodeTree.setRoot(new TreeItem<>(canvas));
		nodeTree.getSelectionModel().select(0);
		shapeHandler = new ShapeHandler();
	}
	
	public void setStage(Stage stage) {
		primaryStage = stage;
	}
	
	@FXML private void handleNew(ActionEvent event) {
		nodeTree.getSelectionModel().select(0);
		nodeTree.getRoot().getChildren().clear();
		canvas.getChildren().clear();
		canvas.setId("drawing");
		canvas.setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));
		canvas.setMinWidth(400.0);
		canvas.setMinHeight(400.0);
		zoomSlider.setValue(0);
		handleZoom();
	}
	
	@FXML private void handleOpen() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().add(extFilter);
		File file = fileChooser.showOpenDialog(primaryStage);
		if (file != null) {
			try {
				JAXBContext context = JAXBContext.newInstance(Drawing.class);
				Unmarshaller um = context.createUnmarshaller();
				Drawing dwgWrapper = (Drawing) um.unmarshal(file);
				handleNew(null);
				dwgWrapper.loadDrawing(canvas);
				List<TreeItem<Node>> layers = nodeTree.getRoot().getChildren();
				for (Node layer : canvas.getChildren()) {
					layer.idProperty().addListener(idListener);
					TreeItem<Node> layItem = new TreeItem<>(layer);
					layers.add(layItem);
					Parent parent = (Parent) layer;
					for (Node item : parent.getChildrenUnmodifiable()) {
						item.idProperty().addListener(idListener);
						item.setOnMouseClicked(shapeHandler);
						layItem.getChildren().add(new TreeItem<>(item));
					}
				}
			} catch (Exception ex) {
				ExceptionDialog dialog = new ExceptionDialog(ex);
				dialog.showAndWait();
			}
		}
	}
	
	@FXML private void handleSave() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().add(extFilter);
		File file = fileChooser.showSaveDialog(primaryStage);
		if (file != null) {
			if (!file.getPath().endsWith(".xml")) {
				file = new File(file.getPath() + ".xml");
			}
			try {
				JAXBContext context = JAXBContext.newInstance(Drawing.class);
				Marshaller m = context.createMarshaller();
				m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
				Drawing dwgWrapper = new Drawing(canvas);
				m.marshal(dwgWrapper, file);
			} catch (Exception ex) {
				ExceptionDialog dialog = new ExceptionDialog(ex);
				dialog.showAndWait();
			}
		}
	}
	
	@FXML private void handleAdd() {
		String type = typeCombo.getValue();
		if (type == null) {
			type = "Layer";
		}
		if (type.equals("Layer")) {
			addLayer();
		} else {
			TreeItem<Node> layItem = nodeTree.getSelectionModel().getSelectedItem();
			if (layItem.getValue() instanceof Shape) {
				layItem = layItem.getParent();
			} else if (!(layItem.getValue() instanceof Group)) {
				layItem = addLayer();
			}
			Shape shape;
			switch (type) {
			case "Arc":
				shape = new Arc(0, 0, 100, 75, 0, 90);
				break;
			case "Circle":
				shape = new Circle(50);
				break;
			case "Ellipse":
				shape = new Ellipse(100, 75);
				break;
			case "Path":
				shape = new Path(new MoveTo(0, 0), new LineTo(100, 0),
						new CubicCurveTo(150, 0, 75, 100, 125, 150), new LineTo(50, 175), new ClosePath());
				break;
			case "Text":
				Text text = new Text("testing...\n1 2 3");
				text.setFont(new Font("Monospaced Regular", 24));
				shape = text;
				break;
			default:
				shape = new Rectangle(200, 150);
			}
			shape.setId("item");
			shape.idProperty().addListener(idListener);
			shape.getTransforms().add(new Affine());
			shape.setOnMouseClicked(shapeHandler);
			Group layer = (Group) layItem.getValue();
			layer.getChildren().add(shape);
			layItem.getChildren().add(new TreeItem<>(shape));
		}
	}
	
	private TreeItem<Node> addLayer() {
		Group layer = new Group();
		layer.setId("layer");
		layer.idProperty().addListener(idListener);
		canvas.getChildren().add(layer);
		TreeItem<Node> layItem = new TreeItem<>(layer);
		nodeTree.getRoot().getChildren().add(layItem);
		return layItem;
	}
	
	@FXML private void handleEdit() {
		try {
			Node item = nodeTree.getSelectionModel().getSelectedItem().getValue();
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("Properties.fxml"));
			VBox root = loader.load();
			propsStage = new Stage();
			propsStage.setScene(new Scene(root));
			propsStage.setTitle("Properties");
			propsStage.initModality(Modality.WINDOW_MODAL);
			propsStage.initOwner(primaryStage);
			Properties controller = loader.getController();
			controller.setItem(item);
			propsStage.showAndWait();
		} catch (Exception ex) {
			(new ExceptionDialog(ex)).showAndWait();
		}
	}
	
	@FXML private void handleDelete() {
		MultipleSelectionModel<TreeItem<Node>> selector = nodeTree.getSelectionModel();
		TreeItem<Node> treeItem = selector.getSelectedItem();
		TreeItem<Node> tiParent = treeItem.getParent();
		if (tiParent != null) {
			List<TreeItem<Node>> tiList = tiParent.getChildren();
			Node item = treeItem.getValue();
			List<Node> list;
			if (item instanceof Shape) {
				list = ((Group) item.getParent()).getChildren();
			} else {
				list = ((AnchorPane) item.getParent()).getChildren();
			}
			selector.select(0);
			tiList.remove(treeItem);
			list.remove(item);
		}
	}
	
	@FXML private void handleMoveBack() {
		MultipleSelectionModel<TreeItem<Node>> selector = nodeTree.getSelectionModel();
		TreeItem<Node> treeItem = selector.getSelectedItem();
		TreeItem<Node> tiParent = treeItem.getParent();
		if (tiParent != null) {
			List<TreeItem<Node>> tiList = tiParent.getChildren();
			int index = tiList.indexOf(treeItem);
			if (index > 0) {
				Node item = treeItem.getValue();
				List<Node> list;
				if (item instanceof Shape) {
					list = ((Group) item.getParent()).getChildren();
				} else {
					list = ((AnchorPane) item.getParent()).getChildren();
				}
				selector.select(0);
				tiList.remove(treeItem);
				tiList.add(index - 1, treeItem);
				list.remove(index);
				list.add(index - 1, item);
				selector.select(treeItem);
			}
		}
	}
	
	@FXML private void handleMoveForward() {
		MultipleSelectionModel<TreeItem<Node>> selector = nodeTree.getSelectionModel();
		TreeItem<Node> treeItem = selector.getSelectedItem();
		TreeItem<Node> tiParent = treeItem.getParent();
		if (tiParent != null) {
			List<TreeItem<Node>> tiList = tiParent.getChildren();
			int index = tiList.indexOf(treeItem);
			if (index < tiList.size() - 1) {
				Node item = treeItem.getValue();
				List<Node> list;
				if (item instanceof Shape) {
					list = ((Group) item.getParent()).getChildren();
				} else {
					list = ((AnchorPane) item.getParent()).getChildren();
				}
				selector.select(0);
				tiList.remove(treeItem);
				tiList.add(index + 1, treeItem);
				list.remove(index);
				list.add(index + 1, item);
				selector.select(treeItem);
			}
		}
	}
	
	@FXML private void handleZoom() {
		double zoom = Math.pow(2, zoomSlider.getValue() / 2.0);
		double h = canvasScroll.getHvalue();
		double v = canvasScroll.getVvalue();
		canvas.setScaleX(zoom);
		canvas.setScaleY(zoom);
		canvasScroll.setHvalue(h);
		canvasScroll.setVvalue(v);
	}
	
	@FXML private void handleZoomKeyReleased(KeyEvent event) {
		KeyCode key = event.getCode();
		if (key == KeyCode.LEFT || key == KeyCode.RIGHT) {
			handleZoom();
		}
	}
}
