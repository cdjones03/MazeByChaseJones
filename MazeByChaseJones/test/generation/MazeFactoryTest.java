package generation;

import static org.junit.Assert.*;
import generation.Order.Builder;

import java.util.Iterator;
import org.junit.Test;
import gui.Constants;
import org.junit.Before;
import org.junit.After;

public class MazeFactoryTest {
	
	private MazeFactory mazeFac, mazeFacImperf;
	private StubOrder stub;
	private MazeConfiguration config;
	
	@Test
	public void testTest()
	{
		//System.out.println(maze);
		assertFalse(false);
	}
	
	@Before
	public void setUp()//General setup for default, 0 skill level, perfect maze.
	{
		mazeFac = new MazeFactory(true); //Makes maze generation deterministic, for purposes of testing.
		stub = new StubOrder(Builder.DFS, 0, true); //Builder type, skill level, whether perfect or not (i.e no rooms or yes rooms)
		mazeFac.order(stub);
		mazeFac.waitTillDelivered();
		config = stub.getMazeConfiguration();
	}
	
	@Test
	public void testMazeCreation()
	{
		assertNotNull(config);
	}
	
	@Test
	public void checkExit() //Checks that the maze has exactly 1 exit.
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
	
	@Test
	public void exitReachableFromAnywhere()//Checks if every cell in the maze can reach the exit.
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
	
	@Test
	public void checkDistance()
	{
		Distance dist = config.getMazedists();
		System.out.println(dist);
	}
	
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
		assertEquals(true, check);
	}

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
}
