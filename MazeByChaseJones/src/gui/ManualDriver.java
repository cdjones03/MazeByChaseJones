package gui;

import generation.Distance;
import gui.Constants.UserInput;

/**
 * @Author: Chase Jones
 * Responsibilities:
 * 
 * Collaborators:
 * BasicRobot.java
 * Controller.java
 * 
 * needs to receive keyboard input
 * 	then delegates it to BasicRobot?
 */
public class ManualDriver implements RobotDriver {
	
	private Robot robot;

	public ManualDriver() {
		robot = new BasicRobot();
	}

	@Override
	public void setRobot(Robot r) {
		// TODO Auto-generated method stub
		robot = r;
	}

	@Override
	public void setDimensions(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setDistance(Distance distance) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean drive2Exit() throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public float getEnergyConsumption() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getPathLength() {
		// TODO Auto-generated method stub
		return 0;
	}
	
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
	
	public void moveForward() {
		robot.move(1, true);
	}
	
	public void turn(Robot.Turn turn) {
		robot.rotate(turn);
		
	}

}
