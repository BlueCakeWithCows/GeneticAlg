package swarm.layers;

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
		System.out.println(this.getLocation());
		if (dim == 0) {
			if (terminatingDimension == null)
				terminatingDimension = new TerminatingLayer(this, o.getInSize());
			return terminatingDimension.solve(inputs, o);
			
		} else if (dim == 1) {
			if (funcDimension == null)
				funcDimension = new FunctionLayer(this, o.getFunctions());
			return funcDimension.solve(inputs, o);
		} else{
			System.out.println(dim);
			throw new RuntimeException("Fuck everything. Binary Layer Got a non-binary answer");
		}
	}

	public String getString(Particle particle) {
		double dim = particle.get(this.getLocation(), BinaryDimension.class);
		if (dim == 0) {
			return terminatingDimension.getString(particle);
		} else if (dim == 1) {
			return funcDimension.getString(particle);
		} else
			throw new RuntimeException("Fuck everything. Binary Layer Got a non-binary answer");

	}
}
