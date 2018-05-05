/**
* Initial code for the Scanner for the class project in COP5556 Programming Language Principles 
* at the University of Florida, Spring 2018.
* 
* This software is solely for the educational benefit of students 
* enrolled in the course during the Spring 2018 semester.  
* 
* This software, and any software derived from it,  may not be shared with others or posted to public web sites,
* either during the course or afterwards.
* 
*  @Beverly A. Sanders, 2018
*/

package cop5556sp18;

import java.util.ArrayList;
import java.util.Arrays;
//import java.util.HashMap;

public class Scanner {
	 
	@SuppressWarnings("serial")
	public static class LexicalException extends Exception {

		int pos;

		public LexicalException(String message, int pos) {
			super(message);
			this.pos = pos;
		}

		public int getPos() {
			return pos;
		}
	}

	public static enum Kind {
		IDENTIFIER, INTEGER_LITERAL, BOOLEAN_LITERAL, FLOAT_LITERAL,
		KW_Z/* Z */, KW_default_width/* default_width */, KW_default_height/* default_height */, 
		KW_width /* width */, KW_height /* height*/, KW_show/*show*/, KW_write /* write */, KW_to /* to */,
		KW_input /* input */, KW_from /* from */, KW_cart_x/* cart_x*/, KW_cart_y/* cart_y */, 
		KW_polar_a/* polar_a*/, KW_polar_r/* polar_r*/, KW_abs/* abs */, KW_sin/* sin*/, KW_cos/* cos */, 
		KW_atan/* atan */, KW_log/* log */, KW_image/* image */, KW_int/* int */, KW_float /* float */, 
		KW_boolean/* boolean */, KW_filename/* filename */, KW_red /* red */, KW_blue /* blue */, 
		KW_green /* green */, KW_alpha /* alpha*/, KW_while /* while */, KW_if /* if */, OP_ASSIGN/* := */, 
		OP_EXCLAMATION/* ! */, OP_QUESTION/* ? */, OP_COLON/* : */, OP_EQ/* == */, OP_NEQ/* != */, 
		OP_GE/* >= */, OP_LE/* <= */, OP_GT/* > */, OP_LT/* < */, OP_AND/* & */, OP_OR/* | */, 
		OP_PLUS/* +*/, OP_MINUS/* - */, OP_TIMES/* * */, OP_DIV/* / */, OP_MOD/* % */, OP_POWER/* ** */, 
		OP_AT/* @ */, LPAREN/*( */, RPAREN/* ) */, LSQUARE/* [ */, RSQUARE/* ] */, LBRACE /*{ */, 
		RBRACE /* } */, LPIXEL /* << */, RPIXEL /* >> */, SEMI/* ; */, COMMA/* , */, DOT /* . */, KW_sleep/*sleep*/,EOF;
	}

	/**
	 * Class to represent Tokens.
	 * 
	 * This is defined as a (non-static) inner class which means that each Token
	 * instance is associated with a specific Scanner instance. We use this when
	 * some token methods access the chars array in the associated Scanner.
	 * 
	 * @author Beverly Sanders
	 *
	 */
	public class Token {
		public final Kind kind;
		public final int pos; // position of first character of this token in the input. Counting starts at 0
								// and is incremented for every character.
		public final int length; // number of characters in this token

		public Token(Kind kind, int pos, int length) {
			super();
			this.kind = kind;
			this.pos = pos;
			this.length = length;
		}

		public String getText() {
			return String.copyValueOf(chars, pos, length);
		}

		/**
		 * precondition: This Token's kind is INTEGER_LITERAL
		 * 
		 * @returns the integer value represented by the token
		 */
		public int intVal() {
			assert kind == Kind.INTEGER_LITERAL;
			return Integer.valueOf(String.copyValueOf(chars, pos, length));
		}

		/**
		 * precondition: This Token's kind is FLOAT_LITERAL]
		 * 
		 * @returns the float value represented by the token
		 */
		public float floatVal() {
			assert kind == Kind.FLOAT_LITERAL;
			return Float.valueOf(String.copyValueOf(chars, pos, length));
		}

		/**
		 * precondition: This Token's kind is BOOLEAN_LITERAL
		 * 
		 * @returns the boolean value represented by the token
		 */
		public boolean booleanVal() {
			assert kind == Kind.BOOLEAN_LITERAL;
			return getText().equals("true");
		}

		/**
		 * Calculates and returns the line on which this token resides. The first line
		 * in the source code is line 1.
		 * 
		 * @return line number of this Token in the input.
		 */
		public int line() {
			return Scanner.this.line(pos) + 1;
		}

		/**
		 * Returns position in line of this token.
		 * 
		 * @param line.
		 *            The line number (starting at 1) for this token, i.e. the value
		 *            returned from Token.line()
		 * @return
		 */
		public int posInLine(int line) {
			return Scanner.this.posInLine(pos, line - 1) + 1;
		}

		/**
		 * Returns the position in the line of this Token in the input. Characters start
		 * counting at 1. Line termination characters belong to the preceding line.
		 * 
		 * @return
		 */
		public int posInLine() {
			return Scanner.this.posInLine(pos) + 1;
		}

		public String toString() {
			int line = line();
			return "[" + kind + "," + String.copyValueOf(chars, pos, length) + "," + pos + "," + length + "," + line
					+ "," + posInLine(line) + "]";
		}

		/**
		 * Since we override equals, we need to override hashCode, too.
		 * 
		 * See
		 * https://docs.oracle.com/javase/9/docs/api/java/lang/Object.html#hashCode--
		 * where it says, "If two objects are equal according to the equals(Object)
		 * method, then calling the hashCode method on each of the two objects must
		 * produce the same integer result."
		 * 
		 * This method, along with equals, was generated by eclipse
		 * 
		 */
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + getOuterType().hashCode();
			result = prime * result + ((kind == null) ? 0 : kind.hashCode());
			result = prime * result + length;
			result = prime * result + pos;
			return result;
		}

		/**
		 * Override equals so that two Tokens are equal if they have the same Kind, pos,
		 * and length.
		 * 
		 * This method, along with hashcode, was generated by eclipse.
		 */
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Token other = (Token) obj;
			if (!getOuterType().equals(other.getOuterType()))
				return false;
			if (kind != other.kind)
				return false;
			if (length != other.length)
				return false;
			if (pos != other.pos)
				return false;
			return true;
		}

		/**
		 * used in equals to get the Scanner object this Token is associated with.
		 * 
		 * @return
		 */
		private Scanner getOuterType() {
			return Scanner.this;
		}

	}// Token

	/**
	 * Array of positions of beginning of lines. lineStarts[k] is the pos of the
	 * first character in line k (starting at 0).
	 * 
	 * If the input is empty, the chars array will have one element, the synthetic
	 * EOFChar token and lineStarts will have size 1 with lineStarts[0] = 0;
	 */
	int[] lineStarts;

	int[] initLineStarts() {
		ArrayList<Integer> lineStarts = new ArrayList<Integer>();
		int pos = 0;

		for (pos = 0; pos < chars.length; pos++) {
			lineStarts.add(pos);
			char ch = chars[pos];
			while (ch != EOFChar && ch != '\n' && ch != '\r') {
				pos++;
				ch = chars[pos];
			}
			if (ch == '\r' && chars[pos + 1] == '\n') {
				pos++;
			}
		}
		// convert arrayList<Integer> to int[]
		return lineStarts.stream().mapToInt(Integer::valueOf).toArray();
	}

	int line(int pos) {
		int line = Arrays.binarySearch(lineStarts, pos);
		if (line < 0) {
			line = -line - 2;
		}
		return line;
	}

	public int posInLine(int pos, int line) {
		return pos - lineStarts[line];
	}

	public int posInLine(int pos) {
		int line = line(pos);
		return posInLine(pos, line);
	}

	/**
	 * Sentinal character added to the end of the input characters.
	 */
	static final char EOFChar = 128;

	/**
	 * The list of tokens created by the scan method.
	 */
	final ArrayList<Token> tokens;

	/**
	 * An array of characters representing the input. These are the characters from
	 * the input string plus an additional EOFchar at the end.
	 */
	final char[] chars;

	/**
	 * position of the next token to be returned by a call to nextToken
	 */
	private int nextTokenPos = 0;

	Scanner(String inputString) {
		int numChars = inputString.length();
		this.chars = Arrays.copyOf(inputString.toCharArray(), numChars + 1); // input string terminated with null char
		chars[numChars] = EOFChar;
		tokens = new ArrayList<Token>();
		lineStarts = initLineStarts();
	}




	 private enum State {START,HAVE_ZERO,HAVE_DIGIT,HAVE_IDENT,HAVE_EQUAL,HAVE_FLOATING};  //TODO:  this is incomplete

	 
	 //TODO: Modify this to deal with the entire lexical specification
	public Scanner scan() throws LexicalException {
		StringBuffer  string_ID = new StringBuffer ();
		int pos = 0;
		State state = State.START;
		int startPos = 0;
		while (pos < chars.length) {
			
			char ch = chars[pos];
			switch(state) {
				case START: {
					startPos = pos;
					switch (ch) 
					{
					
						case ' ':  
						{
							pos++;
						}
						break;
						case '\n': 
						{
							pos++;
						}
						break;
						
						case '\r': 
						{
							pos++; 
						}
						break;
						case '\t': 
						{
							pos++; 
						}
						break;
						case '\f': 
						{
							pos++; 
						}
						break;
						case EOFChar: 
						{
							tokens.add(new Token(Kind.EOF, startPos, 0));
							pos++; // next iteration will terminate loop
						}
						break;
						case ';': 
						{
							tokens.add(new Token(Kind.SEMI, startPos, pos - startPos + 1));
							pos++;
						}
						break;
						
						case ':':
						{	
							
							char nextch = chars[pos+1];
							
							if(nextch=='=') 
							{
								tokens.add(new Token(Kind.OP_ASSIGN, startPos, 2));
								pos+=2;
							}
							else 
							{
								tokens.add(new Token(Kind.OP_COLON, startPos, pos - startPos + 1));
								pos++;
							}
							
						}
						break;
						case '=' :
						{	
							state=State.HAVE_EQUAL;
							
						}
						
						break;
						case '!' :
						{
							char nextch = chars[pos+1];
							if(nextch=='=') 
							{
								tokens.add(new Token(Kind.OP_NEQ, startPos, 2));
								pos+=2;
							}
							else 
							{
								tokens.add(new Token(Kind.OP_EXCLAMATION, startPos, pos - startPos + 1));
								pos++;
							}
							
						}
						break;
						case '?' :
						{
							tokens.add(new Token(Kind.OP_QUESTION, startPos, pos - startPos + 1));
							pos++;
						}
						break;
						case '>' :
						{	
							char nextch = chars[pos+1];
							if(nextch=='=') 
							{
								tokens.add(new Token(Kind.OP_GE, startPos, 2));
								pos+=2;
							}
							else if(nextch=='>')
							{
								tokens.add(new Token(Kind.RPIXEL, startPos, 2));
								pos+=2;
							}
							else 
							{
								tokens.add(new Token(Kind.OP_GT, startPos, pos - startPos + 1));
								pos++;
							}
						}
						break;
						case '<' :
						{
							char nextch = chars[pos+1];
							if(nextch=='=')
							{
								tokens.add(new Token(Kind.OP_LE, startPos, 2));
								pos+=2;
							}
							else if(nextch=='<')
							{
								tokens.add(new Token(Kind.LPIXEL, startPos, 2));
								pos+=2;
							}
							else 
							{
								tokens.add(new Token(Kind.OP_LT, startPos, pos - startPos + 1));
								pos++;
							}
						}
						break;
						case '&' :
						{
							tokens.add(new Token(Kind.OP_AND, startPos, pos - startPos + 1));
							pos++;
						}
						break;
						case '|' :
						{
							tokens.add(new Token(Kind.OP_OR, startPos, pos - startPos + 1));
							pos++;
						}
						break;
						case '+' :
						{
							tokens.add(new Token(Kind.OP_PLUS, startPos, pos - startPos + 1));
							pos++;
						}
						break;
						case '-' :
						{
							tokens.add(new Token(Kind.OP_MINUS, startPos, pos - startPos + 1));
							pos++;
						}
						break;
						case '*' :
						{	
							char x = chars[pos+1];
							if(x=='*') 
							{
								tokens.add(new Token(Kind.OP_POWER, startPos, pos - startPos + 2));
								pos+=2;
							}
							else 
							{
								tokens.add(new Token(Kind.OP_TIMES, startPos, pos - startPos + 1));
								pos++;
							}
							
						}
						break;
						case '%' :
						{
							tokens.add(new Token(Kind.OP_MOD, startPos, pos - startPos + 1));	
							pos++;
						}
						break;
						case '@' :
						{
							tokens.add(new Token(Kind.OP_AT, startPos, pos - startPos + 1));
							pos++;
						}
						break;
						case '/' :
						{	
							int i=pos+1;
							char nextch = chars[i];
							if((nextch=='*')) {
								StringBuffer commentString=new StringBuffer();
								int commentflag=0;
								while(true) 
								{  
									i++;
									char comment = chars[i];
									commentString.append(comment);
									if(comment=='*') commentflag=1;
									if(commentflag==1&&comment=='/')break;
									System.out.print("i "+i+"chars.length"+chars.length+"\n");
									if(i>chars.length-2) 
									{	System.out.print("There should be */ \n");
										error(pos, line(i), posInLine(i), "illegal char"); 
										break;
									}
									
								}
								System.out.print("comment=/*"+commentString.toString()+"\n");
								pos=i+1;
							}
							else 
							{
								tokens.add(new Token(Kind.OP_DIV, startPos, pos - startPos + 1));
								pos++;
							}
							
						}
						break;	
						case '(' :
						{
							tokens.add(new Token(Kind.LPAREN, startPos, pos - startPos + 1));
							pos++;
						}
						break;
						case ')' :
						{
							tokens.add(new Token(Kind.RPAREN, startPos, pos - startPos + 1));
							pos++;
						}
						break;
						case '[' :
						{
							tokens.add(new Token(Kind.LSQUARE, startPos, pos - startPos + 1));
							pos++;
						}
						break;
						case ']' :
						{
							tokens.add(new Token(Kind.RSQUARE, startPos, pos - startPos + 1));
							pos++;
						}
						break;
						case '{' :
						{
							tokens.add(new Token(Kind.LBRACE, startPos, pos - startPos + 1));
							pos++;
						}
						break;
						case '}' :
						{
							tokens.add(new Token(Kind.RBRACE, startPos, pos - startPos + 1));
							pos++;
						}	
						break;
						case ',' :
						{
							tokens.add(new Token(Kind.COMMA, startPos, pos - startPos + 1));
							pos++;
						}
						break;
						case '.' :
						{	
							char nextch=chars[pos+1];
							if(Character.isDigit(nextch))
							state = State.HAVE_FLOATING; 
							else 
							{	
								tokens.add(new Token(Kind.DOT, startPos, 1));
								pos++;
							}
						}
						break;
						default: 
						{   
							if (Character.isDigit(ch)&&ch!='0') 
							{
								System.out.print("enter HAVEDIGIT");
								state = State.HAVE_DIGIT; 
								
							} 
							else if (ch=='0') 
							{	System.out.print("enter HAVE_ZERO");
								state = State.HAVE_ZERO; 
							} 
			            	else if (Character.isJavaIdentifierStart(ch)&&ch!='_'&&ch!='$')
			            	{	
			            		System.out.print("Character.isJavaIdentifierStart(ch)"+ch+"\n");
			            		state = State.HAVE_IDENT;
			            	} 
			            	else 
			            	{	
			            		System.out.print("There is no operator "+ch+"\n");
			            		error(pos, line(pos), posInLine(pos), "illegal char"); 
			            	}
						}
						break;
	
					}//end switch(ch)
				
				}
				break;
				case HAVE_ZERO:
				{
					int i=pos+1;
					char next=chars[pos+1];
					if(Character.isDigit(next)&& next!='.')
					{  System.out.print("the first if\n");
						tokens.add(new Token(Kind.INTEGER_LITERAL, startPos, pos - startPos+1));
						pos++;
				    	 state=State.START;
					}
					else if(next=='.') 
					{
						pos++;
						state=State.HAVE_FLOATING;
					}
					else
					{	
						
						while(true)
						{	
							
							char nextch=chars[i];
							i++;
							if(!Character.isDigit(nextch)) break;
							
						}
						System.out.print("startpos"+startPos+"the third if\n"+i+"\n");
						pos=i-1;
						tokens.add(new Token(Kind.INTEGER_LITERAL, startPos, pos-startPos));
				    	  state=State.START;
					}
				}
				break;
				case HAVE_DIGIT:
				{
					 
					  if (Character.isDigit(ch)) 
				      {  	
				          pos++;
				      } 
					  else if(ch=='.')
					  {
						  state=State.HAVE_FLOATING;
					  }
				      else 
				      {   
				    	  String string= String.copyValueOf(chars, startPos,pos - startPos);
						 int inte=Integer.valueOf(string);
						 if(inte>2147483647)error(pos, line(pos), posInLine(pos), "illegal char");
				    	  tokens.add(new Token(Kind.INTEGER_LITERAL, startPos, pos - startPos));
				    	  state=State.START;
				      }
				}
				break;
				case HAVE_FLOATING:{
					char nextch=chars[pos+1];
					System.out.print("chars[pos]"+chars[pos]+"chars[pos+1]"+chars[pos+1]+"\n");
					int i=pos;
					
						while(true) 
						{
							i++;
							char next=chars[i];
							System.out.print("see"+next+"\n");
							if( !Character.isDigit(next)) 
							{System.out.print("see"+next);
								
								
								break;
							}
							
						}
						pos=i;	
						String string= String.copyValueOf(chars, startPos,pos - startPos);
						float f=Float.parseFloat(string);
						if(f>3.4028235E38)error(pos, line(pos), posInLine(pos), "illegal char"); 
						tokens.add(new Token(Kind.FLOAT_LITERAL, startPos,pos - startPos));
			
				
					
				state=State.START;
				}
				break;
				case HAVE_EQUAL:{
					
					char nextch=chars[pos+1];
					if (nextch=='=') 
				     {  
						tokens.add(new Token(Kind.OP_EQ, startPos, pos - startPos+2));
						
						state=State.START;
				        pos+=2;
				     } 
					else
					{
						//System.out.print("There is no operator '='.\n");
						error(pos, line(pos), posInLine(pos), "illegal char"); 
						
					}
				
				}
				break;
				
				case HAVE_IDENT:
				{
					String string= String.copyValueOf(chars, startPos,pos - startPos);
					if(Character.isDigit(ch)||Character.isJavaIdentifierStart(ch)||ch=='$'||ch=='_')
					{
						pos++;
					}
					else
					{
						switch(string)
						{
							case "Z":tokens.add(new Token(Kind.KW_Z, startPos, pos - startPos));break;
							case "default_width": tokens.add(new Token(Kind.KW_default_width, startPos, pos - startPos));break;
							case "default_height": tokens.add(new Token(Kind.KW_default_height, startPos, pos - startPos));break;
							case "width": tokens.add(new Token(Kind.KW_width, startPos, pos - startPos));break; 
							case "height": tokens.add(new Token(Kind.KW_height, startPos, pos - startPos)); break;
							case "show": tokens.add(new Token(Kind.KW_show, startPos, pos - startPos));break;
							case "write": tokens.add(new Token(Kind.KW_write, startPos, pos - startPos));break; 
							case "to": tokens.add(new Token(Kind.KW_to, startPos, pos - startPos));break; 
							case "input": tokens.add(new Token(Kind.KW_input, startPos, pos - startPos));break; 
							case "from": tokens.add(new Token(Kind.KW_from, startPos, pos - startPos));break; 
							case "cart_x": tokens.add(new Token(Kind.KW_cart_x, startPos, pos - startPos));break; 
							case "cart_y": tokens.add(new Token(Kind.KW_cart_y, startPos, pos - startPos));break; 
							case "polar_a": tokens.add(new Token(Kind.KW_polar_a, startPos, pos - startPos));break;
							case "polar_r": tokens.add(new Token(Kind.KW_polar_r, startPos, pos - startPos));break;
							case "abs": tokens.add(new Token(Kind.KW_abs, startPos, pos - startPos)); break;
							case "sin": tokens.add(new Token(Kind.KW_sin, startPos, pos - startPos)); break;
							case "cos": tokens.add(new Token(Kind.KW_cos, startPos, pos - startPos));break;
							case "atan": tokens.add(new Token(Kind.KW_atan, startPos, pos - startPos)); break;
							case "log": tokens.add(new Token(Kind.KW_log, startPos, pos - startPos)); break;
							case "image": tokens.add(new Token(Kind.KW_image, startPos, pos - startPos)); break;
							case "int": tokens.add(new Token(Kind.KW_int, startPos, pos - startPos)); break;
							case "float": tokens.add(new Token(Kind.KW_float, startPos, pos - startPos));break; 
							case "boolean": tokens.add(new Token(Kind.KW_boolean, startPos, pos - startPos));break;
							case "filename": tokens.add(new Token(Kind.KW_filename, startPos, pos - startPos));break; 
							case "red": tokens.add(new Token(Kind.KW_red, startPos, pos - startPos ));break;
							case "blue": tokens.add(new Token(Kind.KW_blue, startPos, pos - startPos ));break;  
							case "green": tokens.add(new Token(Kind.KW_green, startPos, pos - startPos)); break;
							case "alpha": tokens.add(new Token(Kind.KW_alpha, startPos, pos - startPos ));break;  
							case "while": {tokens.add(new Token(Kind.KW_while, startPos, pos - startPos ));break; }
							case "if": tokens.add(new Token(Kind.KW_if, startPos, pos - startPos ));break;
							case "sleep": tokens.add(new Token(Kind.KW_sleep, startPos, pos - startPos ));break;
							case "true": tokens.add(new Token(Kind.BOOLEAN_LITERAL, startPos, pos - startPos)); break;
							case "false": tokens.add(new Token(Kind.BOOLEAN_LITERAL, startPos, pos - startPos));break; 
							default:tokens.add(new Token(Kind.IDENTIFIER, startPos, pos - startPos ));break;
						}
						string="";
						state=State.START;
					}
				}
				break;
				default: {
					error(pos, 0, 0, "undefined state");
				}
			}// switch state
		} // while
			
		return this;
	}


	private void error(int pos, int line, int posInLine, String message) throws LexicalException {
		String m = (line + 1) + ":" + (posInLine + 1) + " " + message;
		throw new LexicalException(m, pos);
	}

	/**
	 * Returns true if the internal iterator has more Tokens
	 * 
	 * @return
	 */
	public boolean hasTokens() {
		return nextTokenPos < tokens.size();
	}

	/**
	 * Returns the next Token and updates the internal iterator so that the next
	 * call to nextToken will return the next token in the list.
	 * 
	 * It is the callers responsibility to ensure that there is another Token.
	 * 
	 * Precondition: hasTokens()
	 * 
	 * @return
	 */
	public Token nextToken() {
		return tokens.get(nextTokenPos++);
	}

	/**
	 * Returns the next Token, but does not update the internal iterator. This means
	 * that the next call to nextToken or peek will return the same Token as
	 * returned by this methods.
	 * 
	 * It is the callers responsibility to ensure that there is another Token.
	 * 
	 * Precondition: hasTokens()
	 * 
	 * @return next Token.
	 */
	public Token peek() {
		return tokens.get(nextTokenPos);
	}

	/**
	 * Resets the internal iterator so that the next call to peek or nextToken will
	 * return the first Token.
	 */
	public void reset() {
		nextTokenPos = 0;
	}

	/**
	 * Returns a String representation of the list of Tokens and line starts
	 */
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("Tokens:\n");
		for (int i = 0; i < tokens.size(); i++) {
			sb.append(tokens.get(i)).append('\n');
		}
		sb.append("Line starts:\n");
		for (int i = 0; i < lineStarts.length; i++) {
			sb.append(i).append(' ').append(lineStarts[i]).append('\n');
		}
		return sb.toString();
	}

}
