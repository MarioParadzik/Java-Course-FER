package hr.fer.zemris.java.hw06.shell;

import java.util.List;

/**
 * This interface is represents commands in our shell.
 */
public interface ShellCommand {
	
	/**
	 * This method is used for executing a command.
	 * @param env Environment.
	 * @param arguments Command arguments.
	 * @return result of the command.
	 */
	ShellStatus executeCommand(Environment env, String arguments);
	
	/**
	 * This method is a getter for the command name.
	 * @return Command name.
	 */
	String getCommandName();
	
	/**
	 * This method is used as a getter for the description of the command.
	 * @return Command description.
	 */
	List<String> getCommandDescription();

}
