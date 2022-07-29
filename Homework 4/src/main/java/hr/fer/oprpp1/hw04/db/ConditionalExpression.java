package hr.fer.oprpp1.hw04.db;

/**
 *This class represents one query input.
 */
public class ConditionalExpression {
	private IComparisonOperator comparisonOperator;
	private String stringLiteral;
	private IFieldValueGetter fieldGetter;
	
	/**
	 *Basic constructor.
	 * @param fieldGetter Field Value getter.
	 * @param stringLiteral String literal.
	 * @param comparisonOperator Comparison operator.
	 */
	public ConditionalExpression( IFieldValueGetter fieldGetter, String stringLiteral, IComparisonOperator comparisonOperator) {
		this.comparisonOperator = comparisonOperator;
		this.stringLiteral = stringLiteral;
		this.fieldGetter = fieldGetter;
	}

	/**
	 *This method is a comparison operator getter.
	 * @return Comparison operator.
	 */
	public IComparisonOperator getComparisonOperator() {
		return comparisonOperator;
	}

	/**
	 *This method is a String literal getter.
	 * @return String Literal.
	 */
	public String getStringLiteral() {
		return stringLiteral;
	}

	/**
	 *This method is a field getter.
	 * @return Field value getter.
	 */
	public IFieldValueGetter getFieldGetter() {
		return fieldGetter;
	}
	
	
}
