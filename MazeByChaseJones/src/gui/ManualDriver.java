package gui;

import generation.Distance;
import gui.Constants.UserInput;

/**
 * @Author: Chase Jones
 * 
 * Responsibilities:
 * Receives input from user, then changes robot
 * accordingly.
 * Keeps track of the total path length and total energy consumption of
 * the driver's robot.
 * 
 * Collaborators:
 * BasicRobot.java
 * Controller.java
 * SimpleKeyListener.java
 */
public class ManualDriver implements RobotDriver {
	
	private Robot robot;
	private float totalEnergyConsumed;
	private Distance dist;
	private int mazeWidth;
	private int mazeHeight;

	/**
	 * Sets up a new ManualDriver with a new BasicRobot. Also
	 * initializes its total energy consumption to 0.
	 */
	public ManualDriver() {
		robot = new BasicRobot();
		totalEnergyConsumed = 0;
	}

	@Override
	public void setRobot(Robot r) {
		// TODO Auto-generated method stub
		robot = r;
	}

	@Override
	public void setDimensions(int width, int height) {
		// TODO Auto-generated method stub
		mazeWidth = width;
		mazeHeight = height;
	}

	@Override
	public void setDistance(Distance distance) {
		// TODO Auto-generated method stub
		dist = distance;
	}

	@Override
	public boolean drive2Exit() throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public float getEnergyConsumption() {
		// TODO Auto-generated method stub
		return totalEnergyConsumed;
	}

	@Override
	public int getPathLength() {
		// TODO Auto-generated method stub
		return robot.getOdometerReading();
	}
	
	/**
	 * Takes input from the user and either moves or rotates the manual driver
	 * accordingly. User can either input Right or Left to rotate, or Up
	 * to move forward. ManualDriver can only do one of these three operations,
	 * so all other just route through the original keyListener.
	 * @param input
	 */
	public void manualKeyDown(UserInput input) {
		switch(input)
		{
		case Up:
			moveForward();
			break;
		case Right:
			turn(Robot.Turn.RIGHT);
			break;
		case Left:
			turn(Robot.Turn.LEFT);
			break;
		default:
			break;
		}
	}
	
	/**
	 * The ManualDriver robot moves forward one step. Only changes the robot's energy
	 * or adds to the driver's total energy consumption if the driver actually moves 
	 * to a new position. 
	 */
	public void moveForward() {
		int[] oldPos;
		int[] newPos;
		try {
			oldPos = robot.getCurrentPosition();
			System.out.println("old " + oldPos[0] + " " + oldPos[1]);
			robot.move(1, true);
			newPos = robot.getCurrentPosition();
			System.out.println("new " + newPos[0] + " " + newPos[1]);
			if(oldPos[0] != newPos[0] || oldPos[1] != newPos[1]) //If the driver's robot has actually moved, takes energy.
				totalEnergyConsumed += robot.getEnergyForStepForward();
		}
		catch (Exception e)
		{
			//e.printStackTrace();
			//System.out.println("1Robot outside of maze.");
		}
		
	}
	
	/**
	 * The ManualDriver robot turns based on the turn parameter. Depletes the robot's energy
	 * and adds to the total energy consumption of the driver.
	 * @param turn
	 */
	public void turn(Robot.Turn turn) {
		robot.rotate(turn);
		totalEnergyConsumed += (1/4)*robot.getEnergyForFullRotation();
	}
	
	/**
	 * Returns the driver's robot's current position.
	 * @return
	 */
	public int[] getCurrentPosition()
	{
		try {
		return robot.getCurrentPosition();
		}
		catch (Exception e)
		{
			System.out.println("2Robot outside of maze.");
		}
		return null;
	}

}
