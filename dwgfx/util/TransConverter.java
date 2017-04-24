package dwgfx.util;

import javafx.scene.transform.Affine;

public abstract class TransConverter {
	public static String toString(Affine transform) {
		String trans = "matrix " + transform.getMxx() + " " + transform.getMxy() + " " + transform.getTx();
		trans += " " + transform.getMyx() + " " + transform.getMyy() + " " + transform.getTy();
		return trans;
	}
	
	public static Affine parseTransform(String trans) throws Exception {
		Affine transform = new Affine();
		String[] t = trans.split("\\s+");
		int i = 0;
		while (i < t.length) {
			double x, y;
			switch (t[i++]) {
			case "translate":
				x = Double.parseDouble(t[i++]);
				y = Double.parseDouble(t[i++]);
				transform.appendTranslation(x, y);
				break;
			case "rotate":
				x = Double.parseDouble(t[i++]);
				transform.appendRotation(x);
				break;
			case "scale":
				x = Double.parseDouble(t[i++]);
				y = Double.parseDouble(t[i++]);
				transform.appendScale(x, y);
				break;
			case "shear":
				x = Double.parseDouble(t[i++]);
				y = Double.parseDouble(t[i++]);
				transform.appendShear(x, y);
				break;
			case "matrix":
				double xx = Double.parseDouble(t[i++]);
				double xy = Double.parseDouble(t[i++]);
				x = Double.parseDouble(t[i++]);
				double yx = Double.parseDouble(t[i++]);
				double yy = Double.parseDouble(t[i++]);
				y = Double.parseDouble(t[i++]);
				transform.append(xx, xy, x, yx, yy, y);
			}
		}
		return transform;
	}
}