package org.joeftiger.javahelp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Usage {
	private String programName;
	private List<String> targets;

	public Usage() {
		targets = new ArrayList<>();
		setProgramName(new java.io.File(getClass()
                            .getProtectionDomain()
                            .getCodeSource()
                            .getLocation()
                            .getPath())
			                .getName());
	}

	public Usage setProgramName(String programName) {
		this.programName = programName;
		return this;
	}

	public Usage addTargets(String... targets) {
		Collections.addAll(this.targets, targets);
		return this;
	}

	@Override
	public String toString() {
		String out = "USAGE:\n " + programName + " [options]";

		if (!targets.isEmpty()) {
			out += "<" + String.join("> <", targets) + ">";
		}

		return out;
	}
}
