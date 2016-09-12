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
			POPULATION = "POPULATION", TOP_PERCENT = "TOP_PERCENT", INITIAL_MAX = "INITIAL_MAX",
			INITIAL_MIN = "INITIAL_MIN", MAX_TREE_SIZE = "MAX_TREE_SIZE";

	public static final String TEST_DATA_TO_USE = "TEST_DATA_TO_USE";
	public static final String QUICK_RANDOM_TEST_SELECTION = "QUICK_RANDOM_TEST_SELECTION";
	public static final String NUMBER_OF_THREADS = "NUMBER_OF_THREADS";

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
		return Double.valueOf(map.get(TOP_PERCENT));
	}

	public int getMinInitPop() {
		return Integer.valueOf(map.get(INITIAL_MIN));
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
}
