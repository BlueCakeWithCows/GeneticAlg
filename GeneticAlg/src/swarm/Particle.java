package swarm;

import java.util.HashMap;
import java.util.List;
import java.util.Random;

import swarm.dimensions.BinaryDimension;
import swarm.layers.BinaryLayer;

public class Particle {
	private Random rand;

	public HashMap<String, Dimension> map;
	public BinaryLayer layer;

	private int inputSize;
	
	public Particle(int inputSize) {
		map = new HashMap<String, Dimension>(32);
		rand = new Random();
		this.inputSize = inputSize;
		layer = new BinaryLayer(null, 0);
	}

	private void solve(double[] inputs) {
		layer.solve(inputs, this);
	}
	
	public void score(){
		
	}
	

	public double get(String location, Class<? extends Dimension> class1) {
		if (!map.containsKey(location)) {
			Dimension d = null;
			try {
				d = class1.newInstance();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
			d.gen(rand);
			map.put(location, d);
		}
		return map.get(location).getValue();
	}

	public List<Function> getFunctions() {
		// TODO Auto-generated method stub
		return null;
	}

	public int getInSize() {
		return this.inputSize;
	}
}
