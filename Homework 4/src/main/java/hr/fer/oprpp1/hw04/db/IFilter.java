package hr.fer.oprpp1.hw04.db;

/**
 *This interface tells us if we accept the record while filtering.
 */
public interface IFilter {
	
	/**
	 *This method if the record passed the filter.
	 * @param record Student record.
	 * @return True if student passed, false otherwise.
	 */
	public boolean accepts(StudentRecord record);

}
