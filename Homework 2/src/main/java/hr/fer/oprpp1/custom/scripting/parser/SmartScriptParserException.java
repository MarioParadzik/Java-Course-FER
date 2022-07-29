package hr.fer.oprpp1.custom.scripting.parser;

/**
 *This class is an Smart Script Parser exception.
 */
public class SmartScriptParserException extends RuntimeException {
	
	/**
     *Constructor with no argument.
     */
	public SmartScriptParserException() {}
	
	/**
     *Constructor with message.
     * 
     *@param msg Message that will be shown.
     */
	public SmartScriptParserException(String msg) {
		super(msg);
	}
}
