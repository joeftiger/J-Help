package org.joeftiger.jhelp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

import static org.joeftiger.jhelp.HelpOption.OptionInvoke;
import static org.joeftiger.jhelp.HelpOption.OptionParameter;

public class HelpOptionBuilder {

	private final List<String> optionInvokes = new ArrayList<>();
	private Class<?> parameterType;
	private final List<String> parameters = new ArrayList<>();
	private Consumer<String> callback;
	private String description = "";

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
	public HelpOptionBuilder addInvokes(String... invokes) {
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
	public HelpOptionBuilder setParameterType(Class<?> type) {
		this.parameterType = type;
		return this;
	}

	/**
	 * Adds parameters to the option. These parameters can be queried against an input.
	 *
	 * @param parameters possible parameters
	 * @return this builder
	 * @see #setParameterType(Class)
	 * @see HelpOption#matchesParameter(String)
	 */
	public HelpOptionBuilder addParameters(String... parameters) {
		Collections.addAll(this.parameters, parameters);
		return this;
	}

	/**
	 * Sets the description for the option.
	 *
	 * @param description option description
	 * @return this builder
	 */
	public HelpOptionBuilder setDescription(String description) {
		this.description = description;
		return this;
	}

	/**
	 * Sets the callback function for the option.
	 *
	 * @param callback callback function
	 * @return this builder
	 */
	public HelpOptionBuilder setCallback(Consumer<String> callback) {
		this.callback = callback;
		return this;
	}

	/**
	 * Builds the option according to previously set parameters.
	 *
	 * @return option
	 */
	public HelpOption build() {
		var invoke = new OptionInvoke(optionInvokes.toArray(String[]::new));

		OptionParameter parameter;
		if (parameterType != null) {
			parameter = new OptionParameter(parameterType);
		} else {
			parameter = new OptionParameter(this.parameters.toArray(String[]::new));
		}

		return new HelpOption(invoke, parameter, description, callback);
	}
}
