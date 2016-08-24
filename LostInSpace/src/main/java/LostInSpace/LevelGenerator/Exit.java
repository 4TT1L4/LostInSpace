package LostInSpace.LevelGenerator;

import LostInSpace.Game.LevelEvent;

/**
 * Special node for the Exit node.
 * 
 * @author Attila Bujáki
 *
 */
public class Exit extends Node{
	
	private LevelEvent eventHandler;

	public void setEventHandler(LevelEvent eventHandler)
	{
		this.eventHandler = eventHandler;
	}
	
	
	@Override
	public void NodeEntered() {
		super.NodeEntered();
		
		if(eventHandler != null)
		{
			 System.out.println("Notify NodeEntered.");
		     eventHandler.ExitEntered();
		}
		else
		{
			System.out.println("NodeEntered - Event handler is null.");
		}
	};
	
	@Override
	public String toString() {
		return "ExitNode";
	}

}
