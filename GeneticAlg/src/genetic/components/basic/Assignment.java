package genetic.components.basic;

public class Assignment extends Node {
	public Assignment() {
		super(1, 0, 1);
	}

	@Override
	public String toString() {
		return variables[0] + "= " + functions[0].toString();
	}

	@Override
	public Node getCopy() {
		Assignment c = new Assignment();
		c.copy(variables, values, functions);
		return c;
	}

	@Override
	public int compute(int currentLine, Tree tree) {
		
		tree.setVariable(variables[0], functions[0].compute(tree));
	
		return 1;
	}
	
	@Override
	public Node getInstanceOf() {
		return new Assignment();
	}
}
