package hr.fer.oprpp1.hw04.db;

/**
 *This class gives us the implementation of allowed atributes.
 */
public class FieldValueGetters {
	
	/**
	 *Field value first name.
	 */
	public static final IFieldValueGetter FIRST_NAME = (s) -> s.getFirstName();
	
	/**
	 *Field value last name.
	 */
	public static final IFieldValueGetter LAST_NAME = (s) -> s.getLastName();
	
	/**
	 *Field value jmbag.
	 */
	public static final IFieldValueGetter JMBAG = (s) -> s.getJmbag();
}
