package gui;

import static org.junit.jupiter.api.Assertions.assertAll;

import generation.CardinalDirection;
import gui.Constants.UserInput;
import generation.MazeConfiguration;
import generation.Cells;
import generation.Wall;

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
	private Controller control;
	private boolean stopped;
	final private float energyForFullRotation = 12;
	final private float energyForStepForward = 5;
	final private float initialBatteryLevel = 3000;
	MazeConfiguration config;

	public BasicRobot() {
		batteryLevel = initialBatteryLevel;
		odometer = 0;
		curDir = CardinalDirection.East;
		roomSensor = true;
	}

	@Override
	public void rotate(Turn turn) {
		// TODO Auto-generated method stub
		if(turn.equals(Robot.Turn.RIGHT))
		{
			if(batteryLevel >= ((1/4)*getEnergyForFullRotation()))
			{
			control.keyDown(UserInput.Right, 0);
			batteryLevel -= ((1/4)*getEnergyForFullRotation());
			curDir = curDir.rotateClockwise();
			}
		}
		else if(turn.equals(Robot.Turn.LEFT))
		{
			assert batteryLevel >= ((1/4)*getEnergyForFullRotation()): " battery level too low for rotate" + (stopped = true);
			control.keyDown(UserInput.Left, 0);
			batteryLevel -= ((1/4)*getEnergyForFullRotation());
			curDir = curDir.rotateCounterClockwise();
		}
		else if(batteryLevel >= 6 && turn.equals(Robot.Turn.AROUND))
		{
			assert batteryLevel >= ((1/2)*getEnergyForFullRotation()): " battery level too low for rotate" + (stopped = true);
			control.keyDown(UserInput.Right, 0);
			control.keyDown(UserInput.Right, 0);
			batteryLevel -= ((1/2)*getEnergyForFullRotation());
			curDir = curDir.oppositeDirection();
		}
	}

	@Override
	public void move(int distance, boolean manual) {
		// TODO Auto-generated method stub
		assert batteryLevel >= getEnergyForStepForward() : " battery too low for move" + (stopped = true);
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
		config = control.getMazeConfiguration();
	}

	@Override
	public boolean isAtExit() {
		// TODO Auto-generated method stub
		boolean atExit = false;
		try {
			int[] curPos = getCurrentPosition();
			atExit = config.getMazecells().isExitPosition(curPos[0], curPos[1]);
		}
		catch (Exception e)
		{
			System.out.println("No current position yet.");
		}
		return atExit;
	}

	@Override
	public boolean canSeeExit(Direction direction) throws UnsupportedOperationException {
		// TODO Auto-generated method stub
		if(distanceToObstacle(direction) == Integer.MAX_VALUE)
			return true;
		else
			return false;
	}

	@Override
	public boolean isInsideRoom() throws UnsupportedOperationException {
		// TODO Auto-generated method stub
		boolean inRoom = false;
		try {
			int[] curPos = getCurrentPosition();
			inRoom = config.getMazecells().isInRoom(curPos[0], curPos[1]);
		}
		catch (Exception e)
		{
			System.out.println("No current position yet.");
		}
		return inRoom;
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
		return energyForFullRotation;
	}

	@Override
	public float getEnergyForStepForward() {
		// TODO Auto-generated method stub
		return energyForStepForward;
	}

	@Override
	public boolean hasStopped() {
		// TODO Auto-generated method stub
		 return stopped;
	}

	@Override
	/**
	 * Needs access to maze config cells
	 * Start with cur position, go in cur direction until obstace
	 * Count number of steps to get there
	 * Translate between CardinalDirection, Direction, Wall, etc.
	 * 
	 * If Direction is forward, corresponds to robot's curDir
	 * If Direction is Left, corresponds to curDir.rotateCounterClockwise
	 * If Direction is Right, corresponds to curDir.rotateClockwise
	 * If Direction is Backwards, corresponds to curDir.opposite
	 * 	If robot is facing 
	 */
	public int distanceToObstacle(Direction direction) throws UnsupportedOperationException {
		// TODO Auto-generated method stub
		Cells cells = config.getMazecells();
		int distCount = 0;
		try 
		{
			int[] curPos = getCurrentPosition();
			Wall wall = new Wall(curPos[0], curPos[1], translateDir(direction));

			int[] newPos = curPos;
			while(cells.canGo(wall) && !(cells.isExitPosition(newPos[0], newPos[1])))
			{
				distCount++;
				newPos = getNewPos(newPos);
				wall = new Wall(newPos[0], newPos[1], translateDir(direction));
			}
			if(cells.isExitPosition(newPos[0], newPos[1]))
				return Integer.MAX_VALUE;
		}
		
		catch (Exception e)
		{
			System.out.println("No current position yet. ");
		}
		
		return distCount;
	}

	@Override
	public boolean hasDistanceSensor(Direction direction) {
		// TODO Auto-generated method stub
		for(Direction dir : Direction.values()){
			if(direction.equals(dir))
				return true;
		}
		return false;
	}
	
	//Translate between CardinalDirection, Direction, etc.
	private CardinalDirection translateDir(Direction direction)
	{
		CardinalDirection newDir = curDir;
		switch(direction) {
		case FORWARD :
			newDir = curDir;
			break;
		case LEFT :
			newDir = curDir.rotateCounterClockwise();
			break;
		case RIGHT :
			newDir = curDir.rotateClockwise();
			break;
		case BACKWARD :
			newDir = curDir.oppositeDirection();
			break;
		default :
			break;
		}
		return newDir;
	}
	
	private int[] getNewPos(int[] pos)
	{
		int[] newPos = pos;
		switch(curDir)
		{
		case East :
			newPos[1]++;
			break;
		case West :
			newPos[1]--;
			break;
		case North :
			newPos[0]--;
			break;
		case South :
			newPos[0]++;
			break;
		default :
			break;
		}
		return newPos;
	}
	

}
