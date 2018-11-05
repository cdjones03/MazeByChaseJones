package gui;

import generation.Distance;
import generation.MazeConfiguration;
import generation.Wall;
import gui.Robot.Direction;
import gui.Robot.Turn;
import generation.CardinalDirection;
import generation.Cells;


/**
 * 
 * @author chasejones
 *
 */
public class Wizard {

	private Robot robot;
	private Distance dist;
	private Cells cells;
	private MazeConfiguration config;
	
	public Wizard()
	{
		robot = new BasicRobot();
	}
	
	public void setDistance(Distance distance) {
		// TODO Auto-generated method stub
		dist = distance;
	}
	
	public void setCells(Cells cell) {
		cells = cell;
	}
	
	public void setRobot(Robot r) {
		// TODO Auto-generated method stub
		robot = r;
	}
	
	public void setConfig(MazeConfiguration configuration) {
		config = configuration;
	}
	
	public int getDistDir(Direction dir) throws Exception
	{
		int[] curPos = robot.getCurrentPosition();
		int[] newPos = curPos;
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
	
	public boolean drive2Exit() throws Exception {
		System.out.println("This is wizard");
		int[] curPos = robot.getCurrentPosition();
		
		System.out.println("curPos " + curPos[0] + curPos[1]);
		if(config.hasWall(curPos[0], curPos[1], CardinalDirection.North))
			System.out.println("1 north");
		if(config.hasWall(curPos[0], curPos[1], CardinalDirection.South))
			System.out.println("1 south");
		if(config.hasWall(curPos[0], curPos[1], CardinalDirection.East))
			System.out.println("1 east");
		if(config.hasWall(curPos[0], curPos[1], CardinalDirection.West))
			System.out.println("1 west");
		
		int[] exit = dist.getExitPosition();
		System.out.println("exit " + exit[0] + " " + exit[1]);
		
		int[] nextPos = {0, 0};
		while(robot.getBatteryLevel() >= 5 && dist.getDistanceValue(curPos[0], curPos[1]) > 2)
		{
			curPos = robot.getCurrentPosition();
			System.out.println("curPos " + curPos[0] + curPos[1]);
			System.out.println("cur dist " + dist.getDistanceValue(curPos[0], curPos[1]));
			
			nextPos = config.getNeighborCloserToExit(curPos[0], curPos[1]);
			System.out.println("nextPos " + nextPos[0] + " " + nextPos[1]);
			
			if(nextPos[1] == curPos[1]+1) //Go north
			{
				switch(robot.getCurrentDirection()) {
				case North :
					System.out.println("north north");
					robot.move(1, true);
					break;
				case East :
					System.out.println("north east");
					robot.rotate(Turn.LEFT);
					robot.move(1, true);
					break;
				case West :
					System.out.println("north west");
					robot.rotate(Turn.RIGHT);
					robot.move(1, true);
					break;
				case South :
					System.out.println("north south");
					robot.rotate(Turn.RIGHT);
					robot.rotate(Turn.RIGHT);
					robot.move(1, true);
				}
			}
			else if(nextPos[1] == curPos[1]-1) //Go South
			{
				switch(robot.getCurrentDirection()) {
				case South :
					System.out.println("south south");
					robot.move(1, true);
					break;
				case East :
					System.out.println("south east");
					robot.rotate(Turn.RIGHT);
					robot.move(1, true);
					break;
				case West :
					System.out.println("south west");
					robot.rotate(Turn.LEFT);
					robot.move(1, true);
					break;
				case North :
					System.out.println("south north");
					robot.rotate(Turn.RIGHT);
					robot.rotate(Turn.RIGHT);
					robot.move(1, true);
				}
			}
			else if(nextPos[0] == curPos[0]+1) //Go East
			{
				switch(robot.getCurrentDirection()) {
				case North :
					System.out.println("east north");
					robot.rotate(Turn.RIGHT);
					robot.move(1, true);
					break;
				case East :
					System.out.println("east east");
					robot.move(1, true);
					break;
				case South :
					System.out.println("east south");
					robot.rotate(Turn.LEFT);
					robot.move(1, true);
					break;
				case West :
					System.out.println("east west");
					robot.rotate(Turn.RIGHT);
					robot.rotate(Turn.RIGHT);
					robot.move(1, true);
					
				}
			}
			else if(nextPos[0] == curPos[0]-1) //Go West
			{
				switch(robot.getCurrentDirection()) {
				case North :
					System.out.println("west north");
					robot.rotate(Turn.LEFT);
					robot.move(1, true);
					break;
				case South :
					System.out.println("west south");
					robot.rotate(Turn.RIGHT);
					robot.move(1, true);
					break;
				case West :
					System.out.println("west west");
					robot.move(1, true);
					break;
				case East :
					System.out.println("west east");
					robot.rotate(Turn.RIGHT);
					robot.rotate(Turn.RIGHT);
					robot.move(1, true);
				}
			}
			
		}
		curPos = robot.getCurrentPosition();
		if(cells.isExitPosition(curPos[0], curPos[1])) {
			System.out.println("at exit here");
			if(!(robot.canSeeExit(Direction.FORWARD)))
				robot.rotate(Turn.RIGHT);
			if(!(robot.canSeeExit(Direction.FORWARD)))
				robot.rotate(Turn.RIGHT);
			if(!(robot.canSeeExit(Direction.FORWARD)))
				robot.rotate(Turn.RIGHT);
			robot.move(1, true);
		}
		return true;
	}
	}

