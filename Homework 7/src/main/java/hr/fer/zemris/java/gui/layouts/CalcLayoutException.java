package hr.fer.zemris.java.gui.layouts;

/**
 * This represents a CalcLayoutException
 */
public class CalcLayoutException extends RuntimeException {
	
	/**
	 * Default Constructor.
	 */
	public CalcLayoutException() {}
	
	/**
	 * Basic Constructor.
	 * @param msg Message to show.
	 */
	public CalcLayoutException(String msg) {
		super(msg);
	}
}
