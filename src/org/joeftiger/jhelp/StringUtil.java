package org.joeftiger.jhelp;

import java.util.ArrayList;

public class StringUtil {

	/**
	 * Splits the given text into multiple lines with given maximum length. This split will conserve words according to
	 * a "\\s+" regex.
	 *
	 * @param text   text to split
	 * @param length maximum length per line
	 * @return text lines
	 */
	public static String[] splitPreservingWords(String text, int length) {
		var lines = new ArrayList<>();
		var words = text.split("\\s+");

		var line = "";
		for (var i = 0; i < words.length; i++) {
			var newLine = line + (i == 0 ? "" : " ") + words[i];

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


	/**
	 * @param s string
	 * @return whether the given string is a byte.
	 */
	public static boolean isByte(String s) {
		try {
			Byte.parseByte(s);
			return true;
		} catch (NumberFormatException | NullPointerException ignored) {
			return false;
		}
	}


	/**
	 * @param s string
	 * @return whether the given string is a short.
	 */
	public static boolean isShort(String s) {
		try {
			Short.parseShort(s);
			return true;
		} catch (NumberFormatException | NullPointerException ignored) {
			return false;
		}
	}


	/**
	 * @param s string
	 * @return whether the given string is a integer.
	 */
	public static boolean isInteger(String s) {
		try {
			Integer.parseInt(s);
			return true;
		} catch (NumberFormatException | NullPointerException ignored) {
			return false;
		}
	}


	/**
	 * @param s string
	 * @return whether the given string is a long.
	 */
	public static boolean isLong(String s) {
		try {
			Long.parseLong(s);
			return true;
		} catch (NumberFormatException | NullPointerException ignored) {
			return false;
		}
	}


	/**
	 * @param s string
	 * @return whether the given string is a float.
	 */
	public static boolean isFloat(String s) {
		try {
			Float.parseFloat(s);
			return true;
		} catch (NumberFormatException | NullPointerException ignored) {
			return false;
		}
	}


	/**
	 * @param s string
	 * @return whether the given string is a double.
	 */
	public static boolean isDouble(String s) {
		try {
			Double.parseDouble(s);
			return true;
		} catch (NumberFormatException | NullPointerException ignored) {
			return false;
		}
	}

	/**
	 * @param s string
	 * @return whether the given string is a character.
	 */
	public static boolean isCharacter(String s) {
		return s != null && s.length() == 1;
	}
}
