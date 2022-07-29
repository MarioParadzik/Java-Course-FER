package hr.fer.oprpp1.custom.scripting.elems;

/**
 *This class is an element which has integer value.
 */
public class ElementConstantInteger extends Element {
	
	private int value;
	
	/**
	 *This constructor initializes the element value.
	 *@param integer value.
	 */
	public ElementConstantInteger(int value) {
		this.value = value;
	}
	
	/**
	 *This method is a getter of the value.
	 *@return value.
	 */
	public int getValue() {
		return value;
	}

	@Override
	public String asText() {
		return String.valueOf(value);
	}
	
}
