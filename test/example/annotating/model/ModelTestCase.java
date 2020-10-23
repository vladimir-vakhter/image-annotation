package example.annotating.model;

import org.junit.Before;

public abstract class ModelTestCase {

	protected Model model;
	
	@Before
	public void setUp() {
		model = new Model();
	}
}
