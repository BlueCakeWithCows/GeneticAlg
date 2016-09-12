package components;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ScoreKeeper extends Scorer {

	public ScoreKeeper(List<TestCase> cases, double testDataToUse, SecureRandom r) {
		super(cases, testDataToUse, r);
	}

	public ScoreKeeper() {
		
	}
	

	public double scale = 10000d;
	public double errorMargin = .001d;

	@Override
	protected List<ScoredTree> score(List<Tree> children, List<TestCase> cases) {
		List<ScoredTree> sTree = new ArrayList<ScoredTree>();
		for (Tree tree : children) {
			tree.failedTests = 0;
			tree.totalTests = 0;
			sTree.add(new ScoredTree(tree));

		}
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

		for (ScoredTree tweat : sTree) {
			tweat.tree.score = tweat.runningScore * 100d / scale / cases.size();
		}
		return sTree;
	}

}
