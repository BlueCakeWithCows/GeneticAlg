package core;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import components.Breeder;
import components.BreedingSummary;
import components.MutationHelper;
import components.Mutator;
import components.ScoreKeeper;
import components.Selector;
import components.TestCase;
import components.basic.BasicSelector;
import components.mathsolver.Tree;

public class Manager {

	public int getRunningTime() {
		return (int) this.totalRunTime;
	}

	public int getGeneration() {
		return generation;
	}

	public List<Tree> getTop(int number) {
		return children.subList(0, number - 1);
	}

	public Tree getBest() {
		return children.get(0);
	}

	public void doGeneration() {
		long time = System.currentTimeMillis();
		if (!scored)
			scorePopulation();
		Tree[][][] obs = (Tree[][][]) selector.selectBreedingPairs(children);
		List<Tree> newList = breeder.simpleBreed(obs[0]);
		newList.addAll(shaveExcess(obs[1]));

		children.subList(elite, children.size()).clear();
		newList = mutator.mutate(newList);
		children.addAll(newList);

		scorePopulation();
		this.totalRunTime += (System.currentTimeMillis() - time) / 1000d;
		generation++;
	}

	public List<Tree> shaveExcess(Tree[][] trees) {
		List<Tree> newList = new ArrayList<Tree>(trees.length);
		for (Tree[] t : trees) {
			newList.add(t[0]);
		}
		return newList;
	}

	public void scorePopulation() {
		scored = true;
		children = scorer.parallelScore(children, numberOfThreads);
	}

	double topPercent;

	public void shavePopulationToTopAndMutate() {
		int lastIndex = (int) (children.size() * topPercent);
		children = children.subList(0, lastIndex);
		int i = 0;
		while (children.size() < population) {
			i = i % lastIndex;
			Tree tree = mutator.mutateTree(children.get(i));
			if (tree.getSize() < this.maxSize) {
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

	public Manager(Settings settings, List<TestCase> cases) {
		this.applySettings(settings);
		children = new ArrayList<Tree>();
		this.inputSpace = cases.get(0).input.length;
		this.outputSpace = cases.get(0).out.length;
		this.scorer.setTestSuite(cases);
	}

	private long seed;

	private void applySettings(Settings settings) {
		if (random != null || this.seed != settings.getSeed()
				|| ((random instanceof SecureRandom) != settings.getUseSecureRandom())) {
			seed = settings.getSeed();
			if (settings.getUseSecureRandom())
				random = new SecureRandom();
			else
				random = new Random();
			random.setSeed(seed);
		}

		population = settings.getPopulation();
		maxSize = settings.getMaxTreeSize();
		maxInitPop = settings.getMaxInitPop();
		minInitPop = settings.getMinInitPop();
		topPercent = settings.getTopPercent();
		numberOfThreads = settings.getNumberOfThreads();
		elite = settings.getElite();
		
		if (mutator == null)
			mutator = new Mutator();
		mutator.setMutationChance(settings.getMutationChance());
		mutator.setRandom(random);

		if (scorer == null)
			scorer = new ScoreKeeper();
		scorer.setRandom(random);
		scorer.setQuickRandom(settings.getQuickRandomTestSelection());
		scorer.setUsableTestSuitePercent(settings.getTestDataToUse());
		scorer.setError(settings.getErrorMargin());
		scorer.setNoPointMark(settings.getNoPointsForErrorAbove());
		scorer.setUseFailedAsPrimary(settings.getUseFailedTestsPrimaryScoring());
		
		if (breedingSummary == null)
			breedingSummary = new BreedingSummary();
		breedingSummary.elite = settings.getElite();
		breedingSummary.numberPerPairToSelect = settings.getNumberOfParents();
		breedingSummary.percentMutateOnly = settings.getPercentToMutateOnly();
		breedingSummary.percentMixBreed = settings.getSimpleMixBreedPercent();
		breedingSummary.population = this.population;
		breedingSummary.recalc();

		if (breeder == null)
			breeder = new Breeder();
		breeder.setRandom(random);
		breeder.setBreedingSummary(breedingSummary);

		if (selector == null)
			selector = new BasicSelector();
		selector.setRandom(random);
		selector.setBreedingSummary(breedingSummary);
	}

	private int generation;
	private boolean scored = false;

	private Random random;
	private Mutator mutator;
	private ScoreKeeper scorer;
	private Breeder breeder;
	private BasicSelector selector;
	private BreedingSummary breedingSummary;
	private List<Tree> children;
	private int inputSpace, outputSpace;
	private int population;
	private int maxSize;
	private int numberOfThreads;
	private int elite;
	private double totalRunTime;
}
