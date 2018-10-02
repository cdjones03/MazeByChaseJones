package generation;

import java.util.ArrayList;

/**
 * @author chasejones
 */
public class MazeBuilderEller extends MazeBuilder implements Runnable{
	/**
	 * 
	 */
	public MazeBuilderEller() {
		super();
		System.out.println("MazeBuilderPrim uses Eller's algorithm to generate maze.");
	}
	/**
	 * @param deterministic
	 */
	public MazeBuilderEller(boolean deterministic) {
		super(deterministic);
		System.out.println("MazeBuilderPrim uses Eller's algorithm to generate maze.");
	}
	
	@Override
	/**
	 * Generate's a maze according to Eller's algorithm.
	 * Eller's Algorithm:
	 * 1.Initialize first row with set values.
	 * 2.Randomly merge sets.
	 * 3.Randomly create vertical connections (at least 1 per set).
	 * 4.Fill in remaining spots in row as new sets.
	 * 5.Repeat 1-4
	 * 	--do not break down walls between cells of same set.
	 * 6.when at last row, connect all disjoint sets.
	 * 
	 *  Different rules for final row -- Step 6.
	 *  Makes sure that all cells become part of the same set, 
	 *  since this is necessary to make the maze solvable.
	 */
	protected void generatePathways()
	{
		
		int setCount = 1;
		int heightCount = 0;
		ArrayList<Integer> checkSetArr = new ArrayList<Integer>();
		int ran;
		for(int x = 0; x < width; x++)//Place all cells in first row individual sets.
		{
			cells.setSet(x, 0, setCount);
			setCount++;
		}
		while(heightCount < height-1)
		{
			for(int x = 0; x < width-1; x++)//Horizontal merging. -- Step 2
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
			for(int x = 0; x < width; x++)//Vertical merging -- Step 3
			{
				ran = random.nextIntWithinInterval(0, 1);
				if(checkSetArr.contains(cells.getSet(x, heightCount)) || ran == 0)
				{
					Wall wall = new Wall(x, heightCount, CardinalDirection.South);
					cells.deleteWall(wall);
					cells.setSet(x, heightCount+1, cells.getSet(x, heightCount));
					checkSetArr.remove((cells.getSet(x, heightCount)));
				}
			}
			
			for(int x = 0; x < width; x++)//Filling in next row spots as new sets. -- Step 4
			{
				if(cells.getSet(x, heightCount+1) == 0)
				{
					cells.setSet(x, heightCount+1, setCount);
					setCount++;
				}
			}
			heightCount++;
		}
		
		for(int x = 0; x < width-1; x++)//Check method documentation at beginning for explanation.
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