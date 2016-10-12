package testing;

import java.util.Arrays;

import components.ScoreKeeper;
import components.ScoredTree;
import components.TestCase;
import components.basic.Assignment;
import components.basic.Tree;
import components.math.ArithmaticOperators;
import components.math.TrueFunction;

public class TreeTest {
	private Tree tree;

	public TreeTest() {
		tree = new Tree(2, 1);
		Assignment additionAssingment=new Assignment();
		additionAssingment.functions[0] = new TrueFunction(new ArithmaticOperators.Multiplication());
		additionAssingment.variables[0] = "out0";
		additionAssingment.functions[0].values[0] ="in0";
		additionAssingment.functions[0].values[1] ="in1";
		tree.addPoint(additionAssingment);
		Assignment abb=new Assignment();
		abb.functions[0] = new TrueFunction(new ArithmaticOperators.Addition());
		abb.variables[0] = "out0";
		abb.functions[0].values[0] ="out0";
		abb.functions[0].values[1] ="in1";
		tree.addPoint(abb);
	}

	public void run() {
		System.out.println(tree);
		System.out.println(Arrays.toString(tree.execute(new double[]{2,5})));
		TestCase cas = new TestCase();
		cas.input = new double[]{2,5};
		cas.out = new Double[]{15d};
		ScoreKeeper scorer = new ScoreKeeper();
		scorer.aboveThisNoScorePoints=100;
		ScoredTree sT = new ScoredTree(tree);
		scorer.scoreTree(Arrays.asList(new TestCase[]{cas}), sT);
		
		System.out.println("Score:" +sT.tree.score);
	}

	public static void main(String[] args) {
		TreeTest tree = new TreeTest();
		tree.run();
	}
}
