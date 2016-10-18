package swarm.layers;

import swarm.Dimension;
import swarm.Particle;

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
}