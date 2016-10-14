package genetic.core;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import genetic.components.Breeder;
import genetic.components.BreedingSummary;
import genetic.components.Mutator;
import genetic.components.ScoreKeeper;
import genetic.components.TestCase;
import genetic.components.basic.Assignment;
import genetic.components.basic.BasicSelector;
import genetic.components.basic.Conditional;
import genetic.components.basic.Node;
import genetic.components.basic.Tree;
import genetic.components.comparators.Accuracy;
import genetic.components.comparators.FailedTest;
import genetic.components.comparators.LengthScoring;
import genetic.components.comparators.OperatorDistance;
import genetic.components.math.ArithmaticOperators;
import genetic.components.math.Operator;
import genetic.components.mathsolver.TreeBuilder;

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
		if(rotate){
			rotate();
		}
	}

	private int counter = 0;
	double lastScore = 0;
	private int failed = 0;
	double distance = 0;

	private void rotate(){
		if (this.getBest().score == lastScore && this.getBest().failedTests == failed
				&& this.getBest().operationDistance == distance) {
			counter++;
		} else {
			counter = 0;
		}
		if (counter > 10) {
			scorer.switchComparator();
			counter = 0;
		}
		lastScore = this.getBest().score;
		failed=this.getBest().failedTests;
		distance = this.getBest().operationDistance;
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

	List<TestCase> cases;

	public Manager(Settings settings, List<TestCase> cases) {
		children = new ArrayList<Tree>();
		this.inputSpace = cases.get(0).input.length;
		this.outputSpace = cases.get(0).out.length;
		this.cases = cases;
		this.applySettings(settings);
	}

	private long seed;

	public void applySettings(Settings settings) {
		if (random == null || this.seed != settings.seed.getValue()
				|| ((random instanceof SecureRandom) != settings.cryptoRandom.getValue())) {
			seed = settings.seed.getValue();
			if (settings.cryptoRandom.getValue())
				random = new SecureRandom();
			else
				random = new Random();
			random.setSeed(seed);
		}

		rotate = settings.rotateScoring.getValue();
		
		numberOfThreads = settings.numberOfThreads.getValue();
		elite = settings.eliteSize.getValue();

		if (nodeSet == null)
			nodeSet = new ArrayList<Node>();
		nodeSet.clear();

		if (settings.functionNodes.getValue())
			nodeSet.add(new Assignment());
		if (settings.conditionalNodes.getValue())
			nodeSet.add(new Conditional());

		if (operatorSet == null)
			operatorSet = new ArrayList<Operator>();
		operatorSet.clear();
		if (settings.simpleArithmetricOperators.getValue())
			operatorSet.addAll(ArithmaticOperators.getArithmaticOperators());
		if (settings.advancedArithmetricOperators.getValue())
			operatorSet.addAll(ArithmaticOperators.getArithmaticOperators());

		// if (settings.oper)
		// operatorSet.addAll(ConditionalOperators.getConditionalOperators());

		if (mutator == null)
			mutator = new Mutator();
		mutator.setMutationChance(settings.mutationChance.getValue());
		mutator.setRandom(random);
		mutator.setOperatorSet(operatorSet);
		mutator.setNodeSet(nodeSet);

		scorer = new ScoreKeeper();
		scorer.addComparator(new OperatorDistance(), settings.operationDistanceScoringPriority.getValue());
		scorer.addComparator(new FailedTest(), settings.failedTestScoringPriority.getValue());
		scorer.addComparator(new Accuracy(), settings.accuracyScoringPriority.getValue());
		scorer.addComparator(new LengthScoring(), settings.lengthScoringPriority.getValue());

		scorer.setRandom(random);
		scorer.setQuickRandom(settings.testSelection.getValue());
		scorer.setUsableTestSuitePercent(settings.testsPerTreeSize.getValue());
		scorer.setError(settings.errorMarginForScoring.getValue());
		scorer.setNoPointMark(settings.maxDistanceForAccuracyScoring.getValue());
		scorer.setMaxTreeSize(settings.treeMaxLength.getValue());
		scorer.setMinTreeSize(settings.treeMinLength.getValue());
		scorer.setTestSuite(cases);

		if (breedingSummary == null)
			breedingSummary = new BreedingSummary();
		breedingSummary.elite = elite;
		breedingSummary.numberPerPairToSelect = settings.parentsPerTree.getValue();
		breedingSummary.percentMutateOnly = settings.mutationOnlyPercent.getValue();
		breedingSummary.percentMixBreed = settings.simpleBreedPercent.getValue();
		breedingSummary.population = settings.populationSize.getValue();
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
		builder.setMinTreeSize(settings.initialTreeMinLength.getValue());
		builder.setMaxTreeSize(settings.initialTreeMaxLength.getValue());
		builder.setPopulation(settings.populationSize.getValue());
		builder.setOperatorSet(operatorSet);
		builder.setNodeSet(nodeSet);
		builder.setNumberOfInputs(inputSpace);
		builder.setNumberOfOutputs(outputSpace);

	}

	private int generation;
	private boolean scored = false;
	private boolean rotate = false;
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
