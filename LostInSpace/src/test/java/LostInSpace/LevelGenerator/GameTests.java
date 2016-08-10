package LostInSpace.LevelGenerator;

import static org.junit.Assert.*;

import org.junit.Test;

import LostInSpace.Game.Game;

public class GameTests {

	@Test
	public void test() {
		int testSize = 5;
		int testSeed = 5;
		Game game = new Game();
		game.setLevel(new Level().generateNodes(testSize)
                                 .createSpanningTree(testSeed, 4)
                                 .addFurtherEdges(3, testSeed)
                                 .markEntranceAndExit(testSeed));
	}

}
