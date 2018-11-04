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
		//((BasicRobot) robot).getControl().setCurrentPosition(2, 2);
		Direction moveDir = Direction.FORWARD;
		int[] newPos = robot.getCurrentPosition();
		//EAST WEST SOUTH NORTH
		int checkDist = dist.getDistanceValue(newPos[0], newPos[1]);
		/*
		System.out.println(dist.getDistanceValue(newPos[0], newPos[1]));
		System.out.println(dist.getDistanceValue(newPos[0]+1, newPos[1]));
		System.out.println(dist.getDistanceValue(newPos[0]-1, newPos[1]));
		System.out.println(dist.getDistanceValue(newPos[0], newPos[1]-1));
		System.out.println(dist.getDistanceValue(newPos[0], newPos[1]+1));
		
		int dis = dist.getDistanceValue(newPos[0], newPos[1]);
		int disNor = dist.getDistanceValue(newPos[0], newPos[1]-1);
		int disSo = dist.getDistanceValue(newPos[0], newPos[1]+1);
		int disEa = dist.getDistanceValue(newPos[0]+1, newPos[1]);
		int disWe = dist.getDistanceValue(newPos[0]-1, newPos[1]);
		*/
		
		int curDist = dist.getDistanceValue(newPos[0], newPos[1]);
		
		while(robot.getBatteryLevel() >= 5)
		{
			checkDist = dist.getDistanceValue(newPos[0], newPos[1]);
			curDist = dist.getDistanceValue(newPos[0], newPos[1]);
			int[] curPos = robot.getCurrentPosition();
			System.out.println("curPos " + curPos[0] + curPos[1]);
			
			
			checkDist = getDistDir(Direction.FORWARD);
			if(checkDist < curDist && cells.canGo(new Wall(curPos[0], curPos[1], ((BasicRobot) robot).translateDir(Direction.FORWARD))))
			{
				System.out.println("forward");
				moveDir = Direction.FORWARD;
			}
			
			checkDist = getDistDir(Direction.RIGHT);
			if(checkDist < curDist && cells.canGo(new Wall(curPos[0], curPos[1], ((BasicRobot) robot).translateDir(Direction.RIGHT))))
			{
				System.out.println("right");
				moveDir = Direction.RIGHT;
			}
			
			checkDist = getDistDir(Direction.LEFT);
			if(checkDist < curDist && cells.canGo(new Wall(curPos[0], curPos[1], ((BasicRobot) robot).translateDir(Direction.LEFT))))
			{
				System.out.println("left");
				moveDir = Direction.LEFT;
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
			robot.rotate(Turn.RIGHT);	
		}
		return false;
	}
}
