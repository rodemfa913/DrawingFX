package dwgfx.view;

import javafx.scene.Node;

public interface Props {
	public void setItem(Node item) throws Exception;
	public void handleApply() throws Exception;
}