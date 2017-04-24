package dwgfx.util;

import java.util.*;

import javafx.scene.shape.*;

public abstract class PathConverter {
	public static String toString(List<PathElement> elements) {
		String data = "";
		for (PathElement elem : elements) {
			if (!data.isEmpty()) {
				data += " ";
			}
			if (elem instanceof MoveTo) {
				data += "moveto";
				MoveTo mt = (MoveTo) elem;
				data += " " + Double.toString(mt.getX()) + " " + Double.toString(mt.getY());
			} else if (elem instanceof LineTo) {
				data += "lineto";
				LineTo lt = (LineTo) elem;
				data += " " + Double.toString(lt.getX()) + " " + Double.toString(lt.getY());
			} else if (elem instanceof CubicCurveTo) {
				data += "curveto";
				CubicCurveTo ct = (CubicCurveTo) elem;
				data += " " + Double.toString(ct.getControlX1()) + " " + Double.toString(ct.getControlY1()) +
						" " + Double.toString(ct.getControlX2()) + " " + Double.toString(ct.getControlY2()) +
						" " + Double.toString(ct.getX()) + " " + Double.toString(ct.getY());
			} else if (elem instanceof ArcTo) {
				data += "arcto";
				ArcTo at = (ArcTo) elem;
				data += " " + Double.toString(at.getRadiusX()) + " " + Double.toString(at.getRadiusY()) +
						" " + Double.toString(at.getXAxisRotation()) + " " + Double.toString(at.getX()) +
						" " + Double.toString(at.getY()) +	" " + Boolean.toString(at.isLargeArcFlag()) +
						" " + Boolean.toString(at.isSweepFlag());
			} else {
				data += "close";
			}
		}
		return data;
	}
	
	public static List<PathElement> parseElements(String data) throws Exception{
		List<PathElement> elements = new ArrayList<>();
		String[] d = data.split("\\s+");
		int i = 0;
		while (i < d.length) {
			double x, y;
			switch (d[i++]) {
			case "moveto":
				x = Double.parseDouble(d[i++]);
				y = Double.parseDouble(d[i++]);
				elements.add(new MoveTo(x, y));
				break;
			case "lineto":
				x = Double.parseDouble(d[i++]);
				y = Double.parseDouble(d[i++]);
				elements.add(new LineTo(x, y));
				break;
			case "curveto":
				double x1 = Double.parseDouble(d[i++]);
				double y1 = Double.parseDouble(d[i++]);
				double x2 = Double.parseDouble(d[i++]);
				double y2 = Double.parseDouble(d[i++]);
				x = Double.parseDouble(d[i++]);
				y = Double.parseDouble(d[i++]);
				elements.add(new CubicCurveTo(x1, y1, x2, y2, x, y));
				break;
			case "arcto":
				double rx = Double.parseDouble(d[i++]);
				double ry = Double.parseDouble(d[i++]);
				double alpha = Double.parseDouble(d[i++]);
				x = Double.parseDouble(d[i++]);
				y = Double.parseDouble(d[i++]);
				boolean large = Boolean.parseBoolean(d[i++]);
				boolean sweep = Boolean.parseBoolean(d[i++]);
				elements.add(new ArcTo(rx, ry, alpha, x, y, large, sweep));
				break;
			default:
				elements.add(new ClosePath());
			}
		}
		return elements;
	}
}