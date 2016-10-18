package swarm.layers;

import java.util.List;

import swarm.Function;
import swarm.Particle;

public class FunctionLayer extends Layer {

	public FunctionLayer(Layer parent, List<Function> funcs) {
		super(parent, "F");
		functionList = new SubFunctionLayer[funcs.size()];
		for (int i = 0; i < funcs.size(); i++)
			functionList[i] = (new SubFunctionLayer(this, funcs.get(i)));

	}

	public SubFunctionLayer[] functionList;

	@Override
	public double solve(double[] inputs, Particle part) {
		double dub = 0;
		for (int i = 0; i < functionList.length; i++) {
			dub += functionList[i].solve(inputs, part);
		}
		return dub;
	}
}
