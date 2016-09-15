package components;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import components.mathsolver.Node;
import components.mathsolver.Point;
import components.mathsolver.Tree;

public class Breeder {

	private MutationHelper helper;
	private Random rand;

	public Breeder(Random random) {
		rand = random;
	}

	public List<Tree> simpleBreed(BreedingSummary summary, Tree[][] breedingList) {
		int pairs = summary.pairsToSelect;
		List<Tree> newList = new ArrayList<Tree>();

		for (Tree[] breedingPair : breedingList) {

			int maxLength = 0;
			for (Tree bPair : breedingPair) {
				if (bPair.simpleSize() > maxLength) {
					maxLength = bPair.simpleSize();
				}
			}

			Tree newTree = new Tree(breedingPair[0].inputSize, breedingPair[0].outputSize);
			MutationHelper helper = new MutationHelper(rand, newTree);
			for (int i = 0; i < maxLength; i++) {
				int randomIndex = rand.nextInt(pairs);

				Point p = breedingPair[randomIndex].getPoint(i);

				if (p != null){
					
					if(p instanceof Node){
						if(!helper.hasValue(((Node) p).val1)){
							((Node) p).val1 = helper.getRandomValue();
						
						if(!helper.hasValue(((Node) p).val2)){
							((Node) p).val2 = helper.getRandomValue();	
													
					}
						
						if(p instanceof Conditional){
							if(!helper.hasValue(((Node) p).val1)){
								((Node) p).val1 = helper.getRandomValue();
							
							if(!helper.hasValue(((Node) p).val2)){
								((Node) p).val2 = helper.getRandomValue();	
														
						}
					
					
					
					
					newTree.addPoint(p);
				}
			}

			

			newList.add(newTree);

		}

		return newList;

	}
}
