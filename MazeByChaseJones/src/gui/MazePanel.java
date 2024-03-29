package gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Panel;
import java.awt.RenderingHints;
//import java.awt.image.BufferedImage;

/**
 * Add functionality for double buffering to an AWT Panel class.
 * Used for drawing a maze.
 * 
 * @author Peter Kemper
 *
 */
public class MazePanel extends Panel  {
	/* Panel operates a double buffer see
	 * http://www.codeproject.com/Articles/2136/Double-buffer-in-standard-Java-AWT
	 * for details
	 */
	// bufferImage can only be initialized if the container is displayable,
	// uses a delayed initialization and relies on client class to call initBufferImage()
	// before first use
	private Image bufferImage;  
	private Graphics2D graphics; // obtained from bufferImage, 
	// graphics is stored to allow clients to draw on the same graphics object repeatedly
	// has benefits if color settings should be remembered for subsequent drawing operations
	
	static Color segColor;
	
	public static Color getSegColor() {
		return segColor;
	}
	
	public static void setSegColor(int x, int y, int z) {
		segColor = new Color(x, y, z);
	}
	
	public static void setSegColor(int x) {
		segColor = new Color(x);
	}
	
	/**
	 * Constructor. Object is not focusable.
	 */
	public MazePanel() {
		setFocusable(false);
		bufferImage = null; // bufferImage initialized separately and later
		graphics = null;	// same for graphics
	}
	
	//@Override
	public void update(Graphics g) {
		paint(g);
	}
	/**
	 * Method to draw the buffer image on a graphics object that is
	 * obtained from the superclass. 
	 * Warning: do not override getGraphics() or drawing might fail. 
	 */
	public void update() {
		paint(getGraphics());
	}
	
	/**
	 * Draws the buffer image to the given graphics object.
	 * This method is called when this panel should redraw itself.
	 * The given graphics object is the one that actually shows 
	 * on the screen.
	 */
	//@Override
	public void paint(Graphics g) {
		if (null == g) {
			System.out.println("MazePanel.paint: no graphics object, skipping drawImage operation");
		}
		else {
			g.drawImage(bufferImage,0,0,null);	
		}
	}

	/**
	 * Obtains a graphics object that can be used for drawing.
	 * This MazePanel object internally stores the graphics object 
	 * and will return the same graphics object over multiple method calls. 
	 * The graphics object acts like a notepad where all clients draw 
	 * on to store their contribution to the overall image that is to be
	 * delivered later.
	 * To make the drawing visible on screen, one needs to trigger 
	 * a call of the paint method, which happens 
	 * when calling the update method. 
	 * @return graphics object to draw on, null if impossible to obtain image
	 */
	public Graphics getBufferGraphics() {
		// if necessary instantiate and store a graphics object for later use
		if (null == graphics) { 
			if (null == bufferImage) {
				bufferImage = createImage(Constants.VIEW_WIDTH, Constants.VIEW_HEIGHT);
				//bufferImage = new BufferedImage(Constants.VIEW_WIDTH, Constants.VIEW_HEIGHT, BufferedImage.TYPE_INT_RGB);
				if (null == bufferImage)
				{
					System.out.println("Error: creation of buffered image failed, presumedly container not displayable");
					return null; // still no buffer image, give up
				}		
			}
			graphics = (Graphics2D) bufferImage.getGraphics();
			if (null == graphics) {
				System.out.println("Error: creation of graphics for buffered image failed, presumedly container not displayable");
			}
			else {
				// System.out.println("MazePanel: Using Rendering Hint");
				// For drawing in FirstPersonDrawer, setting rendering hint
				// became necessary when lines of polygons 
				// that were not horizontal or vertical looked ragged
				graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
						RenderingHints.VALUE_ANTIALIAS_ON);
				graphics.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
						RenderingHints.VALUE_INTERPOLATION_BILINEAR);
			}
		}
		return graphics;
	}
	
	public void fillRect(int x, int y, int a, int b) {
		graphics.fillRect(x, y, a, b);
	}
	
	public void fillPolygon(int[] xPoints, int[] yPoints, int z) {
		graphics.fillPolygon(xPoints, yPoints, z);
	}
	
	public void drawLine(int x, int y, int a, int b) {
		graphics.drawLine(x, y, a, b);
	}
	
	public void fillOval(int x, int y, int a, int b) {
		graphics.fillOval(x, y, a, b);
	}
	
	public void setColor(Color color) {
		graphics.setColor(color);
	}
	
	public static Color getColorBlack() {
		return Color.black;
	}
	
	public static Color getColorPink() {
		return Color.PINK;
	}
	
	public static Color getColorWhite() {
		return Color.WHITE;
	} 
	
	public static Color getColorGrey() {
		return Color.GRAY;
	}
	
	public static Color getColorDarkGrey() {
		return Color.darkGray;
	}
	
	public static Color getColorRed() {
		return Color.red;
	}
	
	public static Color getColorYellow() {
		return Color.yellow;
	}

}
