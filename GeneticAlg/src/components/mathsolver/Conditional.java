package components.mathsolver;

public class Conditional extends Node {

	public Conditional(String value0, String value1, String value2) {
		super(0, 3);
		this.values[0] = value0;
		this.values[1] = value1;
		this.values[2] = value2;
	}

	public Conditional(Conditional nP) {
		super(0, 3);
		this.variables = nP.variables;
		this.values = nP.values;
	}

	@Override
	public void compute(Tree tree) {
		Double v1 = tree.getValue(values[0]).getDouble();
		Double v2 = tree.getValue(values[1]).getDouble();
		if (v1 > v2)
			lineJump = 1;
		else {
			lineJump = tree.getValue(values[2]).getInt();
			if(lineJump<1 || lineJump>1000)
				lineJump=1;
		}
	}

	@Override
	public String toString() {
		String ret = "if (" + values[0] + " > " + values[1] + ") NEXT: " + values[2];
		StringBuilder s = new StringBuilder();
		s.append(ret + System.lineSeparator());
		return s.toString().trim();
	}

	int lineJump;

	@Override
	public int getLineJump() {
		if (lineJump < 1)
			return 1;
		return lineJump;
	}
}
