package components;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import components.basic.Node;
import components.basic.Tree;
import components.math.Operator;
import components.math.TrueFunction;

public class MutationHelper<T> {

	private Random random;
	private List<String> vars;
	private List<String> values;

	private List<Node> validNodes;
	private List<Operator> validOperators;

	public MutationHelper(Random random2, ArrayList<String> arrayList, ArrayList<String> arrayList2, List<Operator> operators) {
		this.random = random2;
		this.vars = arrayList;
		this.values = arrayList2;
		this.validOperators = operators;
	}

	public MutationHelper(Random random2, Tree tree,List<Operator> operators) {
		this(random2, tree.getDefaultVariables(), tree.getDefaultNames(),operators);
	}

	public Node getNewNode() {
		int index = random.nextInt(validNodes.size());
		Node n = validNodes.get(index).getInstanceOf();
		
		for (int i = 0; i < n.values.length; i++) {
			n.values[i] = this.getRandomValue();
		}
		for (int i = 0; i < n.variables.length; i++) {
			n.variables[i] = this.getRandomVariable();
		}
		for (int i = 0; i < n.variables.length; i++) {
			n.functions[i] = this.getRandomFunction();
		}
		return n;
	}

	public TrueFunction getRandomFunction() {
		TrueFunction n = new TrueFunction(this.getRandomOperator());
		for (int i = 0; i < n.values.length; i++) {
			n.values[i] = this.getRandomValue();
		}
		return n;
	}

	public Operator getRandomOperator() {
		return validOperators.get(random.nextInt(validOperators.size()));
	}

	public String getRandomValue() {
		int i = random.nextInt(vars.size() + values.size());
		if (i < vars.size())
			return vars.get(i);
		else
			return values.get(i - vars.size());
	}

	public String getRandomVariable() {
		int i = random.nextInt(vars.size() + 1);
		if (i < vars.size()) {
			return vars.get(i);
		} else {
			String newVar = "var" + random.nextInt(300);
			addVar(newVar);
			return newVar;
		}
	}

	public void addVar(String var) {
		if (!vars.contains(var)) {
			vars.add(var);
		}
	}

	public boolean hasVariable(String string) {
		return vars.contains(string);
	}

	public boolean hasValue(String string) {
		return (vars.contains(string) || values.contains(string));
	}
}
