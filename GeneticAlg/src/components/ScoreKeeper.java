package components;

import java.security.SecureRandom;
import java.util.List;

public class ScoreKeeper extends Scorer {

	public ScoreKeeper(List<TestCase> cases, double testDataToUse, SecureRandom r) {
		super(cases, testDataToUse, r);
	}

	public ScoreKeeper() {

	}

	public double scale = 10000d;
	


	@Override
	public void scoreTree(List<TestCase> cases, ScoredTree tree) {
		for (TestCase c : cases) {
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
				}
			}
			tree.tree.totalTests++;
			for (int i = 0; i < c.out.length; i++) {
				if (!tree.normaled[i] && tree.tempScores[i] > errorMargin) {
					tree.tree.failedTests += 1;
				} else if (tree.normaled[i] && tree.tempScores[i] != scale) {
					tree.tree.failedTests += 1;
				}
			}

			for (int i = 0; i < c.out.length; i++) {
				if (!tree.normaled[i])
					if (aboveThisNoScorePoints + lowest == 0)
						tree.tempScores[i] = 0;
					else {

						double bottom = scale
								- (tree.tempScores[i] - lowest) / ((lowest + aboveThisNoScorePoints) / scale);

						tree.tempScores[i] = bottom;
					}
				tree.runningScore += tree.tempScores[i] / (double) tree.tempScores.length;
			}
			tree.tree.score = tree.runningScore * 100d / scale / cases.size();
		}
	}




}
