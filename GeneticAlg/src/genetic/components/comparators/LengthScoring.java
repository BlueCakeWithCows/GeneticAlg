package genetic.components.comparators;

import genetic.components.MyComparator;
import genetic.components.ScoredTree;

public class LengthScoring extends MyComparator{

	@Override
	public int compare(ScoredTree o1, ScoredTree o2) {
		return Integer.compare(o1.getLength(), o2.getLength());
	}
}
