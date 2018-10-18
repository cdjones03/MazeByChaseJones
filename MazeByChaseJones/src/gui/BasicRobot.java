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
 * Keeps track of a robot in the maze. Robot has sensors to check for
 * the exit and obstacles (walls, borders) that would be in its path.
 * Robot has a battery that is depleted with every sensor use, move, or
 * rotate.
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
	final private float initialBatteryLevel = 10;
	MazeConfiguration config;

	/**
	 * Constructor for a BasicRobot. Initializes the battery and odometer,
	 * set the robot's current direction, and allows it a room sensor.
	 */
	public BasicRobot() {
		batteryLevel = initialBatteryLevel;
		odometer = 0;
		curDir = CardinalDirection.East;
		roomSensor = true;
	}

	@Override
	/**
	 * Rotates the robot either Right, Left, or Around (180 degrees).
	 * Checks the battery to make sure the robot has enough energy to rotate
	 * before doing so. Updates the robot's current direction if it does
	 * rotate.
	 */
	public void rotate(Turn turn) {
		//if(batteryLevel < 5)
			//noEnergyGameLost();
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
			assert batteryLevel >= ((1/4)*getEnergyForFullRotation()): " battery level too low for rotate";
			control.keyDown(UserInput.Left, 0);
			batteryLevel -= ((1/4)*getEnergyForFullRotation());
			curDir = curDir.rotateCounterClockwise();
		}
		
		else if(batteryLevel >= 6 && turn.equals(Robot.Turn.AROUND))
		{
			assert batteryLevel >= ((1/2)*getEnergyForFullRotation()): " battery level too low for rotate";
			control.keyDown(UserInput.Right, 0);
			control.keyDown(UserInput.Right, 0);
			batteryLevel -= ((1/2)*getEnergyForFullRotation());
			curDir = curDir.oppositeDirection();
		}
	}

	@Override
	/**
	 * Moves the robot based on whether it is being manually driven or automatically
	 * driven. For the manual driver, it will only ever move the robot once, so it just
	 * makes sure the robot has the energy to move. Only takes energy if the robot actually moves.
	 * For the automatic robot, the robot is given @param distance # of steps to move. 
	 * The robot checks its battery and that there is no wall in front of it before each move. If either
	 * of these occur, the robot stops moving, even if the distance and current number of steps taken
	 * do not match. Only takes energy based on the amount of steps the robot actually moves.
	 */
	public void move(int distance, boolean manual) {
		if(batteryLevel < 5)
			noEnergyGameLost();
		if(manual) //Robot will move forward one step if possible.
		{
			try {
				int[] curPos = getCurrentPosition();
				System.out.println("cur " + curPos[0] + curPos[1]);
				assert getBatteryLevel() >= getEnergyForStepForward(): " battery too low for move";
				assert !hasStopped() : " robot has stopped";
				for(int moveCount = 0; moveCount < distance; moveCount++)
				{
					control.keyDown(UserInput.Up, 0);
					int[] newPos = getCurrentPosition();
					if(curPos[0] != newPos[0] || curPos[1] != newPos[1])
					{
						batteryLevel -= getEnergyForStepForward();
						odometer += 1;
					}
				}
			}
			catch (Exception e)
			{
				
			}
		}
		else
		{
			Cells cells = config.getMazecells();
			for(int moveCount = 0; moveCount < distance; moveCount++)
			{
				try {
					int[] curPos = getCurrentPosition();
					//Makes sure the robot has energy to move and has no wall in front of it.
					//If it does, the robot stops the current move operation.
					if (batteryLevel < getEnergyForStepForward() || cells.hasWall(curPos[0],curPos[1], translateDir(Direction.FORWARD)))
					{
						stopped = true;
						break;
					}
					//If the robot has not stopped, the robot moves, and if it actually moved, takes energy
					//and increases the odometer by 1.
					if(!hasStopped())
					{
						control.keyDown(UserInput.Up, 0);
						int[] newPos = getCurrentPosition();
						if(curPos[0] != newPos[0] || curPos[1] != newPos[1])
						{
							batteryLevel -= getEnergyForStepForward();
							odometer += 1;
						}
					}	
				}
				catch (Exception e)
				{
					//System.out.println("Robot outside of maze.");
				}
			}
		}
		stopped = false; //Resets the stopped flag after the move operation.
	}

	@Override
	public int[] getCurrentPosition() throws Exception {
		try {
		return control.getCurrentPosition(); }
		catch (Exception e)
		{
			
		}
		return null;
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
			//System.out.println("Robot outside of maze.");
		}
		return atExit;
	}

	@Override
	public boolean canSeeExit(Direction direction) throws UnsupportedOperationException {
		// TODO Auto-generated method stub
		assert batteryLevel >= 1 : " not enough energy to sense";
		//if(batteryLevel < 5)
			//noEnergyGameLost();
		if(distanceToObstacle(direction) == Integer.MAX_VALUE)
			return true;
		else
			return false;
	}

	@Override
	public boolean isInsideRoom() throws UnsupportedOperationException {
		boolean inRoom = false;
		try {
			int[] curPos = getCurrentPosition();
			inRoom = config.getMazecells().isInRoom(curPos[0], curPos[1]);
		}
		catch (Exception e)
		{
			System.out.println("Robot outside of maze.");
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
	 * 1. Gets the robot's current position.
	 * 2. Goes through the maze (cells object) step by step, checking if
	 * 	each step has a border in the direction. 
	 * 3. Once the border is found,
	 * 	or if the robot is looking through the exit, the distance to the border
	 * 	is returned as the number of steps it takes to get there.
	 * 4. If the robot is looking through the exit, returns Infinity.
	 */
	public int distanceToObstacle(Direction direction) throws UnsupportedOperationException {
		assert batteryLevel >= 1 :  " not enough battery";
		//if(batteryLevel < 5)
			//noEnergyGameLost();
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
			System.out.println("Robot is outside of the maze. ");
		}
		
		return distCount;
	}
	
	/**
	 * Checks if the robot has a distance sensor in the given direction, 
	 * which is checked using the defined enum of Direction in the 
	 * Robot interface.
	 */
	@Override
	public boolean hasDistanceSensor(Direction direction) {
		// TODO Auto-generated method stub
		for(Direction dir : Direction.values()){
			if(direction.equals(dir))
				return true;
		}
		return false;
	}
	
	/**
	 * Translates a Robot direction into a cardinal direction, based
	 * on the robot's current direction.
	 * @param direction
	 * @return
	 */
	public CardinalDirection translateDir(Direction direction)
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
	
	/**
	 * Changes the robot's current position based on
	 * its current direction.
	 * @param pos
	 * @return
	 */
	public int[] getNewPos(int[] pos)
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
	
	/**
	 * Used by winning screen to tell if the robot ran out of energy and
	 * cannot complete the maze. Since 5 energy is needed to move, although the robot
	 * could still rotate or sense, it could not complete the maze without 
	 * at least 5 energy.
	 * @return
	 */
	public boolean robotOutOfEnergy()
	{
		if(this.batteryLevel < 5)
				return true;
		else
			return false;
	}
	
	public void noEnergyGameLost()
	{
		control.switchFromPlayingToWinning(this.getOdometerReading());
	}
	

}
