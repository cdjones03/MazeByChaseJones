package gui;

import generation.Distance;
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
		//System.out.println("hello");
		//robot.move(1, true);
		System.out.println("This is wizard");
		((BasicRobot) robot).getControl().setCurrentPosition(2, 2);
		
		int[] curPos = robot.getCurrentPosition();
		
		  //EAST WEST SOUTH NORTH
		System.out.println("self " + dist.getDistanceValue(curPos[0], curPos[1]));
		System.out.println("east " + dist.getDistanceValue(curPos[0]+1, curPos[1]));
		System.out.println("west " + dist.getDistanceValue(curPos[0]-1, curPos[1]));
		System.out.println("south " + dist.getDistanceValue(curPos[0], curPos[1]-1));
		System.out.println("north " + dist.getDistanceValue(curPos[0], curPos[1]+1));
		/*
		System.out.println(((BasicRobot) robot).translateDir(Direction.FORWARD));
		if(cells.hasWall(curPos[1], curPos[0], ((BasicRobot) robot).translateDir(Direction.FORWARD)))
			System.out.println("1 forward");
		System.out.println(((BasicRobot) robot).translateDir(Direction.RIGHT));
		if(cells.hasWall(curPos[1], curPos[0], ((BasicRobot) robot).translateDir(Direction.RIGHT)))
			System.out.println("1 right");
		System.out.println(((BasicRobot) robot).translateDir(Direction.LEFT));
		if(cells.hasWall(curPos[1], curPos[0], ((BasicRobot) robot).translateDir(Direction.LEFT)))
			System.out.println("1 left");*/
		
		if(cells.hasWall(curPos[0], curPos[1], CardinalDirection.North))
			System.out.println("1 north");
		if(cells.hasWall(curPos[0], curPos[1], CardinalDirection.South))
			System.out.println("1 south");
		if(cells.hasWall(curPos[0], curPos[1], CardinalDirection.East))
			System.out.println("1 east");
		if(cells.hasWall(curPos[0], curPos[1], CardinalDirection.West))
			System.out.println("1 west");
		
		
		Direction moveDir = Direction.FORWARD;
		
		int checkDist = dist.getDistanceValue(curPos[0], curPos[1]);
		int curDist = dist.getDistanceValue(curPos[0], curPos[1]);
		
		int count = 0;
		while(robot.getBatteryLevel() >= 5)
		{
			System.out.println("count " + count);
			curPos = robot.getCurrentPosition();
			checkDist = dist.getDistanceValue(curPos[0], curPos[1]);
			curDist = dist.getDistanceValue(curPos[0], curPos[1]);
			System.out.println("curPos " + curPos[0] + curPos[1]);

			if(cells.canGo(new Wall(curPos[0], curPos[1], ((BasicRobot) robot).translateDir(Direction.FORWARD)))){
				checkDist = getDistDir(Direction.FORWARD);
				System.out.println("forward " + checkDist);
				if(checkDist < curDist)
				{
					System.out.println("forward");
					moveDir = Direction.FORWARD;
				}
			}

			if(cells.canGo(new Wall(curPos[0], curPos[1], ((BasicRobot) robot).translateDir(Direction.RIGHT)))){
				checkDist = getDistDir(Direction.RIGHT);
				System.out.println("right " + checkDist);
				if(checkDist < curDist)
				{
					System.out.println("right");
					moveDir = Direction.RIGHT;
				}
			}

			if(cells.canGo(new Wall(curPos[0], curPos[1], ((BasicRobot) robot).translateDir(Direction.LEFT)))) {
				checkDist = getDistDir(Direction.LEFT);
				System.out.println("left " + checkDist);
				if(checkDist < curDist)
				{
					System.out.println("left");
					moveDir = Direction.LEFT;
				}
			}

			if(moveDir.equals(Direction.FORWARD))
				robot.move(1, true);
			else if(moveDir.equals(Direction.RIGHT))
			{
				robot.rotate(Turn.RIGHT);
				robot.move(1, true);
			}
			else if(moveDir.equals(Direction.LEFT))
			{
				robot.rotate(Turn.LEFT);
				robot.move(1, true);
			}
			//robot.rotate(Turn.RIGHT);	
			if(count > 20)
				break;
			count++;
		}
		return true;
	}
}
