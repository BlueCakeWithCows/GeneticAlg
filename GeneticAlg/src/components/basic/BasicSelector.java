package components.basic;

import java.util.List;
import java.util.Random;

import components.BreedingSummary;
import components.Selector;

public class BasicSelector extends Selector {

	public BasicSelector(Random random) {
		super(random);

	}

	@Override
	public Object[][] selectBreedingPairs(List orderedList, BreedingSummary summary) {
		Object[][] objects = new Object[summary.pairsToSelect][summary.numberPerPairToSelect];

		double insideRootStuff = 1 + 4 * orderedList.size() * orderedList.size() + 4 * orderedList.size();
		double outsideRootStuff = 2 * orderedList.size() + 1;
		int weightedIndexMax = orderedList.size() / 2 * orderedList.size();
		
		for (int i = 0; i < orderedList.size(); i++) {
			for (int pair = 0; pair < summary.numberPerPairToSelect; pair++) {
				int weightedIndex = random.nextInt(weightedIndexMax);
				int index = (int) ((outsideRootStuff - Math.sqrt(insideRootStuff) - 8 * weightedIndex) / 2d); // Some
																												// random
																												// ass
																												// forumula
				objects[i][pair] = orderedList.get(index);

			}
		}

		return objects;

	}
}
