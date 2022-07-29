package hr.fer.oprpp1.custom.scripting.lexer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import hr.fer.oprpp1.hw02.prob1.Lexer;
import hr.fer.oprpp1.hw02.prob1.Token;
import hr.fer.oprpp1.hw02.prob1.TokenType;




public class SmartScriptLexerTest {
	@Test
    public void testNotNull() {
        SmartScriptLexer lexer = new SmartScriptLexer("");

        assertNotNull(lexer.nextToken(), "Token was expected but null was returned.");
    }
	
	@Test
	public void testNullInput() {
		assertThrows(NullPointerException.class, () -> new SmartScriptLexer(null));
	}
	
	@Test
	public void testEmpty() {
		SmartScriptLexer lexer = new SmartScriptLexer("");
		
		assertEquals(SmartScriptLexerTokenType.EOF, lexer.nextToken().getTokenType(), "Empty input must generate only EOF token.");
	}
	
	@Test
	public void testGetReturnsLastNext() {
		// Calling getToken once or several times after calling nextToken must return each time what nextToken returned...
		SmartScriptLexer lexer = new SmartScriptLexer("");
		
		SmartScriptLexerToken token = lexer.nextToken();
		assertEquals(token, lexer.getToken(), "getToken returned different token than nextToken.");
		assertEquals(token, lexer.getToken(), "getToken returned different token than nextToken.");
	}
	
	@Test
	public void testRadAfterEOF() {
		SmartScriptLexer lexer = new SmartScriptLexer("");

		// will obtain EOF
		lexer.nextToken();
		// will throw!
		assertThrows(SmartScriptLexerException.class, () -> lexer.nextToken());
	}
	
	@Test
	public void testBasicText() {
		// When input is only of spaces, tabs, newlines, etc...
		SmartScriptLexer lexer = new SmartScriptLexer("   \r\n\t   aaaaa ");
		SmartScriptLexerToken correctData[] = {
                new SmartScriptLexerToken(SmartScriptLexerTokenType.TEXT, "   \r\n\t   aaaaa "),
                new SmartScriptLexerToken(SmartScriptLexerTokenType.EOF, null)
        };
		String msg = "Tokens are not equal";
		assertEquals(lexer.nextToken().getTokenType(), correctData[0].getTokenType(), msg);
        assertEquals(lexer.nextToken().getTokenType(), correctData[1].getTokenType(), msg);
	}
	
	@Test
	public void testEscaping() {
		SmartScriptLexer lexer = new SmartScriptLexer("\\\\ \\{");

		SmartScriptLexerToken correctData[] = {
                new SmartScriptLexerToken(SmartScriptLexerTokenType.TEXT, "\\ {"),
                new SmartScriptLexerToken(SmartScriptLexerTokenType.EOF, null)
        };
		String msg = "Tokens are not equal";
		assertEquals(lexer.nextToken().getTokenType(), correctData[0].getTokenType(), msg);
        assertEquals(lexer.nextToken().getTokenType(), correctData[1].getTokenType(), msg);
	}
	
	@Test
	public void testInvalidEscaping() {
		SmartScriptLexer lexer = new SmartScriptLexer(" \\");
		assertThrows(SmartScriptLexerException.class, () -> lexer.nextToken());
	}
	
	@Test
	public void testNullSetState() {
		SmartScriptLexer lexer = new SmartScriptLexer("");
		assertThrows(NullPointerException.class, () -> lexer.setState(null));
	}
	
	@Test
	public void testTypes() {
		SmartScriptLexer lexer = new SmartScriptLexer("{$ FOR i 1 10 1 $}");
		SmartScriptLexerToken correctData[] = {
                new SmartScriptLexerToken(SmartScriptLexerTokenType.TAG_START, "{$"),
                new SmartScriptLexerToken(SmartScriptLexerTokenType.TEXT, "FOR"),
                new SmartScriptLexerToken(SmartScriptLexerTokenType.TEXT, "i"),
                new SmartScriptLexerToken(SmartScriptLexerTokenType.NUMBER, "1"),
                new SmartScriptLexerToken(SmartScriptLexerTokenType.NUMBER, "10"),
                new SmartScriptLexerToken(SmartScriptLexerTokenType.NUMBER, "1"),
                new SmartScriptLexerToken(SmartScriptLexerTokenType.TAG_END, "$}"),
                new SmartScriptLexerToken(SmartScriptLexerTokenType.EOF, null)
        };
		String msg = "Tokens are not equal";
		assertEquals(lexer.nextToken().getTokenType(), correctData[0].getTokenType(), msg);
		lexer.setState(SmartScriptLexerState.TAG);
        assertEquals(lexer.nextToken().getTokenType(), correctData[1].getTokenType(), msg);
        assertEquals(lexer.nextToken().getTokenType(), correctData[2].getTokenType(), msg);
        assertEquals(lexer.nextToken().getTokenType(), correctData[3].getTokenType(), msg);
        assertEquals(lexer.nextToken().getTokenType(), correctData[4].getTokenType(), msg);
        assertEquals(lexer.nextToken().getTokenType(), correctData[5].getTokenType(), msg);
        assertEquals(lexer.nextToken().getTokenType(), correctData[6].getTokenType(), msg);
        assertEquals(lexer.nextToken().getTokenType(), correctData[7].getTokenType(), msg);
	}
}
