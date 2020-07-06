package org.joeftiger.jhelp;

import java.util.*;

/**
 * A help consists of a {@link HelpUsage} and multiple {@link HelpOption}s (created with the help of {@link
 * HelpOptionBuilder}). It can be queried for {@link #getOptionByInvoke(String)}.
 *
 * @author Julius Oeftiger
 * @version 0.1
 */
public class JavaHelp {
	private HelpUsage usage = new HelpUsage();
	private final List<HelpOption> options = new ArrayList<>();
	private boolean sortAlphabetically;

	private int indent = 2;
	private int descriptionIndent = 24;
	private int paragraphLimit = 1024;

	/**
	 * Sets the USAGE section
	 *
	 * @param usage help usage
	 * @return this JavaHelp
	 */
	public JavaHelp setUsage(HelpUsage usage) {
		this.usage = usage;
		return this;
	}

	/**
	 * Adds the given options to the OPTIONS section.
	 *
	 * @param options options to add
	 * @return this JavaHelp
	 */
	public JavaHelp addOptions(HelpOption... options) {
		Collections.addAll(this.options, options);
		return this;
	}

	/**
	 * Whether the sort the options alphabetically for {@link #toString()}.
	 *
	 * @param sort {@code true} to sort, {@code false} if not.
	 * @return this JavaHelp
	 */
	public JavaHelp sortOptions(boolean sort) {
		sortAlphabetically = sort;
		return this;
	}

	/**
	 * Returns whether an option contains the given input invoke.
	 *
	 * @param input invoke to search for
	 * @return {@code true} if found. {@code false} if not.
	 */
	public boolean hasOptionInvoke(String input) {
		for (var o : options) {
			if (o.matchesInvoke(input)) {
				return true;
			}
		}

		return false;
	}

	/**
	 * Returns the option exactly matching the given input invoke. If no matching option is found, an exception will be
	 * raised.
	 *
	 * @param input invoke to search for
	 * @return matching option
	 * @throws NoSuchElementException if no option matches the given inp ut
	 */
	public HelpOption getOptionByInvoke(String input) throws NoSuchElementException {
		for (var o : options) {
			if (o.matchesInvoke(input)) {
				return o;
			}
		}

		throw new NoSuchElementException();
	}

	/**
	 * Returns this JavaHelp as a beautified String to print.
	 *
	 * @return printable representation
	 */
	@Override
	public String toString() {
		setOptionIndent(indent);
		setDescriptionIndent(descriptionIndent);
		setParagraphLimit(paragraphLimit);

		var sb = new StringBuilder();

		if (usage != null) {
			sb.append(usage);
		}

		if (!options.isEmpty()) {
			var opts = options.stream()
					.map(i -> i.toString(indent, descriptionIndent, paragraphLimit))
					.toArray(String[]::new);

			if (sortAlphabetically) Arrays.sort(opts);

			sb.append("\n\n")
					.append("OPTIONS:")
					.append("\n")
					.append(String.join("\n", opts));
		}

		return sb.toString();
	}

	/**
	 * Sets the indent for options. If the given indent is negative, it will be clamped to {@code 0}. The default is
	 * {@link #indent}.
	 *
	 * @param indent new indent
	 * @return this JavaHelp
	 */
	public JavaHelp setOptionIndent(int indent) {
		this.indent = Math.max(0, indent);
		return this;
	}

	/**
	 * Sets the indent for option descriptions. If the given indent is negative, it will be clamped to {@code 0}. The
	 * default is {@link #descriptionIndent}.
	 *
	 * @param indent new description indent
	 * @return this JavaHelp
	 */
	public JavaHelp setDescriptionIndent(int indent) {
		this.descriptionIndent = Math.max(0, indent);
		return this;
	}

	/**
	 * Sets the limit for description paragraphs. Will be clamped to ({@link #descriptionIndent} {@code + 10}) if below.
	 * The default is {@link #paragraphLimit}.
	 *
	 * @param limit new paragraph limit
	 * @return this JavaHelp
	 */
	public JavaHelp setParagraphLimit(int limit) {
		this.paragraphLimit = Math.max(descriptionIndent + 10, limit);
		return this;
	}
}
