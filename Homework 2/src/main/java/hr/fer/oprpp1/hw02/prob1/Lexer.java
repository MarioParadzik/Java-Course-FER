package hr.fer.oprpp1.hw02.prob1;

/**
 *This class represents a basic lexic analyzer.
 */
public class Lexer {
	private char[] data; // ulazni tekst 
	private Token token; // trenutni token
	private int currentIndex; // indeks prvog neobrađenog znaka
	private LexerState state;
	// konstruktor prima ulazni tekst koji se tokenizira
	
	/**
	 *This constructor initializes the data with the input text and sets the basic state.
	 *@param text Input with which we initialize our data.
	 */
	public Lexer(String text) { 
		if(text == null) throw new NullPointerException();
		this.data = text.toCharArray();
		this.currentIndex = 0;
		this.state = LexerState.BASIC; //default
	}
	// generira i vraća sljedeći token
	// baca LexerException ako dođe do pogreške
	
	/**
	 *This method is generating tokens by given state
	 *@return Token by calling method getToken()
	 */
	public Token nextToken() {
		if(this.state == LexerState.BASIC) extractNextTokenBasic();
		else extractNextTokenExtended();
		return getToken();
	}
	// vraća zadnji generirani token; može se pozivati
	// više puta; ne pokreće generiranje sljedećeg tokena
	
	/**
	 *This method returns the current produced token.
	 *@return Token current token.
	 */
	public Token getToken() {
		return token;
	}
	
	/**
	 *This method is used to set the lexer state.
	 *@param state LexerState new state.
	 */
	public void setState(LexerState state) {
		if(state == null) throw new NullPointerException();
		this.state = state;
	}
	
	/**
	 *This private method is generating tokens when the assigned state is Basic.
	 */
	private void extractNextTokenBasic() {
		//provjerimo jel vec kraj 
		if(token != null && token.getType() == TokenType.EOF) throw new LexerException("Can't generate tokens after EOF.");
		
		skipBlanks(); //izbjegnimo razmake,tabulatore, novi red
		
		//provjerimo jel nema vise znakova i generirajmo zadnji ako je tako
		if(currentIndex >= data.length) {
			token = new Token(TokenType.EOF, null);
			return;
		}
		
		TokenType current = null; // pomocna varijabla za odredjivanje TokenTypa
		StringBuilder sb = new StringBuilder();
		//provjerimo sad sto je 
		
		while(currentIndex < data.length) {
			if(Character.isLetter(data[currentIndex])) {
				if(current == null) current = TokenType.WORD;
				if(current != TokenType.WORD) break;
				sb.append(data[currentIndex++]);
				
			} else if (Character.isDigit(data[currentIndex])) {
				if(current == null) current = TokenType.NUMBER;
				if(current != TokenType.NUMBER) break;
				sb.append(data[currentIndex++]);
				
			} else if(data[currentIndex] == '\\') {
				if(current == null) current = TokenType.WORD;
				if(current != TokenType.WORD) break;
				currentIndex++;
				if(currentIndex >= data.length) throw new LexerException("Can't produce tokens after data is processed.");
				if(data[currentIndex] == '\\' || Character.isDigit(data[currentIndex])) sb.append(data[currentIndex++]);
				else throw new LexerException("Illegal escaping.");
				
			} else {
				if(data[currentIndex] == '\r' ||
				   data[currentIndex] == '\n' ||
				   data[currentIndex] == '\t' ||
				   data[currentIndex] == ' ') {
				   break;
				} else {
					if(current == null) current = TokenType.SYMBOL;
					if(current != TokenType.SYMBOL) break;
					if(data[currentIndex] == '#') this.state = LexerState.EXTENDED;
					sb.append(data[currentIndex++]);
					break;
				}
			}
		}
		
		if(current == TokenType.WORD) {
			token = new Token(current, sb.toString());
		} else if (current == TokenType.NUMBER) {
			long value = isLong(sb.toString());
			token = new Token(current, value);
		} else {
			if(sb.length() == 1 && current == TokenType.SYMBOL) token = new Token(current, sb.charAt(0));
			else throw new LexerException("Unknown Character.");
		}
		return;
		
	}
	
	/**
	 *This private method is generating tokens when the assigned state is extended.
	 */
	private void extractNextTokenExtended() {
		
		if(token != null && token.getType() == TokenType.EOF) throw new LexerException("Can't generate tokens after EOF.");
		
		skipBlanks();
		
		if(currentIndex >= data.length) {
			token = new Token(TokenType.EOF, null);
			return;
		}
		
		StringBuilder sb = new StringBuilder();
		
		while(currentIndex < data.length) {
			if(data[currentIndex] == '#'  || 
	           data[currentIndex] == '\r' ||
			   data[currentIndex] == '\n' ||
			   data[currentIndex] == '\t' ||
			   data[currentIndex] == ' ') break;
			sb.append(data[currentIndex++]);
		}
		
		if(sb.isEmpty()) {
			if(data[currentIndex] == '#') this.state = LexerState.BASIC;
			token = new Token(TokenType.SYMBOL, data[currentIndex++]);
		} else {
			token = new Token(TokenType.WORD, sb.toString());
		}
		return;
	}
	
	//provjeri jel da parsirati u long
	/**
	 *This method is used to check if the String input is a Long.
	 *@param input String which we try to parse.
	 *@return True if String is Long, otherwise false.
	 */
	private long isLong(String number) {
		long value;
		try {
			value = Long.parseLong(number);
		} catch (NumberFormatException ex) {
			throw new LexerException("Cant parse number.");
		}
		return value;
		
	}
	
	//zanemari whitespace
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
}
