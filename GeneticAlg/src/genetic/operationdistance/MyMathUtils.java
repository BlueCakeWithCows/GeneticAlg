package genetic.operationdistance;

import java.util.List;
import java.util.Set;

public class MyMathUtils {

	public static int getGreatestDivisor(int y, Set<Integer> args) {
		int greatestDivisor = 1;
		for (Integer i : args) {

			if (i != 0) {
				int c = y / i;
				if (c > 0 && i > greatestDivisor)
					greatestDivisor = i;
			}
		}

		return greatestDivisor;
	}

	public static int[] getDivisorAndNearestMultiple(int y, Set<Integer> args) {
		int n[] = { 1, y, 0 };
		int steps = 3;
		float distance = Float.MAX_VALUE;
		for (Integer i : args) {
			if (i != 0 && i != 1 && i !=-1) {
				int c = Math.round((float) y / (float) i);

				int d = y - c * i;
				int absD = Math.abs(d);
				int ops = 2;
				if (args.contains(absD))
					ops--;
				if (args.contains(c))
					ops--;
				
				if (absD < distance || (absD == distance && ops < steps) || (absD==distance &&ops==steps &&i > n[0])) {

					n[0] = i;
					n[1] = c;
					n[2] = absD;
					distance = absD;
					steps = ops;
				}
			}
		}

		return n;
	}
}
