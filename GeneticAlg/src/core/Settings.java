package core;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.JTextField;

import components.TestCase;

public class Settings {
	public HashMap<String, String> map;

	public static final String URL = "TRAINING_LOC", SEED = "SEED", MUTATION_CHANCE = "MUTATION_CHANCE",
			POPULATION = "POPULATION", SIMPLE_SELECTION_PERCENT = "PERCENT_TO_SELECT_BY_SIMPLE_SELECTION",
			INITIAL_MAX = "INITIAL_MAX", INITIAL_MIN = "INITIAL_MIN", MAX_TREE_SIZE = "MAX_TREE_SIZE";

	public static final String TEST_DATA_TO_USE = "TEST_DATA_TO_USE";
	public static final String QUICK_RANDOM_TEST_SELECTION = "QUICK_RANDOM_TEST_SELECTION";
	public static final String NUMBER_OF_THREADS = "NUMBER_OF_THREADS";
	public static final String SIMPLE_ROULETTE_SELECTION_PERCENT = "PERCENT_TO_SELECT_BY_SIMPLE_ROULETTE_SELECTION";
	public static final String PERCENT_TO_MUTATE_ONLY = "PERCENT_TO_MUTATE_ONLY";
	public static final String PERCENT_TO_SIMPLE_MIX_BREED = "PERCENT_TO_SIMPLE_MIX_BREED";
	public static final String NUMBER_OF_ELITE = "NUMBER_OF_ELITE";
	public static final String USE_SECURE_RANDOM = "USE_SECURE_RANDOM";
	public static final String NUMBER_OF_PARENTS_PER_OFFSPRING = "NUMBER_OF_PARENTS_PER_OFFSPRING";
	public static final String SCORER_ERROR_MARGIN = "SCORER_ERROR_MARGIN";
	public static final String NO_POINTS_FOR_ERROR_ABOVE = "NO_POINTS_FOR_ERROR_ABOVE";
	public static final String USE_FAILED_TESTS_PRIMARY_SCORING = "USE_FAILED_TESTS_PRIMARY_SCORING";
	
	
	public Settings() {
		map = new HashMap<String, String>();
	}

	public Settings(List<String> list) {
		this();
		for (String string : list) {
			String value = "";
			String key = string.split(":")[0];
			if (string.split(":").length > 1)
				value = string.replaceFirst(string.split(":")[0] + ":", "");
			map.put(key, value);
		}
	}

	public long getSeed() {
		return Long.valueOf(map.get(SEED));
	}

	public double getMutationChance() {
		return Double.valueOf(map.get(MUTATION_CHANCE));
	}

	public int getPopulation() {
		return Integer.valueOf(map.get(POPULATION));
	}

	public double getTopPercent() {
		return Double.valueOf(map.get(SIMPLE_SELECTION_PERCENT));
	}

	public int getMinInitPop() {
		return Integer.valueOf(map.get(INITIAL_MIN));
	}

	public double getSimpleRoulettePercent() {
		return Double.valueOf(map.get(SIMPLE_ROULETTE_SELECTION_PERCENT));
	}

	public double getPercentToMutateOnly() {
		return Double.valueOf(map.get(PERCENT_TO_MUTATE_ONLY));
	}

	public int getMaxInitPop() {
		return Integer.valueOf(map.get(INITIAL_MAX));
	}

	public int getMaxTreeSize() {
		return Integer.valueOf(map.get(MAX_TREE_SIZE));
	}

	public double getTestDataToUse() {
		return Double.valueOf(map.get(TEST_DATA_TO_USE));
	}

	public void set(String key, String value) {
		map.put(key, value);
	}

	public void save(File file) {
		List<String> stuff = new ArrayList<String>();
		for (String ok : map.keySet()) {
			stuff.add(ok + ":" + map.get(ok));
		}
		SimpleSaveLoad.save(file, stuff);
	}

	public String getURL() {
		return map.get(URL);
	}

	public boolean getQuickRandomTestSelection() {
		return Boolean.valueOf(map.get(QUICK_RANDOM_TEST_SELECTION));
	}

	public int getNumberOfThreads() {
		return Integer.valueOf(map.get(NUMBER_OF_THREADS));
	}

	public int getElite() {
		return Integer.valueOf(map.get(NUMBER_OF_ELITE));
	}

	public int getNumberOfParents() {
		return Integer.valueOf(map.get(NUMBER_OF_PARENTS_PER_OFFSPRING));
	}

	public boolean getUseSecureRandom() {
		return Boolean.valueOf(map.get(USE_SECURE_RANDOM));
	}

	public double getErrorMargin() {
		return Double.valueOf(map.get(SCORER_ERROR_MARGIN));
	}

	public double getSimpleMixBreedPercent() {
		return Double.valueOf(map.get(PERCENT_TO_SIMPLE_MIX_BREED));
	}
	public double getNoPointsForErrorAbove() {
		return Double.valueOf(map.get(NO_POINTS_FOR_ERROR_ABOVE));
	}

	public boolean getUseFailedTestsPrimaryScoring() {
		return Boolean.valueOf(map.get(USE_FAILED_TESTS_PRIMARY_SCORING));
	}
	
	
}
