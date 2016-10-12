package operationdistance;

public class SimpleDivisorAlgorithm extends Algorithm {
	@Override
	protected int solve(int y, int level) {
		level += 1;
		
		if (this.oneStepMoves.contains(y)) {
			if (debug)
				System.out.println("One Step to " + y + "|L:" + level);
			return 1;
		}
		if (this.twoStepMoves.contains(y)) {
			if (debug)
				System.out.println("Two steps to " + y + "|L:" + level);
			return 2;
		}
		int o = 0;

		int divisor = MyMathUtils.getGreatestDivisor(y, args);
		int multiple = (int) y / divisor;
		int remainder = y % divisor;

		if (!args.contains(multiple))
			o += this.solve(multiple, level);
		args.add(multiple);
		
		if (!args.contains(remainder))
			o += this.solve(remainder, level);
		args.add(remainder);
		if (remainder != 0)
			o++;
		if (multiple != 1)
			o++;
		if (debug)
			System.out.println("R:" + remainder + "|D:" + divisor + "|M:" + multiple + "|O:" + o + "|L:" + level);
		return o;
	}
}
