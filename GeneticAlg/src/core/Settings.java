package core;

import java.util.ArrayList;
import java.util.List;

public class Settings {

	private final List<SettingsValue<?>> settings = new ArrayList<SettingsValue<?>>();

	public List<SettingsValue<?>> getSettings() {
		return settings;
	}

	public static void main(String[] args) {
		Settings s = new Settings();
		System.out.println(s.toString());
	}

	public final SettingsValue<Long> seed = new SettingsValue<Long>(0l, "SEED");

	public final SettingsValue<Integer> initialTreeMinLength = new SettingsValue<Integer>(1, "INITIAL_TREE_MIN_LENGTH");
	public final SettingsValue<Integer> initialTreeMaxLength = new SettingsValue<Integer>(50, "INITIAL_TREE_MAX_LENGTH");
	public final SettingsValue<Integer> treeMinLength = new SettingsValue<Integer>(0, "TREE_MIN_LENGTH");
	public final SettingsValue<Integer> treeMaxLength = new SettingsValue<Integer>(100, "TREE_MAX_LENGTH");

	public final SettingsValue<String> testSelection = new SettingsValue<String>("ALL",
			new String[] { "BLOCK RANDOM", "SIMPLE RANDOM", "ALL" }, "TEST_SELECTION_METHOD");
	public final SettingsValue<Integer> testsPerTreeSize = new SettingsValue<Integer>(0, "TESTS_PER_TREE_SIZE");

	public final SettingsValue<Integer> numberOfThreads = new SettingsValue<Integer>(4, "NUMBER_OF_THREADS");;

	public final SettingsValue<Integer> eliteSize = new SettingsValue<Integer>(10, "ELITE_SIZE");;
	public final SettingsValue<Integer> populationSize = new SettingsValue<Integer>(1000, "POPULATION_SIZE");;

	public final SettingsValue<Boolean> cryptoRandom = new SettingsValue<Boolean>(false, "CRYTO_RANDOM");;

	public final SettingsValue<Double> mutationChance = new SettingsValue<Double>(.05d, "MUTATION_CHANCE");

	public final SettingsValue<Double> mutationOnlyPercent = new SettingsValue<Double>(.5d, "MUTATION_ONLY_WEIGHT");
	public final SettingsValue<Double> simpleBreedPercent = new SettingsValue<Double>(.5d, "SIMPLE_BREED_WEIGHT");
	
	public final SettingsValue<Integer> parentsPerTree = new SettingsValue<Integer>(2, "PARENTS_PER_TREE");

	public final SettingsValue<Integer> maxDistanceForAccuracyScoring = new SettingsValue<Integer>(100,
			"MAX_DISTANCE_FOR_ACCURACY_SCORING");
	public final SettingsValue<Integer> errorMarginForScoring = new SettingsValue<Integer>(0,
			"ERROR_MARGIN_FOR_SCORING");

	public final SettingsValue<Integer> operationDistanceScoringPriority = new SettingsValue<Integer>(0,
			"OPERATION_DISTANCE_SCORING_PRIORITY");
	public final SettingsValue<Integer> lengthScoringPriority = new SettingsValue<Integer>(10,
			"LENGTH_SCORING_PRIORITY");
	public final SettingsValue<Integer> accuracyScoringPriority = new SettingsValue<Integer>(4,
			"ACCURACY_DISTANCE_SCORING_PRIORITY");
	public final SettingsValue<Integer> failedTestScoringPriority = new SettingsValue<Integer>(2,
			"FAILED_TEST_SCORING_PRIORITY");

	public final SettingsValue<Boolean> conditionalNodes = new SettingsValue<Boolean>(false, "CONDITIONAL_NODES");
	public final SettingsValue<Boolean> functionNodes = new SettingsValue<Boolean>(true, "FUNCTION_NODES");

	public final SettingsValue<Boolean> simpleArithmetricOperators = new SettingsValue<Boolean>(true,
			"SIMPLE_ARITHMETRIC_OPERATORS");
	public final SettingsValue<Boolean> advancedArithmetricOperators = new SettingsValue<Boolean>(true,
			"ADVANCED_ARITHMETRIC_OPERATORS");

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		for (SettingsValue<?> v : settings) {
			builder.append(v + "\n");
		}
		builder.deleteCharAt(builder.length() - 1);

		return builder.toString();
	}

	public class SettingsValue<E> {
		public E obj;
		public E[] possibilities;
		public final String ID;
		public final Class<?> clas;
		
		public SettingsValue(E obj, String ID) {
			clas = obj.getClass();
			this.obj = obj;
			this.ID = ID;
			settings.add(this);
		}

		public SettingsValue(E obj, E[] possibilities, String ID) {
			clas = obj.getClass();
			this.obj = obj;
			this.ID = ID;
			settings.add(this);
			this.possibilities = possibilities;
		}

		public E getValue() {
			return obj;
		}

		public void setValue(Object o) {
			obj = (E) o;
		}

		@Override
		public String toString() {
			return this.ID + ":" + obj.toString();
		}
	}

}
