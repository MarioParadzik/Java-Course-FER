package hr.fer.oprpp1.hw02.prob1;

/**
 *This class is an Lexer Exception exception.
 */
public class LexerException extends RuntimeException {
	
	/**
     *Constructor with no argument.
     */
	public LexerException() {}
	
	/**
     *Constructor with message.
     * 
     *@param msg Message that will be shown.
     */
	public LexerException(String msg) {
		super(msg);
	}
}
