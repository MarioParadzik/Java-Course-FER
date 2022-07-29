package hr.fer.oprpp1.hw04.db;

/**
 *This enum represents query token types.
 * @author Mario
 *
 */
public enum QueryTokenType {
	/**
	 *Text token.
	 */
	TEXT,
	
	/**
	 *Operator token.
	 */
	OPERATOR,
	
	/**
	 *Literal token.
	 */
	LITERAL,
	
	/**
	 *And token.
	 */
	AND,
	
	/**
	 *End of file token.
	 */
	EOF
}
