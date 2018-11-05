/**
 * 
 */
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
 * @author chasejones
 *
 */
public class WizardTest {
	
	Controller control;
	BasicRobot robot;
	Wizard wizard;
	
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
		wizard = (Wizard) control.getWizard();
		robot.setMaze(control);
		wizard.setRobot(robot);
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
	public void finishesMaze()
	{
		try {
			assertTrue(wizard.drive2Exit());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
	