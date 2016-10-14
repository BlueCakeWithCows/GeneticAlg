package genetic.components.math;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class ArithmaticOperators extends Operator {

	private static List<Operator> arithmaticOperators;

	public static List<Operator> getArithmaticOperators() {
		if (arithmaticOperators == null) {
			arithmaticOperators = new ArrayList<Operator>();
			arithmaticOperators.add(new Addition());
			arithmaticOperators.add(new Subtraction());
			arithmaticOperators.add(new Multiplication());
			arithmaticOperators.add(new Division());
		}
		return arithmaticOperators;
	}

	@Override
	public int getInputSize() {
		return 2;
	}

	@Override
	public int getOperatorOutputType() {
		return Operator.DOUBLE_TYPE;
	}

	private static Integer[] inputTypes = { Operator.DOUBLE_TYPE, Operator.CONDITIONAL_TYPE, Operator.ARRAY_TYPE };

	@Override
	public List<Integer> getInputTypesAt(int i) {
		return Arrays.asList(inputTypes);
	}

	public static class Addition extends ArithmaticOperators {

		@Override
		public Value compute(Value[] v) throws InvalidValueException {
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

	public static class Subtraction extends ArithmaticOperators {
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

	public static class Multiplication extends ArithmaticOperators {
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

	public static class Division extends ArithmaticOperators {
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
