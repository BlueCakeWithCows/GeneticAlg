package swarm.layers;

import java.util.List;

import swarm.Function;

public class FunctionLayer extends Layer {

	public FunctionLayer(Layer parent, List<Function> funcs) {
		super(parent, "F");
		functionList = funcs;
	}

	public List<SubFunctionLayer> functionList;

	@Override
	public double solve(double[] inputs) {
		// TODO Auto-generated method stub
		return 0;
	}

}
