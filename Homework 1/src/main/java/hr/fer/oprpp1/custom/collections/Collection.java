package hr.fer.oprpp1.custom.collections;
/**
 *This class represents some general collection of objects.
 */
public class Collection {
	
	/**
     *Constructor with no argument.
     */
	protected Collection() {}

	/**
     *This method checks if collection has objects or not.
     *
     *@return True if collection has elements, false otherwise.
     */	
	public boolean isEmpty() {
		return size() == 0;
	}

	/**
    *This method returns the current size of collection.
    *
    *@return Current size of collection.
    */	
	public int size() {
		return 0;
	}
	
	/**
    *This method adds an object to the collection.
    *
    *@param value Object which will be added to collection.
    */
	public void add(Object value) {}
	
	/**
    *This method checks if the collection contains the given param.
    *
    *@param value Object that will be checked if it is located in collection.
    *@return True if collection contains object, false otherwise.
    */
	public boolean contains(Object value) {
		return false;
	}
	
	/**
	*This method removes the given param from the collection.
	*
	*@param value Object which will be removed from the collection.
    *@return True if object is removed, false otherwise.
	*/
	public boolean remove(Object value) {
		return false;
	}
	
	/**
	*This method transforms collection to Object array.
	*
	*@return Object array with collection objects.
	*/
	public Object[] toArray() {
		throw new UnsupportedOperationException();
	}
	
	/**
     * This method calls {@link Processor} for every object of the collection.
     *
     * @param processor The processor we call.
     */
	public void forEach(Processor processor) {}
	
	/**
	*This method adds all objects from given collection to the collection.
	*
	*@param other Collection which objects will be added.
	*/
	public void addAll(Collection other) {
		
		/**
        *This class is used as a processor that will add objects the collection.
		*/
		class localProcessor extends Processor {
			
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
	void clear() {}
}
