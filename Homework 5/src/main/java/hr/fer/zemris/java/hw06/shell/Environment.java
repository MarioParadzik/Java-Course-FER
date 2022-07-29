package hr.fer.zemris.java.hw06.shell;

import java.util.SortedMap;

/**
 * This interface is used as an environment which is used for communication between the shell and its commands.
 */
public interface Environment {
	
	/**
	 * This method is used to read line form shell.
	 * @return Read line.
	 * @throws ShellIOException If reading went wrong.
	 */
	String readLine() throws ShellIOException;
	
	/**
	 * This method is used to write text in shell.
	 * @param text Text that will be written.
	 * @throws ShellIOException If anything goes wrong.
	 */
	void write(String text) throws ShellIOException;
	
	/**
	 * This method is used to write text and a new line in shell.
	 * @param text Text that will be written.
	 * @throws ShellIOException If anything goes wrong.
	 */
	void writeln(String text) throws ShellIOException;
	
	/**
	 * This method is a getter for shell commands.
	 * @return Map of commands.
	 */
	SortedMap<String, ShellCommand> commands();
	
	/**
	 * This method is a getter for MultilineSymbol.
	 * @return  Multiline symbol.
	 */
	Character getMultilineSymbol();
	
	/**
	 * This method is a setter for MultilineSymbol.
	 * @param symbol Multiline symbol.
	 */
	void setMultilineSymbol(Character symbol);
	
	/**
	 * This method is a getter for PromptSymbol.
	 * @return  Prompt symbol.
	 */
	Character getPromptSymbol();
	
	/**
	 * This method is a setter for PromptSymbol.
	 * @param symbol Prompt symbol.
	 */
	void setPromptSymbol(Character symbol);
	
	/**
	 * This method is a getter for Morelines.
	 * @return  Morelines symbol.
	 */
	Character getMorelinesSymbol();
	
	/**
	 * This method is a setter for Morelines.
	 * @param symbol Morelines symbol.
	 */
	void setMorelinesSymbol(Character symbol);

}
