package gui;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import generation.CardinalDirection;
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
 * -can use level 0 for some tests, need higher level (i.e. rooms) for some
 * Test ideas:
 * 	Rotate
 * 		-right, left, around
 * 		-actually changes energy
 * 		-won't turn if not enough energy
 * 	Move
 * 		-forward
 * 		-actually changes energy
 * 		-won't move without enough energy
 * 
 * isAtExit
 * 		-true if at exit, false if not
 * 
 * canSeeExit
 * 		-true if can see, false if not
 * 
 * getOdometerReading
 * 		-actually gets correct odometer reading
 * 
 * isInsideRoom
 * 		-true if yes, false if no
 * 
 * getBatteryLevel
 * 		-actually gets correct battery level
 * 
 * hasDistance sensor
 */
public class BasicRobotTest {
	
	BasicRobot robot;
	Controller control;
	Cells cells;
	
	private MazeFactory mazeFac;
	private StubOrder stub;
	MazeConfiguration config;
	
	@Before
	public void setUp()
	{
		MazeApplication app = new MazeApplication();
		control = app.getController();
		control.switchFromTitleToGenerating(1);
		config = control.getMazeConfiguration();
		control.switchFromGeneratingToPlaying(config);
		config = control.getMazeConfiguration();
		robot = (BasicRobot) control.getRobot();
		//control.switchFromGeneratingToPlaying(control.getMazeConfiguration());
		
		//robot.setMaze(control);
		/*
		mazeFac = new MazeFactory(true); //Makes maze generation deterministic, for purposes of testing.
		stub = new StubOrder(Builder.DFS, 0, true); //Builder type, skill level, whether perfect or not (i.e no rooms or yes rooms).
		mazeFac.order(stub);
		mazeFac.waitTillDelivered();
		*/
		//config = stub.getMazeConfiguration();
	}
	
	@Test
	public void testEnergy()
	{
		assertEquals(3000, robot.getBatteryLevel(), 0);
	}
	
	@Test
	public void testRotateRight()
	{
		CardinalDirection curDir = robot.getCurrentDirection();
		robot.rotate(Robot.Turn.RIGHT);
		assertEquals(curDir.rotateClockwise(), robot.getCurrentDirection());
	}
	
	@Test
	public void testRotateLeft()
	{
		CardinalDirection curDir = robot.getCurrentDirection();
		robot.rotate(Robot.Turn.LEFT);
		assertEquals(curDir.rotateCounterClockwise(), robot.getCurrentDirection());
	}
	
	@Test
	public void testRotateAround()
	{
		CardinalDirection curDir = robot.getCurrentDirection();
		robot.rotate(Robot.Turn.AROUND);
		assertEquals(curDir.oppositeDirection(), robot.getCurrentDirection());
	}
	
	@Test
	public void rotateChangesEnergy()
	{
		float newEnergy = robot.getBatteryLevel();
		robot.rotate(Robot.Turn.RIGHT);
		newEnergy -= (1/4)*robot.getEnergyForFullRotation();
		assertEquals(newEnergy, robot.getBatteryLevel(), 0);
	}
	
	@Test
	public void rotateWithoutEnoughEnergy()
	{
		robot.setBatteryLevel((1/4)*robot.getEnergyForFullRotation()-1);
		CardinalDirection dir = robot.getCurrentDirection();
		robot.rotate(Robot.Turn.RIGHT);
		assertEquals(dir, robot.getCurrentDirection());
	}
	
	@Test
	public void testMoveOneStepForward()
	{
		try {
		int[] curPos = robot.getCurrentPosition();
		System.out.println(curPos[0] + " " + curPos[1]);}
		catch (Exception e)
		{
			
		}
		robot.move(1, true);
		try {
			int[] curPos = robot.getCurrentPosition();
			System.out.println(curPos[0] + " " + curPos[1]);}
			catch (Exception e)
			{
				
			}
		robot.rotate(Turn.RIGHT);
		robot.move(1, true);
		try {
			int[] curPos = robot.getCurrentPosition();
			System.out.println(curPos[0] + " " + curPos[1]);}
			catch (Exception e)
			{
				
			}
		robot.rotate(Turn.LEFT);
		robot.rotate(Turn.LEFT);
		robot.move(1, true);
		try {
			int[] curPos = robot.getCurrentPosition();
			System.out.println(curPos[0] + " " + curPos[1]);}
			catch (Exception e)
			{
				
			}
		assertTrue(true);
	}
	
	@Test
	public void testMoveMultipleStepsForward()
	{
		
	}
	
	@Test
	public void isActuallyAtExit()
	{
		//MazeConfiguration config = control.getMazeConfiguration();
		cells = config.getMazecells();
		int[] exit = config.getMazedists().computeDistances(cells);
		//Distance dist = config.getMazedists().computeDistances(cells);
		//control.setCurrentPosition(exit[0], exit[1]);
		assertTrue(robot.isAtExit());
	}
	
	@Test
	public void isNotAtExit()
	{
		
	}
	
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
	
	@Test
	public void robotInRoom()
	{
		cells = config.getMazecells();
		int x = 0; 
		int y = 0;
		while(!(cells.isInRoom(x, y)))
		{
			
		}
	}
	
	@Test
	public void robotNotInRoom()
	{
		cells = config.getMazecells();
		int x = 0; 
		int y = 0;
		while(cells.isInRoom(x, y))
		{
			
		}
	}
	
}
