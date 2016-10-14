package genetic.components;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import genetic.components.basic.Node;
import genetic.components.basic.Tree;
import genetic.components.math.Operator;

public class Breeder {

	private Random rand;
	private BreedingSummary summary;
	private List<Operator> operatorSet;
	
	public Breeder() {
	}

	public List<Tree> simpleBreed(Tree[][] breedingList) {
		int pairs = summary.numberPerPairToSelect;
		List<Tree> newList = new ArrayList<Tree>();

		for (Tree[] breedingPair : breedingList) {

			int maxLength = 0;
			for (Tree bPair : breedingPair) {
				if (bPair.simpleSize() > maxLength) {
					maxLength = bPair.simpleSize();
				}
			}

			Tree newTree = new Tree(breedingPair[0].inputSize, breedingPair[0].outputSize);
			MutationHelper helper = new MutationHelper(rand, newTree, operatorSet);
			for (int i = 0; i < maxLength; i++) {
				int randomIndex = rand.nextInt(pairs);
				Node p = breedingPair[randomIndex].getPoint(i);

				if (p != null) {
					p = p.getCopy();
					for (int index = 0; index < p.values.length; index++) {
						if (!helper.hasValue(p.values[index])) {
							p.values[index] = helper.getRandomValue();
						}
					}

					for (int index = 0; index < p.variables.length; index++) {
						if (!helper.hasVariable(p.variables[index])) {
							p.variables[index] = helper.getRandomVariable();
						}
					}

					newTree.addPoint(p);
				}
			}

			newList.add(newTree);

		}
		return newList;

	}
	
	public void setOperatorSet(List<Operator> lidy){
		this.operatorSet = lidy;
	}

	public void setRandom(Random random) {
		this.rand = random;
	}

	public void setBreedingSummary(BreedingSummary random) {
		this.summary = random;
	}
}
