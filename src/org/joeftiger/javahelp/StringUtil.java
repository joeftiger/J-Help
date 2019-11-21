package org.joeftiger.javahelp;

import java.util.ArrayList;
import java.util.List;

public class StringUtil {
	public static String[] splitPreservingWords(String text, int length) {
		List<String> lines = new ArrayList<>();
		String[] words = text.split(" ");

		String line = "";
		for (int i = 0; i < words.length; i++) {
			String newLine = line + (i == 0 ? "" : " ") + words[i];

			if (newLine.length() > length) {
				lines.add(line);
				line = words[i];
			} else {
				line = newLine;
			}
		}

		lines.add(line);

		return lines.toArray(String[]::new);
	}

	public static boolean isLong(String s) {
		try {
			Long.parseLong(s);
			return true;
		} catch (NumberFormatException | NullPointerException ignored) {
			return false;
		}
	}

	public static boolean isDouble(String s) {
		try {
			Double.parseDouble(s);
			return true;
		} catch (NumberFormatException | NullPointerException ignored) {
			return false;
		}
	}

	public static boolean isCharacter(String s) {
		return s != null && s.length() == 1;
	}
}
