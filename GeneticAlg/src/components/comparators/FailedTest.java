package components.comparators;

import components.MyComparator;
import components.ScoredTree;

public class FailedTest extends MyComparator{

	@Override
	public int compare(ScoredTree o1, ScoredTree o2) {
		return Integer.compare(o1.getFailedTests(), o2.getFailedTests());
	}
}
