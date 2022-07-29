package hr.fer.oprpp1.custom.collections;
/**
 *This interface has some general collection of objects methods.
 */
public interface Collection<T> {
	
	ElementsGetter<T> createElementsGetter();
	/**
     *This default method checks if collection has objects or not.
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
	void add(T value);
	
	/**
    *This method checks if the collection contains the given @param.
    *
    *@param value Object that will be checked if it is located in collection.
    *@return True if collection contains object, false otherwise.
    */
	boolean contains(Object value);
	
	/**
	*This method removes the given @param from the collection.
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
	default void forEach(Processor<? super T> processor) {
		ElementsGetter<T> getter = createElementsGetter();
		while(getter.hasNextElement()) {
			processor.process((T) getter.getNextElement());
		}
	}
	
	/**
	*This default method adds all objects from given collection to the collection.
	*
	*@param other Collection which objects will be added.
	*/
	default void addAll(Collection<? extends T> other) {
		
		/**
        *This class is used as a processor that will add objects the collection.
		*/
		class localProcessor implements Processor<T> {
			
			@Override
			public void process(T value) {
				add((T) value);
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
	default void addAllSatisfying(Collection<? extends T> col, Tester<? super T> tester) {
		ElementsGetter<? extends T> getter =  col.createElementsGetter();
		while(getter.hasNextElement()) {
			T element = getter.getNextElement();
			if(tester.test(element)) this.add(element);
		}
	}

}
