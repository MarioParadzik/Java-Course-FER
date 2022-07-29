package hr.fer.oprpp1.hw02.prob1;

/**
 *This class is a Lexer Token that we produce on basic or extended token type.
 */
public class Token {
	private TokenType tokenType;
	private Object value;
	
	/**
	 *This constructor initializes the tokenType and his value.
	 *@param type Token type.
	 *@param value Token value.
	 */
	public Token(TokenType type, Object value) {
		if(type == null) throw new IllegalArgumentException();
		this.tokenType = type;
		this.value = value;
	}
	
	/**
	 *This method returns the token value.
	 *@return Token value.
	 */
	public Object getValue() {
		return value;
	}
	
	/**
	 *This method returns the token type.
	 *@return Token type.
	 */
	public TokenType getType() {
		return tokenType;
	}

}
