package hr.fer.zemris.java.hw06.shell.commands;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellIOException;
import hr.fer.zemris.java.hw06.shell.ShellParser;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * This class represents tree command.
 * It prints a tree (each directory level shifts output two characters to the right) for the given directory.
 */
public class TreeCommand implements ShellCommand {

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		List<String> path = null;
		try {
			path = ShellParser.parse(arguments);
		} catch (Exception e) {
			e.getMessage();
			return ShellStatus.CONTINUE;
		}
		if(path.size() != 1) throw new ShellIOException("Number of arguments should be 1.");
		File file = new File(path.get(0));
		if(!file.isDirectory() || !file.exists()) throw new ShellIOException("Path should be a directory.");
		
		walk(file, env, 0);
        env.write(env.getPromptSymbol() + " ");
		return ShellStatus.CONTINUE;
	}

	private void walk(File file, Environment env, int dubina) {
		String output = String.format("%s%s", " ".repeat(dubina * 2), file.getName());
		env.writeln(output);
		if(file.isDirectory()) {
			File[] child = file.listFiles();
			Arrays.sort(child);
            if(child != null){
                for(var c : child){
                    walk(c, env, dubina + 1);
                }
            }
		} 
		
		
		
	}

	@Override
	public String getCommandName() {
		return "Tree";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> desc = new ArrayList<>();
		desc.add("Description:");
		desc.add("It prints a tree (each directory level shifts output two characters to the right) for the given directory.");
		return desc;
	}

}
