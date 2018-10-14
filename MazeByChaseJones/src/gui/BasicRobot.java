package gui;

import generation.CardinalDirection;

/**
 * @Author: Chase Jones
 * Responsibilities:
 * 
 * 
 * Collaborators:
 * Controller.java
 * 
 */
public class BasicRobot implements Robot {
	
	private int batteryLevel;
	private int odometer;

	public BasicRobot() {
		batteryLevel = 3000;
	}

	@Override
	public void rotate(Turn turn) {
		// TODO Auto-generated method stub

	}

	@Override
	public void move(int distance, boolean manual) {
		// TODO Auto-generated method stub

	}

	@Override
	public int[] getCurrentPosition() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setMaze(Controller controller) {
		// TODO Auto-generated method stub

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
		return false;
	}

	@Override
	public CardinalDirection getCurrentDirection() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public float getBatteryLevel() {
		// TODO Auto-generated method stub
		return batteryLevel;
	}

	@Override
	public void setBatteryLevel(float level) {
		// TODO Auto-generated method stub

	}

	@Override
	public int getOdometerReading() {
		// TODO Auto-generated method stub
		return odometer;
	}

	@Override
	public void resetOdometer() {
		// TODO Auto-generated method stub

	}

	@Override
	public float getEnergyForFullRotation() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getEnergyForStepForward() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean hasStopped() {
		// TODO Auto-generated method stub
		//if(batteryLevel == 0)
			//return true;
		//else
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
