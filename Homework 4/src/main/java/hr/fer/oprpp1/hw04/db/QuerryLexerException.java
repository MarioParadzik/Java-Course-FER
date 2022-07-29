package hr.fer.oprpp1.hw04.db;

/**
 *This class is an Query Lexer exception.
 */
public class QuerryLexerException extends RuntimeException {
	/**
     *Constructor with no argument.
     */
	public QuerryLexerException() {}
	
	/**
     *Constructor with message.
     * 
     *@param msg Message that will be shown.
     */
	public QuerryLexerException(String msg) {
		super(msg);
	}
}
