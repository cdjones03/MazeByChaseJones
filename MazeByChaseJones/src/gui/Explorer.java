package gui;

/**
 * Implements explorer algorithm that keeps track of previously
 * visited spots in order to randomly make its way out of a maze.
 * 
 * @author chasejones
 *
 */
public class Explorer {
	
	private Robot robot;
	
	/**
	 * Constructor for Explorer. 
	 */
	public Explorer()
	{
		robot = new BasicRobot();
	}
	
	/**
	 * Gives the Explorer the robot.
	 * @param r
	 */
	public void setRobot(Robot r) {
		// TODO Auto-generated method stub
		robot = r;
	}

	/**
	 * Implements Explorer algorithm to solve maze.
	 * @return
	 * @throws Exception
	 */
	public boolean drive2Exit() throws Exception {
		System.out.println("This is explorer");
		
		return true;
	}
}
