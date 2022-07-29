package hr.fer.oprpp1.custom.scripting.parser;


import java.util.Arrays;

import hr.fer.oprpp1.custom.collections.ArrayIndexedCollection;
import hr.fer.oprpp1.custom.collections.ObjectStack;
import hr.fer.oprpp1.custom.scripting.elems.Element;
import hr.fer.oprpp1.custom.scripting.elems.ElementConstantDouble;
import hr.fer.oprpp1.custom.scripting.elems.ElementConstantInteger;
import hr.fer.oprpp1.custom.scripting.elems.ElementFunction;
import hr.fer.oprpp1.custom.scripting.elems.ElementOperator;
import hr.fer.oprpp1.custom.scripting.elems.ElementString;
import hr.fer.oprpp1.custom.scripting.elems.ElementVariable;
import hr.fer.oprpp1.custom.scripting.lexer.SmartScriptLexer;
import hr.fer.oprpp1.custom.scripting.lexer.SmartScriptLexerState;
import hr.fer.oprpp1.custom.scripting.lexer.SmartScriptLexerToken;
import hr.fer.oprpp1.custom.scripting.lexer.SmartScriptLexerTokenType;
import hr.fer.oprpp1.custom.scripting.nodes.DocumentNode;
import hr.fer.oprpp1.custom.scripting.nodes.EchoNode;
import hr.fer.oprpp1.custom.scripting.nodes.ForLoopNode;
import hr.fer.oprpp1.custom.scripting.nodes.Node;
import hr.fer.oprpp1.custom.scripting.nodes.TextNode;

/**
 *This class is s parser that we use to parse Smart Script texts and builds syntax trees.
 */
public class SmartScriptParser {
	private ObjectStack stog;
	private SmartScriptLexer lexer;
	private DocumentNode document;
	
	/**
	 *Constructor that initializes the lexer and starts the parsing.
	 * @param txt Text we parse.
	 */
	public SmartScriptParser(String txt) {
		this.lexer = new SmartScriptLexer(txt);
		this.stog = new ObjectStack();
		this.document = new DocumentNode();
		stog.push(document);
		
		try {
			startParser();
		} catch(Exception ex) {
			throw new SmartScriptParserException(ex.getMessage());
		}
	}

	/*
	 *This private method is used to parse texts into nodes.
	 */
	private void startParser() {
		SmartScriptLexerToken next = lexer.nextToken();
		while(next.getTokenType() != SmartScriptLexerTokenType.EOF) {
			
			if(lexer.getState() == SmartScriptLexerState.TAG) {
				StartTagParser();
			} else if(lexer.getState() == SmartScriptLexerState.TEXT) {
				if(next.getTokenType() == SmartScriptLexerTokenType.TEXT) {
					TextNode txtNode = new TextNode((String) next.getValue());
					Node parent = (Node) stog.peek();
					parent.addChildNode(txtNode);
				} else if(next.getTokenType() == SmartScriptLexerTokenType.TAG_START) {
					lexer.setState(SmartScriptLexerState.TAG);
				} else {
					throw new SmartScriptParserException("Wrong TokenType.");
				}
			} else {
				throw new SmartScriptParserException("Unknown state.");
			}
			
			next = lexer.nextToken();
			
		}
		if(stog.size() != 1) throw new SmartScriptParserException("Some nodes ain't closed.");
		
	}

	/**
	 *This private method is used to parse tags into nodes.
	 */
	private void StartTagParser() {
		SmartScriptLexerToken next = lexer.getToken();
		
		if(next.getTokenType() == SmartScriptLexerTokenType.TEXT) {
			if(((String) next.getValue()).startsWith("END")) {
				if(lexer.nextToken().getTokenType() == SmartScriptLexerTokenType.TAG_END) {
					lexer.setState(SmartScriptLexerState.TEXT);
					stog.pop();
				} else {
					throw new SmartScriptParserException("End tag ain't empty.");
				}
				
			} else if(((String) next.getValue()).startsWith("=")) {
				ArrayIndexedCollection echoElements = new ArrayIndexedCollection();
				SmartScriptLexerToken echo = lexer.nextToken();
				while(echo.getTokenType() != SmartScriptLexerTokenType.TAG_END) {
					echoElements.add(elementType(echo));
					echo = lexer.nextToken();
				}
				if(echoElements.isEmpty()) throw new SmartScriptParserException("Echo tag can't be empty.");
				
				//echo node
				Element[] echoChildren = Arrays.copyOf(echoElements.toArray(), echoElements.size(), Element[].class);
				EchoNode echoNode = new EchoNode(echoChildren);
				Node parent = (Node) stog.peek();
				parent.addChildNode(echoNode);
				
			} else if (((String) next.getValue()).toUpperCase().startsWith("FOR")) {
				forTag();
			} else {
				throw new SmartScriptParserException("Unknown tag.");
			}
		} else {
			throw new SmartScriptParserException("No tag name.");
		}
		lexer.setState(SmartScriptLexerState.TEXT);
	}

	/**
	 *This private method is used to parse for tag into nodes.
	 */
	private void forTag() {
		SmartScriptLexerToken next = lexer.nextToken();
		if(next.getTokenType() != SmartScriptLexerTokenType.TEXT) {
			throw new SmartScriptParserException("Wrong FOR variable inialization.");
		}
		
		String name = (String) next.getValue();
		
		if(!Character.isLetter(name.charAt(0))) throw new SmartScriptParserException("Variable must start with letter.");
		ElementVariable first = new ElementVariable(name);
		
		Element[] forElements = new Element[3];
		
		for (int i = 0; i < forElements.length+1; i++) {
			SmartScriptLexerToken token = lexer.nextToken();
			
			if(token.getTokenType() != SmartScriptLexerTokenType.TAG_END && i == 3) throw new SmartScriptParserException("More elements than recomended.");
			
			if(token.getTokenType() == SmartScriptLexerTokenType.TAG_END) {
				if(i > 1) break;
				else throw new SmartScriptParserException("No enough elements.");
			} else {
				forElements[i] = elementType(token);
			}
		}
		
		for (int i = 0; i < forElements.length; i++) {
			if(forElements[i] instanceof ElementOperator ||
			   forElements[i] instanceof ElementFunction) {
				throw new SmartScriptParserException("FOR loop must have two or three Elements of type variable, number or string.");
			}
		}
		ForLoopNode forLoopNode = new ForLoopNode(first, forElements[0], forElements[1], forElements[2]);
		Node parent = (Node) stog.peek();
		parent.addChildNode(forLoopNode);
		stog.push(forLoopNode);
	}

	/**
	 *This private methods is used to create element types by the generated token.
	 *@param echo SmartScriptLexerToken that we turn into an element.
	 *@return new Element.
	 */
	private Element elementType(SmartScriptLexerToken echo) {
		if(echo.getTokenType() == SmartScriptLexerTokenType.NUMBER) {
			if(echo.getValue() instanceof Integer) {
				int number = (int) echo.getValue();
				return new ElementConstantInteger(number);
			}
			if(echo.getValue() instanceof Double) {
				double number = (double) echo.getValue();
				return new ElementConstantDouble(number);
			}
		} else if(echo.getTokenType() == SmartScriptLexerTokenType.OPERATOR) return new ElementOperator((String) echo.getValue());
		  else if(echo.getTokenType() == SmartScriptLexerTokenType.FUNCTION) return new ElementFunction((String) echo.getValue());
		  else if(echo.getTokenType() == SmartScriptLexerTokenType.TEXT) {
			String text = (String) echo.getValue();
			if(text.startsWith("\"")) {
				return new ElementString(text);
			} else {
				if(!Character.isLetter(text.charAt(0))) {
					throw new SmartScriptParserException("Variable must start with a letter");
				}
				return new ElementVariable(text);
			}
		}
		throw new SmartScriptParserException("Wrong token type");
	}

	/**
	 *This method returns the document node.
	 *@return Document node.
	 */
	public DocumentNode getDocumentNode() {
		return this.document;
	}

	@Override
	public boolean equals(Object obj) {
		if(obj instanceof SmartScriptParser) return ((SmartScriptParser) obj).getDocumentNode().equals(getDocumentNode());		
		return false;
	}

	@Override
	public String toString() {
		return this.getDocumentNode().toString();
	}
	
	
}
