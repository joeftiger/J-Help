package org.joeftiger.javahelp;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.*;

class HelpTargetTest {

	private HelpTarget nullTarget;
	private HelpTarget target;
	private boolean passedCallbackCheck;
	private final Consumer<String> callback = s -> {
		passedCallbackCheck = s.equalsIgnoreCase("success");
	};

	@BeforeEach
	void setUp() {
		nullTarget = new HelpTarget(null);
		target = new HelpTarget("callback", callback);
		passedCallbackCheck = false;
	}

	@Test
	void hasCallback() {
		assertFalse(nullTarget.hasCallback());
		assertTrue(target.hasCallback());
	}

	@Test
	void call() {
		assertThrows(NullPointerException.class, () -> nullTarget.call(""));

		target.call("success");
		assertTrue(passedCallbackCheck);
	}

	@Test
	void testToString() {
		assertEquals("<null>", nullTarget.toString());
		assertEquals("<callback>", target.toString());
	}
}
