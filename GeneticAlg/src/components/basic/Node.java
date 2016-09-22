package components.basic;

import components.math.TrueFunction;

public abstract class Node {
	public String[] values;
	public String[] variables;
	public TrueFunction[] functions;
	public Parent parent;

	public Node() {

	}

	public Node(int variables, int values, int functions) {
		createBlankArrays(variables, values, functions);
	}

	public int execute(int currentLine, Tree tree, Parent parent) {
		this.parent = parent;
		return this.compute(currentLine, tree);
	}

	public abstract int compute(int currentLine, Tree tree);

	public abstract Node getCopy();

	public void copy(String[] variables, String[] values, TrueFunction[] functions) {
		int varLength = getRayLengthOrZero(variables);
		int valLength = getRayLengthOrZero(values);
		int functLength = getRayLengthOrZero(functions);
		createBlankArrays(varLength, valLength, functLength);

		for (int i = 0; i < varLength; i++) {
			this.variables[i] = variables[i];
		}

		for (int i = 0; i < valLength; i++) {
			this.values[i] = values[i];
		}

		for (int i = 0; i < functLength; i++) {
			this.functions[i] = functions[i].getCopy();
		}

	}

	private int getRayLengthOrZero(Object[] variables2) {
		if (variables2 != null)
			return variables2.length;
		return 0;
	}

	public void createBlankArrays(Integer variables, Integer values, Integer functions) {
		if (variables == null)
			variables = 0;
		this.variables = new String[variables];

		if (values == null)
			values = 0;
		this.values = new String[values];
		if (functions == 0)
			functions = 0;
		this.functions = new TrueFunction[functions];

	}

	public Parent getParent() {
		return parent;
	}

	public abstract Node getInstanceOf();

}
