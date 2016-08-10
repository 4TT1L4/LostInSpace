package LostInSpace.LevelGenerator;

public class LevelGenerator implements ILevelGenerator {

	public ILevel generateLevel(long seed, long size) {
		Level level = new Level();
		
		level.generateNodes(size)
             .createSpanningTree(seed, 4)
		     .addFurtherEdges(2, seed);
		
		// generate entrance and exit
		
		return level;
	}
	

}
