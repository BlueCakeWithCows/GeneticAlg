package components.mathsolver;

import java.util.ArrayList;
import java.util.List;

public class Conditional extends Point {
	public String variable;
	public boolean NOT;
	public List<Point> points;

	public Conditional(String variable, boolean NOT) {
		this.variable = variable;
		this.NOT = NOT;
		points = new ArrayList<Point>();
	}

	public Conditional(Conditional nP) {
		this.variable = nP.variable;
		this.NOT = nP.NOT;
		points = new ArrayList<Point>();
		points.addAll(nP.points);
	}

	private boolean test(Value value) {
		boolean b = value.getBoolean();
		if (b != NOT)
			return true;
		return false;
	}

	@Override
	public void compute(Tree tree) {
		if (test(tree.getValue(variable)))
			run(tree);
	}

	private void run(Tree tree) {
		for (Point p : points) {
			p.compute(tree);
		}
	}
	@Override
	public String toString() {
		String ret = "if " + variable + " IS " + !NOT + " THEN: " +points.size();
		StringBuilder s = new StringBuilder();
		s.append(ret+System.lineSeparator());
		for (Point p : points) {
			String[] split = p.toString().split(System.lineSeparator());
			for(String so:split){
				s.append("  "+so + System.lineSeparator());
			}
		}
		return s.toString().trim();
	}
	@Override
	public int getSize() {
		int t= 1;
		for(Point p: points){
			t+=p.getSize();
		}
		return t;
	}
}
