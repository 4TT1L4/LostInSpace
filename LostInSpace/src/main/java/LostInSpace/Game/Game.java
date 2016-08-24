package LostInSpace.Game;

import java.util.ArrayList;
import java.util.List;

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
public class Game implements LevelEvent {
	ILevel level;
	
	List<LevelEvent> levelEventListeners = new ArrayList<LevelEvent>();
	
	volatile Node playerNode;
	public Node getPlayerNode()
	{
		return playerNode;
	}
	
	int playerDirection = 0;
	
	/**
	 * Set the active level.
	 * 
	 * @param level The level to be used in the game.
	 */
	void setLevel(Level level) {
		level.setEventHandler(this);
		this.level = level;
		playerDirection = 0;
		playerNode = level.getEntrance();
	}

	/**
	 * Generate a new level based on the passed seed in the given size.
     *  
     * @param size The size of the level. The number of the nodes is going to be the square of the passed integer.
     * @param seed The seed to be used in the random generators.
     */
	public void generateLevel(int size, int seed)
	{
		this.level = new Level()
				                .generateNodes(size)
                                .createSpanningTree(seed, 4)
                                .addFurtherEdges(size, seed)
                                .markEntranceAndExit(seed)
				                .setEventHandler(this);
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
	    	 playerNode.NodeEntered();

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

    public void AddLevelEventListener(LevelEvent eventListener)
    {
    	this.levelEventListeners.add(eventListener);
    }
	
	@Override
	public void ExitEntered() {
	  	System.out.println("[Game.ExitEntered]");
		for(LevelEvent levelEventListener : levelEventListeners)
		{
		    try
		    {
			  	System.out.println("Notify ExitEntered to event handler: " + levelEventListener);
		    	levelEventListener.ExitEntered();
		    }
		    catch (Exception exception)
		    {
		    	System.err.println("Exception in ExitEntered event handler: " + levelEventListener + " Exception:" + exception);
		    }
		}		
	}
	
}
