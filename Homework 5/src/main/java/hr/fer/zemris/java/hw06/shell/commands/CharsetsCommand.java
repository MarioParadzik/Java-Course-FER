package hr.fer.zemris.java.hw06.shell.commands;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * This class represents charsets command.
 * Charset command has no arguments and lists all supported charsets.
 * Every line is is a charset.
 */
public class CharsetsCommand implements ShellCommand {

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		if(arguments.length() != 0) {
			env.writeln("Charset command has no arguments.");
			env.write(env.getPromptSymbol() + " ");
			return ShellStatus.CONTINUE;
		}
		
		for(var v : Charset.availableCharsets().values()) env.writeln(v.displayName());
		env.write(env.getPromptSymbol() + " ");
		
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "Charsets";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> desc = new ArrayList<>();
		desc.add("Description:");
		desc.add("Charset command has no arguments and lists all supported charsets.");
		desc.add("Every line is is a charset.");
		return desc;
	}

}
