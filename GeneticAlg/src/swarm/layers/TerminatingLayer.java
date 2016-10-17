package swarm.layers;

import swarm.Dimension;

public class TerminatingLayer extends Layer {

	public TerminatingLayer(Layer parent, int inputsize) {
		super(parent, "T");
		this.inputsize = inputsize;
		dimensions = new Dimension[inputsize + 1];
	}

	private int inputsize;
	public Dimension[] dimensions;

	@Override
	public double solve(double[] inputs) {
		double value = 0;
		for (int i = 0; i < inputsize; i++) {
			value += inputs[i] * dimensions[i].getValue();
		}
		value += dimensions[inputsize].getValue();
		return value;
	}
}