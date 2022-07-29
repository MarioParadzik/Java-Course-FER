package hr.fer.zemris.java.hw06.shell.commands;

import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * This class represents symbol command.
 * If used only with symbol name it shows the symbol of MyShell for given name.
 * If used with two argumentss it changes the symbol of MyShell.
 */
public class SymbolCommand implements ShellCommand {

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		String[] elems = arguments.split(" ");
		
		if(!(elems.length == 1 || elems.length == 2)) {
			env.writeln("Number of arguments should be 1 or 2 you entered: " + elems.length + ".");
			env.write(env.getPromptSymbol() + " ");
			return ShellStatus.CONTINUE;
		}
		
		if(elems.length == 1) {
			if(elems[0].equals("MORELINES")) {
				env.writeln("Symbol for MORELINES is '" + env.getMorelinesSymbol() + "'");
				env.write(env.getPromptSymbol() + " ");
			} else if(elems[0].equals("PROMPT")) {
				env.writeln("Symbol for PROMPT is '" + env.getPromptSymbol() + "'");
				env.write(env.getPromptSymbol() + " ");
			} else if(elems[0].equals("MULTILINE")) {
				env.writeln("Symbol for MULTILINE is '" + env.getMultilineSymbol() + "'");
				env.write(env.getPromptSymbol() + " ");
			} else {
				env.writeln("Unknown symbol name.");
				env.write(env.getPromptSymbol() + " ");
			}
			return ShellStatus.CONTINUE;
		} else {
			Character symbol = elems[1].charAt(0);
			if(elems[0].equals("MORELINES")) {
				env.writeln("Symbol for MORELINES changed from '" + env.getMorelinesSymbol() + "' to '" + symbol + "'");
				env.setMorelinesSymbol(symbol);
				env.write(env.getPromptSymbol() + " ");	
			} else if(elems[0].equals("PROMPT")) {
				env.writeln("Symbol for PROMPT changed from '" + env.getPromptSymbol() + "' to '" + symbol + "'");
				env.setPromptSymbol(symbol);
				env.write(env.getPromptSymbol() + " ");
			} else if(elems[0].equals("MULTILINE")) {
				env.writeln("Symbol for MULTILINE changed from '" + env.getMultilineSymbol() + "' to '" + symbol + "'");
				env.setMultilineSymbol(symbol);
				env.write(env.getPromptSymbol() + " ");
			} else {
				env.writeln("Unknown symbol name.");
				env.write(env.getPromptSymbol() + " ");
			}
			return ShellStatus.CONTINUE;
		}
		
		
		
	}

	@Override
	public String getCommandName() {
		return "Symbol";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> desc = new ArrayList<>();
		desc.add("Description:");
		desc.add("If used only with symbol name it shows the symbol of MyShell for given name.");
		desc.add("If used with two argumentss it changes the symbol of MyShell.");
		return desc;
	}

}
