package swarm;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import both.TestCase;
import swarm.layers.BinaryLayer;

public class Particle {
	private Random rand;
	private double score;

	public HashMap<String, Dimension> map;
	public BinaryLayer layer;
	private List<Function> functions;
	private int inputSize;

	public Particle(int inputSize, List<Function> functions, Random random) {
		map = new HashMap<String, Dimension>(32);
		rand = random;
		this.inputSize = inputSize;
		this.functions = functions;
		layer = new BinaryLayer(null, 0);

	}

	private double solve(double[] inputs) {
		return layer.solve(inputs, this);
	}

	public void score(TestCase[] cases) {
		double score = 0;
		for (TestCase c : cases) {
			score += Math.abs(c.out[0] - this.solve(c.input));
		}
		this.score = score;
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
		return functions;
	}

	public double getScore() {
		return this.score;
	}

	public int getInSize() {
		return this.inputSize;
	}

	public String toString() {
		return score + ": " + layer.getString(this);
	}

	public DecimalFormat df = new DecimalFormat("#.00");

	public Particle copy() {
		Particle p = new Particle(inputSize, functions, rand);
		p.setScore(score);
		for(String k : map.keySet()){
			p.map.put(k, this.map.get(k).copy());
		}
		return null;
	}

	private void setScore(double score2) {
		this.score = score2;
	}
}
