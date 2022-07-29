package hr.fer.zemris.java.hw06.shell.commands;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellIOException;
import hr.fer.zemris.java.hw06.shell.ShellParser;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * This class represents cat command.
 * Takes one or two arguments
 * The first argument is path to some file and is mandatory.
 * The second argument is charset name that should be used to interpret chars from bytes
 * If not provided, a default platform charset should be used.
 * This command opens given file and writes its content to console.
 */
public class CatCommand implements ShellCommand {

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		List<String> path = null;
		try {
			path = ShellParser.parse(arguments);
		} catch (Exception e) {
			e.getMessage();
			return ShellStatus.CONTINUE;
		}
		if(!(path.size() == 1 || path.size() == 2)) throw new ShellIOException("Number of arguments should be 1 or 2.");
		File file = new File(path.get(0));
		if(file.isDirectory()) throw new ShellIOException("File was expected.");
		Charset charset = (path.size() == 1) ? Charset.defaultCharset() : Charset.availableCharsets().get(path.get(1));
		
		try(InputStream is = Files.newInputStream(Paths.get(path.get(0)))) {
			byte[] buff = new byte[4096];

            while (true) {
                int r = is.read(buff);
                if (r < 0) break;
                env.write(new String(buff, charset));            
            }
            
		} catch (IOException e) {
			e.printStackTrace();
			env.write("Error while opening file.");
            env.write(env.getPromptSymbol() + " ");
			return ShellStatus.CONTINUE;
		}
		env.write(env.getPromptSymbol() + " ");
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "Cat";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> desc = new ArrayList<>();
		desc.add("Description:");
		desc.add("Takes one or two arguments.");
		desc.add("The first argument is path to some file and is mandatory.");
		desc.add("The second argument is charset name that should be used to interpret chars from bytes.");
		desc.add("If not provided, a default platform charset should be used.");
		desc.add("This command opens given file and writes its content to console.");
		return desc;
	}

}
