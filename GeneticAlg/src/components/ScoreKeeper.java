package components;

import java.security.SecureRandom;
import java.util.List;

import operationdistance.AdvancedDivisorAlgorithm;
import operationdistance.SimpleDivisorAlgorithm;

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
		tree.tree.operationDistance = 0;
		for (TestCase c : cases) {
			Double[] results = tree.tree.execute(c.input);
			tree.tempScores = new double[c.out.length];
			tree.normaled = new boolean[c.out.length];
			SimpleDivisorAlgorithm alg1 = new SimpleDivisorAlgorithm();
			AdvancedDivisorAlgorithm alg2 = new AdvancedDivisorAlgorithm();

			alg1.setArgs(tree.tree.getValuesAsSet());
			alg2.setArgs(tree.tree.getValuesAsSet());
			for (int i = 0; i < c.out.length; i++) {
				tree.normaled[i] = true;

				if (c.out[i] == null && results[i] == null) {
					tree.tempScores[i] = 0;
				} else if (c.out[i] == null && results[i] != null) {
					tree.tempScores[i] = aboveThisNoScorePoints;
					tree.tree.operationDistance +=30;
					
				} else if (results[i] == null) {
					tree.tempScores[i] = aboveThisNoScorePoints;
					tree.tree.operationDistance +=30;
				} else if (Double.isInfinite(results[i]) || Double.isNaN(results[i])) {
					tree.tempScores[i] = aboveThisNoScorePoints;
					tree.tree.operationDistance +=30;
				} else {
					double error = Math.abs(results[i] - c.out[i]);
					if (error < .01)
						error = 0;
					error = Math.min(this.aboveThisNoScorePoints, error);
					error = Math.max(error, 0);
					double scaledForm = (error) / (this.aboveThisNoScorePoints);
					tree.tempScores[i] = scaledForm;

					if (error != 0) {
						alg1.setTarget(Math.abs(c.out[i].intValue()));
						alg2.setTarget(Math.abs(c.out[i].intValue()));
						alg1.setDebug(true);
						tree.tree.operationDistance += Math.min(alg1.run(), alg2.run());
					}
				}
				tree.runningScore += tree.tempScores[i];
			}

			for (int i = 0; i < c.out.length; i++) {
				tree.tree.totalTests += 1;
				if (tree.tempScores[i] != 0) {
					tree.tree.failedTests += 1;

				}
			}
		}
		double tests = tree.tree.totalTests;
		// Each point correlates to 1/1000 of an error
		// So multiply error by 100
		// EH fuck it. Realize score is meaningless
		double multipliedScore = tree.runningScore * 100d;
		// Divide the score by the number of cases AND the scale
		tree.tree.score = (scale - tree.runningScore) * 100d / (scale);
		tree.tree.operationDistance = tree.tree.operationDistance / tests;
		tree.tree.score = tree.tree.score;
	}
}
