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
 * Test ideas:
 * 
 * EnergyConsumption	
 * 	-actually works for series of moves
 * 
 * pathLength
 * 	-actually works for series of moves
 */
public class ManualDriverTest {
	
	Controller control;
	BasicRobot robot;
	ManualDriver driver;
	
	private MazeFactory mazeFac;
	private StubOrder stub;
	MazeConfiguration config;
	
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
	
	@After
	public void resetRobot()
	{
		int[] start = control.getMazeConfiguration().getStartingPosition();
		control.setCurrentPosition(start[0], start[1]);
		robot.setBatteryLevel(3000);
		robot.resetOdometer();
	}
	
	@Test
	public void testEnergy()
	{
		int[] oldPos = driver.getCurrentPosition();
		int[] newPos = driver.getCurrentPosition();
		while(oldPos[0] == newPos[0] && oldPos[1] == newPos[1])
		{
			driver.manualKeyDown(UserInput.Up);
			driver.manualKeyDown(UserInput.Right);
			newPos = driver.getCurrentPosition();
		}
		assertEquals(5, driver.getEnergyConsumption(), 0);
	}
	
	
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

}
