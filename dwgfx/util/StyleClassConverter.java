package dwgfx.util;

import java.util.*;

public abstract class StyleClassConverter {
	public static String toString(List<String> classList) {
		String classes = "";
		for (String styleClass : classList) {
			if (!classes.isEmpty()) {
				classes += " ";
			}
			classes += styleClass;
		}
		return classes;
	}
	
	public static List<String> parseClasses(String classes) {
		List<String> classList = new ArrayList<>();
		for (String styleClass : classes.split("\\s+")) {
			classList.add(styleClass);
		}
		return classList;
	}
}