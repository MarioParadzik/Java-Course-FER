package hr.fer.zemris.java.gui.charts;

import java.util.List;
import java.util.Objects;
/**
 * This class represents a Bar Chart.
 */
public class BarChart {
	private List<XYValue> xyObjects;
	private String xDesc;
	private String yDesc;
	private int minY;
	private int maxY;
	private int diffY;
	
	/**
	 * Basic Constructor.
	 * @param xyObjects List of XYValue objects
	 * @param xDesc Description of x Axis
	 * @param yDesc Description of y Axis
	 * @param minY Minimal y value.
	 * @param maxY Maximal y value.
	 * @param diffY difference.
	 */
	public BarChart(List<XYValue> xyObjects, String xDesc, String yDesc, int minY, int maxY, int diffY) {
		if(xyObjects.isEmpty()) throw new IllegalArgumentException("List is empty.");
		xyObjects.forEach(x -> Objects.requireNonNull(x, "Value can't be null."));
		this.xyObjects = xyObjects;
		
		if(xDesc == null || yDesc == null) throw new NullPointerException("Axis description can't be null.");
		this.xDesc = xDesc;
		this.yDesc = yDesc;
		
		if(minY < 0) throw new IllegalArgumentException("minY can't be negative.");
		if(!(maxY > minY)) throw new IllegalArgumentException("maxY needs to be greather than minY.");
		this.minY = minY;
		
		if(maxY - minY == diffY) {
			this.maxY = maxY;
		} else {
			this.maxY = minY + diffY * (((maxY - minY) / diffY) + 1);
		}
		this.diffY = diffY;
		
		for(int i = 0; i < xyObjects.size(); i++) if(xyObjects.get(i).getY() < minY) throw new IllegalArgumentException("Given y can't be smaller than yMin");
		
	}

	/**
	 * This method is a getter for xyvalue objects.
	 * @return List of xyvalue objects.
	 */
	public List<XYValue> getXyObjects() {
		return xyObjects;
	}

	/**
	 * This method is a getter for x Description.
	 * @return X Description.
	 */
	public String getxDesc() {
		return xDesc;
	}

	/**
	 * This method is a getter for y Description.
	 * @return Y Description.
	 */
	public String getyDesc() {
		return yDesc;
	}

	/**
	 * This method is a getter for Minimal y value.
	 * @return Minimal y value.
	 */
	public int getMinY() {
		return minY;
	}

	/**
	 * This method is a getter for Maximal y value.
	 * @return Maximal y value.
	 */
	public int getMaxY() {
		return maxY;
	}

	/**
	 * This method is a getter for difference y value.
	 * @return Difference y value.
	 */
	public int getDiffY() {
		return diffY;
	}

	
}
