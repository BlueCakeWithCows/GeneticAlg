package swarm;

import java.util.ArrayList;
import java.util.List;

public abstract class Function {

	public int getInputSize() {
		return 2;
	}

	public abstract double solve(double[] vals);

	public abstract String toString();

	private static List<Function> functions;

	public static List<Function> getFunctions() {
		if (functions == null) {
			functions = new ArrayList<Function>();
			functions.add(new Addition());
			functions.add(new Subtraction());
			functions.add(new Multiplication());
			functions.add(new Division());
		}
		return functions;
	}

	public static class Addition extends Function {

		@Override
		public double solve(double[] vals) {
			return vals[0] + vals[1];
		}

		@Override
		public String toString() {
			return "+";
		}

	}

	public static class Subtraction extends Function {
		@Override
		public double solve(double[] vals) {
			return vals[0] - vals[1];
		}

		@Override
		public String toString() {
			return "-";
		}
	}

	public static class Multiplication extends Function {
		@Override
		public double solve(double[] vals) {
			return vals[0] * vals[1];
		}

		@Override
		public String toString() {
			return "*";
		}
	}

	public static class Division extends Function {
		@Override
		public double solve(double[] vals) {
			return vals[0] / vals[1];
		}

		@Override
		public String toString() {
			return "/";
		}
	}

}
