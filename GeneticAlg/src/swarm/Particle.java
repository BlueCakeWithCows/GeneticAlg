package swarm;

import java.util.HashMap;

public class Particle {

	// A = ADD, B = SUBTRACT, C = MULTIPLY, D = DIVIDE
	// 0 = Terminating, 1 = Function
	// # Index
	// # Value
	// So, 1A0, Binary(Function),Addition,Binary(Terminating),4 (4th arg), 0
	// (value)

	public HashMap<String, Dimension> map;

	public Particle() {
		map = new HashMap<String, Dimension>(32);
	}

	private void solve(double[] inputs) {
		
		
		
	}
}
