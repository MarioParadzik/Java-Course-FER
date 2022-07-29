package hr.fer.oprpp1.custom.scripting.elems;

/**
 *This class is an element which is a string.
 */
public class ElementString extends Element{
	
	private String value;

	/**
	 *This constructor initializes the element value.
	 *@param String value.
	 */
	public ElementString(String value) {
		this.value = value;
	}

	/**
	 *This method is a getter of the string.
	 *@return String value.
	 */
	public String getValue() {
		return value;
	}

	@Override
	public String asText() {
		return this.value;
	}
	
	
}
