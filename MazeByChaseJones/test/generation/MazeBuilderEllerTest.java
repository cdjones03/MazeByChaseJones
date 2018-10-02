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
	
	//Test to make sure all cells belong to the same set by the end of the maze generation.
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
	
	//Test to make sure Cells.getSet() method works.
	@Test
	public void testGetSet()
	{
		Cells cells = config.getMazecells();
		assertNotNull(cells.getSet(0, 0));
	}
	
	//Test to make sure Cells.setSet() method works.
	@Test
	public void testSetSet()
	{
		Cells cells = config.getMazecells();
		cells.setSet(0, 0, 7);
		int setNum = cells.getSet(0, 0);
		assertEquals(7, setNum);
	}
	
	//Test to make sure Cells.mergeSet() method works.
	@Test
	public void testMergeSet()
	{
		int[][] cellArr = new int[][] {{1,2,3,4},{5,6,7,8},{9,10,11,12},{13,14,15,16}};
		Cells cells = new Cells(cellArr);
		cells.setSet(0, 0, 6);
		cells.setSet(1, 0, 7);
		cells.mergeSets(0, 0, 1, 0);
		int setNum = cells.getSet(1, 0);
		assertEquals(6, setNum);
	}
	
	@Test
	public void testLastRow()
	{
		Cells cells = config.getMazecells();
		int set = cells.getSet(config.getWidth()-1, config.getHeight()-1);
		int check;
		for(int x = 0; x < config.getWidth(); x++)
		{
			check = cells.getSet(x, config.getHeight()-1);
			assertEquals(set, check);
		}
	}
}
