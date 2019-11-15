package org.joeftiger.javahelp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

public class OptionBuilder {

	private List<String> optionInvokes;
	private Class<?> parameterType;
	private List<String> parameters;
	private Consumer<String> callback;
	private String description = "";

	public OptionBuilder() {
		optionInvokes = new ArrayList<>();
		parameters = new ArrayList<>();
	}

	/**
	 * Adds invoke aliases to the option. <br> Examples:
	 * <ul>
	 *     <li>{@code "-h", "--help"}</li>
	 *     <li>{@code "-m", "--method"}</li>
	 * </ul>
	 *
	 * @param invokes invoke aliases
	 * @return this builder
	 */
	public OptionBuilder addInvokes(String... invokes) {
		Collections.addAll(optionInvokes, invokes);
		return this;
	}

	/**
	 * Sets the parameter type to be displayed if no specific parameters need to be met.
	 *
	 * @param type parameter type
	 * @return this builder
	 * @see #addParameters(String...)
	 */
	public OptionBuilder setParameterType(Class<?> type) {
		parameterType = type;
		return this;
	}

	/**
	 * Adds parameters to the option. These parameters can be queried against an input.
	 *
	 * @param parameters possible parameters
	 * @return this builder
	 * @see #setParameterType(Class)
	 * @see Option#matchesParameter(String)
	 */
	public OptionBuilder addParameters(String... parameters) {
		Collections.addAll(this.parameters, parameters);
		return this;
	}

	/**
	 * Sets the description for the option.
	 *
	 * @param description option description
	 * @return this builder
	 */
	public OptionBuilder setDescription(String description) {
		this.description = description;
		return this;
	}

	/**
	 * Sets the callback function for the option.
	 *
	 * @param callback callback function
	 * @return this builder
	 */
	public OptionBuilder setCallback(Consumer<String> callback) {
		this.callback = callback;
		return this;
	}

	/**
	 * Builds the option according to previously set parameters.
	 *
	 * @return option
	 */
	public Option build() {
		Option.OptionInvoke invoke = new Option.OptionInvoke(optionInvokes.toArray(String[]::new));

		Option.OptionParameter parameter;
		if (parameterType != null) {
			parameter = new Option.OptionParameter(parameterType);
		} else {
			parameter = new Option.OptionParameter(this.parameters.toArray(String[]::new));
		}

		return new Option(invoke, parameter, description, callback);
	}
}
