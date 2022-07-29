package hr.fer.oprpp1.hw04.db;
/**
 *This interface tells us if the comparison operator is satisfied.
 */
public interface IComparisonOperator {
	/**
	 *This method tell us if two string are satisfied with the given comparison operator.
	 * @param value1 First string.
	 * @param value2 Second string.
	 * @return True if satisfied, false otherwise.
	 */
	public boolean satisfied(String value1, String value2);
}
