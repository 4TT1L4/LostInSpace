package LostInSpace.LevelGenerator;

import static org.junit.Assert.*;

import org.junit.Test;

public class LevelGeneratorTests {
	
	@Test
	public void test_levelGenerator_ThrowsNoException() {
		LevelGenerator generator = new LevelGenerator();
		generator.generateLevel(0, 3);
	}

}
