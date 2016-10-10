package operationdistance;

import java.util.HashSet;
import java.util.Set;

public class Algorithm {


	public Algorithm() {

	}

	protected HashSet<Integer> initialArgs;
	protected Set<Integer> args;
	protected Set<Integer> oneStepMoves;
	protected Set<Integer> twoStepMoves;
	protected int setTarget;
	protected int operations;
	protected boolean debug;
	public void setDebug(boolean b){
		this.debug =b ;
	}
	public void setArgs(HashSet<Integer> set) {
		this.initialArgs = set;
	}

	public void setTarget(int number) {
		this.setTarget = number;
	}

	public int run() {
		args = new HashSet<Integer>();
		args.addAll(initialArgs);
		this.precalcOneStepAdditions();
		int goal = setTarget;
		operations = solve(goal, 0);
		return operations;
	}

	protected void precalcOneStepAdditions() {
		oneStepMoves = new HashSet<Integer>();
		twoStepMoves = new HashSet<Integer>();
		for (Integer i : args) {
			addToNSet(i, args, this.oneStepMoves);
			addToNSet(i, args, this.twoStepMoves);
		}
	}

	private void addToNSet(int i, Set<Integer> args2, Set<Integer> set) {
		HashSet<Integer> s = new HashSet<Integer>();
		s.add(i * i);
		s.add(i + i);
		for (Integer i2 : args2) {
			s.add(i2 + i);
			s.add(i2 - i);
			s.add(i - i2);
			s.add(i * i2);
		}
		set.addAll(s);
	}

	protected void addNumber(int i) {
		this.args.add(i);
		this.addToNSet(i, args, oneStepMoves);
		this.addToNSet(i, args, twoStepMoves);
		this.addToNSet(i, oneStepMoves, twoStepMoves);
	}

	protected int solve(int y, int level) {
		level += 1;
		int o = 0;
		int divisor = MyMathUtils.getGreatestDivisor(y, args);
		int multiple = (int) y / divisor;
		int remainder = y % divisor;

		if (!args.contains(multiple))
			o += this.solve(multiple, level);

		if (!args.contains(remainder))
			o += this.solve(remainder, level);
		if (remainder != 0)
			o++;
		if (multiple != 1)
			o++;
		System.out.println("R:" + remainder + "|D:" + divisor + "|M:" + multiple + "|O:" + o + "|L:" + level);
		return o;
	}
}
