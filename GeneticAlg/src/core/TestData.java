package core;

import java.util.ArrayList;
import java.util.List;

import components.TestCase;

public class TestData {
	public List<TestCase> cases;

	public TestData() {
		cases = new ArrayList<TestCase>();
	}

	public List<TestCase> getTestCases() {
		return cases;
	}
}
