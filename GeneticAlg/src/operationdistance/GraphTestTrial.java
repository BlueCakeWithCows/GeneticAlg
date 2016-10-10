package operationdistance;

import java.security.SecureRandom;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;

public class GraphTestTrial {
	private static Random r = new SecureRandom();

	public static void main(String[] args) {
		int goal;
		HashSet<Integer> set;
		Algorithm algorithm;
		double advancedTime = 0, simpleTime = 0;
		double advancedScore = 0, simpleScore = 0;
		double advancedLower = 0;
		set = new HashSet<Integer>();
		set.addAll(Arrays.asList(new Integer[]{0,1,2,4,5,9}));

		int trials = 50000;
		for (int i = 0; i < trials; i+=10) {
			algorithm = new AdvancedDivisorAlgorithm();
			goal = i;

			double[] ad = testAlg(algorithm, goal, set);
			System.out.println(i +","+ad[1]);

		}
	}

	private static double[] testAlg(Algorithm simple, int goal, HashSet<Integer> set) {
		long t = System.currentTimeMillis();
		simple.setTarget(goal);
		simple.setArgs(set);
		int a = simple.run();
		return new double[] { (double) (System.currentTimeMillis() - t) / 1000d, a };
	}
}
