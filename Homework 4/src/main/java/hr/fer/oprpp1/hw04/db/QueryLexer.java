package hr.fer.oprpp1.hw04.db;

/**
 *This class is used a lexer for creating token on input query.
 */
public class QueryLexer {
	private char[] data;
	private QueryToken token;
	private int currentIndex;
	
	/**
	 *Basic constructor
	 * @param text Input query.
	 */
	public QueryLexer(String text) {
		if(text == null) throw new NullPointerException();
		this.data = text.toCharArray();
		this.currentIndex = 0;
	}
	
	/**
	 *This method is reproducing the next token of the input query.
	 * @return QueryToken.
	 */
	public QueryToken nextToken() {
		if(token != null && token.getTokenType() == QueryTokenType.EOF) throw new QuerryLexerException();
		
		if(currentIndex >= data.length) {
			token = new QueryToken(null, QueryTokenType.EOF);
			return token;
		}
		
		skipBlanks();
		
		if(currentIndex >= data.length) {
			token = new QueryToken(null, QueryTokenType.EOF);
			return token;
		}
		StringBuilder sb = new StringBuilder();
		QueryTokenType current = null;
		boolean flag = false;
		
		while(currentIndex < data.length) {
			if(flag) {
				if(data[currentIndex] == '"') {
					sb.append(data[currentIndex++]);
					break;
				}
				sb.append(data[currentIndex++]);
			} else if(data[currentIndex] == '=' || 
					  data[currentIndex] == '>' || 
					  data[currentIndex] == '<' || 
					  data[currentIndex] == '!') {
				if(current == null) current = QueryTokenType.OPERATOR;
				if(current != QueryTokenType.OPERATOR) break;
				sb.append(data[currentIndex++]);
				if (data[currentIndex] == '=') {
                    sb.append(data[currentIndex++]);
                }
				break;
			} else if(data[currentIndex] == '\r' ||
					  data[currentIndex] == '\n' ||
					  data[currentIndex] == '\t' ||
					  data[currentIndex] == ' ') {
				break;
			} else {
				if(current == null) current = QueryTokenType.TEXT;
				if(current != QueryTokenType.TEXT) break;
				if (data[currentIndex] == '"') {
					current = QueryTokenType.LITERAL;
					flag = true;
				}
				sb.append(data[currentIndex++]);
			}
		}
		
		//generiraj token
		if(current == QueryTokenType.TEXT) {
			String text = sb.toString();
			if(text.equalsIgnoreCase("AND")) {
				token = new QueryToken(text.toUpperCase(), QueryTokenType.AND);
			} else if(text.equals("LIKE")) {
				token = new QueryToken(text.toUpperCase(), QueryTokenType.OPERATOR);
			} else {
				token = new QueryToken(text, QueryTokenType.TEXT);
			}
		} else if(current == QueryTokenType.LITERAL) {
			token = new QueryToken(sb.toString(), QueryTokenType.LITERAL);
		} else if(current == QueryTokenType.OPERATOR) {
			token = new QueryToken(sb.toString(), QueryTokenType.OPERATOR);
		} else {
			throw new QuerryLexerException("Unknow token type.");
		}
		return token;
	}
		
	/**
	 *This method is a getter for tokens.
	 * @return Token.
	 */
	public QueryToken getToken() {
		return token;
	}

	/**
	 *This method is used to remove whitespace.
	 */
	private void skipBlanks() {
		for (int i = currentIndex; i < data.length; i++) {
			char c = data[i];
			if(c == '\r' || c == '\n' || c == '\t' || c == ' ') {
				currentIndex++;
				continue;
			}
			break;
		}
		
	}
}
