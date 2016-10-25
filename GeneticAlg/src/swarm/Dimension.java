package swarm;

import java.util.Random;

public abstract class Dimension {

	public Dimension(Random r) {
		this.gen(r);
		value = this.constrain(value);
	}

	public Dimension(double va) {
		this.value = va;
	}

	private double value;

	public abstract double getMin();

	public abstract double getRange();

	public abstract double constrain(double d);

	public double getValue() {
		return value;
	}

	public void setValue(double v) {
		value = constrain(v);
	}

	public abstract Dimension copy();

	public abstract void gen(Random r);

}
