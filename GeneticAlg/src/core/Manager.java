package core;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

import components.MutationHelper;
import components.Mutator;
import components.ScoreKeeper;
import components.TestCase;
import components.mathsolver.Tree;

public class Manager {

	public int getRunningTime() {
		return (int) this.totalRunTime;
	}

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

		long time = System.currentTimeMillis();

		shavePopulationToTopAndMutate();
		scorePopulation();

		this.totalRunTime += (System.currentTimeMillis() - time) / 1000d;
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
		if (random != null || this.seed != settings.getSeed()) {
			seed = settings.getSeed();
			random = new SecureRandom();
			random.setSeed(seed);
		}

		if (mutator == null)
			mutator = new Mutator();
		mutator.setMutationChance(settings.getMutationChance());
		mutator.setRandom(random);

		if (scorer == null)
			scorer = new ScoreKeeper();
		scorer.setRandom(random);
		scorer.setQuickRandom(settings.getQuickRandomTestSelection());
		scorer.setUsableTestSuitePercent(settings.getTestDataToUse());

		population = settings.getPopulation();
		maxSize = settings.getMaxTreeSize();
		maxInitPop = settings.getMaxInitPop();
		minInitPop = settings.getMinInitPop();
		topPercent = settings.getTopPercent();
		numberOfThreads = settings.getNumberOfThreads();
	}

	private int generation;
	private SecureRandom random;
	private Mutator mutator;
	private ScoreKeeper scorer;
	private List<Tree> children;
	private int inputSpace, outputSpace;
	private int population;
	private int maxSize;
	private int numberOfThreads;

	private double totalRunTime;
}
