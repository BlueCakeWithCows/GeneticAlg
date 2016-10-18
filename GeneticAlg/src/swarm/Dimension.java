package swarm;

import java.util.Random;

public abstract class Dimension {

	private String location;
	private String parent;
	private Integer type;

	public static final int Binary = 0, Function = 1, Terminating = 2;

	public Dimension(Dimension d, int index, int type) {

		if (parent != null) {
			parent = d.getLoc();
		} else {
			parent = "";
		}
		location = parent + "[" + index + type + "]";
	}

	public Dimension(String parent, int index, int type) {
		this.parent = parent;
		location = parent + "[" + index + type + "]";
	}

	public String getLoc() {
		return location;
	}

	public Integer getType() {
		return type;
	}

	public String getParent() {
		return parent;
	}

	private double value;

	public abstract double getMin();

	public abstract double getRange();

	public abstract double constrain(double d);

	public double getValue() {
		return value;
	}

	public void setValue(double v) {
		value = constrain(v);
	}

	public void gen(Random r) {
		// TODO Auto-generated method stub
		
	}
}
