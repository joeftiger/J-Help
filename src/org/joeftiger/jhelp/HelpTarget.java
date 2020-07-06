package org.joeftiger.jhelp;

import java.util.function.Consumer;

public class HelpTarget {
	private final String target;
	private final Consumer<String> callback;

	/**
	 * Constructs a HelpTarget without a callback.
	 *
	 * @param target target name
	 * @see #HelpTarget(String, Consumer callback)
	 */
	public HelpTarget(String target) {
		this(target, null);
	}

	/**
	 * Creates a HelpTarget
	 *
	 * @param target   target name
	 * @param callback callback. may be {@code null}
	 */
	public HelpTarget(String target, Consumer<String> callback) {
		this.target = target == null ? "null" : target;
		this.callback = callback;
	}

	/**
	 * Returns whether this target has a callback.
	 *
	 * @return {@code true} if callback is present. {@code false} if not.
	 */
	public boolean hasCallback() {
		return callback != null;
	}

	/**
	 * Calls the targets' callback with given input.
	 *
	 * @param input input to the callback
	 * @see #hasCallback()
	 */
	public void call(String input) {
		callback.accept(input);
	}

	/**
	 * Returns this HelpTarget as a beautified String to print.
	 *
	 * @return printable representation
	 */
	@Override
	public String toString() {
		return "<" + target + ">";
	}
}
