package components;

import java.util.List;
import java.util.Random;

public abstract class Selector {

	protected Random random;

	public Selector(Random random) {
		this.random = random;
	}

	public abstract Object[][] selectBreedingPairs(List orderedList, BreedingSummary summary);

}
