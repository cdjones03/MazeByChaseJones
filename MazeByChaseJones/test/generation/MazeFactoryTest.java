package generation;

import static org.junit.Assert.*;
import generation.Order.Builder;

import java.util.Iterator;
import org.junit.Test;
import gui.Constants;
import org.junit.Before;
import org.junit.After;

public class MazeFactoryTest {
	
	private MazeFactory mazeFac;
	private StubOrder stub;
	private MazeConfiguration config;
	
	@Test
	public void testTest()
	{
		//System.out.println(maze);
		assertFalse(false);
	}
	
	@Before
	public void setUp()
	{
		mazeFac = new MazeFactory(true);
		stub = new StubOrder(Builder.DFS, 0, true);
		mazeFac.order(stub);
		mazeFac.waitTillDelivered();
		config = stub.getMazeConfiguration();
	}
	
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
		StubOrder stub2 = new StubOrder(Builder.DFS, 0, true);
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

}
