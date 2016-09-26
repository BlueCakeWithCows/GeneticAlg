package components;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import components.basic.Tree;

public abstract class Scorer {

	private List<TestCase> totalSetOfTests;
	private double percentOfTestsToUseEachRun;
	private boolean cheatRandomOnDataPercent;
	private Comparator<ScoredTree> comparator;
	private Random random;

	public Scorer(List<TestCase> cases, double testDataToUse, Random r) {
		this.totalSetOfTests = cases;
		percentOfTestsToUseEachRun = testDataToUse;
		this.random = r;
		this.cheatRandomOnDataPercent = false;
	}

	public Scorer() {

	}

	public abstract void scoreTree(List<TestCase> cases, ScoredTree tree);

	protected List<ScoredTree> score(List<Tree> children, List<TestCase> cases) {
		List<ScoredTree> sTree = new ArrayList<ScoredTree>();
		for (Tree tree : children) {
			tree.failedTests = 0;
			tree.totalTests = 0;
			ScoredTree STree = new ScoredTree(tree);
			sTree.add(STree);
		}

		for (ScoredTree ttt : sTree) {
			this.scoreTree(cases, ttt);
		}

		return sTree;
	}

	private List<TestCase> getTestingSet() {
		// If its one don't bother
		if (percentOfTestsToUseEachRun == 1)
			return totalSetOfTests;
		List<TestCase> cases;

		// Cheat by using range rather than random ones
		if (cheatRandomOnDataPercent) {
			double start = random.nextDouble();
			double end = (start + percentOfTestsToUseEachRun);
			if (end < 1) {
				return totalSetOfTests.subList((int) (start * totalSetOfTests.size()),
						(int) (end * totalSetOfTests.size()));
			} else {
				int index1 = (int) (start * totalSetOfTests.size());
				int index2 = (int) ((end - 1) * totalSetOfTests.size());

				cases = totalSetOfTests.subList(0, index2);
				cases.addAll(totalSetOfTests.subList(index1, totalSetOfTests.size()));
				return cases;
			}
		}

		// Pick randomly, repeats allowed
		cases = new ArrayList<TestCase>();
		for (int i = 0; i < totalSetOfTests.size() * percentOfTestsToUseEachRun; i++) {
			cases.add(totalSetOfTests.get(random.nextInt(totalSetOfTests.size())));
		}
		return cases;

	}

	public void setRandom(Random random) {
		this.random = random;
	}

	public void setTestSuite(List<TestCase> cases) {
		this.totalSetOfTests = cases;
	}

	public void setUsableTestSuitePercent(double percent) {
		this.percentOfTestsToUseEachRun = percent;
	}

	public void setQuickRandom(boolean cheat) {
		this.cheatRandomOnDataPercent = cheat;
	}

	public List<Tree> parallelScore(List<Tree> children, int numberOfThreads) {
		List<TestCase> cases = this.getTestingSet();

		List<Tree> needToBeScored = new ArrayList<Tree>();
		for (Tree tree : children) {
			if (!tree.scored)
				needToBeScored.add(tree);
			tree.scored = true;
		}
		children.removeAll(needToBeScored);
		ParallelScorer[] threads = new ParallelScorer[numberOfThreads];
		int min = 0, max = 0;
		int delta = (needToBeScored.size()) / numberOfThreads;
		int i2 = 0;
		for (; i2 < numberOfThreads - 1; i2++) {
			max = min + delta;
			List<Tree> subList = needToBeScored.subList(min, max);
			threads[i2] = new ParallelScorer(subList, cases);
			threads[i2].start();
			min += delta;
		}
		List<Tree> subList = needToBeScored.subList(min, needToBeScored.size());
		threads[i2] = new ParallelScorer(subList, cases);
		threads[i2].start();

		List<ScoredTree> newList = new ArrayList<ScoredTree>();
		for (int i = 0; i < numberOfThreads; i++) {
			try {

				threads[i].join();
				newList.addAll(threads[i].newTrees);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		for (Tree child : children) {
			ScoredTree sc = new ScoredTree(child);
			newList.add(sc);
		}

		sort(newList);
		List<Tree> finalList = new ArrayList<Tree>();

		for (ScoredTree tree : newList) {
			finalList.add(tree.tree);

		}
		return finalList;
	}

	public void sort(List<ScoredTree> newList) {
		Collections.sort(newList,comparator);
	}
	
	public void setComparator(Comparator<ScoredTree> comparator){
		this.comparator = comparator;
	}

	public class ParallelScorer extends Thread implements Runnable {
		public List<Tree> trees;
		public List<ScoredTree> newTrees;
		public List<TestCase> cases;

		public ParallelScorer(List<Tree> trees, List<TestCase> cases) {
			this.trees = trees;
			this.cases = cases;
		}

		@Override
		public void run() {
			this.newTrees = score(trees, cases);
		}
	}
	public double errorMargin = 0d;
	public double aboveThisNoScorePoints = 100;
	public double lowest = 0d;

	public void setNoPointMark(double d) {
		this.aboveThisNoScorePoints = d;
	}

	public void setError(double errorMargin2) {
		this.errorMargin = errorMargin2;
	}
	public void setMaxTreeSize(int maxTreeSize) {
		// TODO Auto-generated method stub

	}

}
