package gui;

import static org.junit.jupiter.api.Assertions.assertAll;

import generation.CardinalDirection;
import gui.Constants.UserInput;
import generation.MazeConfiguration;

/**
 * @Author: Chase Jones
 * Responsibilities:
 * 
 * 
 * Collaborators:
 * Controller.java
 * ManualDriver.java
 * 
 * Battery start = 3000
 * Sensing in 1D = -1
 * Rotate 90degress = -3
 * Moving one step = -5
 */
public class BasicRobot implements Robot {
	
	private float batteryLevel;
	private int odometer;
	private CardinalDirection curDir;
	private boolean roomSensor;
	private boolean distSensor;
	private Controller control;
	private MazeConfiguration config;

	public BasicRobot() {
		batteryLevel = 3000;
		odometer = 0;
		curDir = CardinalDirection.East;
		config = control.getMazeConfiguration();
	}

	@Override
	public void rotate(Turn turn) {
		// TODO Auto-generated method stub
		if(turn.equals(Robot.Turn.RIGHT))
		{
			assert batteryLevel >= 3: " battery level too low for rotate";
			control.keyDown(UserInput.Right, 0);
			batteryLevel -= 3;
		}
		else if(turn.equals(Robot.Turn.LEFT))
		{
			assert batteryLevel >= 3: " battery level too low for rotate";
			control.keyDown(UserInput.Left, 0);
			batteryLevel -= 3;
		}
		else if(batteryLevel >= 6 && turn.equals(Robot.Turn.AROUND))
		{
			assert batteryLevel >= 6: " battery level too low for rotate";
			control.keyDown(UserInput.Right, 0);
			control.keyDown(UserInput.Right, 0);
			batteryLevel -= 6;
		}
	}

	@Override
	public void move(int distance, boolean manual) {
		// TODO Auto-generated method stub
		assert batteryLevel >= getEnergyForStepForward() : " battery too low for move";
		if(manual)
			for(int moveCount = 0; moveCount < distance; moveCount++)
			{
				control.keyDown(UserInput.Up, 0);
				batteryLevel -= getEnergyForStepForward();
				odometer += 1;
			}
	}

	@Override
	public int[] getCurrentPosition() throws Exception {
		// TODO Auto-generated method stub
		return control.getCurrentPosition();
	}

	@Override
	public void setMaze(Controller controller) {
		// TODO Auto-generated method stub
		control = controller;
	}

	@Override
	public boolean isAtExit() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean canSeeExit(Direction direction) throws UnsupportedOperationException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isInsideRoom() throws UnsupportedOperationException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean hasRoomSensor() {
		// TODO Auto-generated method stub
		return roomSensor;
	}

	@Override
	public CardinalDirection getCurrentDirection() {
		// TODO Auto-generated method stub
		return curDir;
	}

	@Override
	public float getBatteryLevel() {
		// TODO Auto-generated method stub
		return batteryLevel;
	}

	@Override
	public void setBatteryLevel(float level) {
		// TODO Auto-generated method stub
		batteryLevel = level;
	}

	@Override
	public int getOdometerReading() {
		// TODO Auto-generated method stub
		return odometer;
	}

	@Override
	public void resetOdometer() {
		// TODO Auto-generated method stub
		odometer = 0;
	}

	@Override
	public float getEnergyForFullRotation() {
		// TODO Auto-generated method stub
		return 12;
	}

	@Override
	public float getEnergyForStepForward() {
		// TODO Auto-generated method stub
		return 5;
	}

	@Override
	public boolean hasStopped() {
		// TODO Auto-generated method stub
		 return false;
	}

	@Override
	public int distanceToObstacle(Direction direction) throws UnsupportedOperationException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean hasDistanceSensor(Direction direction) {
		// TODO Auto-generated method stub
		return false;
	}

}
