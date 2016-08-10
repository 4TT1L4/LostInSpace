package LostInSpace.LevelGenerator;

import java.security.InvalidParameterException;
import java.util.HashMap;
import java.util.Map;

public class Node {

	Map<Long, Node> edges = new HashMap<Long, Node>();
	
	static int nextID = 1;
	
	final int id;
	
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
	
	@Override
	public String toString() {
		return "Node(" + id  + ")";
	}

	public Node getNode(long playerDirection) {
		return edges.get(Long.valueOf(playerDirection));
	}

}
