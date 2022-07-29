package hr.fer.oprpp1.custom.collections;

/**
 *This class is used as an object stack.
 *In this class we implemented {@link ArrayIndexedCollection} for storing objects on the stack.
 */
public class ObjectStack {
	
	/**
	 *Collection that we use to make a stack.
	 */
	private ArrayIndexedCollection col = new ArrayIndexedCollection();
	
	/**
	 *This method checks if stack has objects or not.
	 *
	 *@return True if stack has objects, false otherwise.
	 */
	public boolean isEmpty() {
		return col.isEmpty();
	}
	
	/**
    *This method return the current size of stack.
    *
    *@return Current size of stack.
    */
	public int size() {
		return col.size();
	}
	
	/**
    *This method pushes an object to stack.
    *
    *@param value Object that is pushed.
    */
	public void push(Object value) {
		col.add(value);
	}
	
	/**
	*This method pops the last pushed object on the stack.
	*
    *@return Object that is popped.
	*/
	public Object pop() {
		if(size() == 0) throw new EmptyStackException();
		Object value = col.get(size() -1);
		col.remove(size() -1);
		
		return value;
	}
	
	/**
	*This method peaks the last pushed object on the stack.
	*
    *@return Object that is peaked.
	*/
	public Object peek() {
		if(size() == 0) throw new EmptyStackException();
		return col.get(size() -1);
	}
	
	/**
	*This method clears all objects from stack.
	*/
	public void clear() {
		col.clear();
	}
}
