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
    setSize(1600,800);
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
	   return rand.nextInt(750) + 50;
  }
  
  
  boolean generateCoordinates = true;

  /**
   * Paint a graphical representation of the game.
   */
  public void paint(Graphics g) {
    Graphics2D g2d = (Graphics2D)g;
    
    g2d.drawString("PlayerDirection:" + game.playerDirection, 30, 50);
    
    Node firstNode = this.game.level.getEntrance();

    drawLevelGraph(g2d, firstNode);
    drawLevelTopView(g2d, game.playerNode, game.playerDirection);
  }

private void drawLevelTopView(Graphics2D g2d, Node playerNode, int playerDirection) {
	int x0 = 1200;
	int y0 = 600;
	
	// Draw the top view of the level
	g2d.fillRect(x0-1 , y0 - 1, 4, 4);
	
	drawNode(g2d, playerNode, playerDirection, x0, y0, 0, 0, 0 , 0);
	
}

private void drawNode(Graphics2D g2d, Node node, int playerDirection, int x0, int y0, int depth, int top, int left, int right) {
	
	if(node == null)
	{
		return;
	}
	
	if(depth >= 10) 
	{
		// Don't go deeper than 10 nodes.
		return;
	}

	if( left > 1)
	{
		// Don't go more left than top
		return;
	}
	
	if( right > 1)
	{
		// Don't go more right than top
		return;
	}
	
	
	
	// 1. draw the current node. Easy.
	// player direction is always on top.
	int topDirection = playerDirection;
	int rightDirection = (playerDirection + 1) % 4;
	int bottomDirection = (playerDirection + 2) % 4;
	int leftDirection = (playerDirection + 3) % 4;
		
    if(!node.isEdgePresent(topDirection)) drawTopWall(g2d, x0, y0, topDirection);
	if(!node.isEdgePresent(rightDirection)) drawRightWall(g2d, x0, y0, rightDirection);
	if(!node.isEdgePresent(bottomDirection)) drawBottomWall(g2d, x0, y0, bottomDirection);
	if(!node.isEdgePresent(leftDirection)) drawLeftWall(g2d, x0, y0, leftDirection);

    if(node.isEdgePresent(topDirection)) drawNode(g2d, node.getNode(topDirection), playerDirection, x0, y0 - 10, depth + 1, top +1, left, right);
    if(node.isEdgePresent(rightDirection)) drawNode(g2d, node.getNode(rightDirection), playerDirection, x0 + 10, y0 , depth + 1, top, left, right+1);
    if(node.isEdgePresent(leftDirection)) drawNode(g2d, node.getNode(leftDirection), playerDirection, x0 - 10, y0 , depth + 1, top, left +1, right);
	
}

private void drawLeftWall(Graphics2D g2d, int x, int y, int debug)
{
    g2d.setColor(new Color(0.0f, 0.0f, 0.0f));
	g2d.fillRect(x - 5 , y - 5, 2, 10);
    g2d.setColor(new Color(0.0f, 1.0f, 0.0f));
    g2d.drawString(""+ debug, x - 13, y-3);
}
private void drawTopWall(Graphics2D g2d, int x, int y, int debug)
{
    g2d.setColor(new Color(0.0f, 0.0f, 0.0f));
	g2d.fillRect(x - 5 , y - 5, 10, 2);
    g2d.setColor(new Color(0.0f, 1.0f, 0.0f));
    g2d.drawString(""+ debug, x - 3, y-13);
}
private void drawRightWall(Graphics2D g2d, int x, int y, int debug)
{
    g2d.setColor(new Color(0.0f, 0.0f, 0.0f));
	g2d.fillRect(x + 4 , y - 4, 2, 10);
    g2d.setColor(new Color(0.0f, 1.0f, 0.0f));
    g2d.drawString(""+ debug, x + 10, y-3);
}
private void drawBottomWall(Graphics2D g2d, int x, int y, int debug)
{
    g2d.setColor(new Color(0.0f, 0.0f, 0.0f));
	g2d.fillRect(x - 4 , y + 4, 10, 2);
    g2d.setColor(new Color(0.0f, 1.0f, 0.0f));
    g2d.drawString(""+ debug, x-3, y + 10);
}

private void drawLevelGraph(Graphics2D g2d, Node firstNode) {
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

	    g2d.drawString(node.toString(), node.x, node.y -10);
	    
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
		    
		    // Print the direction of the edge.
		    int textCoordX = (((node.x+5) * 5) + (neighbour.x+5))/6;
		    int textCoordY = (((node.y+5) * 5) + (neighbour.y+5))/6;
		    g2d.drawString("edge:" + key, textCoordX+1, textCoordY+1);
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