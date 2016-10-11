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
		tree.tree.failedTests = 0;
		tree.tree.totalTests = 0;
		tree.runningScore = 0;
		tree.tree.score = 0;
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
				} else if (c.out[i] == results[i]) {
					tree.tempScores[i] = scale;
				} else {
			
					tree.tempScores[i] = scale - (Math.abs(c.out[i] - tree.tempScores[i] )) / ((lowest + aboveThisNoScorePoints) / scale);
					System.out.println(tree.tempScores[i]);
				}
				tree.runningScore += tree.tempScores[i] / (double) tree.tempScores.length;
			}

			for (int i = 0; i < c.out.length; i++) {
				tree.tree.totalTests += 1;
				if (tree.tempScores[i] != scale) {
					tree.tree.failedTests += 1;
				}
			}
		}

		tree.tree.score = tree.runningScore * 100d / (scale * cases.size());
	}
}
