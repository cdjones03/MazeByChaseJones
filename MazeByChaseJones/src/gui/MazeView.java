package gui;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JFrame;

import gui.Constants.StateGUI;

/**
 * Implements the screens that are displayed whenever the game is not in 
 * the playing state. The screens shown are the title screen, 
 * the generating screen with the progress bar during maze generation,
 * and the final screen when the game finishes.
 * 
 * @author Peter Kemper
 *
 */
public class MazeView implements ActionListener{

	private StateGenerating controllerState; // used for generating screen
	//ActionListener l = new ActionListener();
    MazePanel mazePanel;
    MazePanel bPanel;
    public MazeView(StateGenerating c) {
        super() ;
        controllerState = c ;
    }
    
    public void drawBoxes(MazePanel boxPanel)
	{
    	bPanel = boxPanel;
    	Graphics g = boxPanel.getBufferGraphics();
    	g.setColor(Color.white);
    	g.fillRect(0, 0, Constants.VIEW_WIDTH, 50);
    	g.setFont(smallBannerFont);
    	g.setColor(Color.red);
		boxPanel.add(new Button("Test1"));
    	
    	Integer[] skillLevels = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15};
    	JComboBox skillBox = new JComboBox(skillLevels);
    	skillBox.setSelectedIndex(0);
    	/*
    	skillBox.addActionListener(new ActionListener() {
    		@Override
    		public void actionPerformed(ActionEvent event) {
    			JComboBox<Integer> combo = (JComboBox<Integer>) event.getSource();
    	        int selectedBook = (int) combo.getSelectedItem();
    			}});*/
    	boxPanel.add(skillBox);
    	skillBox.setLightWeightPopupEnabled (false);
        boxPanel.setVisible(true);
    	boxPanel.revalidate();
    	boxPanel.repaint();
	}
    
    /**
     * Draws the title screen, screen content is hard coded
     * @param panel holds the graphics for the off-screen image
     * @param filename is a string put on display for the file
     * that contains the maze, can be null
     */
    public void redrawTitle(MazePanel panel, String filename) {
    	Graphics g = panel.getBufferGraphics() ;
    	mazePanel = panel;
        if (null == g) {
            System.out.println("MazeView.redrawTitle: can't get graphics object to draw on, skipping redraw operation") ;
        }
        else {
            redrawTitle(g,filename, panel);
        }
    }
    /**
     * Helper method for redraw to draw the title screen, screen is hard coded
     * @param  gc graphics is the off-screen image, can not be null
     * @param filename is a string put on display for the file
     * that contains the maze, can be null
     */
    private void redrawTitle(Graphics gc, String filename, MazePanel panel) {
        // produce white background
        gc.setColor(Color.white);
        gc.fillRect(0, 50, Constants.VIEW_WIDTH, Constants.VIEW_HEIGHT);
        // write the title 
        gc.setFont(largeBannerFont);
        FontMetrics fm = gc.getFontMetrics();
        gc.setColor(Color.red);
        centerString(gc, fm, "MAZE", 100);
        // write the reference to Paul Falstad
        gc.setColor(Color.blue);
        gc.setFont(smallBannerFont);
        fm = gc.getFontMetrics();
        centerString(gc, fm, "by Paul Falstad", 160);
        centerString(gc, fm, "www.falstad.com", 190);
        // write the instructions
        gc.setColor(Color.black);
        if (filename == null) {
        	// default instructions
        	centerString(gc, fm, "To start, select a skill level.", 250);
        	centerString(gc, fm, "(Press a number from 0 to 9,", 300);
        	centerString(gc, fm, "or a letter from A to F)", 320);
        		
        }
        else {
        	// message if maze is loaded from file
        	centerString(gc, fm, "Loading maze from file:", 250);
        	centerString(gc, fm, filename, 300);
        }
        centerString(gc, fm, "Version 4.0", 350);
    }

	
	public void redraw(Graphics gc, StateGUI state, int px, int py, int view_dx,
			int view_dy, int walk_step, int view_offset, RangeSet rset, int ang) {
		//dbg("redraw") ;
		switch (state) {
		case STATE_TITLE:
			redrawTitle(gc,null, mazePanel);
			drawBoxes(bPanel);
			break;
		case STATE_GENERATING:
			redrawGenerating(gc);
			break;
		case STATE_PLAY:
			// skip this one
			break;
		case STATE_FINISH:
			redrawFinish(gc);
			break;
		}
	}
	
	private void dbg(String str) {
		System.out.println("MazeView:" + str);
	}
    /**
     * Draws the finish screen, screen content is hard coded
     * @param panel holds the graphics for the off-screen image
     * 
     * Parameters for energyConsumed and pathLength added by @ChaseJones
     * for Project 3.
     */
	void redrawFinish(MazePanel panel, float energyConsumed, int pathLength, boolean outOfEnergy) {
		Graphics g = panel.getBufferGraphics() ;
        if (null == g) {
            System.out.println("MazeView.redrawFinish: can't get graphics object to draw on, skipping redraw operation") ;
        }
        else {
            redrawFinish(g, energyConsumed, pathLength, outOfEnergy);
        }
	}
	
	/**
	 * Helper method for redraw to draw final screen, screen is hard coded
	 * @param gc graphics is the off-screen image
	 */
	void redrawFinish(Graphics gc) {
		// produce blue background
		gc.setColor(Color.blue);
		gc.fillRect(0, 0, Constants.VIEW_WIDTH, Constants.VIEW_HEIGHT);
		// write the title 
		gc.setFont(largeBannerFont);
		FontMetrics fm = gc.getFontMetrics();
		gc.setColor(Color.yellow);
		centerString(gc, fm, "You won!", 100);
		// write some extra blurb
		gc.setColor(Color.orange);
		gc.setFont(smallBannerFont);
		fm = gc.getFontMetrics();
		centerString(gc, fm, "Congratulations!", 160);
		// write the instructions
		gc.setColor(Color.white);
		centerString(gc, fm, "Hit any key to restart", 300);
	}
	
	/**
	 * Overloaded of first redrawFinish method.
	 * Has parameters for robot's total energy consumption and path length
	 * to be displayed on final screen for user.
	 * @param gc
	 * @param energyConsumed
	 * @param pathLength
	 */
	void redrawFinish(Graphics gc, float energyConsumed, int pathLength, boolean outOfEnergy) {
		// produce blue background
		gc.setColor(Color.blue);
		gc.fillRect(0, 0, Constants.VIEW_WIDTH, Constants.VIEW_HEIGHT);
		// write the title 
		gc.setFont(largeBannerFont);
		FontMetrics fm = gc.getFontMetrics();
		gc.setColor(Color.yellow);
		if(!outOfEnergy)
			centerString(gc, fm, "You won!", 100);
		else
			centerString(gc, fm, "You lost!", 100);
		// write some extra blurb
		gc.setColor(Color.orange);
		gc.setFont(smallBannerFont);
		fm = gc.getFontMetrics();
		if(!outOfEnergy) {
		centerString(gc, fm, "Congratulations!", 160);}
		centerString(gc, fm, "Total Energy Consumed: " + energyConsumed, 200);
		centerString(gc, fm, "Total Path Length: " + pathLength, 220);
		if(outOfEnergy)
			centerString(gc, fm, "Your robot ran out of energy! You'll have to try again.", 260);
		// write the instructions
		gc.setColor(Color.white);
		centerString(gc, fm, "Hit any key to restart", 300);
	}
    /**
     * Draws the generating screen, screen content is hard coded
     * @param panel holds the graphics for the off-screen image
     */
    public void redrawGenerating(MazePanel panel) {
    	Graphics g = panel.getBufferGraphics() ;
        if (null == g) {
            System.out.println("MazeView.redrawGenerating: can't get graphics object to draw on, skipping redraw operation") ;
        }
        else {
            redrawGenerating(g);
        }
    }
	/**
	 * Helper method for redraw to draw screen during phase of maze generation, screen is hard coded
	 * only attribute percentdone is dynamic
	 * @param gc graphics is the off screen image
	 */
	void redrawGenerating(Graphics gc) {
		// produce yellow background
		gc.setColor(Color.yellow);
		gc.fillRect(0, 0, Constants.VIEW_WIDTH, Constants.VIEW_HEIGHT);
		// write the title 
		gc.setFont(largeBannerFont);
		FontMetrics fm = gc.getFontMetrics();
		gc.setColor(Color.red);
		centerString(gc, fm, "Building maze", 150);
		gc.setFont(smallBannerFont);
		fm = gc.getFontMetrics();
		// show progress
		gc.setColor(Color.black);
		if (null != controllerState) 
		    centerString(gc, fm, controllerState.getPercentDone()+"% completed", 200);
		else
			centerString(gc, fm, "Error: no controller, no progress", 200);
		// write the instructions
		centerString(gc, fm, "Hit escape to stop", 300);
	}
	
	private void centerString(Graphics g, FontMetrics fm, String str, int ypos) {
		g.drawString(str, (Constants.VIEW_WIDTH-fm.stringWidth(str))/2, ypos);
	}

	final Font largeBannerFont = new Font("TimesRoman", Font.BOLD, 48);
	final Font smallBannerFont = new Font("TimesRoman", Font.BOLD, 16);
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	

}
