package components.basic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import components.math.Value;

public class Tree {
	public double score;
	public boolean scored;
	public int failedTests;
	public int totalTests;
	private List<Node> points;
	private HashMap<String, Value> variables;
	private HashMap<String, Value> values;
	private HashMap<String, Value> constants;
	public int outputSize;
	public int inputSize;
	public Double[] outputs;

	public List<Value> getValues(){
		List<Value> values = new ArrayList<Value>();
		values.addAll(variables.values());
		values.addAll(this.values.values());
		values.addAll(variables.values());
		return values;
	}
	
	public Tree(int inp, int out) {
		points = new ArrayList<Node>();
		outputSize = out;
		this.inputSize = inp;
		variables = new HashMap<String, Value>();
		values = new HashMap<String, Value>();
		constants = new HashMap<String, Value>();
	}


	public Double[] execute(double[] inputs) {
		values.clear();
		variables.clear();
		outputs = new Double[outputSize];

		for (int i = 0; i < inputs.length; i++)
			values.put(formatInput(i), new Value(inputs[i]));

		for (int i = 0; i < outputs.length; i++)
			variables.put(formatOut(i), new Value(outputs[i]));

		values.putAll(constants);
		simulate(this);
		for (int i = 0; i < outputs.length; i++)
			outputs[i] = variables.get(formatOut(i)).getDouble();

		return outputs;
	}

	private String formatOut(int i) {
		return "out" + i;
	}

	public Parent currentParent;

	public void simulate(Tree tree) {
		int currentLine = 0;
		try {
			for (; currentLine < points.size();) {
				int lineJump = points.get(currentLine).execute(currentLine, tree, null);
				if (currentParent != null)
					lineJump = currentParent.check(lineJump, currentLine, this);
				currentLine += lineJump;
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

	public void setVariable(String var, Value value) {
		variables.put(var, value);
	}

	public Value getValue(String val2) {
		if (values.containsKey(val2))
			return values.get(val2);
		else
			return variables.get(val2);
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
		return points.size();
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
