package components;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import components.mathsolver.Conditional;
import components.mathsolver.Function;
import components.mathsolver.Operator;
import components.mathsolver.Node;
import components.mathsolver.Tree;

public class MutationHelper<T> {

	private Random random;
	private List<String> vars;
	private List<String> values;

	public MutationHelper(Random random2, ArrayList<String> arrayList, ArrayList<String> arrayList2) {
		this.random = random2;
		this.vars = arrayList;
		this.values = arrayList2;
	}

	public MutationHelper(Random random2, Tree tree) {
		this(random2, tree.getDefaultVariables(),tree.getDefaultNames());
	}

	public Node getNewPoint() {
		if (random.nextDouble() < .2) {
			return new Conditional(this.getRandomValue(), this.getRandomValue(),this.getRandomValue());
		} else {
			return new Function(this.getRandomVariable(), this.getRandomOperator(), this.getRandomValue(),
					this.getRandomValue());
		}
	}

	public int getRandomOperator() {
		return random.nextInt(Operator.getRange());
	}

	public String getRandomValue() {
		int i = random.nextInt(vars.size() + values.size());
		if (i < vars.size())
			return vars.get(i);
		else
			return values.get(i-vars.size());
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
