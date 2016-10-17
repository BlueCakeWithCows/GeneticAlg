package swarm.dimensions;

import swarm.Dimension;

public class BinaryDimension extends Dimension {

	public double getMin() {
		return 0;
	}

	public double getRange() {
		return 2;
	}

	public double constrain(double d) {
		if (d >= 2)
			d = 1.99d;
		if (d < 0)
			d = 0d;
		return d;
	}

	public BinaryDimension(Dimension d, int index, int type) {
		super(d, index, type);
		// TODO Auto-generated constructor stub
	}
}
