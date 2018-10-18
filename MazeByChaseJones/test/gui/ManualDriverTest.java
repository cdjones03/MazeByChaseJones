package gui;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.After;
import org.junit.Test;

import generation.MazeConfiguration;
import generation.MazeFactory;
import generation.StubOrder;
import generation.Order.Builder;
import gui.Robot.Turn;
import generation.Distance;
import gui.Constants.UserInput;

/**
 * @Author: Chase Jones
 * 
 * Tests the ManualDriver.java class and its various methods
 * for functionality.
 */
public class ManualDriverTest {
	
	Controller control;
	BasicRobot robot;
	ManualDriver driver;
	
	private MazeFactory mazeFac;
	private StubOrder stub;
	MazeConfiguration config;
	
	/**
	 * Creates a new Maze app, and gets all the necessary info to have
	 * a driver and robot in a generated maze.
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
		driver = (ManualDriver) control.getDriver();
		robot.setMaze(control);
		driver.setRobot(robot);
	}
	
	/**
	 * Resets the driver's robot back to the starting position, and 
	 * resets its odometer and battery level.
	 */
	@After
	public void resetRobot()
	{
		int[] start = control.getMazeConfiguration().getStartingPosition();
		control.setCurrentPosition(start[0], start[1]);
		robot.setBatteryLevel(3000);
		robot.resetOdometer();
	}
	
	/**
	 * Test's that a driver performing a turn operation actually changes the
	 * robot's total energy consumption. 
	 */
	@Test
	public void testEnergyChange()
	{
		driver.manualKeyDown(UserInput.Right);
		assertEquals(3, driver.getEnergyConsumption(), 0);
	}
	
	/**
	 * Tests that after moving one step the driver has a total
	 * path length of one. The driver moves (move and rotate) until
	 * its position has actually changes. 
	 */
	@Test
	public void testPathLength()
	{
		int[] oldPos = driver.getCurrentPosition();
		int[] newPos = driver.getCurrentPosition();
		while(oldPos[0] == newPos[0] && oldPos[1] == newPos[1])
		{
			driver.manualKeyDown(UserInput.Up);
			driver.manualKeyDown(UserInput.Left);
			newPos = driver.getCurrentPosition();
		}
		assertEquals(1, driver.getPathLength(), 0);
	}
	
	/**
	 * Tests the drivers get current position method. The positions returned
	 * by the controller and driver should match.
	 */
	@Test
	public void testGetCurrentPosition()
	{
		try {
		int[] manualPos = driver.getCurrentPosition();
		int[] robotPos = control.getRobot().getCurrentPosition();
		assertEquals(manualPos[0], robotPos[0]);
		assertEquals(manualPos[1], robotPos[1]);
		}
		catch (Exception e)
		{
			
		}
	}
	
	/**
	 * Tests the resetEnergyConsumption method. Should perform a series of moves
	 * and turns, then reset the driver's total energy consumption, and
	 * return the driver's total energy consumption as 0.
	 */
	@Test
	public void testResetEnergyConsumption()
	{
		driver.turn(Turn.RIGHT);
		driver.moveForward();
		driver.moveForward();
		driver.turn(Turn.AROUND);
		driver.resetEnergyConsumption();
		assertEquals(0, driver.getEnergyConsumption(), 0);
	}

}
