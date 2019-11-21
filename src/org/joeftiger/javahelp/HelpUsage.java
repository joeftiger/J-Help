package org.joeftiger.javahelp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class HelpUsage {
	private String programName;
	private List<HelpTarget> targets;

	private int index = 0;

	public HelpUsage() {
		targets = new ArrayList<>();
		var stack = Thread.currentThread().getStackTrace();
		setProgramName(null);
	}

	public String getProgramName() {
		return programName;
	}

	/**
	 * Sets the program name. If the given String is {@code null}, "null" will be used.
	 *
	 * @param programName program name
	 * @return this HelpUsage
	 */
	public HelpUsage setProgramName(String programName) {
		this.programName = programName == null ? "null" : programName;
		return this;
	}

	/**
	 * Adds simple targets without callbacks to this usage to display. These should be sorted for cohesion.
	 *
	 * @param targets targets (files, inputs, etc.)
	 * @return this HelpUsage
	 */
	public HelpUsage addTargets(String... targets) {
		for (String t : targets) {
			this.targets.add(new HelpTarget(t));
		}
		return this;
	}

	public HelpTarget[] getTargets() {
		return targets.toArray(HelpTarget[]::new);
	}

	/**
	 * Adds targets to this usage to display. These should be sorted.
	 *
	 * @param targets targets (files, inputs, etc.)
	 * @return this HelpUsage
	 * @see #getTarget(int)
	 * @see #hasNextTarget()
	 * @see #getNextTarget()
	 */
	public HelpUsage addTargets(HelpTarget... targets) {
		Collections.addAll(this.targets, targets);
		return this;
	}

	/**
	 * Returns the target at the specified position.
	 *
	 * @param index index of target
	 * @return target at the specified position
	 * @throws IndexOutOfBoundsException if the index is out of range ({@code index < 0 || index >= numberOfTargets()})
	 */
	public HelpTarget getTarget(int index) throws IndexOutOfBoundsException {
		return targets.get(index);
	}

	/**
	 * Returns whether there exists a next target using the built-in index.
	 *
	 * @return {@code true} if next target exists. {@code false} if not.
	 * @see #getNextTarget()
	 * @see #resetNextTarget()
	 */
	public boolean hasNextTarget() {
		return targets.size() > index;
	}

	/**
	 * Returns the next target and increases the built-int index.
	 *
	 * @return next target
	 * @see #hasNextTarget()
	 * @see #resetNextTarget()
	 * @throws IndexOutOfBoundsException if built-in index is out of range
	 */
	public HelpTarget getNextTarget() throws IndexOutOfBoundsException {
		return getTarget(index++);
	}

	/**
	 * Returns the number of targets.
	 *
	 * @return number of targets
	 */
	public int numberOfTargets() {
		return targets.size();
	}

	/**
	 * Resets the built-int index for {@link #getNextTarget()}.
	 *
	 * @return this HelpUsage
	 */
	public HelpUsage resetNextTarget() {
		index = 0;
		return this;
	}

	/**
	 * Returns this HelpUsage as a beautified String to print.
	 *
	 * @return printable representation
	 */
	@Override
	public String toString() {
		String out = "USAGE:\n " + programName + " [options]";

		if (!targets.isEmpty()) {
			out += " " + targets.stream().map(HelpTarget::toString).collect(Collectors.joining(" "));
		}

		return out;
	}
}
