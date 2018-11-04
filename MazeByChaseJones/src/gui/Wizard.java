package gui;

import generation.Distance;
import gui.Robot.Direction;
import gui.Robot.Turn;
import generation.CardinalDirection;


/**
 * 
 * @author chasejones
 *
 */
public class Wizard {

	private Robot robot;
	private Distance dist;
	
	public Wizard()
	{
		robot = new BasicRobot();
	}
	
	public void setDistance(Distance distance) {
		// TODO Auto-generated method stub
		dist = distance;
	}
	
	public void setRobot(Robot r) {
		// TODO Auto-generated method stub
		robot = r;
	}
	
	public int[] getDistPos(Direction dir) throws Exception
	{
		int[] curPos = robot.getCurrentPosition();
		int[] newPos = curPos;
		CardinalDirection carDir = robot.getCurrentDirection();
		switch(carDir)
		{
		case North :
			switch(dir)
			{
			case FORWARD :
				newPos[1] -= 1;
				break;
			
			case RIGHT :
				newPos[0] += 1;
				break;
				
			case LEFT : 
				newPos[0] -= 1;
				break;
			}
			break;
		
		case West :
			switch(dir)
			{
			case FORWARD :
				newPos[0] -= 1;
				break;
			
			case RIGHT :
				newPos[1] -= 1;
				break;
				
			case LEFT : 
				newPos[1] += 1;
				break;
			}
			break;
			
		case East :
			switch(dir)
			{
			case FORWARD :
				newPos[0] += 1;
				break;
			
			case RIGHT :
				newPos[1] += 1;
				break;
				
			case LEFT : 
				newPos[1] -= 1;
				break;
			}
			break;
		
		case South :
			switch(dir)
			{
			case FORWARD :
				newPos[1] += 1;
				break;
			
			case RIGHT :
				newPos[0] -= 1;
				break;
				
			case LEFT : 
				newPos[0] += 1;
				break;
			}
			break;
		
		default :
			break;
		}
		return newPos;
	}
	
	public boolean drive2Exit() throws Exception {
		//System.out.println("hello");
		//robot.move(1, true);
		((BasicRobot) robot).getControl().setCurrentPosition(2, 2);
		Direction moveDir = Direction.FORWARD;
		int[] newPos = robot.getCurrentPosition();
		//EAST WEST SOUTH NORTH
		int checkDist = dist.getDistanceValue(newPos[0], newPos[1]);
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
		
		while(robot.getBatteryLevel() >= 5)
		{
			checkDist = dist.getDistanceValue(newPos[0], newPos[1]);
			int[] curPos = robot.getCurrentPosition();
			System.out.println("curPos " + curPos[0] + curPos[1]);
			
			//int distTo = -1;
			//distTo = robot.distanceToObstacle(Direction.FORWARD);
			//System.out.println("distforward " + distTo);
			
			newPos = getDistPos(Direction.FORWARD);
			System.out.println("newPos1 " + newPos[0] + newPos[1]);
			if(dist.getDistanceValue(newPos[0], newPos[1]) < checkDist)
			{
				System.out.println("forward");
				moveDir = Direction.FORWARD;
			}
			
			newPos = getDistPos(Direction.RIGHT);
			System.out.println("newPos2 " + newPos[0] + newPos[1]);
			if(dist.getDistanceValue(newPos[0], newPos[1]) < checkDist)
			{
				System.out.println("right");
				moveDir = Direction.RIGHT;
			}
			
			newPos = getDistPos(Direction.LEFT);
			System.out.println("newPos3 " + newPos[0] + newPos[1]);
			if(dist.getDistanceValue(newPos[0], newPos[1]) < checkDist)
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
