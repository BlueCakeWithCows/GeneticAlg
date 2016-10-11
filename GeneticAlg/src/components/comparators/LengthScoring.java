package components.comparators;

import components.MyComparator;
import components.ScoredTree;

public class LengthScoring extends MyComparator{

	@Override
	public int compare(ScoredTree o1, ScoredTree o2) {
		return Integer.compare(o1.getLength(), o2.getLength());
	}
}
