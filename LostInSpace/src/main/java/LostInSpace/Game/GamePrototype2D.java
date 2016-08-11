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

public class GamePrototype2D extends Frame implements KeyListener {
	private Game game;
	
  /**
   * Instantiates an Example01 object.
   **/
  public static void main(String args[]) {
    new GamePrototype2D();
  }

  /**
   * Our Example01 constructor sets the frame's size, adds the
   * visual components, and then makes them visible to the user.
   * It uses an adapter class to deal with the user closing
   * the frame.
   **/
  public GamePrototype2D() {
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

  Random rand = new Random(3);
  
  private int getRandomCoord()
  {
	   return rand.nextInt(750) + 25;
  }
  
  
  boolean generateCoordinates = true;
  /**
   * The paint method provides the real magic.  Here we
   * cast the Graphics object to Graphics2D to illustrate
   * that we may use the same old graphics capabilities with
   * Graphics2D that we are used to using with Graphics.
   **/
  public void paint(Graphics g) {
    Graphics2D g2d = (Graphics2D)g;
    
    g2d.drawString("PlayerDirection:" + game.playerDirection, 10, 10);
    
    // set coordinates for each node
    Node firstNode = this.game.level.getEntrance();

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

	for(Node node : accessibleNodes)
	{
		if (game.playerNode.equals(node))
		{
			g2d.fillRect(node.x+2, node.y+2, 6, 6);
		}
		
	    g2d.setColor(new Color(0.0f, 0.0f, 0.0f));
		
		if(node instanceof Entrance)
	        g2d.setColor(new Color(0.0f, 1f, 0.0f));

		if(node instanceof Exit)
	        g2d.setColor(new Color(0.0f, 0.0f, 1.0f));
		
	    g2d.drawOval(node.x, node.y, 10, 10);
	    
	    for(Long key : node.edges.keySet())
	    {
	    	Node neighbour = node.edges.get(key);
	    	if((key == game.playerDirection && node.equals(game.playerNode)) || (((key + 2)%4) == game.playerDirection &&  neighbour.equals(game.playerNode)))
	    	{
				System.out.println(" red:" + node.toString() + " <-> " + neighbour.toString() );
			    g2d.setColor(new Color(1f, 0.0f, 0.0f));	    		
	    	}
	    	else
	    	{	
		        g2d.setColor(new Color(0.5f, 0.5f, 0.5f));	    		
    	    }
		    g2d.drawLine(node.x+5, node.y+5, neighbour.x+5, neighbour.y+5);	    	
	    }
	}
  }

@Override
public void keyPressed(KeyEvent arg0) {
	switch (arg0.getKeyCode()) {
	case 38:
		   if(!game.goForward())
		   {
			   System.out.println("Wall is in front of the player.");
		   }
		break;
	case 39:
		   game.turnRight();
		break;
	case 40:	
		break;
	case 37:
		   game.turnLeft();	
		
		break;

	default:
		break;
	}
	
	this.repaint();
	
}

@Override
public void keyReleased(KeyEvent arg0) {
	// TODO Auto-generated method stub
	
}

@Override
public void keyTyped(KeyEvent arg0) {
	// TODO Auto-generated method stub
	
}  
}