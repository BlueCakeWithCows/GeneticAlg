package components;

import components.basic.Tree;

public class ScoredTree {
	public ScoredTree(Tree tree) {
		this.tree = tree;
	}

	public boolean[] normaled;

	public Tree tree;
	public double[] tempScores;

	public int getFailedTests() {
		return tree.failedTests;
	}

	public int getLength() {
		return tree.getSize();
	}
	
	public double getScore(){
		return tree.score;
	}

	public int bruteDistance;

	public double runningScore;

	public double getOperatorDistance() {
		return tree.operationDistance;
	}

}
