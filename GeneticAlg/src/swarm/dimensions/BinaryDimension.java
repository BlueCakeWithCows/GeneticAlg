package swarm.dimensions;

import java.util.Random;

import swarm.Dimension;

public class BinaryDimension extends Dimension {

	public BinaryDimension(double value) {
		super(value);
	}
	

	public BinaryDimension(Random value) {
		super(value);
	}
	public BinaryDimension() {
		
	}

	public double getValue() {
		if(value>1)
			return 1;
		else
			return 0;
	}
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

	@Override
	public Dimension copy() {
		return new BinaryDimension(this.getValue());
	}

	@Override
	public void gen(Random r) {
		this.setValue(r.nextFloat() * 2f);
		if(value <1.5f)
			value = 0;
		else
			value = 1.5;
	}
	
}
