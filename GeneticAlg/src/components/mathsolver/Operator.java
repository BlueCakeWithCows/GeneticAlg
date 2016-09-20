package components.mathsolver;

public abstract class Operator {
	public static final int CONDITIONAL_TYPE = 0, DOUBLE_TYPE = 1, ARRAY_TYPE = 2;
	
	
	public abstract int getInputSize();

	public abstract int getOperatorOutputType();
	public abstract int getNumberOfInputDecimals();
	public abstract int getNumberOfInputCollections();

	
	public abstract Value compute(Value[] v) throws InvalidValueException;

	@Override
	public abstract String toString();
}
