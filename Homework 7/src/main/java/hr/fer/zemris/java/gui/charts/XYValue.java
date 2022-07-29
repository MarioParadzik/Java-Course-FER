package hr.fer.zemris.java.gui.charts;

/**
 * This class represents a x,y value
 */
public class XYValue {
	private int x;
	private int y;
	
	/**
	 * Basic Constructor.
	 * @param x X coordinate.
	 * @param y Y coordinate.
	 */
	public XYValue(int x, int y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * This method is a getter for x coordinate.
	 * @return X coordinate.
	 */
	public int getX() {
		return x;
	}

	/**
	 * This method is a getter for y coordinate.
	 * @return Y coordinate.
	 */
	public int getY() {
		return y;
	}
	
}
