package components.comparators;

import components.MyComparator;
import components.ScoredTree;

public class OperatorDistance extends MyComparator{

	@Override
	public int compare(ScoredTree o1, ScoredTree o2) {
		return Double.compare(o1.getOperatorDistance(), o2.getOperatorDistance());
	}
}
