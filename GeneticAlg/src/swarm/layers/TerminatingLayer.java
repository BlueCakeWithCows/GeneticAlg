package swarm.layers;

import swarm.Dimension;
import swarm.Particle;
import swarm.dimensions.DoubleDimension;

public class TerminatingLayer extends Layer {

	public TerminatingLayer(Layer parent, int inputsize) {
		super(parent, "T");
		this.inputsize = inputsize;
	}

	private int inputsize;

	@Override
	public double solve(double[] inputs, Particle o) {
		double value = 0;
		for (int i = 0; i < inputsize; i++) {
			value += inputs[i] * o.get(location + i, Dimension.class);
		}
		value += o.get(location + (inputsize), Dimension.class);
		return value;
	}

	public String getString(Particle particle) {
		String s = "{";
		for (int i = 0; i < inputsize; i++) {
			double value = particle.get(location + i, DoubleDimension.class);
			if (value != 0) {
				if (s.length() > 1)
					s += "+";
				s += particle.df.format(value) + "*" + i;
			}
		}
		s += "}";
		return s;
	}
}