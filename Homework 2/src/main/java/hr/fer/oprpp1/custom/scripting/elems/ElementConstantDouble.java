package hr.fer.oprpp1.custom.scripting.elems;

/**
 *This class is an element which has double value.
 */
public class ElementConstantDouble extends Element {
	
	private double value;
	
	/**
	 *This constructor initializes the element value.
	 *@param Double value.
	 */
	public ElementConstantDouble(double value) {
		this.value = value;
	}

	/**
	 *This method is a getter of the value.
	 *@return value.
	 */
	public double getValue() {
		return value;
	}

	@Override
	public String asText() {
		return String.valueOf(value);
	}

}
