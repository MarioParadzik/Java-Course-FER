package hr.fer.oprpp1.hw02;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import hr.fer.oprpp1.custom.scripting.nodes.DocumentNode;
import hr.fer.oprpp1.custom.scripting.parser.SmartScriptParser;
import hr.fer.oprpp1.custom.scripting.parser.SmartScriptParserException;

/**
 *This class is used for testing SmartScriptParser.
 */

public class SmartScriptTester {

	/**
	 *This main method reads files from resources and tests if the parser works and
	 *is trying to reproduce the same input.
	 * @param args
	 */
	public static void main(String[] args) {
		
		for(int i=1; i<10; i++) {
            try {
                String docBody = new String(
                        Files.readAllBytes(Paths.get("src/test/resources/primjer" +i+ ".txt")),
                        StandardCharsets.UTF_8
                );
                SmartScriptParser parser = null;
                try {
                    parser = new SmartScriptParser(docBody);
                } catch(SmartScriptParserException e) {
                    System.out.println("Error while parsing " +i+ ". document! " + e.getMessage());
                    continue;
                } catch(Exception e) {
                    System.out.println("If this line ever executes, you have failed this class!");
                    System.exit(-1);
                }
                DocumentNode document = parser.getDocumentNode();
                String originalDocumentBody = document.toString();
                System.out.println(originalDocumentBody); // should write something like original
                // content of docBody

            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                System.out.println(e);
            }
        }
	}

}
