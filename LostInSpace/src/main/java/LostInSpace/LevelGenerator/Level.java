package LostInSpace.LevelGenerator;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class Level implements ILevel {
	
	Node entrance;
	Node exit;
	Set<Node> nodes = new HashSet<Node>();
	private int directions;
	
	Level generateNodes(long size)
	{
	     for(int i = 0; i < size * size; i++)
	     {
	    	 nodes.add(new Node());
	     }
	     
	     return this;
	}

	public Level createSpanningTree(long seed, int directions) {
		this.directions = directions;
		
		List<Node> nodesList = new ArrayList<Node>();
		nodesList.addAll(nodes);
		Random rand = new Random(seed);
		java.util.Collections.shuffle(nodesList, rand);
		
		for(int i = 0; i < nodesList.size(); i ++)
		{
			boolean added = false;
			
			do
			{
				int direction1 = Math.abs(rand.nextInt()) % directions;
			    int direction2 = Math.abs(rand.nextInt()) % directions;
			    int targetNodeIndex = Math.abs(rand.nextInt()) % nodesList.size();
			    Node currentNode = nodesList.get(i);
			    Node targetNode = nodesList.get(targetNodeIndex);
			    
			    if(currentNode.equals(targetNode))
			    {
			    	// The edge should go between different nodes.
			    	continue;
			    }
			    
			    if (!currentNode.isEdgePresent(direction1) && !targetNode.isEdgePresent(direction2))
			    {
			    	currentNode.addEdge(direction1, targetNode);
			    	targetNode.addEdge(direction2, currentNode);
			    	added = true;
			    }
			}
			while(!added);
		}
		
		return this;
	}

	public Level addFurtherEdges(int i, long seed)
	{
	    // TODO: implement
	
		return this;
	}

	public Level markEntranceAndExit(long seed)
	{
		List<Node> nodesList = new ArrayList<Node>();
		nodesList.addAll(nodes);
		
		boolean marked = false;
		Random rand = new Random(seed);
		
		do
		{
		    int entranceIndex = Math.abs(rand.nextInt()) % nodesList.size();
		    int exitIndex = Math.abs(rand.nextInt()) % nodesList.size();
		    
		    if(entranceIndex != exitIndex)
		    {
		    	this.entrance = nodesList.get(entranceIndex);
		    	this.exit = nodesList.get(exitIndex);
		    	marked = true;
		    }
		    
		}
		while(!marked);	
	    
		return this;
	}

	@Override
	public Node getEntrance() {
		return entrance;
	}

	@Override
	public int getDirections() {
		return directions;
	}
}
