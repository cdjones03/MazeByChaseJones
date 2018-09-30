package generation;

import static org.junit.Assert.*;

import java.util.Iterator;

import org.junit.Test;

import gui.Constants;

public class MazeFactoryTest {
	
	private MazeFactory mazeFac = new MazeFactory();
	private StubOrder stub = new StubOrder();
	private MazeConfiguration config;
	
	MazeBuilder maze = new MazeBuilder();
	
	@Test
	public void testTest()
	{
		System.out.println(maze);
		assertFalse(false);
	}

}
