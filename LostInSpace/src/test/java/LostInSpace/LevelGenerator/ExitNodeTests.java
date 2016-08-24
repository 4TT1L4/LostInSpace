package LostInSpace.LevelGenerator;

import static org.junit.Assert.*;

import org.junit.Test;

import LostInSpace.Game.LevelEvent;

public class ExitNodeTests {
	Exit exit = new Exit();

	public static boolean exitEntered = false;
	
	@Test
	public void test() {
		exitEntered = false;
		
		this.exit.setEventHandler(new LevelEvent() {
			
			@Override
			public void ExitEntered() {
				ExitNodeTests.exitEntered = true;
			}
		});
		
		exit.NodeEntered();
		
		assertTrue(exitEntered);
	}

}
