package generation;

import static org.junit.Assert.*;

import java.util.Iterator;

import org.junit.Before;
import org.junit.Test;

import generation.Order.Builder;

public class MazeBuilderEllerTest {
	/*Test ideas:
	 * make sure mazebuildereller delivers an maze object
	 * make sure all cells belong to same set by end of algorithm
	 * test getSet method
	 * test setSet method
	 * test mergeSet method
	 */
	private MazeFactory mazeFac;
	private StubOrder stub;
	private MazeConfiguration config;
	
	@Before
	public void setUp()//General setup for default, 0 skill level, perfect maze.
	{
		mazeFac = new MazeFactory(true); //Makes maze generation deterministic, for purposes of testing.
		stub = new StubOrder(Builder.Eller, 0, true); //Builder type, skill level, whether perfect or not (i.e no rooms or yes rooms)
		mazeFac.order(stub);
		mazeFac.waitTillDelivered();
		config = stub.getMazeConfiguration();
	}
	
	@Test
	public void testSameSetByEnd()
	{
		Cells cells = config.getMazecells();
		int setNum = cells.getSet(0, 0);
		for(int x = 0; x < config.getWidth(); x++)
		{
			for(int y = 0; y < config.getHeight(); y++)
			{
				if(cells.getSet(x, y) != setNum)
					assertFalse(false);
			}
		}
		assertTrue(true);
	}
	
	@Test
	public void testGetSet()
	{
		
	}
	
	@Test
	public void testSetSet()
	{
		
	}
	
	@Test
	public void testMergeSet()
	{
		
	}

}
