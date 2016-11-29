package swarm.layers;

import swarm.Particle;

public abstract class Layer {
	private final Layer parent;
	protected String location;

	public Layer getParent() {
		return parent;
	}

	public Layer(Layer parent, String ID) {
		if (parent != null) {
			this.parent = parent;
			this.location = parent.getLocation() + ID;
		} else {
			this.location = ID;
			this.parent = null;
		}
	}

	public abstract double solve(double[] inputs, Particle o);

	public String getLocation() {
		return location;
	}
}
