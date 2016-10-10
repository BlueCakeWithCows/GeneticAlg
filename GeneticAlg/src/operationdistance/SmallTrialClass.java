package operationdistance;

import java.security.SecureRandom;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;

public class SmallTrialClass {
	private static Random r = new SecureRandom();

	public static void main(String[] args) {
		double advancedTime = 0, simpleTime = 0;
		double advancedScore = 0, simpleScore = 0;
		int goal = 333;
		HashSet<Integer> set = new HashSet<Integer>();
		set.addAll(Arrays.asList(new Integer[]{0,1,2,26,333,108}));

		AdvancedDivisorAlgorithm advanced = new AdvancedDivisorAlgorithm();
		advanced.setDebug(true);
		double[] ad = testAlg(advanced, goal, set);
		advancedTime += ad[0];
		advancedScore += ad[1];
		System.out.println("[Advanced] Time:" + advancedTime + "|Average:" + advancedScore);
		
		SimpleDivisorAlgorithm simple = new SimpleDivisorAlgorithm();
		simple.setDebug(true);
		double[] si = testAlg(simple, goal, set);
		
		simpleTime += si[0];
		simpleScore += si[1];

		System.out.println("[Simple] Time:" + simpleTime + "|Average:" + simpleScore );

	}

	private static double[] testAlg(Algorithm simple, int goal, HashSet<Integer> set) {
		long t = System.currentTimeMillis();
		simple.setTarget(goal);
		simple.setArgs(set);
		int a = simple.run();
		return new double[] { (double) (System.currentTimeMillis() - t) / 1000d, a };
	}
}
