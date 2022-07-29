package hr.fer.oprpp1.hw04.db;

/**
 *This class represents a query token.
 */
public class QueryToken {
	private String value;
	private QueryTokenType tokenType;
	
	/**
	 *Basic construcotr
	 * @param value Value of the token.
	 * @param tokenType Query token type.
	 */
	public QueryToken(String value, QueryTokenType tokenType) {
		this.value = value;
		this.tokenType = tokenType;
	}

	/**
	 *This method is a getter for the token value.
	 * @return Value.
	 */
	public String getValue() {
		return value;
	}

	/**
	 *This method is a getter for the token type.
	 * @return Query token type.
	 */
	public QueryTokenType getTokenType() {
		return tokenType;
	}
	
	
}
