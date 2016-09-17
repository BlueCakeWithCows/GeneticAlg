package components;

import java.util.List;
import java.util.Random;

public abstract class Selector {

	protected Random random;
	protected BreedingSummary summary;

	public Selector() {

	}

	public abstract Object[][][] selectBreedingPairs(List orderedList);

	public void setRandom(Random random) {
		this.random = random;
	}

	public void setBreedingSummary(BreedingSummary random) {
		this.summary = random;
	}
}
