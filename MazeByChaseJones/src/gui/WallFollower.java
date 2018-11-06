package gui;

/**
 * Algorithm to solve maze where robot
 * follows wall on its left side until it reaches
 * the exit.
 * 
 * @author chasejones
 *
 */
public class WallFollower {
	
	private Robot robot;
	
	/**
	 * Constructor for WallFollower.
	 */
	public WallFollower()
	{
		robot = new BasicRobot();
	}
	
	/**
	 * Gives the WallFollower the robot.
	 * @param r
	 */
	public void setRobot(Robot r) {
		// TODO Auto-generated method stub
		robot = r;
	}
	
	/**
	 * Implements WallFollower algorithm to 
	 * drive robot to the exit.
	 * @return
	 * @throws Exception
	 */
	public boolean drive2Exit() throws Exception {
		System.out.println("This is wall follower");
		
	return true;
	}

}
