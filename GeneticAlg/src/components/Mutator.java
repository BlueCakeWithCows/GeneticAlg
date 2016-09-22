package components;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.Stack;

import components.basic.Conditional;
import components.basic.Node;
import components.basic.Tree;
import components.math.Operator;

public class Mutator {
	private Random random;
	private double mutChance;
	private MutationHelper helper;
	private List<Operator> operators;
	private List<Node> nodes;
	
	public Mutator(SecureRandom random, double mutChance) {
		this.random = random;
		this.mutChance = mutChance;

	}
	
	public void setOperatorSet(List<Operator> operators){
		this.operators = operators;
	}
	public void setNodeSet(List<Node> nodes){
		this.nodes = nodes;
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
		helper = new MutationHelper(random, oldTree.getDefaultVariables(), oldTree.getDefaultNames(),operators,nodes);

		Tree tree = new Tree(oldTree.inputSize, oldTree.outputSize);

		List<Node> points = mutateList(oldTree.getPoints());

		tree.setPoints(points);

		return tree;

	}

	public Node mutatePoint(Node oPoint) {
		Node newNode = oPoint.getCopy();

		for (int i = 0; i < newNode.functions.length; i++) {
			if (m())
				newNode.functions[i] = helper.getRandomFunction();
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
			Node point = helper.getNewNode();
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
