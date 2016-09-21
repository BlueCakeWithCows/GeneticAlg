package components.mathsolver;

import java.util.regex.Pattern;

import components.basic.Node;
import components.basic.Tree;

public class TreeParser {

	public static Tree getTree(String inputTree, int length, Integer numberOfOutputs) {
		String[] lines = inputTree.split("\n");
		Tree tree = new Tree(length, numberOfOutputs);

		for (String s : lines) {
			Node n = parseLine(s);
			if (n != null)
				tree.addPoint(n);
		}

		return tree;

	}

	private static Node parseLine(String s) {
		Node n = null;
		if (s.startsWith("IF")) {
			String ignoreIf = s.replaceFirst("IF", "");
			String[] firstSplit = ignoreIf.split(Pattern.quote(">"));
			String var = firstSplit[0];
			String[] secondSplit = ignoreIf.split("THEN");
			String val1 = secondSplit[0];
			String val2 = secondSplit[1];
			// n = new Conditional(var, val1, val2);
		} else {
			String[] varSplit = s.split(Pattern.quote("="));
			String variable = varSplit[0];

			// int operator = Operator.getInt(s.replaceFirst(varSplit[0]+"=",
			// ""));
			// String[] valueString = s.replaceFirst(varSplit[0]+"=",
			// "").split(Pattern.quote(Operator.getOperator(operator)));
			// String value1 = valueString[0];
			// String value2 = valueString[1];
			// n = new Function(variable, operator, value2, value1);
		}
		return n;
	}
}
