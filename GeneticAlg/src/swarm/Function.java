package swarm;

import genetic.components.math.ArithmaticOperators;
import genetic.components.math.InvalidValueException;
import genetic.components.math.Value;

public abstract class Function {

	public int getInputSize() {
		// TODO Auto-generated method stub
		return 2;
	}

	public double solve(double[] vals) {
		// TODO Auto-generated method stub
		return 0;
	}

	public String getSymbol() {
		// TODO Auto-generated method stub
		return null;
	}

	public static class Addition extends Function {

		@Override
		public double solve(v) throws InvalidValueException {
			try {
				return new Value(v[0].getDouble() + v[1].getDouble());
			} catch (Exception e) {

			}
			return new Value(0);
		}

		@Override
		public String toString() {
			return "+";
		}

	}

	public static class Subtraction extends Function {
		@Override
		public Value compute(Value[] v) throws InvalidValueException {
			try {
				return new Value(v[0].getDouble() - v[1].getDouble());
			} catch (Exception e) {

			}
			return new Value(0);
		}

		@Override
		public String toString() {
			return "-";
		}
	}

	public static class Multiplication extends Function {
		@Override
		public Value compute(Value[] v) throws InvalidValueException {
			try {
				return new Value(v[0].getDouble() * v[1].getDouble());
			} catch (Exception e) {

			}
			return new Value(0);
		}

		@Override
		public String toString() {
			return "*";
		}
	}

	public static class Division extends Function {
		@Override
		public Value compute(Value[] v) throws InvalidValueException {
			try {
				return new Value(v[0].getDouble() / v[1].getDouble());
			} catch (Exception e) {
			}
			return new Value(0);
		}

		@Override
		public String toString() {
			return "/";
		}
	}

}
