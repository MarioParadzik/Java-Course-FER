package hr.fer.zemris.java.hw06.shell;

/**
 * This class represents Shell input output exception.
 */
public class ShellIOException extends RuntimeException {

	 /**
     * Basic constructor.
     */
	public ShellIOException() {
		super();
	}
	
    /**
     * Constructor with message.
     * @param message Message.
     */
	public ShellIOException(String msg) {
		super(msg);
	}
}
