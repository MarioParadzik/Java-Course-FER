package hr.fer.zemris.java.hw06.shell.commands;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellIOException;
import hr.fer.zemris.java.hw06.shell.ShellParser;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * This class represents mkdir command.
 * It creates a directory for the given name.
 */
public class MkdirCommand implements ShellCommand {

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		List<String> path = null;
		try {
			path = ShellParser.parse(arguments);
		} catch (Exception e) {
			e.getMessage();
			return ShellStatus.CONTINUE;
		}
		if(path.size() != 1) throw new ShellIOException("Number of arguments should be 1");
		Path pathh = Paths.get(path.get(0));
		
		try {
			Files.createDirectories(pathh);
			env.writeln(path + " successfully created.");
		} catch (IOException e) {
			env.write("Error while creating directory.");
            env.write(env.getPromptSymbol() + " ");
		}
        env.write(env.getPromptSymbol() + " ");
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "Mkdir";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> desc = new ArrayList<>();
		desc.add("Description:");
		desc.add("It creates a directory for the given name.");
		return desc;
	}

}
