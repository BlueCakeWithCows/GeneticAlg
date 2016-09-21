package components.basic;

public class Conditional extends Node {

	public Conditional() {
		super(0, 1, 1);
	}

	@Override
	public String toString() {
		String ret = "IF " + values[0] + " > " + values[1] + " THEN " + values[2];
		StringBuilder s = new StringBuilder();
		s.append(ret + System.lineSeparator());
		return s.toString().trim();
	}

	@Override
	public int compute(int currentLine, Tree tree ) {

		// Evaluates the function and if it returns false skips however many
		// lines
		boolean skip = !functions[0].compute(tree).getBoolean();

		int numberOfLinesToSkip;
		if (skip)
			numberOfLinesToSkip = tree.getValue(values[0]).getInt();
		else
			numberOfLinesToSkip = 1;

		return numberOfLinesToSkip;
	}

	@Override
	public Node getCopy() {
		Conditional c = new Conditional();
		c.copy(variables, values, functions);
		return c;
	}
}
