package swarm;

import java.util.HashMap;
import java.util.List;
import java.util.Random;

import swarm.dimensions.BinaryDimension;

public class Particle {
	Random r;
	
	public HashMap<String, Dimension> map;

	public Particle() {
		map = new HashMap<String, Dimension>(32);
	}

	private void solve(double[] inputs) {

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
			d.gen(r);
			map.put(location, d);
		}
		return map.get(location).getValue();
	}

	public List<Function> getFunctions() {
		// TODO Auto-generated method stub
		return null;
	}

	public int getInSize() {
		// TODO Auto-generated method stub
		return null;
	}
}
