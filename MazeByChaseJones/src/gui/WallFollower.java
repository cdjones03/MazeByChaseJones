package gui;

import generation.Cells;
import generation.Distance;

/**
 * 
 * @author chasejones
 *
 */
public class WallFollower {
	
	private Robot robot;
	private Distance dist;
	private Cells cells;
	
	public WallFollower()
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
	
	public boolean drive2Exit() throws Exception {
		System.out.println("This is wall follower");
		
	return true;
	}

}
