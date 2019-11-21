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
	void isByte() {
		assertFalse(StringUtil.isByte("false"));
		assertFalse(StringUtil.isByte(String.valueOf(Long.MAX_VALUE)));
		assertTrue(StringUtil.isByte(String.valueOf(Byte.MAX_VALUE)));
	}

	@Test
	void isShort() {
		assertFalse(StringUtil.isShort("false"));
		assertFalse(StringUtil.isShort(String.valueOf(Long.MAX_VALUE)));
		assertTrue(StringUtil.isShort(String.valueOf(Short.MAX_VALUE)));
	}

	@Test
	void isInteger() {
		assertFalse(StringUtil.isInteger("false"));
		assertFalse(StringUtil.isInteger(String.valueOf(Long.MAX_VALUE)));
		assertTrue(StringUtil.isInteger(String.valueOf(Integer.MAX_VALUE)));
	}

	@Test
	void isLong() {
		assertFalse(StringUtil.isLong("false"));
		assertTrue(StringUtil.isLong(String.valueOf(Long.MAX_VALUE)));
	}

	@Test
	void isFloat() {
		assertFalse(StringUtil.isFloat("false"));
		assertTrue(StringUtil.isFloat(String.valueOf(Long.MAX_VALUE)));
		assertTrue(StringUtil.isFloat(String.valueOf(Double.MAX_VALUE)));
	}

	@Test
	void isDouble() {
		assertFalse(StringUtil.isDouble("false"));
		assertTrue(StringUtil.isDouble(String.valueOf(Long.MAX_VALUE)));
		assertTrue(StringUtil.isDouble(String.valueOf(Double.MAX_VALUE)));
	}

	@Test
	void isCharacter() {
		assertFalse(StringUtil.isCharacter("false"));
		assertTrue(StringUtil.isCharacter("t"));
	}
}
