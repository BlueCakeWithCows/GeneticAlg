package components.mathsolver;

public class Value {
	public String name;

	public Double value;

	public Value(Double double1) {
		this.value = double1;
	}

	public boolean getBoolean() {
		if (value == null)
			return false;
		if (value > 0)
			return true;
		return false;
	}

	public Double getDouble() {
		return value;
	}
}
