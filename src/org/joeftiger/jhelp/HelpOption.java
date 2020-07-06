package org.joeftiger.jhelp;

import java.util.Arrays;
import java.util.function.Consumer;

public class HelpOption {

	private final OptionInvoke optionInvoke;
	private final OptionParameter optionParameter;
	private final String description;
	private final Consumer<String> callback;

	/**
	 * Creates a new Help option. It is recommended to use the specifically targeted {@link HelpOptionBuilder}.
	 *
	 * @param optionInvoke    invoke
	 * @param optionParameter parameter
	 * @param description     description
	 * @param callback        callback
	 */
	public HelpOption(OptionInvoke optionInvoke, OptionParameter optionParameter, String description, Consumer<String> callback) {
		this.optionInvoke = optionInvoke;
		this.optionParameter = optionParameter;
		this.description = description;
		this.callback = callback;
	}

	/**
	 * @return {@code this.toString(int 2)}
	 */
	@Override
	public String toString() {
		return toString(2);
	}

	/**
	 * @param indent indentation
	 * @return {@code this.toString(int indent, int 24)}
	 */
	public String toString(int indent) {
		return toString(indent, 24);
	}

	/**
	 * @param indent            identation
	 * @param descriptionIndent description identation
	 * @return {@code this.toString(int indent, int descriptionIndent, int 1024)}
	 */
	public String toString(int indent, int descriptionIndent) {
		return toString(indent, descriptionIndent, 1024);
	}

	/**
	 * @param indent            indent
	 * @param descriptionIndent description indent
	 * @param paragraphLimit    paragraph limit (before line break in description)
	 * @return printable form of this Option
	 */
	public String toString(int indent, int descriptionIndent, int paragraphLimit) {
		var out = optionInvoke.toString();
		if (!optionParameter.isEmpty()) {
			out += " " + optionParameter;
		}

		if (description != null && !description.isBlank()) {
			String[] descriptionLines = StringUtil.splitPreservingWords(this.description, paragraphLimit - descriptionIndent);
			String desc = String.join("\n" + " ".repeat(descriptionIndent), descriptionLines);

			if (out.length() >= descriptionIndent - indent) {
				return " ".repeat(indent) + out + "\n" + " ".repeat(descriptionIndent) + desc;
			} else {
				out = " ".repeat(indent) + out;
				return out + " ".repeat(Math.max(0, descriptionIndent - out.length())) + desc;
			}
		}
		return " ".repeat(indent) + out;
	}

	/**
	 * Returns whether this option has the given input as invoke.
	 *
	 * @param input option invoke
	 * @return {@code true} if match. {@code false} if not.
	 */
	public boolean matchesInvoke(String input) {
		return optionInvoke != null && optionInvoke.matches(input);
	}

	/**
	 * Returns whether this option has the given input as valid parameter.
	 *
	 * @param input option parameter
	 * @return {@code true} if match. {@code false} if not.
	 * @see #hasParameter()
	 */
	public boolean matchesParameter(String input) {
		if (!hasParameter()) {
			return false;
		}
		return optionParameter.matches(input);
	}

	/**
	 * @return whether this option has specific parameters
	 */
	public boolean hasParameter() {
		return optionParameter != null;
	}

	/**
	 * @return option callback
	 */
	public Consumer<String> getCallback() {
		return callback;
	}

	/**
	 * Applies the given parameter to the callback function.
	 *
	 * @param parameter parameter
	 * @throws IllegalArgumentException if callback cannot process the parameter
	 */
	public void applyToCallback(String parameter) throws IllegalArgumentException {
		callback.accept(parameter);
	}

	static class OptionInvoke {
		private final String[] invokes;

		public OptionInvoke(String[] invokes) {
			this.invokes = invokes;
		}

		public boolean matches(String input) {
			return Arrays.asList(invokes).contains(input);
		}

		@Override
		public String toString() {
			return String.join(", ", invokes);
		}
	}

	static class OptionParameter {
		private String Class;
		private String[] parameters;

		public OptionParameter(Class<?> Class) {
			this(Class.getSimpleName());
		}

		public OptionParameter(String Class) {
			this.Class = Class.strip();
		}

		public OptionParameter(String[] parameters) {
			this.parameters = parameters;
		}

		/**
		 * Returns whether this parameter is empty and has therefore no specific needed class or String parameters.
		 *
		 * @return {@code true} if empty. {@code false} if not.
		 */
		public boolean isEmpty() {
			return Class != null && !Class.isBlank() && parameters != null && parameters.length != 0;
		}

		/**
		 * Returns whether the given param matches the set Class (if any) or any of the parameters.
		 *
		 * @param param param to check
		 * @return {@code true} if classes match or parameters contain param. {@code false} if not.
		 */
		public boolean matches(String param) {
			if (Class != null) {
				return switch (Class) {
					case "Byte" -> StringUtil.isByte(param);
					case "Short" -> StringUtil.isShort(param);
					case "Integer" -> StringUtil.isInteger(param);
					case "Long" -> StringUtil.isLong(param);
					case "Float" -> StringUtil.isFloat(param);
					case "Double" -> StringUtil.isDouble(param);
					case "Character" -> StringUtil.isCharacter(param);
					default -> true;
				};
			}

			return Arrays.asList(parameters).contains(param);
		}

		@Override
		public String toString() {
			var format = "{%s}";

			if (Class != null) return String.format(format, Class);

			if (parameters == null || parameters.length == 0) return "";

			return String.format(format, String.join(",", parameters));
		}
	}
}
