package genetic.operationdistance;

import java.security.SecureRandom;
import java.util.HashSet;
import java.util.Random;

public class TestTrialClass {
	private static Random r = new SecureRandom();

	public static void main(String[] args) {
		int goal;
		HashSet<Integer> set;
		AdvancedDivisorAlgorithm advanced;
		SimpleDivisorAlgorithm simple;

		double advancedTime = 0, simpleTime = 0;
		double advancedScore = 0, simpleScore = 0;
		double advancedLower=0,simpleLower=0;
		
		int trials = 1000;
		for (int i = 0; i < trials; i++) {
			goal = r.nextInt(1000);
			set = new HashSet<Integer>();
			set.add(0);
			set.add(1);
			set.add(2);
			for (int i2 = 0; i2 < r.nextInt(5); i2++)
				set.add(r.nextInt(500));

			advanced = new AdvancedDivisorAlgorithm();
			double[] ad = testAlg(advanced, goal, set);
			advancedTime += ad[0];
			advancedScore += ad[1];
			simple = new SimpleDivisorAlgorithm();
			double[] si = testAlg(simple, goal, set);
			simpleTime += si[0];
			simpleScore += si[1];
			if(ad[1]<si[1])
				advancedLower++;
			if(ad[1]>si[1])
				simpleLower++;
		}
		System.out.println("[Advanced] Time:" + advancedTime + "|Average:" + advancedScore/trials);
		System.out.println("[Simple] Time:" + simpleTime + "|Average:" + simpleScore/trials);
		double adP = advancedLower/trials;
		double adS = simpleLower/trials;
		double equal = 1 - adP-adS;
		System.out.println("Advanced Percent: " +adP);
		System.out.println("Simple Percent: " +adS);
		System.out.println("Equal: " +equal);
	}
	private static double[] testAlg(Algorithm simple, int goal, HashSet<Integer> set) {
		long t = System.currentTimeMillis();
		simple.setTarget(goal);
		simple.setArgs(set);
		int a = simple.run();
		return new double[] { (double) (System.currentTimeMillis() - t) / 1000d, a };
	}
}
