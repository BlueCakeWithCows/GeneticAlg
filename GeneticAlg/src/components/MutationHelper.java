package components;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MutationHelper {
	private Random random;
	private List<String> vars;
	private List<String> values;

	public MutationHelper(Random random2, ArrayList<String> arrayList, ArrayList<String> arrayList2) {
		this.random = random2;
		this.vars = arrayList;
		this.values = arrayList2;
	}

	public MutationHelper(SecureRandom random2, Tree tree) {
		this(random2, tree.getDefaultVariables(),tree.getDefaultNames());
	}

	public Point getNewPoint() {
		if (random.nextDouble() < .2) {
			return new Conditional(this.getRandomValue(), random.nextBoolean());
		} else {
			return new Node(this.getRandomVariable(), this.getRandomOperator(), this.getRandomValue(),
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
			String newVar = "var" + random.nextInt();
			addVar(newVar);
			return newVar;
		}
	}

	public void addVar(String var) {
		if (!vars.contains(var)) {
			vars.add(var);
		}
	}
}
