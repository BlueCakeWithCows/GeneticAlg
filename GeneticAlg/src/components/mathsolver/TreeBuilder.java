package components.mathsolver;

import java.util.List;
import java.util.Random;

import components.basic.Tree;
import components.math.Operator;

public class TreeBuilder {

	private int minSize = 1;
	private int maxSize = 100;
	private int populationSize = 200;
	private List<Operator> operatorSet;
	private Random random;
	
	public TreeBuilder(Random random) {
		this.random = new Random();
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

	public List<Tree> generateTrees() {
		return null;

	}
}
