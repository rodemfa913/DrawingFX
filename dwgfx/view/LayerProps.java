package dwgfx.view;

import javafx.fxml.FXML;
import javafx.scene.*;
import javafx.scene.control.*;

public class LayerProps implements Props {
	private Group layer;
	@FXML private Slider opacitySlider;
	@FXML private CheckBox visibleCheck;
	
	@Override public void setItem(Node item) {
		layer = (Group) item;
		opacitySlider.setValue(100.0 * layer.getOpacity());
		visibleCheck.setSelected(layer.isVisible());
	}
	
	@Override public void handleApply() {
		layer.setOpacity(opacitySlider.getValue() / 100.0);
		layer.setVisible(visibleCheck.isSelected());
	}
}
