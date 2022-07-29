package hr.fer.oprpp1.custom.scripting.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

public class SmartScriptParserTest {
	
    @Test
    void testWrongVariableInForTag() {
        String docBody = "{$ FOR 3 1 10 10 $}";

        assertThrows(SmartScriptParserException.class, () -> new SmartScriptParser(docBody));
    }
    
    @Test
    void testWrongVariableInForTag2() {
        String docBody = "{$ FOR * 1 10 10 $}";

        assertThrows(SmartScriptParserException.class, () -> new SmartScriptParser(docBody));
    }
    
    @Test
    void testToManyElementsInForTag() {
        String docBody = "{$ FOR year 1 10 \"1\" \"10\" $}";

        assertThrows(SmartScriptParserException.class, () -> new SmartScriptParser(docBody));
    }
    
    @Test
    void testToManyElementsInForTag2() {
        String docBody = "{$ FOR year 1 10 1 3 $}";

        assertThrows(SmartScriptParserException.class, () -> new SmartScriptParser(docBody));
    }
    
    @Test
    void testToFewElementsInForTag() {
        String docBody = "{$ FOR year $} ";

        assertThrows(SmartScriptParserException.class, () -> new SmartScriptParser(docBody));
    }
    
    @Test
    void testWrongElementTypeInForTag() {
        String docBody = "{$ FOR year @sin 10 $} ";

        assertThrows(SmartScriptParserException.class, () -> new SmartScriptParser(docBody));
    }
      
    @Test
    void testForTag1() {
        String docBody = "{$ FOR i -1 10 1 $} {$END$}";
        SmartScriptParser parser = new SmartScriptParser(docBody);
        String output = parser.getDocumentNode().toString();
        
        SmartScriptParser parser2 = new SmartScriptParser(output);
        String output2 = parser2.getDocumentNode().toString();
        
        assertEquals(parser, parser2);
        assertEquals(output, output2);
    }
    
    @Test
    void testForTag2() {
        String docBody = "{$ FOR sco_re    \"-1\"   10   \"1\" $} {$END$}";
        SmartScriptParser parser = new SmartScriptParser(docBody);
        String output = parser.getDocumentNode().toString();
        
        SmartScriptParser parser2 = new SmartScriptParser(output);
        String output2 = parser2.getDocumentNode().toString();
        
        assertEquals(parser, parser2);
        assertEquals(output, output2);

    }
    
    @Test
    void testForTag3() {
        String docBody = "{$ FOR year 1 last_year $} {$END$}";
        SmartScriptParser parser = new SmartScriptParser(docBody);
        String output = parser.getDocumentNode().toString();
        
        SmartScriptParser parser2 = new SmartScriptParser(output);
        String output2 = parser2.getDocumentNode().toString();
        
        assertEquals(parser, parser2);
        assertEquals(output, output2);

    }
    
    @Test
    void testCaseInsensitiveForTag() {
        String docBody = "{$ foR i 1 10 1 $} {$END$}";
        SmartScriptParser parser = new SmartScriptParser(docBody);
        String output = parser.getDocumentNode().toString();
        
        SmartScriptParser parser2 = new SmartScriptParser(output);
        String output2 = parser2.getDocumentNode().toString();
        
        assertEquals(parser, parser2);
        assertEquals(output, output2);
    }

    @Test
    void testForTagElementsWithNoSpace() {
        String docBody = "{$ FOR i-1.35bbb\"1\" $} {$END$}";
        SmartScriptParser parser = new SmartScriptParser(docBody);
        String output = parser.getDocumentNode().toString();
        
        SmartScriptParser parser2 = new SmartScriptParser(output);
        String output2 = parser2.getDocumentNode().toString();
        
        assertEquals(parser, parser2);
        assertEquals(output, output2);
    }
    
    @Test
    void testNumberOfChildren() {
        String docBody = "This is sample text.\n{$ FOR i 1 10 1 $}\n This is {$= i $}-th time this message is generated.\n{$END$}\n{$FOR i 0 10 2 $}\n sin({$=i$}^2) = {$= i i * @sin \"0.000\" @decfmt $}\n{$END$}";
        SmartScriptParser parser = new SmartScriptParser(docBody);

        if(parser.getDocumentNode().numberOfChildren() != 4) fail();
        if(parser.getDocumentNode().getChild(0).numberOfChildren() != 0) fail();
        if(parser.getDocumentNode().getChild(1).numberOfChildren() != 3) fail();
        if(parser.getDocumentNode().getChild(2).numberOfChildren() != 0) fail();
        if(parser.getDocumentNode().getChild(3).numberOfChildren() != 5) fail();
    }
    
    //Ostalih 9 primjera u SmartScriptTesteru.
}
