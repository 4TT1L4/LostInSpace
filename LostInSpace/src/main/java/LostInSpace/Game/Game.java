package LostInSpace.Game;

import LostInSpace.LevelGenerator.ILevel;
import LostInSpace.LevelGenerator.Level;
import LostInSpace.LevelGenerator.Node;

/**
 * The game class implements the basic game logic. It makes possible to control the player in the current level through
 * providing the possibility to execute different actions like; turning left, right or moving forward in the actual level.
 * 
 * @author Attila Bujáki
 *
 */
public class Game {
	ILevel level;
	
	volatile Node playerNode;
	int playerDirection = 0;
	
	/**
	 * Set the active level.
	 * 
	 * @param level The level to be used in the game.
	 */
	public void setLevel(Level level) {
		this.level = level;
		playerDirection = 0;
		playerNode = level.getEntrance();
	}
	
	/**
	 * Turn the player left.
	 */
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
	
	/**
	 * Make the player try to go forward. If it is possible, then updates the actual position node of the player.
	 * 
	 * @return true, if the movement was successful, otwerwise false.
	 */
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

	/**
	 * Turn the player right.
	 */
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
