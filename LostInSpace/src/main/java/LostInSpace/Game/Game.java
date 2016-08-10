package LostInSpace.Game;

import LostInSpace.LevelGenerator.ILevel;
import LostInSpace.LevelGenerator.Level;
import LostInSpace.LevelGenerator.Node;

public class Game {
	ILevel level;
	
	Node playerNode;
	int playerDirection = 0;
	
	public void setLevel(Level level) {
		this.level = level;
		playerDirection = 0;
		playerNode = level.getEntrance();
	}
	
	public void turnLeft()
	{
		if(playerDirection == 0)
		{
			playerDirection = this.level.getDirections()-1;
		}
		else
		{
			playerDirection--;
		}
	}
	
	public boolean goForward()
	{
	     if(this.playerNode.isEdgePresent(playerDirection))
	     {
	    	 return false;
	     }
	     else
	     {
	    	 playerNode = this.playerNode.getNode(playerDirection);
	 		 return true;
	     }
	}	
	
	public void turnRight()
	{

		if(playerDirection == this.level.getDirections()-1)
		{
			playerDirection = 0;
		}
		else
		{
			playerDirection++;
		}
	}
	
}
