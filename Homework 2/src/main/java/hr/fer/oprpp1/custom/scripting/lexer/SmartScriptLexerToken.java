package hr.fer.oprpp1.custom.scripting.lexer;

/**
 *This class is a Smart Script Lexer Token that we produce while
 *parsing the given text or tag.
 */
public class SmartScriptLexerToken {
	private SmartScriptLexerTokenType tokenType;
	private Object value;
	
	/**
	 *This constructor initializes the tokenType and his value.
	 *@param tokenType Token type.
	 *@param value Token value.
	 */
	public SmartScriptLexerToken(SmartScriptLexerTokenType tokenType, Object value) {
		this.tokenType = tokenType;
		this.value = value;
	}

	/**
	 *This method returns the token type.
	 *@return Token type.
	 */
	public SmartScriptLexerTokenType getTokenType() {
		return tokenType;
	}

	/**
	 *This method returns the token value.
	 *@return Token value.
	 */
	public Object getValue() {
		return value;
	}
	
	
}
