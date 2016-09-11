package components;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.Stack;

public class Mutator {
	private SecureRandom random;
	private double mutChance;
	private MutationHelper helper;

	public Mutator(SecureRandom random, double mutChance) {
		this.random = random;
		this.mutChance = mutChance;
		
	}


	public Tree mutateTree(Tree oldTree) {
		helper = new MutationHelper(random, oldTree.getDefaultVariables(),oldTree.getDefaultNames());

		Tree tree = new Tree(oldTree.inputSize, oldTree.outputSize);

		List<Point> points = mutateList(oldTree.getPoints());

		tree.setPoints(points);

		return tree;

	}

	public Point mutatePoint(Point p) {

		if (p instanceof Node) {
			Node n = (Node) p;
			Node newPoint = new Node(n);
			if (m())
				newPoint.var = helper.getRandomVariable();
			if (m())
				newPoint.val1 = helper.getRandomValue();
			if (m())
				newPoint.val2 = helper.getRandomValue();
			if (m())
				newPoint.operator = helper.getRandomOperator();
			helper.addVar(newPoint.var);
			return newPoint;
		}
		if (p instanceof Conditional) {
			Conditional nP = (Conditional) p;
			Conditional n = new Conditional(nP);
			if (m())
				n.variable = helper.getRandomVariable();
			if (m())
				n.NOT = !n.NOT;
			n.points = this.mutateList(n.points);
			return n;
		}
		return null;
	}

	private List<Point> mutateList(List<Point> points) {

		List<Point> copy = new ArrayList<Point>();
		Stack<Integer> toAdd = new Stack<Integer>();
		Stack<Integer> toKill = new Stack<Integer>();

		int counter = 0;
		for (Point p : points) {
			copy.add(mutatePoint(p));
			if (m())
				toAdd.add(counter);
			if (m())
				toKill.add(counter);
			counter++;
			
		}
		for (int i = 0; i < toAdd.size(); i++) {
			Integer val = toAdd.pop();
			Point point = helper.getNewPoint();
			if (point instanceof Conditional) {
				if (val < copy.size()) {
					Point p = copy.get(val);
					copy.remove(p);
					((Conditional) point).points.add(p);
				}
			}
			copy.add(val, point);
		}
		for(int i = 0; i <toKill.size(); i ++){
			int lol = (int)toKill.pop();
			Point p =copy.remove(lol);
			if(p instanceof Conditional){
				List<Point> pList = ((Conditional) p).points;
				copy.addAll(lol, pList);
			}
		}
		return copy;
	}

	public boolean m() {
		if (random.nextFloat() < mutChance)
			return true;
		else
			return false;
	}
}
