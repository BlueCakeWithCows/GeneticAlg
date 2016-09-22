package components.mathsolver;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import components.MutationHelper;
import components.basic.Node;
import components.basic.Tree;
import components.math.Operator;

public class TreeBuilder {

	private int minSize = 1;
	private int maxSize = 100;
	private int numberOfInputs = 1;;
	private int numberOfOutputs = 1;
	private int populationSize = 200;
	private List<Operator> operatorSet;
	private List<Node> nodeSet;
	private Random random;

	public TreeBuilder(Random random) {
		this.random = random;
	}

	public TreeBuilder() {

	}

	public void setPopulation(int population) {
		this.populationSize = population;
	}

	public void setMinTreeSize(int size) {
		this.maxSize = size;
	}

	public void setMaxTreeSize(int size) {
		this.minSize = size;
	}

	public void setOperatorSet(List<Operator> set) {
		this.operatorSet = set;
	}

	public void setNodeSet(List<Node> set) {
		this.nodeSet = set;
	}

	public List<Tree> generateTrees() {
		List<Tree> trees = new ArrayList<Tree>(populationSize);
		for (int i = 0; i < populationSize; i++) {
			trees.add(generateTree(random.nextInt(this.minSize) + this.maxSize));
		}
		return trees;
	}

	private Tree generateTree(int size) {
		Tree tree = new Tree(numberOfInputs, numberOfOutputs);
		MutationHelper helper = new MutationHelper(random, tree, this.operatorSet, this.nodeSet);
		for (int i = 0; i < size; i++) {
			int r = random.nextInt(tree.getPointSize() + 1);
			Node n = helper.getNewNode();
			tree.addPoint(n, r);
		}
		return tree;
	}

	public void setRandom(Random random2) {
		this.random = random2;
	}

	public void setNumberOfInputs(int inputSpace) {
		this.numberOfInputs = inputSpace;
	}

	public void setNumberOfOutputs(int outputSpace) {
		this.numberOfOutputs = outputSpace;
	}
}
