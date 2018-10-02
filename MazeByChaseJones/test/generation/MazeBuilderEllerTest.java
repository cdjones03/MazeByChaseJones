package generation;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import generation.Order.Builder;

/**
 * @author chasejones
 */

public class MazeBuilderEllerTest {
	private MazeFactory mazeFac;
	private StubOrder stub;
	private MazeConfiguration config;
	
	/**
	 * General setup for default, Eller build, 0 skill level, perfect maze.
	 * Runs before every test.
	 */
	@Before
	public void setUp()
	{
		mazeFac = new MazeFactory(true); //Makes maze generation deterministic, for purposes of testing.
		stub = new StubOrder(Builder.Eller, 0, true); //Builder type, skill level, whether perfect or not (i.e no rooms or yes rooms).
		mazeFac.order(stub);
		mazeFac.waitTillDelivered();
		config = stub.getMazeConfiguration();
	}
	
	/**
	 * Test to make sure all cells belong to the same set by the end of the maze generation.
	 */
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
	
	/**
	 * Test to make sure Cells.getSet() method works.
	 */
	@Test
	public void testGetSet()
	{
		Cells cells = config.getMazecells();
		assertNotNull(cells.getSet(0, 0));
	}
	
	/**
	 * Test to make sure Cells.setSet() method works.
	 */
	@Test
	public void testSetSet()
	{
		Cells cells = config.getMazecells();
		cells.setSet(0, 0, 7);
		int setNum = cells.getSet(0, 0);
		assertEquals(7, setNum);
	}
	
	/**
	 * Test to make sure Cells.mergeSet() method works.
	 */
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
	
	/**
	 * Test to make sure all cells in the last row become part of the same set.
	 */
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
	
	/**
	 * Test to makes sure outside borders are not destroyed by algorithm.
	 */
	@Test
	public void testBorders()
	{
		Cells cells = config.getMazecells();
		for(int x = 0; x < config.getWidth(); x++)
		{
			Wall border = new Wall(x, 0, CardinalDirection.North);
			assertFalse(cells.canGo(border));
			Wall borderBottom = new Wall(x, config.getHeight()-1, CardinalDirection.South);
			assertFalse(cells.canGo(borderBottom));
		}
		for(int y = 0; y < config.getHeight(); y++)
		{
			Wall borderLeft = new Wall(0, y, CardinalDirection.West);
			assertFalse(cells.canGo(borderLeft));
			Wall borderRight = new Wall(config.getWidth()-1, y, CardinalDirection.East);
			assertFalse(cells.canGo(borderRight)); 
		}
		assertTrue(true);
	}
}
