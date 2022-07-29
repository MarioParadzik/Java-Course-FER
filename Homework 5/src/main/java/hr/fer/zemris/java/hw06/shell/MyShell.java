package hr.fer.zemris.java.hw06.shell;

/**
 * This class is representing a shell. Which has supported few commands like: ls, tree,
 * charsets, cat, copy, mkdir, help, symbol, exit, hexdump.
 */
public class MyShell {

	public static void main(String[] args) {
		EnvironmentImpl env = new EnvironmentImpl();
		env.writeln("Welcome to MyShell v 1.0");
		env.write(env.getPromptSymbol() + " ");
        ShellStatus status = ShellStatus.CONTINUE;
        do {
        	String l = env.readLine();
        	String commandName = ShellParser.getName(l);
        	String arguments = l.substring(commandName.length()).trim();
        	ShellCommand command = env.commands().get(commandName);
        	
        	if(command == null) {
        		env.writeln("Unknown command!");
                env.write(env.getPromptSymbol() + " ");
        	} else {
        		status = command.executeCommand(env, arguments);
        	}

        } while(status != ShellStatus.TERMINATE);
	}
}
