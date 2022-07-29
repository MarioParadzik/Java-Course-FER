package hr.fer.oprpp1.hw04.db;

/**
 *This class gives us the implementation of allowed comparison operators.
 */
public class ComparisonOperators {
	
	/**
	 *Operator less.
	 */
	public static final IComparisonOperator LESS = (v1, v2) -> v1.compareTo(v2) < 0;
	
	/**
	 *Operator less or equal.
	 */
	public static final IComparisonOperator LESS_OR_EQUALS = (v1, v2) -> v1.compareTo(v2) <= 0;
	
	/**
	 *Operator greater.
	 */
	public static final IComparisonOperator GREATER = (v1, v2) -> v1.compareTo(v2) > 0;
	
	/**
	 *Operator greater or equal.
	 */
	public static final IComparisonOperator  GREATER_OR_EQUALS = (v1, v2) -> v1.compareTo(v2) >= 0;
	
	/**
	 *Operator equal.
	 */
	public static final IComparisonOperator EQUALS = (v1, v2) -> v1.equals(v2);
	
	/**
	 *Operator not equal.
	 */
	public static final IComparisonOperator NOT_EQUALS = (v1, v2) -> !(v1.equals(v2));
	
	/**
	 *Operator like.
	 */
	public static final IComparisonOperator LIKE = (v1, v2) -> {
		if(v2.contains("*")) {
			int index = v2.indexOf("*");
			if(index != v2.lastIndexOf("*")) throw new IllegalArgumentException("Query can't contain multiple *.");
			if(v1.length() < (v2.length() - 1)) return false;
			
			String[] strs = v2.split("\\*");
			// slucaj kad je * prvom mjestu
			if(strs.length == 1 && index == 0) {
				return v1.endsWith(strs[0]);
			// slucaj kad je * na zadnje mjestu
			} else if(strs.length == 1 && index == v2.length() - 1) {
				return v1.startsWith(strs[0]);
			// inace provjeri pocetni i zadnji dio
			} else {
				return v1.startsWith(strs[0]) && v1.endsWith(strs[1]);
			}
		} else {
			return v1.equals(v2);
		}
		
		
	};

}
