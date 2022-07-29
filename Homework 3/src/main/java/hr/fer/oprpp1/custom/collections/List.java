package hr.fer.oprpp1.custom.collections;

/**
 *This interface extends {@link Collection} with more useful methods. 
 */

public interface List<T> extends Collection<T>{
	
	/**
	 *This method is getting the element at the given index.
	 *@param index Index of the object.
	 *@return Object at the index.
	 */
	T get(int index);
	
	/**
	 *This method inserts objects at the given index. Objects at the given
	 *index and so on are shifted forward for one place.
	 *
	 *@param value Object that will be inserted.
	 *@param position Index where the object will be inserted.
	 */
	void insert(T value, int position);
	
	/**
	 *This method searches for the index of the given object.
	 *
	 *@param value Object which index we are searching for.
	 *@return Index of the given object, -1 otherwise.
	 */
	int indexOf(Object value);
	
	/**
	 *This method is used to remove an object from the given index.
	 *
	 *@param index Index where we want to remove an object.
	 */
	void remove(int index);
	
}
