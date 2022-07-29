package hr.fer.zemris.java.hw06.shell.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * This class represents help command.
 * If used with no arguments it lists all commands.
 * If used with command name as argument it shows commands description.
 */
public class HelpCommand implements ShellCommand {

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		Map<String, ShellCommand> commands = env.commands();
		if(arguments.length() == 0) {
			for(String s: commands.keySet()) env.writeln(s);
		} else {
			if(commands.containsKey(arguments)) {
				ShellCommand c = commands.get(arguments);
				env.writeln(c.getCommandDescription().get(0));
				env.write(" ".repeat(5));
				for(int i = 1; i < c.getCommandDescription().size(); i++) {
					env.writeln(c.getCommandDescription().get(i));
				}
				
			} else {
				env.write("Command does not exists.");
			}
		}
        env.write(env.getPromptSymbol() + " ");
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "Help";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> desc = new ArrayList<>();
		desc.add("Description:");
		desc.add("If used with no arguments it lists all commands.");
		desc.add("If used with command name as argument it shows commands description.");
		return desc;
	}

}
