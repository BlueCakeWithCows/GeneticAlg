package swarm.layers;

import swarm.Function;

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
	public double solve(double[] inputs) {
		double[] vals = new double[layers.length];
		for (int i = 0; i < layers.length; i++) {
			vals[i] = layers[i].solve(inputs);
		}
		return function.solve(vals);
	}
}
