package hr.fer.oprpp1.custom.collections.demo;

import hr.fer.oprpp1.custom.collections.EmptyStackException;
import hr.fer.oprpp1.custom.collections.ObjectStack;

/**
 *This class is used for evaluating Expression postfix representation with {@link ObjectStack}.
 */
public class StackDemo {

	/**
     * Main method.
     *
     * @param args Command-line arguments.
     */
	public static void main(String[] args) {
		if(args.length != 1) {
			System.exit(0);
		}

		String[] poljeZnakova = args[0].split("\\s");
		ObjectStack stog = new ObjectStack();
		
		try {
			for (int i = 0; i < poljeZnakova.length; i++) {
				if(isNumber(poljeZnakova[i]) == true) {
					int n = Integer.parseInt(poljeZnakova[i]);
					stog.push(n);
				} else if (poljeZnakova[i].startsWith("+")) {
					int first = (Integer) stog.pop();
					int second = (Integer) stog.pop();
					stog.push(first + second);
				} else if (poljeZnakova[i].startsWith("-")) {
					int second = (Integer) stog.pop();
					int first = (Integer) stog.pop();
					stog.push(first - second);
				} else if (poljeZnakova[i].startsWith("*")) {
					int first = (Integer) stog.pop();
					int second = (Integer) stog.pop();
					stog.push(first * second);
				} else if (poljeZnakova[i].startsWith("/")) {
					int second = (Integer) stog.pop();
					if(second == 0) System.exit(0);
					int first = (Integer) stog.pop();
					stog.push(first / second);
				} else if (poljeZnakova[i].startsWith("%")) {
					int second = (Integer) stog.pop();
					int first = (Integer) stog.pop();
					stog.push(first % second);
				}
			}
		} catch (EmptyStackException ex) {
            wirteError();
        }
		
		if(stog.size() != 1) {
			 wirteError();
		} 
		
		System.out.println("Expression evaluates to " + stog.pop() + ".");

	}
	
	/**
	 *This method is used for writing error and closing the program if a problem appears.
	 */
	private static void wirteError() {
		System.err.println("Wrong expression");
		System.exit(0);
	}

	/**
	 *This method is used to parse strings into numbers.
	 *
	 * @param number String that we try to parse.
	 * @return True if given string is a number, false otherwise.
	 */
	public static boolean isNumber(String number) {
	    if (number == null) return false;
	    try {
	    	@SuppressWarnings("unused")
			int n = Integer.parseInt(number);
	    } catch (NumberFormatException nfe) {
	        return false;
	    }
	    return true;
	}

}
