package hr.fer.zemris.java.hw06.shell.commands;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellIOException;
import hr.fer.zemris.java.hw06.shell.ShellParser;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * This class represents ls command.
 * It lists all files(non recursive) for the given directory.
 */
public class LsCommand implements ShellCommand {

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
		
		File[] files = file.listFiles();
		if(files != null) {
			
			for(File f : files) {
				try {
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					Path pathh = Paths.get(f.getAbsolutePath());
					BasicFileAttributeView faView = Files.getFileAttributeView(
					pathh, BasicFileAttributeView.class, LinkOption.NOFOLLOW_LINKS);
					BasicFileAttributes attributes = faView.readAttributes();
					FileTime fileTime = attributes.creationTime();
					String formattedDateTime = sdf.format(new Date(fileTime.toMillis()));
					
					String output = String.format("%c%c%c%c %12d %s %s",f.isDirectory() ? 'd' : '-', 
							f.canRead() ? 'r' : '-', f.canWrite() ? 'w' : '-', f.canExecute() ? 'w' : '-',
									f.length(), formattedDateTime, f.getName());
					
					env.writeln(output);
					
				} catch (Exception e) {
					e.printStackTrace();
					env.writeln("Error while opening files!");
		            env.write(env.getPromptSymbol() + " ");
		            return ShellStatus.CONTINUE;
				}

			}

		} else {
			throw new ShellIOException("Can't acess this directory.");
		}
        env.write(env.getPromptSymbol() + " ");
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "Ls";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> desc = new ArrayList<>();
		desc.add("Description:");
		desc.add("It lists all files(non recursive) for the given directory.");
		return desc;
	}

}
