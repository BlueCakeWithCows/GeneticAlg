package swarm.layers;

import swarm.dimensions.BinaryDimension;

public class BinaryLayer extends Layer {

	public BinaryLayer(Layer parent, String ID) {
		super(parent, ID);
		
	}



	public BinaryDimension dim;

	public TerminatingLayer terminatingDimension;
	public FunctionLayer funcDimension;

	
	
	public double solve(double[] inputs) {
		if (dim.getValue() == 0)
			return terminatingDimension.solve(inputs);
		else if (dim.getValue() == 1)
			return funcDimension.solve(inputs);
		else
			throw new RuntimeException("Fuck everything. Binary Layer Got a non-binary answer");
	}
}
