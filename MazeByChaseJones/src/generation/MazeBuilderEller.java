package generation;

import java.util.ArrayList;
import gui.Constants;

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
		 * 	do not break down walls between cells of same set
		 * 6.when at last row, connect all disjoint sets
		 */
		
		int setCount = 1;
		int heightCount = 0;
		ArrayList<Integer> checkSetArr = new ArrayList<Integer>();
		int ran;
		for(int x = 0; x < width; x++)//Place all cells in first row individual sets.
		{
			cells.setSet(x, 0, setCount);
			setCount++;
		}
		//System.out.println(cells);
		//for(;heightCount < height-1; heightCount++)
		while(heightCount < height-1)
		{
			for(int x = 0; x < width-1; x++)//horizontal merging
			{
				ran = random.nextIntWithinInterval(0, 1);
				if(ran == 0)
				{
					Wall wall = new Wall(x, heightCount, CardinalDirection.East);
					if(cells.canGo(wall)) 
					{
					cells.mergeSets(x, heightCount, x+1, heightCount);
					cells.deleteWall(wall);
					}
				}
			}
			
			for(int x = 0; x < width; x++)
			{
				int check = cells.getSet(x, heightCount);
				if(checkSetArr.contains(check) == false)
					checkSetArr.add(check);
			}
			
			//System.out.println("checkSetArr " + checkSetArr.toString());
			
			for(int x = 0; x < width; x++)//vertical merging
			{
				ran = random.nextIntWithinInterval(0, 1);
				if(checkSetArr.contains(cells.getSet(x, heightCount)) || ran == 0)
				{
					Wall wall = new Wall(x, heightCount, CardinalDirection.South);
					//if(cells.canGo(wall))
					{
						cells.deleteWall(wall);
						cells.setSet(x, heightCount+1, cells.getSet(x, heightCount));
						checkSetArr.remove((cells.getSet(x, heightCount)));
					}
				}
			}
			
			for(int x = 0; x < width; x++)//filling in next row spots as new sets
			{
				if(cells.getSet(x, heightCount+1) == 0)
				{
					cells.setSet(x, heightCount+1, setCount);
					setCount++;
				}
			}
			heightCount++;
			//System.out.println(heightCount + " " + height);
			//System.out.println(cells);
			
		}
		for(int x = 0; x < width-1; x++)
		{
			if(cells.getSet(x, heightCount) != cells.getSet(x+1, heightCount))
			{
				cells.mergeSets(x, heightCount, x+1, heightCount);
				Wall wall = new Wall(x, heightCount, CardinalDirection.East);
				cells.deleteWall(wall);
			}
		}
		}
	}