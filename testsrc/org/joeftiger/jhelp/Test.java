package org.joeftiger.jhelp;

public class Test {
	public static void main(String[] args) {
		HelpOption o1 = new HelpOptionBuilder()
				.addInvokes("-c", "--count")
				.setParameterType(Integer.class)
				.setDescription("This is a very long description intended to fill up the line, such that it will be split up on words accordingly." +
				                "(Only if specified by help.toString(indent1, indent2, limit)")
				.build();
		HelpOption o2 = new HelpOptionBuilder()
				.addInvokes("-m", "--method")
				.addParameters("rank", "random")
				.setDescription("d".repeat(81 - 24) + " newline")
				.build();

		JavaHelp help = new JavaHelp()
				.setUsage(new HelpUsage().addTargets("foo", "bar"))
				.addOptions(o1, o2);

		System.out.println(help);
	}
}
