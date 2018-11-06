package gui;

import generation.Distance;
import generation.MazeConfiguration;
import gui.Robot.Direction;
import gui.Robot.Turn;
import generation.CardinalDirection;
import generation.Cells;


/**
 * 
 * @author chasejones
 * 
 * Maze solver that implements the wizard algorithm, which
 * uses knowledge of the maze's distance values to choose the
 * next spot with a lower distance value/closer to the exit.
 *
 */
public class Wizard {

	private Robot robot;
	private Distance dist;
	private Cells cells;
	private MazeConfiguration config;
	
	/**
	 * Constructor for Wizard algorithm.
	 */
	public Wizard()
	{
		robot = new BasicRobot();
	}
	
	/**
	 * Gives the wizard the maze's distance values.
	 * @param distance
	 */
	public void setDistance(Distance distance) {
		// TODO Auto-generated method stub
		dist = distance;
	}
	
	/**
	 * Gives the wizard the maze's cells.
	 * @param cell
	 */
	public void setCells(Cells cell) {
		cells = cell;
	}
	
	/**
	 * Gives the wizard the robot.
	 * @param r
	 */
	public void setRobot(Robot r) {
		// TODO Auto-generated method stub
		robot = r;
	}
	
	/**
	 * Gives the wizard the maze configuration.
	 * @param configuration
	 */
	public void setConfig(MazeConfiguration configuration) {
		config = configuration;
	}
	
	/**
	 * Gets the new coordinates to check the Distance value for
	 * based on the Robot's sensed direction and current direction.
	 * @param dir
	 * @return
	 * @throws Exception
	 */
	public int getDistDir(Direction dir) throws Exception
	{
		int[] curPos = robot.getCurrentPosition();
		int checkDist = dist.getDistanceValue(curPos[0], curPos[1]);
		switch(robot.getCurrentDirection())
		{
		case North :
			switch(dir)
			{
			case FORWARD :
				checkDist = dist.getDistanceValue(curPos[0], curPos[1]+1);
				break;
			
			case RIGHT :
				checkDist = dist.getDistanceValue(curPos[0]+1, curPos[1]);
				break;
				
			case LEFT : 
				checkDist = dist.getDistanceValue(curPos[0]-1, curPos[1]);
				break;
			}
			break;
		
		case West :
			switch(dir)
			{
			case FORWARD :
				checkDist = dist.getDistanceValue(curPos[0]-1, curPos[1]);
				break;
			
			case RIGHT :
				checkDist = dist.getDistanceValue(curPos[0], curPos[1]+1);
				break;
				
			case LEFT : 
				checkDist = dist.getDistanceValue(curPos[0], curPos[1]-1);
				break;
			}
			break;
			
		case East :
			switch(dir)
			{
			case FORWARD :
				checkDist = dist.getDistanceValue(curPos[0]+1, curPos[1]);
				break;
			
			case RIGHT :
				checkDist = dist.getDistanceValue(curPos[0], curPos[1]-1);
				break;
				
			case LEFT : 
				checkDist = dist.getDistanceValue(curPos[0], curPos[1]+1);
				break;
			}
			break;
		
		case South :
			switch(dir)
			{
			case FORWARD :
				checkDist = dist.getDistanceValue(curPos[0], curPos[1]-1);
				break;
			
			case RIGHT :
				checkDist = dist.getDistanceValue(curPos[0]-1, curPos[1]);
				break;
				
			case LEFT : 
				checkDist = dist.getDistanceValue(curPos[0]+1, curPos[1]);
				break;
			}
			break;
		
		default :
			break;
		}
		return checkDist;
	}
	
	/**
	 * Actual implementation of wizard algorithm to solve maze.
	 * 1. Check battery and not at exit.
	 * 2. Get spot with lower distance value from distance class.
	 * 3. Compute move and rotation, if applicable, to get to that spot.
	 * 4. Repeat 1-3 until robot is at exit.
	 * 5. Based on where the exit is, perform the appropriate rotations
	 * till the robot can move forward to exit the maze.
	 * @return
	 * @throws Exception
	 */
	public boolean drive2Exit() throws Exception {
		System.out.println("This is wizard");
		int[] curPos = robot.getCurrentPosition();
		int[] nextPos = {0, 0};
		
		//While loop to get robot to exit position by choosing move
		//based on which spot has lower distance value (i.e. closer
		//to exit).
		while(robot.getBatteryLevel() >= 5 && dist.getDistanceValue(curPos[0], curPos[1]) > 2)
		{
			curPos = robot.getCurrentPosition();
			
			nextPos = config.getNeighborCloserToExit(curPos[0], curPos[1]);
			
			if(nextPos[1] == curPos[1]+1) //Go north
			{
				switch(robot.getCurrentDirection()) {
				case North :
					robot.move(1, true);
					break;
				case East :
					robot.rotate(Turn.LEFT);
					robot.move(1, true);
					break;
				case West :
					robot.rotate(Turn.RIGHT);
					robot.move(1, true);
					break;
				case South :
					robot.rotate(Turn.RIGHT);
					robot.rotate(Turn.RIGHT);
					robot.move(1, true);
				}
			}
			else if(nextPos[1] == curPos[1]-1) //Go South
			{
				switch(robot.getCurrentDirection()) {
				case South :
					robot.move(1, true);
					break;
				case East :
					robot.rotate(Turn.RIGHT);
					robot.move(1, true);
					break;
				case West :
					robot.rotate(Turn.LEFT);
					robot.move(1, true);
					break;
				case North :
					robot.rotate(Turn.RIGHT);
					robot.rotate(Turn.RIGHT);
					robot.move(1, true);
				}
			}
			else if(nextPos[0] == curPos[0]+1) //Go East
			{
				switch(robot.getCurrentDirection()) {
				case North :
					robot.rotate(Turn.RIGHT);
					robot.move(1, true);
					break;
				case East :
					robot.move(1, true);
					break;
				case South :
					robot.rotate(Turn.LEFT);
					robot.move(1, true);
					break;
				case West :
					robot.rotate(Turn.RIGHT);
					robot.rotate(Turn.RIGHT);
					robot.move(1, true);
					
				}
			}
			else if(nextPos[0] == curPos[0]-1) //Go West
			{
				switch(robot.getCurrentDirection()) {
				case North :
					robot.rotate(Turn.LEFT);
					robot.move(1, true);
					break;
				case South :
					robot.rotate(Turn.RIGHT);
					robot.move(1, true);
					break;
				case West :
					robot.move(1, true);
					break;
				case East :
					robot.rotate(Turn.RIGHT);
					robot.rotate(Turn.RIGHT);
					robot.move(1, true);
				}
			}
			
		}
		
		/**
		 * All different cases for where exit could be and how to sucessfully
		 * get robot to exit the maze.
		 */
		curPos = robot.getCurrentPosition();
		if(cells.isExitPosition(curPos[0], curPos[1])) {
			if(curPos[0] == 0 && curPos[1] !=(config.getHeight()-1) && curPos[1] != 0) {
				while(!(robot.getCurrentDirection().equals(CardinalDirection.West)))
					robot.rotate(Turn.RIGHT);
				robot.move(1, true);
			}
			if(curPos[0] == 0 && curPos[1] == (config.getHeight()-1)) {
				while(!(robot.getCurrentDirection().equals(CardinalDirection.West)))
					robot.rotate(Turn.RIGHT);
				robot.move(1, true);
				robot.rotate(Turn.RIGHT);
				robot.move(1, true);
				}
			if(curPos[0] == 0 && curPos[1] == 0) {
				while(!(robot.getCurrentDirection().equals(CardinalDirection.West)))
					robot.rotate(Turn.RIGHT);
				robot.move(1, true);
				robot.rotate(Turn.LEFT);
				robot.move(1, true);
			}
			if(curPos[0] != 0 && curPos[0] != config.getWidth()-1 && curPos[1] == 0) {
				while(!(robot.getCurrentDirection().equals(CardinalDirection.South)))
					robot.rotate(Turn.RIGHT);
				robot.move(1, true);
				}
			if(curPos[0] == config.getWidth()-1 && curPos[1] == 0) {
				while(!(robot.getCurrentDirection().equals(CardinalDirection.South)))
					robot.rotate(Turn.RIGHT);
				robot.move(1, true);
				robot.rotate(Turn.LEFT);
				robot.move(1, true);
			}
			if(curPos[0] == config.getWidth()-1 && curPos[1] != 0 && curPos[1] != config.getHeight()-1) {
				while(!(robot.getCurrentDirection().equals(CardinalDirection.East)))
					robot.rotate(Turn.RIGHT);
				robot.move(1, true);
			}
			if(curPos[0] == config.getWidth()-1 && curPos[1] == config.getHeight()-1) {
				while(!(robot.getCurrentDirection().equals(CardinalDirection.North)))
					robot.rotate(Turn.RIGHT);
				robot.move(1, true);
				robot.rotate(Turn.RIGHT);
				robot.move(1, true);
			}
			if(curPos[0] != config.getWidth()-1 && curPos[0] != 0 && curPos[1] == config.getHeight()-1) {
				while(!(robot.getCurrentDirection().equals(CardinalDirection.North)))
					robot.rotate(Turn.RIGHT);
				robot.move(1, true);
			}
			
			
			}
		
		return true;
	}
	}

