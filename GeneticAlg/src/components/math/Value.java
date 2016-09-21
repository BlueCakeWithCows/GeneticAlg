package components.math;

public class Value {
	
	public Double value;

	public Value(Double double1) {
		this.value = double1;
	}

	public Value(double d) {
		this.value = d;
	}

	public boolean getBoolean() {
		if (value == null)
			return false;
		if (value > 0)
			return true;
		return false;
	}

	public double getDouble() {
		if (value == null)
			return 0d;
		return value;
	}

	public int getInt() {
		if (value == null)
			return 0;
		return (int) Math.floor(value);
	}
}
