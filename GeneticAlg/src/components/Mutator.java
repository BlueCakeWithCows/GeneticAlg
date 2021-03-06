package components;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.Stack;

import components.mathsolver.Conditional;
import components.mathsolver.Function;
import components.mathsolver.Node;
import components.mathsolver.Tree;

public class Mutator {
	private Random random;
	private double mutChance;
	private MutationHelper helper;

	public Mutator(SecureRandom random, double mutChance) {
		this.random = random;
		this.mutChance = mutChance;

	}

	public void setMutationChance(double d) {
		this.mutChance = d;
	}

	public void setRandom(Random random) {
		this.random = random;
	}

	public Mutator() {
	}

	public List<Tree> mutate(List<Tree> newList2) {
		List<Tree> newList = new ArrayList<Tree>();
		for (Tree tree : newList2) {
			newList.add(mutateTree(tree));
		}

		return newList;
	}

	public Tree mutateTree(Tree oldTree) {
		helper = new MutationHelper(random, oldTree.getDefaultVariables(), oldTree.getDefaultNames());

		Tree tree = new Tree(oldTree.inputSize, oldTree.outputSize);

		List<Node> points = mutateList(oldTree.getPoints());

		tree.setPoints(points);

		return tree;

	}

	public Node mutatePoint(Node oPoint) {
		Node newNode = null;
		if (oPoint instanceof Function) {
			newNode = new Function((Function) oPoint);
			if (m())
				((Function) newNode).operator = helper.getRandomOperator();
		} else if (oPoint instanceof Conditional) {
			newNode = new Conditional((Conditional) oPoint);
		}

		for (int i = 0; i < newNode.values.length; i++) {
			if (m())
				newNode.values[i] = helper.getRandomValue();
		}
		for (int i = 0; i < newNode.variables.length; i++) {
			if (m())
				newNode.variables[i] = helper.getRandomVariable();
		}
		return newNode;
	}

	private List<Node> mutateList(List<Node> points) {

		List<Node> copy = new ArrayList<Node>();
		Stack<Integer> toAdd = new Stack<Integer>();
		Stack<Integer> toKill = new Stack<Integer>();

		int counter = 0;
		for (Node p : points) {
			copy.add(mutatePoint(p));
			if (m())
				toAdd.add(counter);
			if (m())
				toKill.add(counter);
			counter++;

		}
		for (int i = 0; i < toAdd.size(); i++) {
			Integer val = toAdd.pop();
			Node point = helper.getNewPoint();
			copy.add(val, point);
		}
		for (int i = 0; i < toKill.size(); i++) {
			int lol = (int) toKill.pop();
			copy.remove(lol);
		}
		return copy;
	}

	public boolean m() {
		if (random.nextFloat() < mutChance)
			return true;
		else
			return false;
	}
}
