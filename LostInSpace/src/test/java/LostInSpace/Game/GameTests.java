package LostInSpace.Game;

import static org.junit.Assert.*;

import org.junit.Test;

import LostInSpace.Game.Game;
import LostInSpace.LevelGenerator.Level;
import LostInSpace.LevelGenerator.Node;

public class GameTests {

	@Test
	public void Game_ThrowsNoException_AfterCommonOperations() {
		int testSize = 5;
		int testSeed = 5;
		Game game = new Game();
		game.setLevel(new Level().generateNodes(testSize)
                                 .createSpanningTree(testSeed, 4)
                                 .addFurtherEdges(3, testSeed)
                                 .markEntranceAndExit(testSeed));
		game.turnLeft();
		game.turnLeft();
		game.turnLeft();

		game.turnRight();
		game.turnRight();
		
		game.goForward();	

		game.turnRight();
		game.turnRight();

		game.goForward();
	}

	@Test
	public void Game_AfterStart_EntranceCanBeLeft() {
		int testSize = 5;
		int testSeed = 5;
		Game game = new Game();
		game.setLevel(new Level().generateNodes(testSize)
                                 .createSpanningTree(testSeed, 4)
                                 .addFurtherEdges(3, testSeed)
                                 .markEntranceAndExit(testSeed));

		boolean entranceCouldBeLeft = false;
		for(int i = 0; i < 4 ; i++)
		{
			if(game.goForward())
			{
				entranceCouldBeLeft = true;
				break;
			}
			
			game.turnLeft();
		}
	
		assertTrue(entranceCouldBeLeft);
	}

	@Test
	public void Game_generateLevel_NoExceptionIsThrown() {
		new Game().generateLevel(5, 5);
	}

	@Test
	public void Game_AfterLeavingEntrance_TheNeighbourCouldBeLeft() {
		int testSize = 5;
		int testSeed = 5;
		Game game = new Game();
		game.setLevel(new Level().generateNodes(testSize)
                                 .createSpanningTree(testSeed, 4)
                                 .addFurtherEdges(3, testSeed)
                                 .markEntranceAndExit(testSeed));

		Node EntranceNode = game.getPlayerNode();
		
		boolean entranceCouldBeLeft = false;
		for(int i = 0; i < 4 ; i++)
		{
			if(game.goForward())
			{
				break;
			}
			
			game.turnLeft();
		}
		
		// The entrance should be left by now. It has only a single neighbour.
		Node OnlyNodeNextToEntrance = game.getPlayerNode();

		boolean newNodeEntered = false;
		if(game.goForward())
		{
			newNodeEntered = true;
		}
		else
		{
			game.turnLeft();
			if(game.goForward())
			{
				newNodeEntered = true;
			}
			else			
			{
				game.turnRight();
				game.turnRight();
				if(game.goForward())
				{
					newNodeEntered = true;					
				}
			}
		}
		
		assertTrue(newNodeEntered);
		assertTrue(!game.getPlayerNode().equals(EntranceNode));
		assertTrue(!game.getPlayerNode().equals(OnlyNodeNextToEntrance));
		
		
	}

}
