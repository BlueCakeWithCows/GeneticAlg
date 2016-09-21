package components.basic;

import java.util.List;
import java.util.Random;

import components.BreedingSummary;
import components.Selector;

public class BasicSelector extends Selector {

	public BasicSelector() {

	}

	@Override
	public Tree[][][] selectBreedingPairs(List orderedList) {
		Tree[][][] grandArray = new Tree[2][][];
		grandArray[0] = new Tree[summary.pairsToSelect][summary.numberPerPairToSelect];
		grandArray[1] = new Tree[summary.singleMutationsToSelect][1];
		long insideRootStuff = 1 + 4 * orderedList.size() * orderedList.size() + 4 * orderedList.size();
		double outsideRootStuff = 2 * orderedList.size() + 1;
		int weightedIndexMax = orderedList.size() / 2 * orderedList.size();
		for (Object[][] typeOfBreeding : grandArray) {
			for (int i = 0; i < typeOfBreeding.length; i++) {
				for (int pair = 0; pair < typeOfBreeding[i].length; pair++) {
					int weightedIndex = random.nextInt(weightedIndexMax);
					weightedIndex *= 8;
					int index = (int) ((outsideRootStuff - Math.sqrt(insideRootStuff - weightedIndex)) / 2d); // forumula
	
					typeOfBreeding[i][pair] = orderedList.get(index);
				}
			}
		}

		return grandArray;

	}
}
