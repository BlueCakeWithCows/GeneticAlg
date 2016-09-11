package components;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ScoreKeeper {

	public List<TestCase> cases;
	public double percentToUse;
	public SecureRandom random;

	public ScoreKeeper(List<TestCase> cases, double testDataToUse, SecureRandom r) {
		this.cases = cases;
		percentToUse = testDataToUse;
		this.random = r;
	}

	public double scale = 10000d;
	public double errorMargin = .001d;

	public List<TestCase> getThisRoundTest() {
		if (percentToUse == 1)
			return cases;
		List<TestCase> c = new ArrayList<TestCase>();
		for (int i = 0; i < cases.size() * percentToUse; i++) {
			c.add(cases.get(random.nextInt(cases.size())));
		}
		return c;

	}

	public List<Tree> score(List<Tree> children) {
		List<ScoredTree> sTree = new ArrayList<ScoredTree>();
		for (Tree tree : children) {
			tree.failedTests = 0;
			tree.totalTests = 0;
			sTree.add(new ScoredTree(tree));

		}
		List<TestCase> cases = getThisRoundTest();

		for (TestCase c : cases) {

			double[] highest = new double[c.out.length], lowest = new double[c.out.length];
			for (ScoredTree tree : sTree) {

				Double[] results = tree.tree.execute(c.input);
				tree.tempScores = new double[c.out.length];
				tree.normaled = new boolean[c.out.length];

				for (int i = 0; i < c.out.length; i++) {
					tree.normaled[i] = true;
					if (c.out[i] == null && results[i] == null) {
						tree.tempScores[i] = scale;
					} else if (c.out[i] == null && results[i] != null) {
						tree.tempScores[i] = 0;
					} else if (results[i] == null) {
						tree.tempScores[i] = 0;
					} else if (Double.isInfinite(results[i]) || Double.isNaN(results[i])) {
						tree.tempScores[i] = 0;
					} else {
						tree.normaled[i] = false;

						tree.tempScores[i] = Math.abs(c.out[i] - results[i]);
						if (tree.tempScores[i] > highest[i])
							highest[i] = tree.tempScores[i];
						if (tree.tempScores[i] < lowest[i]) {
							lowest[i] = tree.tempScores[i];
						}

					}
					tree.tree.totalTests++;
					if (!tree.normaled[i] && tree.tempScores[i] > errorMargin) {
						tree.tree.failedTests += 1;
					} else if (tree.normaled[i] && tree.tempScores[i] != scale) {
						tree.tree.failedTests += 1;
					}

				}
			}
			for (ScoredTree tree : sTree) {
				for (int i = 0; i < c.out.length; i++) {
					if (!tree.normaled[i])
						if (lowest[i] + highest[i] == 0)
							tree.tempScores[i] = 0;
						else {

							double bottom = scale
									- (tree.tempScores[i] - lowest[i]) / ((lowest[i] + highest[i]) / scale);

							tree.tempScores[i] = bottom;
						}
					tree.runningScore += tree.tempScores[i] / (double) tree.tempScores.length;
				}

			}
		}

		try {
			Collections.sort(sTree, new Comparator<ScoredTree>() {
				@Override
				public int compare(ScoredTree o2, ScoredTree o1) {
					int res = Integer.compare(o2.tree.failedTests, o1.tree.failedTests);
					if (res == 0)
						res = Double.compare(o1.runningScore, o2.runningScore);
					if (res == 0)
						res = Integer.compare(o2.tree.getSize(), o1.tree.getSize());
					return res;
				}
			});
		} catch (Exception e) {
			
			e.printStackTrace();
		}

		List<Tree> newList = new ArrayList<Tree>();
		for (ScoredTree tweat : sTree) {
			tweat.tree.score = tweat.runningScore * 100d / scale / cases.size();
			newList.add(tweat.tree);
		}
		return newList;
	}

	private class ScoredTree {
		public ScoredTree(Tree tree) {
			this.tree = tree;
		}

		public boolean[] normaled;

		Tree tree;
		double[] tempScores;
		double runningScore = 0;
	}
}
