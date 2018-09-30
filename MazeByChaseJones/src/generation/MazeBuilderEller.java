package generation;

import java.util.ArrayList;

/**
 * @author chasejones
 *
 */
public class MazeBuilderEller extends MazeBuilder implements Runnable{

	/**
	 * 
	 */
	public MazeBuilderEller() {
		// TODO Auto-generated constructor stub
		super();
		System.out.println("MazeBuilderPrim uses Eller's algorithm to generate maze.");
	}
	/**
	 * @param deterministic
	 */
	public MazeBuilderEller(boolean deterministic) {
		super(deterministic);
		System.out.println("MazeBuilderPrim uses Eller's algorithm to generate maze.");
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void generatePathways()
	{
		/**
		 * 1.Initialize first row (already done)
		 * 2.Randomly merge sets
		 * 3.Randomly create vertical connections (at least 1 per set)
		 * 4.Fill in remaining spots in row with as new sets
		 * 5.Repeat 1-4
		 * 	do not break down walls between cells of same cet
		 * 6.when at last row, connect all disjoint sets
		 */
		//MazeBuilderEller maze = new MazeBuilderEller();
		//maze.buildOrder(order);
		//maze.run();
		int setCount = 1;
		int heightCount = 0;
		int checkSetCount;
		for(int x = 0; x < width; x++)
		{
			cells.setSet(0, x, setCount);
			setCount++;
		}
		while (heightCount != height)
		{
			for(int x = 0; x < width-1; x++)
			{
				int ran = random.nextIntWithinInterval(0, 1);
				if(ran == 0)
				{
					cells.mergeSets(0, x, 0, x+1);
					Wall wall = new Wall(x, heightCount, CardinalDirection.East);
					cells.deleteWall(wall);
				}
			}
			for(int x = 0; x < width; x++)
			{
				int ran = random.nextIntWithinInterval(0, 1);
				if(ran == 0)
				{
					Wall wall = new Wall(x, 0, CardinalDirection.South);
					cells.deleteWall(wall);
					cells.setSet(heightCount+1, x, cells.getSet(heightCount,x));
				}
			}
			for(int x = 0; x < width; x++)
			{
				if(cells.getSet(heightCount, x) == 0)
				{
					cells.setSet(heightCount, x, setCount);
					setCount++;
				}
			}
			if(heightCount == height-1)
			{
				for(int x = 0; x < width-1; x++)
				{
					if(cells.getSet(heightCount, x) != cells.getSet(heightCount, x+1))
					{
						cells.mergeSets(heightCount, x, heightCount, x+1);
						Wall wall = new Wall(x, heightCount, CardinalDirection.East);
						cells.deleteWall(wall);
					}
				}
			}
			heightCount++;
			//System.out.println(heightCount);
		}
			
			
			System.out.println(cells);
		}
		
	}