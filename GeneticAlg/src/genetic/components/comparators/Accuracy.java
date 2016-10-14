package genetic.components.comparators;

import genetic.components.MyComparator;
import genetic.components.ScoredTree;

public class Accuracy extends MyComparator{

	
	
	@Override
	public int compare(ScoredTree o1, ScoredTree o2) {
		//Higher is better so flip
		return Double.compare(o2.getScore(), o1.getScore());
		
	}
}
