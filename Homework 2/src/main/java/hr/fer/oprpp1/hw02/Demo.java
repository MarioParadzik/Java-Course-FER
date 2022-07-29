package hr.fer.oprpp1.hw02;

import hr.fer.oprpp1.custom.scripting.parser.SmartScriptParser;
/**
 *This class is used for testing SmartScriptParser.
 */
public class Demo {

	public static void main(String[] args) {
		String docBody = "This is sample text.\n{$ FOR i 1 10 1 $}\n This is {$= i $}-th time this message is generated.\n{$END$}\n{$FOR i 0 10 2 $}\n sin({$=i$}^2) = {$= i i * @sin \"0.000\" @decfmt $}\n{$END$}";
        SmartScriptParser parser = new SmartScriptParser(docBody);

        String output = parser.getDocumentNode().toString();
        System.out.println(output);
        System.out.println();
        SmartScriptParser parser2 = new SmartScriptParser(output);
        String output2 = parser2.getDocumentNode().toString();
        System.out.println(output2);
        boolean b = parser.equals(parser2);
        System.out.println(b);
        boolean c = output.equals(output2);
        System.out.println(c);
        
	}

}
