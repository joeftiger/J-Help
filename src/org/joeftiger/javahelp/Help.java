package org.joeftiger.javahelp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * A help consists of a {@link Usage} and multiple {@link Option}s (created with the help of {@link OptionBuilder}.
 *
 * @author Julius Oeftiger
 * @version 0.1
 */
public class Help {
	private Usage usage;
	private List<Option> options;

	public Help() {
		options = new ArrayList<>();
		setUsage(new Usage());
	}

	public Help setUsage(Usage usage) {
		this.usage = usage;
		return this;
	}

	public Help addOptions(Option... options) {
		Collections.addAll(this.options, options);
		return this;
	}

	public boolean hasOptionInvoke(String input) {
		for (Option o : options) {
			if (o.matchesInvoke(input)) {
				return true;
			}
		}

		return false;
	}

	public Option getOptionInvoke(String input) {
		for (Option o : options) {
			if (o.matchesInvoke(input)) {
				return o;
			}
		}

		throw new NoSuchElementException();
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		if (usage != null) {
			sb.append(usage);
		}

		if (!options.isEmpty()) {
			String[] opts = options.stream().map(Object::toString).toArray(String[]::new);
			sb.append("\n\n")
					.append("OPTIONS:")
					.append("\n")
					.append(String.join("\n", opts));
		}


		return sb.toString();
	}
}
