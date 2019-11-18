package org.joeftiger.javahelp;

import java.util.Arrays;
import java.util.function.Consumer;

public class Option {

	private OptionInvoke optionInvoke;
	private OptionParameter optionParameter;
	private String description;
	private Consumer<String> callback;

	public Option(OptionInvoke optionInvoke, OptionParameter optionParameter, String description, Consumer<String> callback) {
		this.optionInvoke = optionInvoke;
		this.optionParameter = optionParameter;
		this.description = description;
		this.callback = callback;
	}

	@Override
	public String toString() {
		return toString(2);
	}

	public String toString(int indent) {
		return toString(indent, 24);
	}

	public String toString(int indent, int descriptionIndent) {
		return toString(indent, descriptionIndent, 81);
	}

	public String toString(int indent, int descriptionIndent, int limit) {
		String out = String.format("%s %s", optionInvoke, optionParameter);

		String[] descriptionLines = StringUtil.splitPreservingWords(this.description, limit - descriptionIndent);
		String desc = String.join("\n" + " ".repeat(descriptionIndent), descriptionLines);

		if (out.length() >= descriptionIndent) {
			return " ".repeat(indent) + out + "\n" + " ".repeat(descriptionIndent) + desc;
		} else {
			out = " ".repeat(indent) + out;
			return out + " ".repeat(descriptionIndent - out.length()) + desc;
		}
	}

	/**
	 * Returns whether this option has the given input as invoke.
	 *
	 * @param input option invoke
	 * @return {@code true} if match. {@code false} if not.
	 */
	public boolean matchesInvoke(String input) {
		if (optionInvoke == null) return false;
		return Arrays.asList(optionInvoke.invokes).contains(input);
	}

	/**
	 * Returns whether this option has the given input as valid parameter.
	 *
	 * @param input option parameter
	 * @return {@code true} if match. {@code false} if not.
	 */
	public boolean matchesParameter(String input) {
		if (optionParameter == null) return false;
		return Arrays.asList(optionParameter.options).contains(input);
	}

	public Consumer<String> getCallback() {
		return callback;
	}

	/**
	 * Applies the given parameter to the callback function.
	 *
	 * @param parameter parameter
	 * @throws IllegalArgumentException if callback cannot process the parameter
	 */
	public void apply(String parameter) throws IllegalArgumentException {
		callback.accept(parameter);
	}

	static class OptionInvoke {
		private String[] invokes;

		public OptionInvoke(String[] invokes) {
			this.invokes = invokes;
		}

		@Override
		public String toString() {
			return String.join(", ", invokes);
		}
	}

	static class OptionParameter {
		private Class<?> Class;
		private String[] options;

		public OptionParameter(Class<?> Class) {
			this.Class = Class;
		}

		public OptionParameter(String[] options) {
			this. options = options;
		}

		@Override
		public String toString() {
			String format = "{%s}";
			if (Class != null) {
				return String.format(format, Class.getSimpleName());
			}
			if (options == null || options.length == 0) return "";

			return String.format(format, String.join(",", options));
		}
	}
}
