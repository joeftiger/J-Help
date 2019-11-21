package org.joeftiger.javahelp;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class JavaHelpTest {

	private JavaHelp help;
	private HelpTarget target = new HelpTarget("empty");
	private HelpUsage usage = new HelpUsage()
			.addTargets(target);
	private HelpOption option = new HelpOptionBuilder()
			.addInvokes("-c", "--callback")
			.setDescription("description")
			.build();

	@BeforeEach
	void setUp() {
		help = new JavaHelp().setUsage(usage).addOptions(option);
	}

	@Test
	void setUsage() {
		assertEquals("USAGE:\n" +
		             " null [options] <empty>", help.toString().substring(0, 30));
	}

	@Test
	void addOptions() {
		assertEquals("OPTIONS:\n" +
		             "  -c, --callback", help.toString().split("\n", 4)[3].substring(0, 25));
	}

	@Test
	void setOptionIndent() {
		help.setOptionIndent(0);
		assertEquals("OPTIONS:\n" +
		             "-c, --callback", help.toString().split("\n", 4)[3].substring(0, 23));
	}

	@Test
	void setDescriptionIndent() {
		help.setDescriptionIndent(20);
		assertEquals("OPTIONS:\n" +
		             "  -c, --callback    description", help.toString().split("\n", 4)[3].substring(0, 40));
	}

	@Test
	void hasOptionInvoke() {
		assertTrue(help.hasOptionInvoke("-c"));
		assertTrue(help.hasOptionInvoke("--callback"));
		assertFalse(help.hasOptionInvoke("-callback"));
		assertFalse(help.hasOptionInvoke(null));
	}

	@Test
	void getOptionInvoke() {
		assertThrows(NoSuchElementException.class, () -> help.getOptionByInvoke(null));

		assertEquals(option, help.getOptionByInvoke("-c"));
		assertEquals(option, help.getOptionByInvoke("--callback"));
	}

	@Test
	void testToString() {
		assertEquals("USAGE:\n" +
		             " null [options] <empty>\n" +
		             "\n" +
		             "OPTIONS:\n" +
		             "  -c, --callback        description", help.toString());
	}
}
