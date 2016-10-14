package genetic.components;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import genetic.components.basic.Node;
import genetic.components.basic.Tree;
import genetic.components.math.Operator;
import genetic.components.math.TrueFunction;

public class MutationHelper<T> {

	private Random random;
	private List<String> vars;
	private List<String> values;

	private List<Node> validNodes;
	private List<Operator> validOperators;

	public MutationHelper(Random random2, ArrayList<String> arrayList, ArrayList<String> arrayList2,
			List<Operator> operators, List<Node> noes) {
		this.random = random2;
		this.vars = arrayList;
		this.values = arrayList2;
		this.validOperators = operators;
		this.validNodes = noes;
	}

	public void setValidNodes(List<Node> noes) {
		this.validNodes = noes;
	}

	public MutationHelper(Random random2, Tree tree, List<Operator> operators, List<Node> noes) {
		this(random2, tree.getDefaultVariables(), tree.getDefaultNames(), operators, noes);
	}

	public MutationHelper(Random rand, Tree tree, List<Operator> operatorSet) {
		this(rand, tree.getDefaultVariables(), tree.getDefaultNames(), operatorSet, null);

	}

	public Node getNewNode() {
		int index = random.nextInt(validNodes.size());
		Node n = validNodes.get(index).getInstanceOf();
		for (int i = 0; i < n.values.length; i++) {
			n.values[i] = this.getRandomValue();
		}
		for (int i = 0; i < n.functions.length; i++) {
			n.functions[i] = this.getRandomFunction();
		}
		for (int i = 0; i < n.variables.length; i++) {
			n.variables[i] = this.getRandomVariable();
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
		String s;
		if (i < vars.size())
			s= vars.get(i);
		else
			s= values.get(i - vars.size());
		return s;
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

	public List<String> getValues() {
		List<String> fuck = new ArrayList<String>();
		for (String x : this.values) {
			fuck.add(x);
		}
		for (String x : this.vars) {
			fuck.add(x);
		}
		return fuck;
	}
}
