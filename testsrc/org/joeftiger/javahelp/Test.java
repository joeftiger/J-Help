package org.joeftiger.javahelp;

public class Test {
	public static void main(String[] args) {
		Option o1 = new OptionBuilder()
				.addInvokes("-c", "--count")
				.setParameterType(Integer.class)
				.setDescription("This is a very long description intended to fill up the line, such that it will be split up on words accordingly." +
				                "(Only if specified by help.toString(indent1, indent2, limit)")
				.build();
		Option o2 = new OptionBuilder()
				.addInvokes("-m", "--method")
				.addParameters("rank", "random")
				.setDescription("d".repeat(81 - 24) + " newline")
				.build();

		Help help = new Help()
				.setUsage(new Usage().addTargets("foo", "bar"))
				.addOptions(o1, o2);

		System.out.println(help);
	}
}
