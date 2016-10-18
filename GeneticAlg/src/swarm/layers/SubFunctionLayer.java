package swarm.layers;

import swarm.Function;
import swarm.Particle;

public class SubFunctionLayer extends Layer {
	public SubFunctionLayer(Layer parent, Function function) {
		super(parent, function.getSymbol());
		this.function = function;
		layers = new BinaryLayer[function.getInputSize()];
		// TODO Auto-generated constructor stub
	}

	private Function function;
	private BinaryLayer[] layers;

	@Override
	public double solve(double[] inputs, Particle o) {
		double[] vals = new double[layers.length];
		for (int i = 0; i < layers.length; i++) {
			if (layers[i] == null)
				layers[i] = new BinaryLayer(this, i);
			vals[i] = layers[i].solve(inputs, o);
		}
		return function.solve(vals);
	}
}
