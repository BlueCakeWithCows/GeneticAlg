package components.comparators;

import java.util.Comparator;

import components.ScoredTree;

public class ScoreFailLength implements Comparator<ScoredTree> {

	@Override
	public int compare(ScoredTree o1, ScoredTree o2) {
		int res = 0;
		res = Double.compare(o1.bruteDistance, o2.bruteDistance);
		if (res == 0)
			res = Double.compare(o2.getScore(), o1.getScore());
		if (res == 0)
			res = Integer.compare(o1.getFailedTests(), o2.getFailedTests());
		if (res == 0)
			res = Integer.compare(o1.getLength(), o2.getLength());
		return res;
	}
}
