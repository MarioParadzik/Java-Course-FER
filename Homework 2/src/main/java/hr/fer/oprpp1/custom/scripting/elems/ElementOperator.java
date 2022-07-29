package hr.fer.oprpp1.custom.scripting.elems;
/**
 *This class is an element which is an operator.
 */
public class ElementOperator extends Element {
	private String symbol;
	
	/**
	 *This constructor initializes the element value.
	 *@param symbol Operator.
	 */
	public ElementOperator(String symbol) {
		this.symbol = symbol;
	}
	
	/**
	 *This method is a getter of the operator.
	 *@return symbol.
	 */
	public String getSymbol() {
		return symbol;
	}

	@Override
	public String asText() {
		return symbol;
	}

}
