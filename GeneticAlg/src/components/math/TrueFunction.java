package components.math;

import components.basic.Tree;

public class TrueFunction {

	private Operator operator;
	public String[] values;

	public TrueFunction(Operator operator) {
		this.operator = operator;
		values = new String[this.operator.getInputSize()];
	}

	public Value compute(Tree tree) {
		Value[] v = new Value[values.length];
		for (int i = 0; i < v.length; i++) {
			v[i] = tree.getValue(values[i]);
		}
		return operator.compute(v);
	}

	public TrueFunction getCopy() {
		TrueFunction f = new TrueFunction(operator);
		for (int i = 0; i < values.length; i++) {
			f.values[i] = values[i];
		}
		return f;
	}
}
