package hr.fer.oprpp1.custom.scripting.lexer;

/**
 *This class is used as a SmarScriptLecer whose job is it to 
 *parse the given text input into tokens by the asigned state.
 */
public class SmartScriptLexer {
	private char[] data; // ulazni tekst 
	private SmartScriptLexerToken token; // trenutni token
	private int currentIndex; // indeks prvog neobrađenog znaka
	private SmartScriptLexerState state; // trenutni state
	
	/**
	 *This constructor initializes our data and sets the default state.
	 *@param text That we parse into tokens
	 */
	public SmartScriptLexer(String text) {
		if(text == null) throw new NullPointerException();
		this.data = text.toCharArray();
		this.currentIndex = 0;
		this.state = SmartScriptLexerState.TEXT;
	}
	
	/**
	 *This method is generating tokens by given state
	 *@return SmartScriptLexerToken by calling method getToken()
	 */
	public SmartScriptLexerToken nextToken() {
		if(this.state == SmartScriptLexerState.TEXT) extractNextTokenText();
		else extractNextTokenTAG();
		return getToken();
	}	

	/**
	 *This method returns the current produced token.
	 *@return SmartScriptLexerToken current token.
	 */
	public SmartScriptLexerToken getToken() {
		return token;
	}
	
	/**
	 *This method is used to set the lexer state.
	 *@param state SmartScriptLexerState new state.
	 */
	public void setState(SmartScriptLexerState state) {
		if(state == null) throw new NullPointerException();
		this.state = state;
	}
	
	/**
	 *This method is used to return the value of the current state.
	 *@return Current lexer state.
	 */
	public SmartScriptLexerState getState() {
        return state;
    }
	
	/**
	 *This private method is generating tokens when the assigned state is TEXT.
	 */
	private void extractNextTokenText() {
		
		if(token != null && token.getTokenType() == SmartScriptLexerTokenType.EOF) throw new SmartScriptLexerException("Can't generate tokens after EOF.");
		
		if(currentIndex >= data.length) {
			token = new SmartScriptLexerToken(SmartScriptLexerTokenType.EOF, null);
			return;
		}
		
		StringBuilder sb = new StringBuilder();
		while(currentIndex < data.length) {
			//escape
			if(data[currentIndex] == '\\') {
				currentIndex++;
				if(currentIndex >= data.length) throw new SmartScriptLexerException("Can't produce tokens after data is processed.");
				if(data[currentIndex] == '\\' || data[currentIndex] == '{') sb.append(data[currentIndex++]);
				else throw new SmartScriptLexerException("Illegal escaping.");
				
			} else if(data[currentIndex] == '{') {
				//provjeri { zasebno ako tag zapocinje
				int tst = currentIndex +1;
				if(tst == data.length) break;//slucaj ako je zadnji znak
				if (!sb.isEmpty() && data[tst] == '$') break;
				sb.append(data[currentIndex++]);
				if(currentIndex >= data.length) throw new SmartScriptLexerException("Can't produce tokens after data is processed.");
				if(data[currentIndex] == '$') {
					sb.append(data[currentIndex++]);
					break;
				} else {
					sb.append(data[currentIndex++]);;
				}
			} else {
				sb.append(data[currentIndex++]);
			}
		}
		//generiraj token
		if(sb.toString().startsWith("{$")) {
			token = new SmartScriptLexerToken(SmartScriptLexerTokenType.TAG_START, sb.toString());
		} else {
			token = new SmartScriptLexerToken(SmartScriptLexerTokenType.TEXT, sb.toString());
		}
		return;
	}
	
	/**
	 *This private method is generating tokens when the assigned state is TAG.
	 */
	private void extractNextTokenTAG() {
		//provjeri jel kraj
		if(token != null && token.getTokenType() == SmartScriptLexerTokenType.EOF) throw new SmartScriptLexerException("Can't generate tokens after EOF.");
		//preskoci razmake
		skipBlanks();
		//ako nemamo vise podataka generiraj EOF
		if(currentIndex >= data.length) {
			token = new SmartScriptLexerToken(SmartScriptLexerTokenType.EOF, null);
			return;
		}
		//provjeri dal se pojavio tag = 
		if(data[currentIndex] == '=') {
			token = new SmartScriptLexerToken(SmartScriptLexerTokenType.TEXT, "=");
			currentIndex++;
			return;
		}
		
		StringBuilder sb = new StringBuilder();
		boolean flag = false;// ceka prvi "
		SmartScriptLexerTokenType current = null;
		while(currentIndex < data.length) {
			if(flag) {
				//escape u stringu
				if(data[currentIndex] =='\\') {
					currentIndex++;
					if(currentIndex >= data.length) throw new SmartScriptLexerException("Can't produce tokens after data is processed.");
					if(data[currentIndex] == 'n') {
						currentIndex++;
						sb.append("\n");
					} else if(data[currentIndex] == 'r') {
						currentIndex++;
						sb.append("\r");
					} else if(data[currentIndex] == 't') {
						currentIndex++;
						sb.append("\t");
					} else if(data[currentIndex] == '\\' ||
							data[currentIndex] == '"') {
						sb.append(data[currentIndex++]);
					} else {
						throw new SmartScriptLexerException("Illegal escaping.");
					}

				} else if (data[currentIndex] == '"') {
					//end of string
					sb.append(data[currentIndex++]);
					break;
				} else {
				sb.append(data[currentIndex++]);
				} 
				//jel end tag
			} else if(data[currentIndex] == '$') {
				if(current != null) break;
				sb.append(data[currentIndex++]);
				if(currentIndex >= data.length) throw new SmartScriptLexerException("Can't produce tokens after data is processed.");
				if(data[currentIndex] == '}') {
					sb.append(data[currentIndex++]);
					current = SmartScriptLexerTokenType.TAG_END;
					break;
				} else {
					throw new SmartScriptLexerException("Wrong exit from tag.");
				}
			//ako je whitespace generiraj token	
			} else if(data[currentIndex] == '\r' ||
					   data[currentIndex] == '\n' ||
					   data[currentIndex] == '\t' ||
					   data[currentIndex] == ' ') {
				break;
				//provjeri jel negativan broj ako ne onda je operator
			} else if(data[currentIndex] == '-') {
				if (!sb.isEmpty()) break;
				sb.append(data[currentIndex++]);
				current = SmartScriptLexerTokenType.OPERATOR;
				if(currentIndex >= data.length) break;
				if(Character.isDigit(data[currentIndex])) {
					sb.append(data[currentIndex++]);
					current = SmartScriptLexerTokenType.NUMBER;
				}
				//provjeri jel broj
			} else if(Character.isDigit(data[currentIndex])) {
				if(current == null) current = SmartScriptLexerTokenType.NUMBER;
				if(current != SmartScriptLexerTokenType.NUMBER) break;
				sb.append(data[currentIndex++]);
				
				//provjeri jel decimalan broj
			} else if(data[currentIndex] == '.' && 
					current == SmartScriptLexerTokenType.NUMBER) {
				int test = currentIndex-1;
				if(data[test] != '.') {
					sb.append(data[currentIndex++]);
				} else {
					//primjer 12..
					throw new SmartScriptLexerException("Neispravan decimalan broj.");
				}
				//provjeri jel funkcija
			} else if(data[currentIndex] == '@') {
				currentIndex++;
				if(currentIndex >= data.length) throw new SmartScriptLexerException("Can't produce tokens after data is processed.");
				if(Character.isLetter(data[currentIndex])) {
					current = SmartScriptLexerTokenType.FUNCTION;
					sb.append(data[currentIndex++]);
					if(currentIndex >= data.length) throw new SmartScriptLexerException("Can't produce tokens after data is processed.");
					while(currentIndex < data.length) {
						if(Character.isLetter(data[currentIndex])) {
							sb.append(data[currentIndex++]);
						} else {
							break;
						}
					}
				} else {
					throw new SmartScriptLexerException("Function must start with letter.");
				}
				//provjera jel operator osim - on je gore
			} else if(data[currentIndex] == '+' ||
					  data[currentIndex] == '*' ||
					  data[currentIndex] == '/' ||
					  data[currentIndex] == '^') {
				sb.append(data[currentIndex++]);
				current = SmartScriptLexerTokenType.OPERATOR;
				break;
				//inace je tekst
			}else {
				if(current == null) current = SmartScriptLexerTokenType.TEXT;
				if(current != SmartScriptLexerTokenType.TEXT) break;
				if (data[currentIndex] == '"') flag = true;
				sb.append(data[currentIndex++]);
			}
		}
		//generiraj token
		if(current == SmartScriptLexerTokenType.NUMBER) {
			if(isInteger(sb.toString())) {
				token = new SmartScriptLexerToken(current, Integer.parseInt(sb.toString()));
			} else if (isDouble(sb.toString())){
				token = new SmartScriptLexerToken(current, Double.parseDouble(sb.toString()));
			}
		} else {
			token = new SmartScriptLexerToken(current, sb.toString());
		}
		return;
	}
	
	/**
	 *This method is used to remove whitespace.
	 */
	private void skipBlanks() {
		for (int i = currentIndex; i < data.length; i++) {
			char c = data[i];
			if(c == '\r' || c == '\n' || c == '\t' || c == ' ') {
				currentIndex++;
				continue;
			}
			break;
		}
		
	}
	
	/**
	 *This method is used to check if the String input is an Integer.
	 *@param input String which we try to parse.
	 *@return True if String is an Integer, otherwise false.
	 */
	public boolean isInteger(String input) {
	    try {
	        Integer.parseInt(input);
	        return true;
	    } catch(Exception e) {
	        return false;
	    }
	}
	
	/**
	 *This method is used to check if the String input is a Double.
	 *@param input String which we try to parse.
	 *@return True if String is a Double, otherwise false.
	 */
	public boolean isDouble(String input) {
		 try {
			Double.parseDouble(input);
		    return true;
		 } catch(Exception e) {
		    return false;
		 }
	}
}
