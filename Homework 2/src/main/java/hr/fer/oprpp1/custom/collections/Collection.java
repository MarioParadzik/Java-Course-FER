package hr.fer.oprpp1.custom.collections;
/**
 *This interface has some general collection of objects methods.
 */
public interface Collection {
	
	ElementsGetter createElementsGetter();
	/**
     *This defaultmethod checks if collection has objects or not.
     *@return True if collection has elements, false otherwise.
     */	
	default boolean isEmpty() {
		return size() == 0;
	}

	/**
    *This method returns the current size of collection.
    *
    *@return Current size of collection.
    */	
	int size();
	
	/**
    *This method adds an object to the collection.
    *
    *@param value Object which will be added to collection.
    */
	void add(Object value);
	
	/**
    *This method checks if the collection contains the given param.
    *
    *@param value Object that will be checked if it is located in collection.
    *@return True if collection contains object, false otherwise.
    */
	boolean contains(Object value);
	
	/**
	*This method removes the given param from the collection.
	*
	*@param value Object which will be removed from the collection.
    *@return True if object is removed, false otherwise.
	*/
	boolean remove(Object value);
	
	/**
	*This method transforms collection to Object array.
	*
	*@return Object array with collection objects.
	*/
	Object[] toArray();
	
	/**
     * This method calls {@link Processor} for every object of the collection 
     * which we get by {@link ElementsGetter}.
     * @param processor The processor we call.
     */
	default void forEach(Processor processor) {
		ElementsGetter getter = createElementsGetter();
		while(getter.hasNextElement()) {
			processor.process(getter.getNextElement());
		}
	}
	
	/**
	*This default method adds all objects from given collection to the collection.
	*
	*@param other Collection which objects will be added.
	*/
	default void addAll(Collection other) {
		
		/**
        *This class is used as a processor that will add objects the collection.
		*/
		class localProcessor implements Processor {
			
			@Override
			public void process(Object value) {
				add(value);
			}
		}
		
		other.forEach(new localProcessor());
	}
	
	/**
	*This method clears all objects from collection.
	*/
	void clear();

	/**
	*This default method is getting every element in the given collection and adding
	*all elements where the tester returns true.
	*/
	default void addAllSatisfying(Collection col, Tester tester) {
		ElementsGetter getter = col.createElementsGetter();
		while(getter.hasNextElement()) {
			Object element = getter.getNextElement();
			if(tester.test(element)) this.add(element);
		}
	}

}
