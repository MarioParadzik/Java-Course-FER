package hr.fer.zemris.java.hw06.shell.commands;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellIOException;
import hr.fer.zemris.java.hw06.shell.ShellParser;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * This class represents copy command.
 * Expects two arguments.
 * The first is source file name.
 * The second argument is destination file name.
 * If destination file exists, you should ask user is it allowed to overwrite it.
 * Works only with files (no directories).
 * If the second argument is directory, you should assume that user wants to copy the original file into that directory using the original file name.
 * If destination file exists, you should ask user is it allowed to overwrite it.
 */
public class CopyCommand implements ShellCommand {

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		List<String> path = null;
		try {
			path = ShellParser.parse(arguments);
		} catch (Exception e) {
			e.getMessage();
			return ShellStatus.CONTINUE;
		}
		if(path.size() != 2) throw new ShellIOException("Number of arguments should be 2.");
		File file = new File(path.get(0));
		if(file.isDirectory()) throw new ShellIOException(path.get(0) + " is a directory, it should be a file.");
		File copyFile = new File(path.get(1));
		if(copyFile.exists() && !copyFile.isDirectory()) {
			env.writeln("This file already exists, shold we overwrite it? (y/n)" );
			String answer = env.readLine();
			if(answer.equals("n")  || answer.equals("N")){
                env.writeln("Command stopped.");
                return ShellStatus.CONTINUE;
            }
		} else if(copyFile.isDirectory()) {
			copyFile = new File(path.get(1) + "\\copy");
		}
		
		try (InputStream is = Files.newInputStream(Paths.get(path.get(0)));
				OutputStream os = Files.newOutputStream(copyFile.toPath())) {
			byte[] buff = new byte[4096];

            while (true) {
                int r = is.read(buff);
                if (r < 1) break;
                os.write(Arrays.copyOf(buff, r));
            }
		} catch (IOException e) {
			e.printStackTrace();
			env.writeln("Error while opening files!");
            env.write(env.getPromptSymbol() + " ");
            return ShellStatus.CONTINUE;
		}
		env.writeln("File copied.");
        env.write(env.getPromptSymbol() + " ");
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "Copy";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> desc = new ArrayList<>();
		desc.add("Description:");
		desc.add("Expects two arguments.");
		desc.add("The first is source file name.");
		desc.add("The second argument is destination file name.");
		desc.add("If destination file exists, you should ask user is it allowed to overwrite it.");
		desc.add("Works only with files (no directories).");
		desc.add("If the second argument is directory, you should assume that user wants to copy the original file into that directory using the original file name.");
		desc.add("If destination file exists, you should ask user is it allowed to overwrite it.");
		return desc;
	}

}
