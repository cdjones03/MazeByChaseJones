package gui;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import generation.MazeConfiguration;
import gui.Robot.Turn;

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
	
	MazeApplication app = new MazeApplication();
	Controller control;
	MazeConfiguration config;
	BasicRobot robot;
	ManualDriver driver;
	
	@Before
	public void setUp()
	{
		control = app.getController();
		control.switchFromTitleToGenerating(1);
		config = control.getMazeConfiguration();
		robot = (BasicRobot) control.getRobot();
		driver = (ManualDriver) control.getDriver();
		driver.setRobot(robot);
	}
	
	@Test
	public void testEnergy()
	{
		int[] oldPos = driver.getCurrentPosition();
		int[] newPos = driver.getCurrentPosition();
		while(oldPos[0] == newPos[0] && oldPos[1] == newPos[1])
		{
			driver.moveForward();
			driver.turn(Turn.RIGHT);
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
			driver.moveForward();
			driver.turn(Turn.RIGHT);
		}
		assertEquals(1, driver.getPathLength(), 0);
	}

}
