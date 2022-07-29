package hr.fer.oprpp1.hw04.db;

import java.util.ArrayList;
import java.util.List;

/**
 *This class is used as a Parser for the given input Query.
 */
public class QueryParser {
	private QueryLexer lexer;
	private List<ConditionalExpression> queries;
	
	/**
	 *Basic constructor.
	 * @param txt Input query.
	 */
	public QueryParser(String txt) {
		if(txt.isBlank()) throw new QueryParserException("Can't parse whitespace.");
		this.lexer = new QueryLexer(txt);
		this.queries = new ArrayList<>();
		
		try {
			startParser();
		} catch(Exception ex) {
			throw new QueryParserException(ex.getMessage());
		}
	}

	/**
	 *This method checks if we have a direct query or a multiple one.
	 * @return True if query is direct, flase otherwise.
	 */
	public boolean isDirectQuery() {
		if(queries.size() != 1) return false;
		
		return queries.get(0).getFieldGetter() == FieldValueGetters.JMBAG &&
				queries.get(0).getComparisonOperator() == ComparisonOperators.EQUALS;
	}
	
	/**
	 *This method is used to get the jmbag from a direct query.
	 * @return Jmbag, otherwise throw exception.
	 */
	public String getQueriedJMBAG() {
		if(!isDirectQuery()) throw new IllegalArgumentException("Not a direct query.");
		return queries.get(0).getStringLiteral();
	}
	
	/**
	 *This method is a getter for queries.
	 * @return List of queries.
	 */
	public List<ConditionalExpression> getQueries() {
		return queries;
	}

	/**
	 *This method is used to parse the query by tokens.
	 */
	private void startParser() {
		QueryToken token = lexer.nextToken();
		
		while(token.getTokenType() != QueryTokenType.EOF) {
			if(token.getTokenType() != QueryTokenType.TEXT) throw new QueryParserException("First token must be an attribute.");
			
			IFieldValueGetter attribut;
			if(token.getValue().equals("jmbag")) attribut = FieldValueGetters.JMBAG;
			else if(token.getValue().equals("firstName")) attribut = FieldValueGetters.FIRST_NAME;
			else if(token.getValue().equals("lastName")) attribut = FieldValueGetters.LAST_NAME;
			else throw new QueryParserException("Wrong name of the attribute.");
			
			token = lexer.nextToken();
			if(token.getTokenType() != QueryTokenType.OPERATOR) throw new QueryParserException("After atribute must be an operator.");
			IComparisonOperator operator;
			if(token.getValue().equals("<")) operator = ComparisonOperators.LESS;
			else if (token.getValue().equals("<=")) operator = ComparisonOperators.LESS_OR_EQUALS;
			else if (token.getValue().equals(">")) operator = ComparisonOperators.GREATER;
			else if (token.getValue().equals(">=")) operator = ComparisonOperators.GREATER_OR_EQUALS;
			else if (token.getValue().equals("=")) operator = ComparisonOperators.EQUALS;
			else if (token.getValue().equals("!=")) operator = ComparisonOperators.NOT_EQUALS;
			else if (token.getValue().equals("LIKE")) operator = ComparisonOperators.LIKE;
			else throw new QueryParserException("Not allowed operator.");
			
			token = lexer.nextToken();
			if(token.getTokenType() != QueryTokenType.LITERAL) throw new QueryParserException("After operator must be a literal.");
			queries.add(new ConditionalExpression(attribut, token.getValue().replace("\"", ""), operator));
			
			token = lexer.nextToken();
			if(token.getTokenType() == QueryTokenType.EOF) break;
			else if(token.getTokenType() == QueryTokenType.AND)token = lexer.nextToken();
			else throw new QuerryLexerException("Unable to parse.");
			
		}
	}

}
