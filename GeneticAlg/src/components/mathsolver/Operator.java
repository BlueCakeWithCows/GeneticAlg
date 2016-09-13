package components.mathsolver;

public class Operator {
	public static final int OR = 0, ADD = 1, SUB = 2, MULTIPLY = 3, DIVIDE = 4, GREATER = 5, LESSER = 6, EQUAL = 7,
			SET = 8;

	public static Double doOperation(int operator, Value val1, Value val2) {
		switch (operator) {
		case ADD:
			return val1.getDouble() + val2.getDouble();
		case SUB:
			return val1.getDouble() - val2.getDouble();
		case DIVIDE:
			return val1.getDouble() / val2.getDouble();
		case MULTIPLY:
			return val1.getDouble() * val2.getDouble();
		case GREATER:
			if (val1.getDouble() > val2.getDouble())
				return 1d;
			return -1d;
		case LESSER:
			if (val1.getDouble() < val2.getDouble())
				return 1d;
			return -1d;
		case EQUAL:
			if (val1.getDouble() == val2.getDouble())
				return 1d;
			return -1d;
		case OR:
			if (val1.getBoolean() || val2.getBoolean())
				return 1d;
			return -1d;
		case SET:
			return val1.getDouble();
		}
		return null;
	}

	public static final int getRange() {
		return 9;
	}
	public static String getOperator(int operator) {
		switch (operator) {
		case ADD:
			return "+";
		case SUB:
			return "-";
		case DIVIDE:
			return "/";
		case MULTIPLY:
			return "*";
		case GREATER:
			return ">";
		case LESSER:
			return "<";
		case EQUAL:
			return "==";
		case OR:
			return "||";
		case SET:
			return "SET";
		}
		return null;
	}

	public static String setup(int operator, String var, String val1, String val2) {
		if(ADD ==operator||Operator.SUB ==operator||Operator.DIVIDE ==operator||Operator.MULTIPLY ==operator){
			return var+"="+val1+getOperator(operator)+val2+";";
		}
		if(GREATER == operator || LESSER ==operator|| EQUAL ==operator){
			return "if("+val1+getOperator(operator)+val2+")\n"+var+"=1;\nelse\n"+var+"=-1;";
		}
		return var+"="+getOperator(operator)+" "+val1+", "+val2+";";
	}

}
