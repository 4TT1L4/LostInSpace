package LostInSpace.LevelGenerator;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;

public class LevelTests {

	@Test
	public void test_generatedLevelSize_isCorrect() {
		final int testSize = 4;
		Level level = new Level().generateNodes(testSize);
		assertEquals(testSize * testSize, level.nodes.size()); 
	}
	
	@Test
	public void test_generatedLevelSize_isCorrect2() {
		final int testSize = 6;
		Level level = new Level().generateNodes(testSize);
		assertEquals(testSize * testSize, level.nodes.size()); 
	}
	
	@Test
	public void test_generatedLevel_AllNodesAreAccessible() {
		final int testSize = 6;
		final int testSeed = 3;
		Level level = new Level().generateNodes(testSize).createSpanningTree(testSeed, 4);
		
		Set<Node> accessibleNodes = getAccessibleNodes(level);

		assertTrue(accessibleNodes.size() == level.nodes.size());	
	}

	
	@Test
	public void test_generatedLevel_AllNodesAreAccessibleAfterAddingEdges() {
		final int testSize = 6;
		final int testSeed = 3;
		Level level = new Level().generateNodes(testSize)
				                 .createSpanningTree(testSeed, 4)
				                 .addFurtherEdges(3, testSeed);
		
		Set<Node> accessibleNodes = getAccessibleNodes(level);

		assertTrue(accessibleNodes.size() == level.nodes.size());	
	}
	
	@Test
	public void test_generatedLevel_AllNodesAreAccessibleAfterMarkingEntranceAndExit() {
		final int testSize = 6;
		final int testSeed = 3;
		Level level = new Level().generateNodes(testSize)
				                 .createSpanningTree(testSeed, 4)
				                 .addFurtherEdges(3, testSeed)
				                 .markEntranceAndExit(testSeed);
		
		Set<Node> accessibleNodes = getAccessibleNodes(level);

		assertTrue(accessibleNodes.size() == level.nodes.size());	
	}

	private Set<Node> getAccessibleNodes(Level level) {
		Node firstNode = (Node)level.nodes.toArray()[0];

	    Set<Node> accessibleNodes = new HashSet<Node>();
	    accessibleNodes.add(firstNode);
		
	    boolean grows = false;
		do
		{
			int count = accessibleNodes.size();

		    Set<Node> newNodes = new HashSet<Node>();
			for(Node node : accessibleNodes)
			{
				newNodes.addAll(node.edges.values());
			}
			accessibleNodes.addAll(newNodes);
			
			grows = count < accessibleNodes.size();
		}
		while(grows);
		return accessibleNodes;
	}

}
