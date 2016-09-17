package components.mathsolver;

public abstract class Node {
	public String[] values;
	public String[] variables;
	
	
	public Node(int variables, int values){
		this.variables = new String[variables];
		this.values = new String[values];
	}
	
	public abstract void compute(Tree tree);
	public int getSize() {
		return 1;
	}


	public int getLineJump() {
		return 1;
	}

	public void copy(String[] variables, String[] values) {
		for(int i = 0 ; i < this.variables.length; i ++){
			this.variables[i] = variables[i];
		}
		
		for(int i = 0 ; i < this.values.length; i ++){
			this.values[i] = values[i];
		}
	}

	public abstract Node getCopy();
}
