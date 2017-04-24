package dialogs;

import java.io.*;

import javafx.scene.control.*;
import javafx.scene.layout.*;

/**
 * Shows a dialog with a descrition of the through exception.
 */
public final class ExceptionDialog extends Alert {
	public ExceptionDialog(Exception ex) {
		super(AlertType.ERROR);
		setHeaderText(ex.getClass().getSimpleName() + " caught!");
		setContentText(ex.getMessage());
		
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		ex.printStackTrace(pw);
		String exceptionText = sw.toString();
		
		Label label = new Label("The exception stacktrace was:");
		
		TextArea textArea = new TextArea(exceptionText);
		textArea.setEditable(false);
		textArea.setWrapText(true);
		textArea.setMaxWidth(Double.MAX_VALUE);
		textArea.setMaxHeight(Double.MAX_VALUE);
		
		GridPane.setHgrow(textArea, Priority.ALWAYS);
		GridPane.setVgrow(textArea, Priority.ALWAYS);
		GridPane expContent = new GridPane();
		expContent.setMaxWidth(Double.MAX_VALUE);
		expContent.add(label, 0, 0);
		expContent.add(textArea, 0, 1);
		
		DialogPane dialogPane = getDialogPane();
		dialogPane.setExpandableContent(expContent);
		dialogPane.setPrefWidth(600);
		dialogPane.setPrefHeight(450);
	}
}
