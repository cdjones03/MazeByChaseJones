package gui;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import generation.CardinalDirection;
import gui.Robot.Turn;
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
 * getCurrentDirection
 * 		-current direction works after series of rotates/moves
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
	
	//MazeApplication app = new MazeApplication();
	BasicRobot robot = new BasicRobot();
	Controller control = new Controller();
	
	@Before
	public void setUp()
	{
		robot.setMaze(control);
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
		//robot.move(1, yes);
	}
	
	@Test
	public void testMoveMultipleStepsForward()
	{
		
	}
	
	@Test
	public void isActuallyAtExit()
	{
		control.getMazeConfiguration().getMazedists().getExitPosition();
	}
	
	@Test
	public void isNotAtExit()
	{
		
	}
	
}
