package hr.fer.oprpp1.custom.scripting.lexer;

/**
 *This class is an Smart Script Lexer exception.
 */
public class SmartScriptLexerException extends RuntimeException {
	/**
     *Constructor with no argument.
     */
	public SmartScriptLexerException() {}
	
	/**
     *Constructor with message.
     * 
     *@param msg Message that will be shown.
     */
	public SmartScriptLexerException(String msg) {
		super(msg);
	}
}
