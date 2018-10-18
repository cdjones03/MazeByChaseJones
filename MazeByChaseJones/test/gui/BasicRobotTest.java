package gui;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.After;
import org.junit.Test;

import generation.CardinalDirection;
import gui.Constants.UserInput;
import gui.Robot.Direction;
import gui.Robot.Turn;
import generation.Cells;
import generation.Distance;
import generation.MazeConfiguration;
import generation.MazeFactory;
import generation.StubOrder;
import generation.Order.Builder;

/**
 * 
 * @author chasejones
 * 
 * Tests the BasicRobot.java class and its various methods
 * for functionality. Tests features like its movements and
 * rotations, energy consumption, odometer, etc.
 */
public class BasicRobotTest {
	
	BasicRobot robot;
	Controller control;
	Cells cells;
	
	private MazeFactory mazeFac;
	private StubOrder stub;
	MazeConfiguration config;
	
	/**
	 * Runs before each test, creating a new app and maze.
	 * Get the BasicRobot from the controller so it can be tested on.
	 * Skill level 2 and imperfect maze is order for room checking testing.
	 */
	@Before
	public void setUp()
	{
		MazeApplication app = new MazeApplication();
		control = app.getController();

		mazeFac = new MazeFactory(true); //Makes maze generation deterministic, for purposes of testing.
		stub = new StubOrder(Builder.DFS, 2, false); //Builder type, skill level, whether perfect or not (i.e no rooms or yes rooms).
		mazeFac.order(stub);
		mazeFac.waitTillDelivered();
		config = stub.getMazeConfiguration();
		
		control.switchFromGeneratingToPlaying(config);
		robot = (BasicRobot) control.getRobot();
		robot.setMaze(control);
	}
	
	/**
	 * Resets the robot to the starting position of the maze, and refills
	 * its battery back to full.
	 */
	@After
	public void resetRobot()
	{
		int[] start = control.getMazeConfiguration().getStartingPosition();
		control.setCurrentPosition(start[0], start[1]);
		robot.setBatteryLevel(3000);
	}
	
	/**
	 * Tests to make sure the robot starts with odometer at 0, and
	 * after one move the odometer is 1.
	 */
	@Test
	public void testOdometer()
	{
		try {
		assertEquals(0, robot.getOdometerReading());
		int[] oldPos = robot.getCurrentPosition();
		int[] newPos = robot.getCurrentPosition();
		while(oldPos[0] == newPos[0] && oldPos[1] == newPos[1])
		{
			robot.move(1, false);
			robot.rotate(Robot.Turn.RIGHT);
			newPos = robot.getCurrentPosition();
		}
		assertEquals(1, robot.getOdometerReading());
		robot.resetOdometer();
		assertEquals(0, robot.getOdometerReading());
		}
		catch (Exception e)
		{
			
		}
	}
	
	/**
	 * Tests that the robot starts with 3000 energy.
	 */
	@Test
	public void testEnergy()
	{
		assertEquals(3000, robot.getBatteryLevel(), 0);
	}
	
	/**
	 * Tests the robot's rotate right functionality. Should change its
	 * direction to clockwise from the original one.
	 */
	@Test
	public void testRotateRight()
	{
		CardinalDirection curDir = robot.getCurrentDirection();
		robot.rotate(Robot.Turn.RIGHT);
		assertEquals(curDir.rotateClockwise(), robot.getCurrentDirection());
	}
	
	/**
	 * Tests the robot's rotate left functionality. Should change its
	 * direction to counter clockwise from the original one.
	 */
	@Test
	public void testRotateLeft()
	{
		CardinalDirection curDir = robot.getCurrentDirection();
		robot.rotate(Robot.Turn.LEFT);
		assertEquals(curDir.rotateCounterClockwise(), robot.getCurrentDirection());
	}
	/**
	 * Tests the robot's rotate around functionality. Should change its
	 * direction to opposite the original one.
	 */
	@Test
	public void testRotateAround()
	{
		CardinalDirection curDir = robot.getCurrentDirection();
		robot.rotate(Robot.Turn.AROUND);
		assertEquals(curDir.oppositeDirection(), robot.getCurrentDirection());
	}
	
	/**
	 * Tests that a robot's battery level is actually changed after a
	 * rotate operation.
	 */
	@Test
	public void rotateChangesEnergy()
	{
		float newEnergy = robot.getBatteryLevel();
		robot.rotate(Robot.Turn.RIGHT);
		newEnergy -= (0.25)*robot.getEnergyForFullRotation();
		assertEquals(newEnergy, robot.getBatteryLevel(), 0);
	}
	
	/**
	 * Tests that a robot without enough energy to rotate that 
	 * tries to rotate does not successfully do so.
	 */
	@Test
	public void rotateWithoutEnoughEnergy()
	{
		robot.setBatteryLevel((1/4)*robot.getEnergyForFullRotation()-1);
		CardinalDirection dir = robot.getCurrentDirection();
		robot.rotate(Robot.Turn.RIGHT);
		assertEquals(dir, robot.getCurrentDirection());
	}
	
	/**
	 * Tests that moving the robot, testing its .move function,
	 * actually changes its position.
	 */
	@Test
	public void testMoveOneStepForward()
	{
		try {
			int[] oldPos = robot.getCurrentPosition();
			int[] newPos = robot.getCurrentPosition();
			while(oldPos[0] == newPos[0] && oldPos[1] == newPos[1])
			{
				robot.move(1, false);
				robot.rotate(Robot.Turn.RIGHT);
				newPos = robot.getCurrentPosition();
			}
			assertNotEquals(oldPos, newPos);
			}
			catch (Exception e)
			{
				
			}
		
	}
	
	/**
	 * Tests that moving the robot manually, testing its .move function,
	 * actually changes its position.
	 */
	@Test
	public void testMoveOneStepForwardManual()
	{
		try {
			int[] oldPos = robot.getCurrentPosition();
			int[] newPos = robot.getCurrentPosition();
			while(oldPos[0] == newPos[0] && oldPos[1] == newPos[1])
			{
				robot.move(1, true);
				robot.rotate(Robot.Turn.RIGHT);
				newPos = robot.getCurrentPosition();
			}
			assertNotEquals(oldPos, newPos);
			}
			catch (Exception e)
			{
				
			}
		
	}
	
	/**
	 * Tests the .isAtExit method. Gets the maze's exit from the maze's
	 * Distances, sets the robot's position to there, then
	 * tests the method.
	 */
	@Test
	public void isActuallyAtExit()
	{
		cells = config.getMazecells();
		int[] exit = config.getMazedists().computeDistances(cells);
		control.setCurrentPosition(exit[0], exit[1]);
		assertTrue(robot.isAtExit());
	}
	
	/**
	 * A robot should never spawn next to the exit, so this test
	 * makes sure the isAtExit() method actually returns false when 
	 * the robot is at the start.
	 */
	@Test
	public void isNotAtExit()
	{
		assertFalse(robot.isAtExit());
	}
	
	/**
	 * Tests that a robot at the exit can actually see the exit. Gets
	 * the maze's exit from the Distances computed and then sets the robot's
	 * location to there.
	 */
	@Test
	public void canSeeExit()
	{
		cells = config.getMazecells();
		int[] exit = config.getMazedists().computeDistances(cells);
		control.setCurrentPosition(exit[0], exit[1]);
		while(!(robot.canSeeExit(Direction.FORWARD)))
			robot.rotate(Turn.RIGHT);
		assertTrue(robot.canSeeExit(Direction.FORWARD));
	}
	
	/**
	 * Tests the .isInsideRoom method. Searches the maze until it finds a
	 * position inside a room, then sets the robot's position to there
	 * and checks if the robot is in a room using its own method.
	 */
	@Test
	public void robotInRoom()
	{
		assertTrue(robot.hasRoomSensor());
		cells = config.getMazecells();
		int checkX = 0;
		int checkY = 0;
		for (int x = 0; x < config.getWidth(); x++)
		{
			for(int y = 0; y < config.getHeight(); y++)
			{
				if(cells.isInRoom(x, y))
				{
					checkX = x;
					checkY = y;
					break;
				}
			}
			if(cells.isInRoom(x, checkY))
				break;
		}
		control.setCurrentPosition(checkX, checkY);
		assertTrue(robot.isInsideRoom());
	}
	
	/**
	 * Tests the .isInsideRoom method. Searches the maze until it finds a
	 * position not inside a room, then sets the robot's position to there
	 * and checks if the robot is not in a room using its own method.
	 */
	@Test
	public void robotNotInRoom()
	{
		cells = config.getMazecells();
		int checkX = 0; 
		int checkY = 0;
		for (int x = 0; x < config.getWidth(); x++)
		{
			for(int y = 0; y < config.getHeight(); y++)
			{
				if(!(cells.isInRoom(x, y)))
				{
					checkX = x;
					checkY = y;
					break;
				}
			}
			if(!(cells.isInRoom(x, checkY)))
				break;
		}
		control.setCurrentPosition(checkX, checkY);
		assertFalse(robot.isInsideRoom());
	}
	
	/**
	 * Tests that a generic basicRobot has a distance sensor.
	 */
	@Test
	public void testHasDistanceSensor()
	{
		Direction dir = Direction.FORWARD;
		assertTrue(robot.hasDistanceSensor(dir));
	}
	
	/**
	 * Tests that a robot cannot see the exit. Since this is tested
	 * when the robot is at the start of the maze, it should never be
	 * positioned next to the exit, making the test work.
	 */
	@Test
	public void testCannotSeeExit()
	{
		assertFalse(robot.canSeeExit(Direction.FORWARD));
	}
	
	/**
	 * Tests the translateDir() method, which takes the robot's current
	 * direction and maps it to a cardinal direction.
	 */
	@Test
	public void testTranslateDirection()
	{
		assertEquals(CardinalDirection.East, robot.translateDir(Direction.FORWARD));
		assertEquals(CardinalDirection.South, robot.translateDir(Direction.RIGHT));
		assertEquals(CardinalDirection.West, robot.translateDir(Direction.BACKWARD));
		assertEquals(CardinalDirection.North, robot.translateDir(Direction.LEFT));
	}
}
