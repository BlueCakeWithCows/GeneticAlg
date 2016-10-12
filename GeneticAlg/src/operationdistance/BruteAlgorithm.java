package operationdistance;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BruteAlgorithm extends Algorithm {

	public void setMaxSize(int i) {
		this.maxSize = i;
	}

	private int maxSize = 5;

	private List<Set<Integer>> layers;

	@Override
	public int run() {
		layers = new ArrayList<Set<Integer>>();
		args = new HashSet<Integer>();
		args.addAll(initialArgs);
		layers.add(args);
		int goal = setTarget;
		operations = solve(goal, 0);
		return operations;

	}

	private void populate() {
		HashSet<Integer> s = new HashSet<Integer>();
		for (Integer i : layers.get(layers.size() - 1)) {
			for (Integer i2 : args) {
				s.add(i2 + i);
				s.add(i2 - i);
				s.add(i - i2);
				s.add(i * i2);
			}
		}
		for (Set<Integer> set : layers) {
			s.removeAll(set);
		}
		System.out.println("Size:"+s.size() +"   Layer:"+ layers.size());
		layers.add(s);
	}

	@Override
	protected int solve(int y, int level) {
		level += 1;
		int i=0;
		for (i = 0; i < maxSize; i++) {
			if (layers.get(i).contains(y)) {
				if (debug)
					System.out.println("Solved in " + i);
				return i;

			}
			if (debug)
				System.out.println("Populating layer " + (i + 1));
			populate();
		}
		return i;
	}
}
