package LostInSpace.Game;

import java.awt.*;
import java.awt.event.*;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import LostInSpace.LevelGenerator.Entrance;
import LostInSpace.LevelGenerator.Exit;
import LostInSpace.LevelGenerator.Level;
import LostInSpace.LevelGenerator.Node;

/**
 * This class implement a simple awt prototype for testing purposes.
 * 
 * @author Attila Bujáki
 *
 */
public class GamePrototype2D extends Frame implements KeyListener {
	private Game game;
	
  /**
   * Craete the GamePrototype2D frame instance.
   **/
  public static void main(String args[]) {
    new GamePrototype2D();
  }

  /**
   * Constructor.
   **/
  GamePrototype2D() {
    super("GamePrototype2D");
    game = new Game();
	game.setLevel(Level.getLevel(3, 2));	
    setSize(800,800);
    setVisible(true);
    addKeyListener(this);
   
    addWindowListener(
    		new WindowAdapter()
            {
    			public void windowClosing(WindowEvent e)
                {
    				dispose(); 
                    System.exit(0);
                }
      }
    );
  }

    private Random rand = new Random(3);  
  private int getRandomCoord()
  {
	   return rand.nextInt(750) + 25;
  }
  
  
  boolean generateCoordinates = true;

  /**
   * Paint a graphical representation of the game.
   */
  public void paint(Graphics g) {
    Graphics2D g2d = (Graphics2D)g;
    
    g2d.drawString("PlayerDirection:" + game.playerDirection, 10, 10);
    
    Node firstNode = this.game.level.getEntrance();

    // Collect all the nodes.
    Set<Node> accessibleNodes = new HashSet<Node>();
    accessibleNodes.add(firstNode);
	
    boolean grows = false;
	do
	{
		int count = accessibleNodes.size();

	    Set<Node> newNodes = new HashSet<Node>();
		for(Node node : accessibleNodes)
		{
			// System.out.println("Current node:" + node.toString());
			newNodes.addAll(node.edges.values());
			for(Node newNode : newNodes)
			{
				// Generate coordinates for all the nodes (once).
			    if(generateCoordinates)
			    {
				//System.out.println(" neighbour:" + newNode.toString());
				newNode.x = getRandomCoord();
				newNode.y = getRandomCoord();
			    }
			}
		}
		accessibleNodes.addAll(newNodes);
		
		grows = count < accessibleNodes.size();
	}
	while(grows);
	
	generateCoordinates = false;

	// Draw the graphical representation
	for(Node node : accessibleNodes)
	{
		// Draw a special rectangle for the node with the player.
		if (game.playerNode.equals(node))
		{
			g2d.fillRect(node.x+2, node.y+2, 6, 6);
		}
		
		// The default node color is black.
	    g2d.setColor(new Color(0.0f, 0.0f, 0.0f));
		
	    // Paint the Entrance green.
		if(node instanceof Entrance)
	        g2d.setColor(new Color(0.0f, 1f, 0.0f));

		// Paint the Exit blue.
		if(node instanceof Exit)
	        g2d.setColor(new Color(0.0f, 0.0f, 1.0f));
		
		// Paint an oval for each node.
	    g2d.drawOval(node.x, node.y, 10, 10);
	    
	    // Draw the edges between the nodes.
	    for(Long key : node.edges.keySet())
	    {
	    	Node neighbour = node.edges.get(key);
	    	if((key == game.playerDirection && node.equals(game.playerNode)) || (((key + 2)%4) == game.playerDirection &&  neighbour.equals(game.playerNode)))
	    	{
	    		// If the current node is the "forward movement node", then paint it red.
				System.out.println(" red:" + node.toString() + " <-> " + neighbour.toString() );
			    g2d.setColor(new Color(1f, 0.0f, 0.0f));	    		
	    	}
	    	else
	    	{	
	    		// The default edge color is grey.
		        g2d.setColor(new Color(0.5f, 0.5f, 0.5f));	    		
    	    }
	    	
	    	// Draw the current edge.
		    g2d.drawLine(node.x+5, node.y+5, neighbour.x+5, neighbour.y+5);	    	
	    }
	}
  }

/**
 * Keyboard handling for the game prototype.
 */
@Override
public void keyPressed(KeyEvent arg0) {
	switch (arg0.getKeyCode()) {
	case 38: // Up key.
		   if(!game.goForward())
		   {
			   System.out.println("Wall is in front of the player.");
		   }
		break;
	case 39: // Left key.
		   game.turnRight();
		break;
	case 40: // Down key.
		 // Nothing to do.
		break;
	case 37: // Right key.
		   game.turnLeft();	
		
		break;

	default:
		break;
	}
	
	// The game graph must be repainted after movements. 
	// The node of the player or the "forward movement edge" could have changed.
	this.repaint();
	
}

@Override
public void keyReleased(KeyEvent arg0) {
	// nothing to do.
	
}

@Override
public void keyTyped(KeyEvent arg0) {
	// nothing to do.
	
}  
}