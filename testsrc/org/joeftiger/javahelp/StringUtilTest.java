package org.joeftiger.javahelp;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class StringUtilTest {

	private final String text = "There two types of people in the world. Those who can extrapolate from incomplete data";


	@Test
	void splitPreservingWords() {
		String[] out = StringUtil.splitPreservingWords(text, 10);
		final String[] expected = {
				"There two",
				"types of",
				"people in",
				"the world.",
				"Those who",
				"can",
				"extrapolate",
				"from",
				"incomplete",
				"data"
		};
		assertArrayEquals(expected, out);
	}

	@Test
	void isLong() {
		assertFalse(StringUtil.isLong("false"));
		assertTrue(StringUtil.isLong("10"));
	}

	@Test
	void isDouble() {
		assertFalse(StringUtil.isDouble("false"));
		assertTrue(StringUtil.isDouble("10"));
		assertTrue(StringUtil.isDouble("10.205"));
	}

	@Test
	void isCharacter() {
		assertFalse(StringUtil.isCharacter("false"));
		assertTrue(StringUtil.isCharacter("t"));
	}
}
