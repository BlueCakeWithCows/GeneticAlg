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

		int maxSize = orderedList.size() / 2 * orderedList.size();

		for (int i = 0; i < orderedList.size(); i++) {
			for(int pair =0;pair < summary.numberPerPairToSelect; pair++){
				int index = random.nextInt(maxSize);
				index = 0; //Some random ass forumula		
				objects[i][pair] = orderedList.get(index);
				
			}
		}

		return objects;

	}
}
