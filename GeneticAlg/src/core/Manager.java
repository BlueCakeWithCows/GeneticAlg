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
import components.basic.Assignment;
import components.basic.BasicSelector;
import components.basic.Conditional;
import components.basic.Node;
import components.basic.Tree;
import components.math.ArithmaticOperators;
import components.math.ConditionalOperators;
import components.math.Operator;
import components.mathsolver.TreeBuilder;

public class Manager {

	public int getRunningTime() {
		return (int) this.totalRunTime;
	}

	public int getGeneration() {
		return generation;
	}

	public List<Tree> getTop(int number) {
		if (number > children.size())
			number = children.size() + 1;
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

	public void generatePopulation() {
		children = builder.generateTrees();
	}

	public Manager(Settings settings, List<TestCase> cases) {
		children = new ArrayList<Tree>();
		this.inputSpace = cases.get(0).input.length;
		this.outputSpace = cases.get(0).out.length;
		this.applySettings(settings);
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

		topPercent = settings.getTopPercent();
		numberOfThreads = settings.getNumberOfThreads();
		elite = settings.getElite();

		if (nodeSet == null)
			nodeSet = new ArrayList<Node>();
		nodeSet.clear();
		nodeSet.add(new Assignment());
		if (settings.getUseConditionalNodes())
			nodeSet.add(new Conditional());
		// nodeSet.add(new Conditional());

		if (operatorSet == null)
			operatorSet = new ArrayList<Operator>();
		operatorSet.clear();
		if (settings.getUseOperatorArithmatic())
			operatorSet.addAll(ArithmaticOperators.getArithmaticOperators());
		if (settings.getUseOperatorConditional())
			operatorSet.addAll(ConditionalOperators.getConditionalOperators());

		if (mutator == null)
			mutator = new Mutator();
		mutator.setMutationChance(settings.getMutationChance());
		mutator.setRandom(random);
		mutator.setOperatorSet(operatorSet);
		mutator.setNodeSet(nodeSet);

		if (scorer == null)
			scorer = new ScoreKeeper();
		scorer.setRandom(random);
		scorer.setQuickRandom(settings.getQuickRandomTestSelection());
		scorer.setUsableTestSuitePercent(settings.getTestDataToUse());
		scorer.setError(settings.getErrorMargin());
		scorer.setNoPointMark(settings.getNoPointsForErrorAbove());
		scorer.setUseFailedAsPrimary(settings.getUseFailedTestsPrimaryScoring());
		scorer.setMaxTreeSize(settings.getMaxTreeSize());

		if (breedingSummary == null)
			breedingSummary = new BreedingSummary();
		breedingSummary.elite = settings.getElite();
		breedingSummary.numberPerPairToSelect = settings.getNumberOfParents();
		breedingSummary.percentMutateOnly = settings.getPercentToMutateOnly();
		breedingSummary.percentMixBreed = settings.getSimpleMixBreedPercent();
		breedingSummary.population = settings.getPopulation();
		breedingSummary.recalc();

		if (breeder == null)
			breeder = new Breeder();
		breeder.setRandom(random);
		breeder.setOperatorSet(operatorSet);
		breeder.setBreedingSummary(breedingSummary);

		if (selector == null)
			selector = new BasicSelector();
		selector.setRandom(random);
		selector.setBreedingSummary(breedingSummary);

		if (builder == null)
			builder = new TreeBuilder();
		builder.setRandom(random);
		builder.setMinTreeSize(settings.getMinInitPop());
		builder.setMaxTreeSize(settings.getMaxInitPop());
		builder.setPopulation(settings.getPopulation());
		builder.setOperatorSet(operatorSet);
		builder.setNodeSet(nodeSet);
		builder.setNumberOfInputs(inputSpace);
		builder.setNumberOfOutputs(outputSpace);

	}

	private int generation;
	private boolean scored = false;

	private Random random;
	private Mutator mutator;
	private ScoreKeeper scorer;
	private Breeder breeder;
	private BasicSelector selector;
	private BreedingSummary breedingSummary;
	private List<Operator> operatorSet;
	private List<Node> nodeSet;
	private List<Tree> children;
	private TreeBuilder builder;
	private int inputSpace, outputSpace;
	private int numberOfThreads;
	private int elite;
	private double totalRunTime;
}
