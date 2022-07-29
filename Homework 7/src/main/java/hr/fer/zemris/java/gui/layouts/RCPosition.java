package hr.fer.zemris.java.gui.layouts;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class is used as a representation of the button position.
 */
public class RCPosition {
	private int row;
	private int column;
	
	/**
	 * Basic Constructor
	 * @param row Row.
	 * @param column Column.
	 */
	public RCPosition(int row, int column) {
		this.row = row;
		this.column = column;
	}

	/**
	 * This method is a getter for row.
	 * @return Row.
	 */
	public int getRow() {
		return row;
	}

	/**
	 * This method is a getter for column.
	 * @return Column.
	 */
	public int getColumn() {
		return column;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + column;
		result = prime * result + row;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null || getClass() != obj.getClass()) return false;
		RCPosition other = (RCPosition) obj;
		if (column != other.column) return false;
		if (row != other.row) return false;
		return true;
	}
	
	/**
	 * This method is a parser for points.
	 * @param txt String representation of points.
	 * @return Position.
	 */
	public static RCPosition parse(String txt) {
		if(txt == null) throw new NullPointerException();
		Pattern pat = Pattern.compile("^\\d,\\d$");
		Matcher possition = pat.matcher(txt.replaceAll("\\s",""));
		if(possition.find()) {
			return new RCPosition(Integer.parseInt(possition.group(1)), Integer.parseInt(possition.group(2)));
		} else {
			throw new IllegalArgumentException("Can't parse.");
		}
		
	}
	
}
