package components.mathsolver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class Tree {
	public double score;
	public boolean scored;
	public int failedTests;
	public int totalTests;
	private List<Node> points;
	private HashMap<String, Double> variables;
	private HashMap<String, Double> values;
	private HashMap<String, Double> constants;
	public int outputSize;
	public int inputSize;
	public Double[] outputs;

	public Tree(int inp, int out) {
		points = new ArrayList<Node>();
		outputSize = out;
		this.inputSize = inp;
		variables = new HashMap<String, Double>();
		values = new HashMap<String, Double>();
		constants = new HashMap<String, Double>();
		constants.put("c0", 0d) ;
		constants.put("c1", 1d) ;
		constants.put("c2", 2d) ;


	}

	public Double[] execute(double[] inputs) {
		values.clear();
		variables.clear();
		outputs = new Double[outputSize];

		for (int i = 0; i < inputs.length; i++)
			values.put(formatInput(i), inputs[i]);
		;

		for (int i = 0; i < outputs.length; i++)
			variables.put(formatOut(i), outputs[i]);

		values.putAll(constants);
		simulate(this);
		for (int i = 0; i < outputs.length; i++)
			outputs[i] = variables.get(formatOut(i));

		return outputs;
	}

	private String formatOut(int i) {
		return "out" + i;
	}

	public void simulate(Tree tree) {
		int currentLine = 0;
		try {
			for (; currentLine < points.size();) {
				
				points.get(currentLine).compute(tree);
				currentLine += points.get(currentLine).getLineJump();
				
			}
		} catch (ArrayIndexOutOfBoundsException e) {

		}
	}

	public ArrayList<String> getDefaultNames() {
		ArrayList<String> list = new ArrayList<String>();
		if (constants.keySet().size() > 0)
			list.addAll(constants.keySet());
		for (int i = 0; i < inputSize; i++)
			list.add(formatInput(i));
		return list;
	}

	public ArrayList<String> getDefaultVariables() {
		ArrayList<String> list = new ArrayList<String>();
		for (int i = 0; i < outputSize; i++)
			list.add(formatOut(i));
		return list;
	}

	public void addPoint(Node point) {
		this.addPoint(point, points.size());
	}

	public int getPointSize() {
		return points.size();
	}

	public void addPoint(Node point, int index) {
		points.add(index, point);
	}

	public void setVariable(String var, Double doOperation) {
		variables.put(var, doOperation);
	}

	public Value getValue(String val2) {
		if (values.containsKey(val2))
			return new Value(values.get(val2));
		else
			return new Value(variables.get(val2));
	}

	public String formatInput(int i) {
		return "in" + i;
	}

	public List<Node> getPoints() {
		return this.points;
	}

	@Override
	public String toString() {
		StringBuilder s = new StringBuilder();
		s.append("Failed Tests: " + failedTests + " of " + totalTests + " ");
		s.append("Score: " + score + System.lineSeparator());
		for (Node p : points) {
			s.append(p.toString() + System.lineSeparator());
		}
		return s.toString().trim();
	}

	public int getSize() {
		int total = 0;
		for (Node p : points) {
			total += p.getSize();
		}
		return total;
	}

	public void setPoints(List<Node> points2) {
		this.points = points2;
	}

	public Node getPoint(int i) {
		if (points.size() > i) {
			return points.get(i);
		}
		return null;
	}

	public int simpleSize() {
		return points.size();
	}

}
