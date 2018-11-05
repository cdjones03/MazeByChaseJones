package gui;

import generation.CardinalDirection;
import generation.Distance;
import generation.MazeConfiguration;
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
	
	MazeConfiguration config;

	/**
	 * Sets up a new ManualDriver with a new BasicRobot. Also
	 * initializes its total energy consumption to 0.
	 */
	public ManualDriver() {
		robot = new BasicRobot();
		totalEnergyConsumed = 0;
	}
	
	public void setConfig(MazeConfiguration configuration) {
		config = configuration;
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
		System.out.println("This is manual");
		
		for(int x = 0; x < 4; x++)
			for(int y = 0; y < 4; y++)
				if(config.getMazecells().isExitPosition(x, y))
					System.out.println(x + " " + y + " the exit");
		
		int[] curPos;
		try {
			curPos = robot.getCurrentPosition();
			System.out.println(curPos[0] + " " + curPos[1]);
			if(robot.isAtExit())
				System.out.println("at exit ");
		}
		catch (Exception e) {
			
		}
		/*
		if(config.hasWall(curPos[0], curPos[1], CardinalDirection.North))
			System.out.println("1 north");
		if(config.hasWall(curPos[0], curPos[1], CardinalDirection.South))
			System.out.println("1 south");
		if(config.hasWall(curPos[0], curPos[1], CardinalDirection.East))
			System.out.println("1 east");
		if(config.hasWall(curPos[0], curPos[1], CardinalDirection.West))
			System.out.println("1 west");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
		System.out.println(robot.getCurrentDirection());
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
			robot.move(1, true);
			newPos = robot.getCurrentPosition();
			if(oldPos[0] != newPos[0] || oldPos[1] != newPos[1]) //If the driver's robot has actually moved, takes energy.
				totalEnergyConsumed += robot.getEnergyForStepForward();
		}
		catch (Exception e)
		{
			
		}
		
	}
	
	/**
	 * The ManualDriver robot turns based on the turn parameter. Depletes the robot's energy
	 * and adds to the total energy consumption of the driver.
	 * @param turn
	 */
	public void turn(Robot.Turn turn) {
		robot.rotate(turn);
		totalEnergyConsumed += (0.25)*robot.getEnergyForFullRotation();
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
			
		}
		return null;
	}
	
	/**
	 * Used by controller to reset total energy consumed to 0.
	 */
	public void resetEnergyConsumption()
	{
		totalEnergyConsumed = 0;
	}

}
