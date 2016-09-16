package components.mathsolver;

public class Function extends Node {
	public int operator;

	public Function(String var, int o, String v1, String v2) {
		super(1,2);
		this.operator = o;
		variables[0] = var;
		values[0] = var;
		values[1] = v1;
	}

	public Function(Function n) {
		super(1,2);
		super.copy(n.variables,n.values);
		this.operator = n.operator;
		
	}

	@Override
	public void compute(Tree tree) {
		Value vl1 = tree.getValue(values[0]);
		Value vl2 = tree.getValue(values[1]);
		try{
		tree.setVariable(variables[0], Operator.doOperation(operator, vl1, vl2));
		}catch(Exception e){
			tree.setVariable(variables[0], 0d);
		}
	}

	@Override
	public String toString() {
		return Operator.setup(operator,variables[0],values[0],values[1]);
	//	return var + "= " + Operator.getOperator(operator) + " " + val1 + ", " + val2;
	}
}
