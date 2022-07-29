package hr.fer.oprpp1.custom.scripting.lexer;
/**
 *This enum contains the {@link SmartScriptLexerToken} types.
 */
public enum SmartScriptLexerTokenType {
	
	/**
	 *End of line.
	 */
	EOF, 
	
	/**
	 *Text.
	 */
	TEXT,
	
	/**
	 *Number.
	 */
	NUMBER,
	
	/**
	 *Start of the tag.
	 */
	TAG_START,
	
	/**
	 *End of the tag.
	 */
	TAG_END,
	
	/**
	 *Function.
	 */
	FUNCTION,
	
	/**
	 *Operator.
	 */
	OPERATOR
}
