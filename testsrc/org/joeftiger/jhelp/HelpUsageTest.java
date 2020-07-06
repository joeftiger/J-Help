package org.joeftiger.jhelp;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.*;

class HelpUsageTest {

	private HelpUsage usage;
	private final Consumer<String> callback = s -> {
		passedCallbackCheck = s.equalsIgnoreCase("success");
	};
	private HelpTarget target = new HelpTarget("callback", callback);
	private boolean passedCallbackCheck;

	@BeforeEach
	void setUp() {
		usage = new HelpUsage()
				.setProgramName("Test")
				.addTargets("empty")
				.addTargets(target);
		passedCallbackCheck = false;
	}

	@Test
	void setProgramName() {
		assertEquals("Test", usage.getProgramName());
	}

	@Test
	void getTargets() {
		assertEquals(target, usage.getTargets()[1]);
	}

	@Test
	void addTargets() {
		assertEquals("<empty>", usage.getTarget(0).toString());
		assertEquals(target, usage.getTarget(1));
	}

	@Test
	void hasNextTarget() {
		assertTrue(usage.hasNextTarget());
		usage.getNextTarget();
		assertTrue(usage.hasNextTarget());
		usage.getNextTarget();
		assertFalse(usage.hasNextTarget());
	}

	@Test
	void getNextTarget() {
		assertEquals("<empty>", usage.getNextTarget().toString());
		assertEquals(target, usage.getNextTarget());
		assertThrows(IndexOutOfBoundsException.class, () -> usage.getNextTarget());
	}

	@Test
	void numberOfTargets() {
		assertEquals(2, usage.numberOfTargets());
	}

	@Test
	void resetNextTarget() {
		usage.getNextTarget();
		usage.resetNextTarget();
		assertEquals("<empty>", usage.getNextTarget().toString());
	}

	@Test
	void testToString() {
		assertEquals("USAGE:\n" +
		             " Test [options] <empty> <callback>", usage.toString());
	}
}
