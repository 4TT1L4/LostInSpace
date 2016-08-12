package LostInSpace.LevelGenerator;

import java.security.InvalidParameterException;
import java.util.HashMap;
import java.util.Map;

public class Node {

	public static int LEFT = 0;
	public static int UP = 1;
	public static int RIGHT = 2;
	public static int DOWN = 3;

	public Map<Long, Node> edges = new HashMap<Long, Node>();
	
	static int nextID = 1;
	
	final int id;
	
	// DEBUG 
	public int x;
	public int y;
	
	public Node()
	{
		id = nextID;
		nextID++;		
	}
		
	public boolean isEdgePresent(long direction)
	{
		return edges.containsKey(Long.valueOf(direction));
	}
	
	public void addEdge(long direction, Node node) {
		if(edges.containsKey(Long.valueOf(direction)))
		{
			throw new InvalidParameterException();
		}
		
		edges.put(Long.valueOf(direction), node);
	}
		
	public Node getNode(long direction)
	{
		if(!edges.containsKey(Long.valueOf(direction)))
		{
			return null;
		}
			
		return edges.get(Long.valueOf(direction));
	}
	
	@Override
	public String toString() {
		return "Node(" + id  + ")";
	}

}
