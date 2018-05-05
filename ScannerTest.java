 /**
 * JUunit tests for the Scanner for the class project in COP5556 Programming Language Principles 
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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import cop5556sp18.Scanner.LexicalException;
import cop5556sp18.Scanner.Token;
import static cop5556sp18.Scanner.Kind.*;

public class ScannerTest {

	//set Junit to be able to catch exceptions
	@Rule
	public ExpectedException thrown = ExpectedException.none();

	
	//To make it easy to print objects and turn this output on and off
	static boolean doPrint = true;
	private void show(Object input) {
		if (doPrint) {
			System.out.println(input.toString());
		}
	}

	/**
	 *Retrieves the next token and checks that it is an EOF token. 
	 *Also checks that this was the last token.
	 *
	 * @param scanner
	 * @return the Token that was retrieved
	 */
	
	Token checkNextIsEOF(Scanner scanner) {
		Scanner.Token token = scanner.nextToken();
		assertEquals(Scanner.Kind.EOF, token.kind);
		assertFalse(scanner.hasTokens());
		return token;
	}


	/**
	 * Retrieves the next token and checks that its kind, position, length, line, and position in line
	 * match the given parameters.
	 * 
	 * @param scanner
	 * @param kind
	 * @param pos
	 * @param length
	 * @param line
	 * @param pos_in_line
	 * @return  the Token that was retrieved
	 */
	Token checkNext(Scanner scanner, Scanner.Kind kind, int pos, int length, int line, int pos_in_line) {
		Token t = scanner.nextToken();
		assertEquals(kind, t.kind);
		assertEquals(pos, t.pos);
		assertEquals(length, t.length);
		assertEquals(line, t.line());
		assertEquals(pos_in_line, t.posInLine())
		;
		return t;
	}

	/**
	 * Retrieves the next token and checks that its kind and length match the given
	 * parameters.  The position, line, and position in line are ignored.
	 * 
	 * @param scanner
	 * @param kind
	 * @param length
	 * @return  the Token that was retrieved
	 */
	Token checkNext(Scanner scanner, Scanner.Kind kind, int length) {
		Token t = scanner.nextToken();
		assertEquals(kind, t.kind);
		assertEquals(length, t.length);
		return t;
	}
	


	/**
	 * Simple test case with an empty program.  The only Token will be the EOF Token.
	 *   
	 * @throws LexicalException
	 */

	/**
	 * Test illustrating how to put a new line in the input program and how to
	 * check content of tokens.
	 * 
	 * Because we are using a Java String literal for input, we use \n for the
	 * end of line character. (We should also be able to handle \n, \r, and \r\n
	 * properly.)
	 * 
	 * Note that if we were reading the input from a file, the end of line 
	 * character would be inserted by the text editor.
	 * Showing the input will let you check your input is 
	 * what you think it is.
	 * 
	 * @throws LexicalException
	 */

	
	/**
	 * This example shows how to test that your scanner is behaving when the
	 * input is illegal.  In this case, we are giving it an illegal character '~' in position 2
	 * 
	 * The example shows catching the exception that is thrown by the scanner,
	 * looking at it, and checking its contents before rethrowing it.  If caught
	 * but not rethrown, then JUnit won't get the exception and the test will fail.  
	 * 
	 * The test will work without putting the try-catch block around 
	 * new Scanner(input).scan(); but then you won't be able to check 
	 * or display the thrown exception.
	 * 
	 * @throws LexicalException
	 */


	
	/**
	 * This example shows how to test that your scanner is behaving when the
	 * input is illegal.  In this case, we are giving it an illegal character '~' in position 2
	 * 
	 * The example shows catching the exception that is thrown by the scanner,
	 * looking at it, and checking its contents before rethrowing it.  If caught
	 * but not rethrown, then JUnit won't get the exception and the test will fail.  
	 * 
	 * The test will work without putting the try-catch block around 
	 * new Scanner(input).scan(); but then you won't be able to check 
	 * or display the thrown exception.
	 * 
	 * @throws LexicalException
	 */
	
	@Test
	public void testEmpty() throws LexicalException {
		String input = "";  //The input is the empty string.  This is legal
		show(input);        //Display the input 
		Scanner scanner = new Scanner(input).scan();  //Create a Scanner and initialize it
		show(scanner);   //Display the Scanner
		checkNextIsEOF(scanner);  //Check that the only token is the EOF token.
	}
	
	/**
	 * Test illustrating how to put a new line in the input program and how to
	 * check content of tokens.
	 * 
	 * Because we are using a Java String literal for input, we use \n for the
	 * end of line character. (We should also be able to handle \n, \r, and \r\n
	 * properly.)
	 * 
	 * Note that if we were reading the input from a file, the end of line 
	 * character would be inserted by the text editor.
	 * Showing the input will let you check your input is 
	 * what you think it is.
	 * 
	 * @throws LexicalException
	 */
	@Test
	public void testSemi() throws LexicalException {
		String input = ";;\n;;";
		Scanner scanner = new Scanner(input).scan();
		show(input);
		show(scanner);
		checkNext(scanner, SEMI, 0, 1, 1, 1);
		checkNext(scanner, SEMI, 1, 1, 1, 2);
		checkNext(scanner, SEMI, 3, 1, 2, 1);
		checkNext(scanner, SEMI, 4, 1, 2, 2);
		checkNextIsEOF(scanner);
	}
	

	
	/**
	 * This example shows how to test that your scanner is behaving when the
	 * input is illegal.  In this case, we are giving it an illegal character '~' in position 2
	 * 
	 * The example shows catching the exception that is thrown by the scanner,
	 * looking at it, and checking its contents before rethrowing it.  If caught
	 * but not rethrown, then JUnit won't get the exception and the test will fail.  
	 * 
	 * The test will work without putting the try-catch block around 
	 * new Scanner(input).scan(); but then you won't be able to check 
	 * or display the thrown exception.
	 * 
	 * @throws LexicalException
	 */
	@Test
	public void failIllegalChar() throws LexicalException {
		String input = ";;~";
		show(input);
		thrown.expect(LexicalException.class);  //Tell JUnit to expect a LexicalException
		try {
			new Scanner(input).scan();
		} catch (LexicalException e) {  //Catch the exception
			show(e);                    //Display it
			assertEquals(2,e.getPos()); //Check that it occurred in the expected position
			throw e;                    //Rethrow exception so JUnit will see it
		}
	}

	@Test
	public void floatoverflow() throws LexicalException {
		String input = "1234567890123456789012345678901234567890123456789012345678901234567890.123456789123456789123456789123456789";
		show(input);
		thrown.expect(LexicalException.class);
		try {
			new Scanner(input).scan();
		}catch(LexicalException e) {
			show(e);
			//System.out.print(e.getPos());
			assertEquals(0,e.getPos());
			throw e;
		}
	}
	@Test
	public void integeroverflow() throws LexicalException {
		String input = "1234567890123456789012345678901234567890123456789012345678901234567890";
		show(input);
		thrown.expect(LexicalException.class);
		try {
			new Scanner(input).scan();
		}catch(LexicalException e) {
			show(e);
			//System.out.print(e.getPos());
			assertEquals(0,e.getPos());
			throw e;
		}
	}
	@Test 
	public void comment() throws LexicalException{	
		String input = "09.430.3/* mycomment*/ **/";                     
		Scanner scanner = new Scanner(input).scan(); 	
		show(input);                                 
		show(scanner);  
		checkNext(scanner, INTEGER_LITERAL,0,1,1, 1); 
		checkNext(scanner, FLOAT_LITERAL,1,5,1,2); 
		checkNext(scanner, FLOAT_LITERAL,6,2,1,7); 
		checkNext(scanner, OP_POWER,23,2,1,24); 
		checkNext(scanner, OP_DIV,25,1,1,26); 
		
		checkNextIsEOF(scanner);                     
	}   
	@Test
	public void wrongcomment() throws LexicalException {
		String input = "!@#*****/*** /* wrongcomment";
		show(input);
		thrown.expect(LexicalException.class);
		try {
			new Scanner(input).scan();
		}catch(LexicalException e) {
			show(e);
			assertEquals(26,e.getPos());
			throw e;
		}
	}
	@Test
	public void operator() throws LexicalException{	
		String input = "==!=>+-*/@>>&| !:=?:<=>=<";                     
		Scanner scanner = new Scanner(input).scan(); 	
		show(input);                                 
		show(scanner);                               
		checkNext(scanner, OP_EQ,0,2,1,1); 
		checkNext(scanner, OP_NEQ,2,2,1,3);
		checkNext(scanner, OP_GT,4,1,1,5); 
		checkNext(scanner, OP_PLUS,5,1,1,6); 
		checkNext(scanner, OP_MINUS,6,1,1,7); 
		checkNext(scanner, OP_TIMES,7,1,1,8); 
		checkNext(scanner, OP_DIV,8,1,1,9); 
		checkNext(scanner, OP_AT,9,1,1,10); 
		checkNext(scanner, RPIXEL,10,2,1,11); 
		checkNext(scanner, OP_AND,12,1,1,13); 
		checkNext(scanner, OP_OR,13,1,1,14); 
		checkNext(scanner,OP_EXCLAMATION,15,1,1,16); 
		checkNext(scanner, OP_ASSIGN,16,2,1,17); 
		checkNext(scanner, OP_QUESTION,18,1,1,19); 
		checkNext(scanner, OP_COLON,19,1,1,20); 
		checkNext(scanner, OP_LE,20,2,1,21); 
		checkNext(scanner, OP_GE,22,2,1,23); 
		checkNext(scanner, OP_LT,24,1,1,25); 

		checkNextIsEOF(scanner);                     
	} 
	@Test
	public void digit() throws LexicalException {
		String input = "1234.554...6600.30.090";
		Scanner scanner = new Scanner(input).scan();
		show(input);
		show(scanner);
		checkNext(scanner, FLOAT_LITERAL, 0,8,1,1);
		checkNext(scanner, DOT, 8,1,1,9);
		checkNext(scanner, DOT, 9,1,1,10);
		checkNext(scanner, FLOAT_LITERAL, 10,5,1,11);
		checkNext(scanner, FLOAT_LITERAL, 15,3,1,16);
		checkNext(scanner, FLOAT_LITERAL, 18,4,1,19);
		checkNextIsEOF(scanner);
	}
	@Test     
	public void digit2() throws LexicalException{	
		String input = "00.0020.1000.001.0.0. 044";                     
		Scanner scanner = new Scanner(input).scan(); 	
		show(input);                                 
		show(scanner);                               
		checkNext(scanner, INTEGER_LITERAL,0,1,1,1); 
		checkNext(scanner, FLOAT_LITERAL,1,6,1,2);
		checkNext(scanner, FLOAT_LITERAL,7,5,1,8); 
		checkNext(scanner, FLOAT_LITERAL,12,4,1,13);
		checkNext(scanner, FLOAT_LITERAL,16,2,1,17); 
		checkNext(scanner, FLOAT_LITERAL,18,2,1,19);
		checkNext(scanner, DOT,20,1,1,21); 
		checkNext(scanner, INTEGER_LITERAL,22,1,1,23); 
		checkNext(scanner, INTEGER_LITERAL,23,2,1,24); 
		checkNextIsEOF(scanner);                     
	}  
	
	
	
	@Test
	public void keyword() throws LexicalException {
		String input = "reedread write )()input from cart_x abs sin atan Z";
		Scanner scanner = new Scanner(input).scan();
		show(input);
		show(scanner);
		checkNext(scanner, IDENTIFIER, 0,8,1,1);
		checkNext(scanner, KW_write, 9,5,1,10);
		checkNext(scanner, RPAREN, 15,1,1,16);
		checkNext(scanner, LPAREN, 16,1,1,17);
		checkNext(scanner, RPAREN, 17,1,1,18);
		checkNext(scanner, KW_input, 18,5,1,19);
		checkNext(scanner, KW_from, 24,4,1,25);
		checkNext(scanner, KW_cart_x, 29,6,1,30);
		checkNext(scanner, KW_abs, 36,3,1,37);
		checkNext(scanner, KW_sin,40,3,1,41);
		checkNext(scanner, KW_atan, 44,4,1,45);
		checkNext(scanner, KW_Z,49,1,1,50);
		checkNextIsEOF(scanner);
	}
	@Test
	public void trueorfalse() throws LexicalException {
		String input = "true or false";
		Scanner scanner = new Scanner(input).scan();
		show(input);
		show(scanner);
		checkNext(scanner, BOOLEAN_LITERAL, 0, 4, 1, 1);
		checkNext(scanner,IDENTIFIER, 5,2,1,6);
		checkNext(scanner, BOOLEAN_LITERAL, 8,5,1,9);
		checkNextIsEOF(scanner);
	}
	
	@Test
	public void abcint() throws LexicalException {
		String input = "if ggg4";
		Scanner scanner = new Scanner(input).scan();
		show(input);
		show(scanner);
	
	}

	
}
	

