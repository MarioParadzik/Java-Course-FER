package hr.fer.oprpp1.hw04.db;

/**
 *This class is an Query Parser exception.
 */
public class QueryParserException extends RuntimeException  {
	/**
     *Constructor with no argument.
     */
	public QueryParserException() {}
	
	/**
     *Constructor with message.
     * 
     *@param msg Message that will be shown.
     */
	public QueryParserException(String msg) {
		super(msg);
	}
}
