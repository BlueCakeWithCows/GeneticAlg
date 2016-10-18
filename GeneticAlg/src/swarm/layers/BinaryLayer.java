package swarm.layers;

import java.security.acl.Owner;

import swarm.Particle;
import swarm.dimensions.BinaryDimension;

public class BinaryLayer extends Layer {

	public BinaryLayer(Layer parent, int index) {
		super(parent, "B" + index);

	}

	public TerminatingLayer terminatingDimension;
	public FunctionLayer funcDimension;

	public double solve(double[] inputs, Particle o) {
		double dim = o.get(this.getLocation(), BinaryDimension.class);
		if (dim == 0) {
			if (terminatingDimension == null)
				terminatingDimension = new TerminatingLayer(this, o.getInSize());
			return terminatingDimension.solve(inputs, o);
		} else if (dim == 1) {
			if (funcDimension == null)
				funcDimension = new FunctionLayer(this, o.getFunctions());
			return funcDimension.solve(inputs, o);
		} else
			throw new RuntimeException("Fuck everything. Binary Layer Got a non-binary answer");
	}
}