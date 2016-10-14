package genetic.components.basic;

import genetic.components.math.Value;

public class Conditional extends Node {

	public Conditional() {
		super(0, 1, 1);
	}

	@Override
	public String toString() {
		String ret = "IF " + functions[0] + " THEN " + values[0];
		StringBuilder s = new StringBuilder();
		s.append(ret + System.lineSeparator());
		return s.toString().trim();
	}

	@Override
	public int compute(int currentLine, Tree tree) {

		// Evaluates the function and if it returns false skips however many
		// lines
		boolean skip = !functions[0].compute(tree).getBoolean();

		
		int numberOfLinesToSkip;

		if (skip) {
			Value value = tree.getValue(values[0]);
			if (value == null)
				numberOfLinesToSkip = 2;
			else
				numberOfLinesToSkip = value.getInt();
			numberOfLinesToSkip = Math.max(2, numberOfLinesToSkip);
		} else
			numberOfLinesToSkip = 1;

		return numberOfLinesToSkip;
	}

	@Override
	public Node getCopy() {
		Conditional c = new Conditional();
		c.copy(variables, values, functions);
		return c;
	}

	@Override
	public Node getInstanceOf() {
		return new Conditional();
	}

}
