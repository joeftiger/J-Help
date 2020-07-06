package org.joeftiger.jhelp;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.*;

class HelpOptionTest {

	private final String description = "This is a long description meant to confuse the reader.";
	private HelpOption option;
	private boolean passedCallbackCheck;
	private final Consumer<String> callback = s -> {
		passedCallbackCheck = s.equalsIgnoreCase("success");
	};

	@BeforeEach
	void setUp() {
		option = new HelpOptionBuilder()
				.addInvokes("-h", "--help")
				.setCallback(callback)
				.setDescription(description)
				.build();
		passedCallbackCheck = false;
	}

	@Test
	void testToString() {
		final String expected = "  -h, --help            " + description;
		assertEquals(expected, option.toString());
	}

	@Test
	void testToStringIndent1() {
		final String expected = " -h, --help             " + description;
		assertEquals(expected, option.toString(1));
	}

	@Test
	void testToStringIndent24() {
		final String start = " ".repeat(24) + "-h, --help\n";
		final String expected = start + " ".repeat(24) + description;
		assertEquals(expected, option.toString(24));
	}

	@Test
	void testToStringDescriptionIndent20() {
		final String expected = "  -h, --help        " + description;
		assertEquals(expected, option.toString(2, 20));
	}

	@Test
	void testToStringParagraphLimit50() {
		final String expected = "  -h, --help            This is a long description\n" +
		                        "                        meant to confuse the\n" +
		                        "                        reader.";
		assertEquals(expected, option.toString(2, 24, 50));
	}

	@Test
	void matchesInvoke() {
		assertTrue(option.matchesInvoke("-h"));
		assertTrue(option.matchesInvoke("--help"));
		assertFalse(option.matchesInvoke("-help"));
	}

	@Test
	void matchesNoParameter() {
		assertFalse(option.matchesParameter(null));
		assertFalse(option.matchesParameter(""));
		assertFalse(option.matchesParameter("String"));
	}

	@Test
	void matchesParameter() {
		HelpOption o1 = new HelpOptionBuilder()
				.setParameterType(Integer.class)
				.build();
		assertTrue(o1.matchesParameter("100"));
		assertFalse(o1.matchesParameter("hundred"));

		HelpOption o2 = new HelpOptionBuilder()
				.addParameters("Foo", "Bar")
				.build();
		assertTrue(o2.matchesParameter("Foo"));
		assertFalse(o2.matchesParameter("foo"));
		assertTrue(o2.matchesParameter("Bar"));
		assertFalse(o2.matchesParameter("bar"));
	}

	@Test
	void getCallback() {
		assertNotNull(option.getCallback());
	}

	@Test
	void applyToCallback() {
		option.applyToCallback("fail");
		assertFalse(passedCallbackCheck);

		option.applyToCallback("success");
		assertTrue(passedCallbackCheck);
	}
}
