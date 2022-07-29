package hr.fer.oprpp1.custom.scripting.elems;

/**
 *This class is an element which is a variable.
 */
public class ElementVariable extends Element {

	private String name;

	/**
	 *This constructor initializes the element value.
	 *@param String value.
	 */
	public ElementVariable(String name) {
		this.name = name;
	}

	/**
	 *This method is a getter of the variable name.
	 *@return variable value.
	 */
	public String getName() {
		return name;
	}

	@Override
	public String asText() {
		return this.name;
	}
	
	
}
