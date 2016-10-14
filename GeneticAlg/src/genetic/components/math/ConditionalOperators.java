package genetic.components.math;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class ConditionalOperators extends Operator {

	private static List<Operator> conditionalOperators;

	public static List<Operator> getConditionalOperators() {
		if (conditionalOperators == null) {
			conditionalOperators = new ArrayList<Operator>();
			conditionalOperators.add(new Or());
			conditionalOperators.add(new And());
			conditionalOperators.add(new Neither());
		}
		return conditionalOperators;
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

	public static class Or extends ConditionalOperators {

		@Override
		public Value compute(Value[] v) throws InvalidValueException {
			try {
				return new Value(v[0].getBoolean() || v[1].getBoolean());
			} catch (Exception e) {
				
			}
			return new Value(0);
		}

		@Override
		public String toString() {
			return "||";
		}

	}

	public static class And extends ConditionalOperators {
		@Override
		public Value compute(Value[] v) throws InvalidValueException {
			try {
				return new Value(v[0].getBoolean() || v[1].getBoolean());
			} catch (Exception e) {
				
			}
			return new Value(0);
		}

		@Override
		public String toString() {
			return "&";
		}
	}

	public static class Neither extends ConditionalOperators {
		@Override
		public Value compute(Value[] v) throws InvalidValueException {
			try {
				return new Value(!(v[0].getBoolean() || v[1].getBoolean()));
			} catch (Exception e) {
				
			}
			return new Value(0);
		}

		@Override
		public String toString() {
			return "!||";
		}
	}


}
