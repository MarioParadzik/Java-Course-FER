package hr.fer.oprpp1.custom.scripting.elems;

/**
 *This class is an element which is a function.
 */
public class ElementFunction extends Element {

	private String name;
	
	/**
	 *This constructor initializes the function name.
	 *@param value function name.
	 */
	public ElementFunction(String name) {
		this.name = name;
	}

	/**
	 *This method is a getter of the function name.
	 *@return String name.
	 */
	public String getName() {
		return name;
	}


	@Override
	public String asText() {
		return name;
	}

}
