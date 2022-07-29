package hr.fer.zemris.java.hw06.shell;

import java.util.Collections;
import java.util.Scanner;
import java.util.SortedMap;
import java.util.TreeMap;

import hr.fer.zemris.java.hw06.shell.commands.CatCommand;
import hr.fer.zemris.java.hw06.shell.commands.CharsetsCommand;
import hr.fer.zemris.java.hw06.shell.commands.CopyCommand;
import hr.fer.zemris.java.hw06.shell.commands.ExitCommand;
import hr.fer.zemris.java.hw06.shell.commands.HelpCommand;
import hr.fer.zemris.java.hw06.shell.commands.HexdumpCommand;
import hr.fer.zemris.java.hw06.shell.commands.LsCommand;
import hr.fer.zemris.java.hw06.shell.commands.MkdirCommand;
import hr.fer.zemris.java.hw06.shell.commands.SymbolCommand;
import hr.fer.zemris.java.hw06.shell.commands.TreeCommand;

/**
 * This class is used for implementing environment methods.
 */
public class EnvironmentImpl implements Environment {

	private Character multilineSymbol = '|';
	private Character promptSymbol = '>';
	private Character morelinesSymbol = '\\';
	private SortedMap<String, ShellCommand> commands = new TreeMap<>();
	
	private Scanner sc = new Scanner(System.in);
	
	/**
	 * Basic construcotr.
	 */
	public EnvironmentImpl() {
		put();
	}

	/**
	 * This private method is used to fill the map with shell commands.
	 */
	private void put() {
		commands.put("charsets", new CharsetsCommand());
		commands.put("cat", new CatCommand());
		commands.put("ls", new LsCommand());
		commands.put("tree", new TreeCommand());
		commands.put("copy", new CopyCommand());
		commands.put("mkdir", new MkdirCommand());
		commands.put("hexdump", new HexdumpCommand());
		commands.put("help", new HelpCommand());
		commands.put("symbol", new SymbolCommand());
		commands.put("exit", new ExitCommand());
	}
	
	@Override
	public String readLine() throws ShellIOException {
		StringBuilder sb = new StringBuilder();
		String input = sc.nextLine();
		while(input.charAt(input.length()-1) == morelinesSymbol) {
			write(multilineSymbol + " ");
			sb.append(input, 0, input.length()-1);
			input = sc.nextLine();
		}
		sb.append(input);
		return sb.toString();
	}

	@Override
	public void write(String text) throws ShellIOException {
		System.out.print(text);
	}

	@Override
	public void writeln(String text) throws ShellIOException {
		System.out.println(text);	
	}

	@Override
	public SortedMap<String, ShellCommand> commands() {
		return Collections.unmodifiableSortedMap(commands);
	}

	@Override
	public Character getMultilineSymbol() {
		return this.multilineSymbol;
	}

	@Override
	public void setMultilineSymbol(Character symbol) {
		this.multilineSymbol = symbol;	
	}

	@Override
	public Character getPromptSymbol() {
		return this.promptSymbol;
	}

	@Override
	public void setPromptSymbol(Character symbol) {
		this.promptSymbol = symbol;
	}

	@Override
	public Character getMorelinesSymbol() {
		return this.morelinesSymbol;
	}

	@Override
	public void setMorelinesSymbol(Character symbol) {
		this.morelinesSymbol = symbol;
	}

}
