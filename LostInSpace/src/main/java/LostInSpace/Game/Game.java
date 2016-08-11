package LostInSpace.Game;

import LostInSpace.LevelGenerator.ILevel;
import LostInSpace.LevelGenerator.Level;
import LostInSpace.LevelGenerator.Node;

public class Game {
	ILevel level;
	
	volatile Node playerNode;
	int playerDirection = 0;
	
	public void setLevel(Level level) {
		this.level = level;
		playerDirection = 0;
		playerNode = level.getEntrance();
	}
	
	public void turnLeft()
	{
		System.out.println(" turn left - old direction: " + playerDirection);
		if(playerDirection == 0)
		{
			playerDirection = this.level.getDirections()-1;
		}
		else
		{
			playerDirection--;
		}
		
		System.out.println(" turn left - new direction: " + playerDirection);
	}
	
	public boolean goForward()
	{
		 System.out.println(" goForward - direction: " + playerDirection);
		 for(Long key : this.playerNode.edges.keySet())
		 {
			 System.out.println(" goForward - edge:" + key + " leading to:" + this.playerNode.edges.get(key));
		 }
	     if(!this.playerNode.isEdgePresent(playerDirection))
	     {
			 System.out.println(" goForward - no such edge. direction: " + playerDirection);			 
	    	 return false;
	     }
	     else
	     {
			 System.out.println(" goForward - edge found. direction: " + playerDirection + "moving to:" + this.playerNode.getNode(playerDirection));	
	    	 playerNode = this.playerNode.getNode(playerDirection);

			 for(Long key : this.playerNode.edges.keySet())
			 {
				 System.out.println(" goForward after movement - edge:" + key + " leading to:" + this.playerNode.edges.get(key));
			 }
	 		 return true;
	     }
	}	
	
	public void turnRight()
	{
		System.out.println(" turn right - old direction: " + playerDirection);

		if(playerDirection == this.level.getDirections()-1)
		{
			playerDirection = 0;
		}
		else
		{
			playerDirection++;
		}
		System.out.println(" turn right - new direction: " + playerDirection);
	}
	
}
