package hr.fer.oprpp1.custom.collections;

/**
 *This class is an empty stack exception.
 *Thrown when we try to get objects from empty stack.
 */
public class EmptyStackException extends RuntimeException {

	/**
     *Constructor with no argument.
     */
	public EmptyStackException() {}
	
	/**
     *Constructor with message.
     * 
     *@param msg Message that will be shown.
     */
	public EmptyStackException(String msg) {
        super(msg);
    }
}
