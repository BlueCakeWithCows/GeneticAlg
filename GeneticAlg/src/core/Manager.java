package core;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

import components.MutationHelper;
import components.Mutator;
import components.ScoreKeeper;
import components.TestCase;
import components.Tree;

public class Manager {
	private int generation;
	private SecureRandom random;
	private Mutator mutator;
	private ScoreKeeper scorer;
	private List<Tree> children;
	private int inputSpace, outputSpace;
	public int population;

	public int getGeneration() {
		return generation;
	}

	public Manager(SecureRandom random, double mutationChance, List<TestCase> cases, int inSize, int outSize,
			int population, double topPercent) {
		this.random = random;
		this.mutator = new Mutator(random, mutationChance);
		this.scorer = new ScoreKeeper(cases, 1d, random);
		children = new ArrayList<Tree>();
		inputSpace = inSize;
		outputSpace = outSize;
		this.population = population;
		this.topPercent = topPercent;
	}

	// Not yet implemented
	private int maxSize;

	public Manager(SecureRandom secureRandom, double mutationChance, List<TestCase> testCases,
			int population, double topPercent, int minInitPop, int maxInitPop, int maxLength, double TestDataToUse) {
		this.random = secureRandom;
		this.mutator = new Mutator(random, mutationChance);
		this.scorer = new ScoreKeeper(testCases,TestDataToUse,random);
		children = new ArrayList<Tree>();
		inputSpace = testCases.get(0).input.length;
		outputSpace = testCases.get(0).out.length;
		this.population = population;
		this.topPercent = topPercent;
		this.maxInitPop = maxInitPop;
		this.minInitPop = minInitPop;
		this.maxSize = maxLength;
		
	}

	public List<Tree> getBest(int number) {
		return children.subList(0, number);

	}

	public Tree getBest() {
		return children.get(0);

	}

	boolean scored = false;

	public void doGeneration() {
		if (!scored) {
			scored = true;
			scorePopulation();
		}
		shavePopulationToTopAndMutate();
		scorePopulation();
	}

	public void scorePopulation() {
		scored = true;
		children = scorer.score(children);
	}

	double topPercent;

	public void shavePopulationToTopAndMutate() {
		int lastIndex = (int) (children.size() * topPercent);
		children = children.subList(0, lastIndex);
		int i = 0;
		while (children.size() < population) {
			i = i % lastIndex;
			Tree tree = mutator.mutateTree(children.get(i));
			if(tree.getSize() < this.maxSize){
				children.add(tree);
			}
		}
		generation++;
	}

	int minInitPop, maxInitPop;

	public void generatePopulation() {
		children.clear();
		for (int i = 0; i < population; i++) {
			children.add(generateTree(random.nextInt(maxInitPop) + minInitPop));
		}
	}

	private Tree generateTree(int size) {
		Tree tree = new Tree(inputSpace, outputSpace);
		MutationHelper helper = new MutationHelper(random, tree);
		for (int i = 0; i < size; i++) {
			int r = random.nextInt(tree.getPointSize() + 1);
			tree.addPoint(helper.getNewPoint(), r);
		}
		return tree;
	}
}
