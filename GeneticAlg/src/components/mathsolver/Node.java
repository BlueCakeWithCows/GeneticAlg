package components.mathsolver;

public class Node extends Point {
	public int operator;
	public String val1, val2;
	public String var;

	public Node(String var, int o, String v1, String v2) {
		this.operator = o;
		this.val1 = v1;
		this.val2 = v2;
		this.var = var;
	}

	public Node(Node n) {
		this.operator = n.operator;
		this.val1 = n.val1;
		this.val2 = n.val2;
		this.var = n.var;
	}

	@Override
	public void compute(Tree tree) {
		Value vl1 = tree.getValue(val1);
		Value vl2 = tree.getValue(val2);
		try{
		tree.setVariable(var, Operator.doOperation(operator, vl1, vl2));
		}catch(Exception e){
			tree.setVariable(var, 0d);
		}
	}

	@Override
	public String toString() {
		return Operator.setup(operator,var,val1,val2);
	//	return var + "= " + Operator.getOperator(operator) + " " + val1 + ", " + val2;
	}
}
