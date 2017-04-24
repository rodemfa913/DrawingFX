package dwgfx;

import dialogs.ExceptionDialog;
import dwgfx.view.PrimaryScene;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * Main application.
 * @author rodemfa
 */
public class MainApp extends Application {
	public static void main(String[] args) {
		launch(args);
	}

	@Override public void start(Stage primaryStage) {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("view/PrimaryScene.fxml"));
			BorderPane root = loader.load();
			primaryStage.setScene(new Scene(root));
			primaryStage.setTitle("DrawingFX");
			PrimaryScene controller = loader.getController();
			controller.setStage(primaryStage);
			primaryStage.show();
		} catch (Exception ex) {
			(new ExceptionDialog(ex)).showAndWait();
		}
	}
}
