package generation;

import static org.junit.Assert.*;
import generation.Order.Builder;
import org.junit.Test;
import gui.Constants;
import org.junit.Before;

/**
 * @author chasejones
 */

public class MazeFactoryTest {
	
	private MazeFactory mazeFac;
	private StubOrder stub;
	private MazeConfiguration config;
	
	/**
	 * General setup for default, DFS build, 0 skill level, perfect maze.
	 * Runs before every test.
	 */
	@Before
	public void setUp()
	{
		mazeFac = new MazeFactory(true); //Makes maze generation deterministic, for purposes of testing.
		stub = new StubOrder(Builder.Eller, 0, true); //Builder type, skill level, whether perfect or not (i.e no rooms or yes rooms)
		mazeFac.order(stub);
		mazeFac.waitTillDelivered();
		config = stub.getMazeConfiguration();
	}
	
	/**
	 * Test to make sure setUp method actually creates a maze.
	 */
	@Test
	public void testMazeCreation()
	{
		assertNotNull(config);
	}
	
	/**
	 * Test to make sure the maze has an exit.
	 */
	@Test
	public void checkExit()
	{
		int exitCount = 0;
		Distance dist = config.getMazedists();
		for(int x = 0; x < config.getWidth(); x++)
		{
			for(int y = 0; y < config.getHeight(); y++)
			{
				if(dist.getDistanceValue(x,y) == 1)
					exitCount++;
			}
		}
		assertEquals(1, exitCount);
	}
	
	/**
	 * Makes sure every cell in the maze can reach the exit.
	 */
	@Test
	public void exitReachableFromAnywhere()
	{
		Distance dist = config.getMazedists();
		for(int x = 0; x < config.getWidth(); x++)
		{
			for(int y = 0; y < config.getHeight(); y++)
			{
				if(dist.getDistanceValue(x, y) == Distance.INFINITY)
					assertFalse(false);
			}
		}
		assertTrue(true);
	}
	
	/**
	 * Makes sure than when deterministic is true, two consecutive 
	 * mazes produced with the exact same settings are identical.
	 */
	@Test
	public void reproduceMaze()
	{
		boolean check = true;
		StubOrder stub2 = new StubOrder(stub.getBuilder(), stub.getSkillLevel(), stub.isPerfect());
		mazeFac.order(stub2);
		mazeFac.waitTillDelivered();
		MazeConfiguration config2 = stub2.getMazeConfiguration();
		
		Distance dist, dist2;
		dist = config.getMazedists();
		dist2 = config2.getMazedists();
		
		for(int x = 0; x < config.getWidth(); x++)
		{
			for(int y = 0; y < config.getHeight(); y++)
			{
				if(dist.getDistanceValue(x,y) == dist2.getDistanceValue(x,y))
					continue;
				else
				{
					check = false;
					break;
				}
			}
			if(check)
				break;
		}
		assertTrue(check);
	}

	/**
	 * Test to makes sure that an imperfect maze has rooms.
	 */
	@Test
	public void areThereRoomsWhenExpected()//Checks if a maze that should have rooms (i.e. >= skill level 1) does have rooms.
	{
		StubOrder stubImperf = new StubOrder(Builder.DFS, 1, false); //A maze must be at least level 1 to have rooms.
		mazeFac.order(stubImperf);
		mazeFac.waitTillDelivered();
		MazeConfiguration configImperf = stub.getMazeConfiguration();
		Cells mazeCells = configImperf.getMazecells();
		assertTrue(mazeCells.areaOverlapsWithRoom(0, 0, configImperf.getWidth()-1, configImperf.getHeight()-1));
	}
	
	/**
	 * Test to make sure a maze that should not have rooms (like a level 0 maze)
	 * does not have rooms.
	 */
	@Test
	public void areThereNoRoomsWhenNotExpected()
	{
		Cells cells = config.getMazecells();
		for(int x = 0; x < config.getWidth(); x++)
		{
			for(int y = 0; y < config.getHeight(); y++)
			{
				if(cells.isInRoom(x, y))
					assertFalse(false);
			}
		}
	}
	
	/**
	 * Makes sure every cell in a perfect maze has at least one wall.
	 */
	@Test
	public void enoughWalls()
	{
		Cells cells = config.getMazecells();
		for(int x = 0; x < config.getWidth(); x++)
		{
			for(int y = 0; y < config.getHeight(); y++)
			{
				if(cells.hasWall(x, y, CardinalDirection.North))
					continue;
				else if
					(cells.hasWall(x, y, CardinalDirection.South))
					continue;
				else if
					(cells.hasWall(x, y, CardinalDirection.East))
					continue;
				else if
					(cells.hasWall(x, y, CardinalDirection.West))
					continue;
				else
					assertFalse(false);
			}
		}
		assertTrue(true);
	}
	
	/**
	 * Test to make sure the maze has a starting position.
	 */
	@Test
	public void hasStartingPosition()
	{
		assertNotNull(config.getStartingPosition());
	}
	
	/**
	 * Test to make sure the starting position is at least the maze's width
	 * distance from the exit position.
	 */
	@Test
	public void startIsFarEnoughFromEnd()
	{
		int[] start = config.getStartingPosition();
		assertTrue(config.getDistanceToExit(start[0], start[1]) >= config.getWidth());
	}
	
	/**
	 * Tests the getSign() method from MazeBuilder.
	 */
	@Test
	public void testGetSign()
	{
		assertTrue(MazeBuilder.getSign(1) == 1);
		assertTrue(MazeBuilder.getSign(10) == 1);
		assertTrue(MazeBuilder.getSign(-5) == -1);
		assertTrue(MazeBuilder.getSign(-100) == -1);
		assertTrue(MazeBuilder.getSign(0) == 0);
	}
	
	/**
	 * Makes sure the maze has the correct dimensions as defined in the
	 * Constants class.
	 */
	@Test
	public void testDimensions()
	{
		int width = config.getWidth();
		int height = config.getHeight();
		int skill = stub.getSkillLevel();
		int checkWidth = Constants.SKILL_X[skill];
		int checkHeight = Constants.SKILL_Y[skill];
		assertEquals(width, checkWidth);
		assertEquals(height, checkHeight);
	}
	
}
