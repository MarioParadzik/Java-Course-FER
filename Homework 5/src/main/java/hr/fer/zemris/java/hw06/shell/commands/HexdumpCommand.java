package hr.fer.zemris.java.hw06.shell.commands;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
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
 * This class represents hexdump command.
 * It takes a single argument which has to be file name.
 * It produces hex-output of given file.
 */
public class HexdumpCommand implements ShellCommand {

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
		if(file.isDirectory()) throw new ShellIOException(path.get(0) + " is a directory, it should be a file.");
		
		 try (InputStream is = Files.newInputStream(Paths.get(path.get(0)))) {
			 final int width = 16;
			 byte[] buff = new byte[width];
			 int pos = 0x0;
	         
			 StringBuilder sb = new StringBuilder();
			 
			 while(true) {
				 int r = is.read(buff);
	             if (r < 1) break;
	             
	             sb.append(String.format("%08X", pos));
	             sb.append(": ");
	             
	             for(int i = 0; i < width; i++) {
	            	 if(i >= r) {
	            		 sb.append("   ");
		             } else {
	                     sb.append(String.format("%02X", buff[i])).append(" ");
	                 }
	            	 
	            	 if (i == 7) {
                        sb.setLength(sb.length() - 1);
                        sb.append("|");
	                 }
	             }
	             
	             sb.append(" | ");
	             for (int i = 0; i < r; i++) {
                    if (buff[i] < 32 || buff[i] > 127) {
                        buff[i] = 46;
                    }
                }
                sb.append(new String(buff));
                
                env.writeln(sb.toString());
                sb.setLength(0);
                pos += 0x10;
			 }
			 
		 } catch (IOException e) {
			e.printStackTrace();
			env.writeln("Error while opening file!");
            env.write(env.getPromptSymbol() + " ");
            return ShellStatus.CONTINUE;
		}
		env.write(env.getPromptSymbol() + " ");
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "Hexdump";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> desc = new ArrayList<>();
		desc.add("Hexdump command.");
		desc.add("It takes a single argument which has to be file name.");
		desc.add("It produces hex-output of given file.");

        return desc;
	}

}
