package hr.fer.zemris.java.hw06.shell;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is used as a utility for shell. Which parses the arguments.
 */
public class ShellParser {
	
	/**
	 * This method is used to parse the name of the command.
	 * @param input Command.
	 * @return Command name.
	 */
	public static String getName(String input) {
		if(!(input.contains(" "))) return input;
		return input.substring(0, input.indexOf(" "));
	}
	
	/**
	 * This method is used to parse the arguments for the command.
	 * @param input Arguments.
	 * @return List of arguments.
	 */
	public static List<String> parse(String input) {
        List<String> list = new ArrayList<>();

        int begin = 0, counter = 0;
        boolean mark = false;

        while(begin < input.length()){
            counter = 0;
            mark = false;
            while (begin < input.length() && input.charAt(begin) == ' ')
                begin++;
            if(begin >= input.length()) break;
            if(input.charAt(begin) == '\"'){
                begin++;
                mark = true;
            }

            if(mark){
                while(begin+counter < input.length() && input.charAt(begin+counter) != '\"') counter++;
                if(begin+counter >= input.length() && input.charAt(begin+counter-1) != '\"') throw new ShellIOException("Wrong input, \" was expexted.");
                int end = (begin+counter < input.length()-1) ? begin+counter : input.length()-1;
                list.add(input.substring(begin, end));
                begin++;
            }
            else{
                while(begin+counter < input.length() && input.charAt(begin+counter) != ' ') counter++;
                int end = (begin+counter < input.length()) ? begin+counter : input.length();
                list.add(input.substring(begin,end));
            }

            begin+=counter;
        }

        return list;
	}
}
