package hr.fer.oprpp1.hw04.db;

/**
 *This interface is used to get a specific field.
 */
public interface IFieldValueGetter {
	
	/**
	 *This method is used to get a specific filed from a record.
	 * @param record Student record.
	 * @return Specific field.
	 */
	public String get(StudentRecord record);
}
