package genetic.components;

import java.util.List;
import java.util.Random;

public abstract class Selector<T> {
	protected Random random;
	protected BreedingSummary summary;

	public Selector() {
	}

	public abstract T[][][] selectBreedingPairs(List<T> orderedList);

	public void setRandom(Random random) {
		this.random = random;
	}

	public void setBreedingSummary(BreedingSummary random) {
		this.summary = random;
	}
}
