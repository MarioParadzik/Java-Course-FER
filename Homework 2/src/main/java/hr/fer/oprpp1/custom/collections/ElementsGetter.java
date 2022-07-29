package hr.fer.oprpp1.custom.collections;

/**
 *This interface is used as a collection itterator.
 */
public interface ElementsGetter {
	
	/**
	 *This method tells us if collection has next element.
	 *@return true if collection has next element, otherwise false.
	 */
	boolean hasNextElement();

	/**
	 *This method is used to get the next object.
	 *@return Object at the next place.
	 */
	Object getNextElement();
	
	/**
	 *This default method is used as for loop to do some useful work on collection elements.
	 *@param p The processor we use
	 */
	default void processRemaining(Processor p) {
		while(hasNextElement() == true) {
			p.process(getNextElement());
		}
	}

}
