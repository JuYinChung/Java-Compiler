package cop5556sp18;
/* *
 * Initial code for SimpleParser for the class project in COP5556 Programming Language Principles 
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

import java.util.List;
import cop5556sp18.Scanner.Token;
import cop5556sp18.AST.ASTNode;
import cop5556sp18.AST.Program;
import cop5556sp18.AST.Block;
import cop5556sp18.AST.LHS;
import cop5556sp18.AST.LHSPixel;
import cop5556sp18.AST.PixelSelector;
import cop5556sp18.AST.LHSIdent;
import cop5556sp18.AST.LHSSample;
import cop5556sp18.AST.Block;
import cop5556sp18.AST.Declaration;
import cop5556sp18.AST.Expression;
import cop5556sp18.AST.Statement;
import cop5556sp18.AST.StatementWrite;
import cop5556sp18.AST.StatementWhile;
import cop5556sp18.AST.StatementAssign;
import cop5556sp18.AST.StatementIf;
import cop5556sp18.AST.StatementInput;
import cop5556sp18.AST.StatementShow;
import cop5556sp18.AST.StatementSleep;
import cop5556sp18.AST.ExpressionBinary;
import cop5556sp18.AST.ExpressionBooleanLiteral;
import cop5556sp18.AST.ExpressionConditional;
import cop5556sp18.AST.ExpressionFloatLiteral;
import cop5556sp18.AST.ExpressionFunctionAppWithExpressionArg;
import cop5556sp18.AST.ExpressionFunctionAppWithPixel;
import cop5556sp18.AST.ExpressionIdent;
import cop5556sp18.AST.ExpressionIntegerLiteral;
import cop5556sp18.AST.ExpressionPixel;
import cop5556sp18.AST.ExpressionPixelConstructor;
import cop5556sp18.AST.ExpressionPredefinedName;
import cop5556sp18.AST.ExpressionUnary;
import cop5556sp18.Parser.SyntaxException;
import cop5556sp18.Scanner.Kind;
import static cop5556sp18.Scanner.Kind.*;


import java.util.ArrayList;

import org.w3c.dom.Node;


public class Parser {
	
	@SuppressWarnings("serial")
	public static class SyntaxException extends Exception {
		Token t;
//		int pos=t.pos;
		
		public SyntaxException(Token t, String message) {
			
			super(message);
			
			this.t = t;
		}

	}



	Scanner scanner;
	Token t;
    static int pos;
	Parser(Scanner scanner) {
		this.scanner = scanner;
		t = scanner.nextToken();
		pos=t.pos;
	}


	public Program parse() throws SyntaxException {
		//program();
		//matchEOF();
		return program();
	}


	
	/*
	 * Block ::=  { (  (Declaration | Statement) ; )* }
	 */
	
	Kind[] firstDec = { KW_int, KW_boolean, KW_image, KW_float, KW_filename };
	Kind[] firstStatement = {KW_input,KW_write,IDENTIFIER,KW_while,KW_if,KW_show,KW_sleep };
	Kind[] FunctionName  = { KW_sin ,KW_cos ,KW_atan ,KW_abs ,KW_log ,KW_cart_x ,KW_cart_y ,KW_polar_a ,KW_polar_r 
			,KW_int ,KW_float ,KW_width ,KW_height ,KW_red , KW_green, KW_blue , KW_alpha  };
	Kind[] PredefinedName  = {KW_Z ,KW_default_height ,KW_default_width };
	Kind[] Color   = {  KW_red , KW_green, KW_blue , KW_alpha };
	//Program ::= Identifier Block
	public Program program() throws SyntaxException {
		Token id = t;
		Block b = null;
		Program p = null;
		//match(IDENTIFIER);
		consume();
		b = block();
		p = new Program(t,id,b);
		return p;
	}
	//Block ::=  { (  (Declaration | Statement) ; )* }
	public Block block() throws SyntaxException {
		System.out.print("   block");
		ASTNode declare = null;
		ASTNode s = null;
		Block b = null;
		List<ASTNode> list = new ArrayList<ASTNode>();
		match(LBRACE);
		while (isKind(firstDec)|isKind(firstStatement)|isKind(Color)) 
		{
		     if (isKind(firstDec)) 
		     {
				declare = declaration();
				list.add(declare);
				//System.out.print("list::"+list.toArray().toString()+"\n");
				match(SEMI);
				b = new Block(t,list);
				
		     }
		     else if (isKind(firstStatement)|isKind(Color)) 
		     {
				s = (ASTNode)statement();
				list.add(s);
				match(SEMI);
				b = new Block(t,list);
			 }
				
		}
	   
		match(RBRACE);
		return b; 

	}
	//Declaration ::= Type IDENTIFIER | image IDENTIFIER [ Expression , Expression ]
	public ASTNode declaration() throws SyntaxException{
		//TODO
		System.out.print("   declaration");
		ASTNode node = null;
		Expression a = null;
		Expression b = null;
		if(isKind(KW_int)|isKind(KW_boolean)|isKind(KW_float)|isKind(KW_filename)) 
		{
			Token type=t;
			consume();
			Token id=t;
			consume();
			//System.out.print("@@"+type.getText()+"##"+id.getText()+"\n");
			node = new Declaration(t,type, id,a,b);
		}
		else if(isKind(KW_image))
		{
			Token type=t;
			consume();
			if(isKind(IDENTIFIER)) 
			{
				Token id=t;
				consume();
				System.out.print(type.toString());
				System.out.print(id.toString());
				if(isKind(LSQUARE)) 
				{
					match(LSQUARE);
					a = expression();
					match(COMMA);
					b = expression();
					match(RSQUARE);
				}
				node = new Declaration(t,type, id,a,b);
			}
			else throw new SyntaxException(t,"Syntaxerror");
			
			
		}
		
		else throw new SyntaxException(t,"Syntaxerror");
		//System.out.print(id.toString());
		return node;
	}
	// StatementInput | StatementWrite | StatementAssignment 
	//| StatementWhile | StatementIf | StatementShow | StatementSleep	
	public Statement statement() throws SyntaxException{
		//TODO
		System.out.print("   statement");
		Statement s = null;
		if(isKind(KW_input)) 
		{
			s = StatementInput();
		}
		else if(isKind(KW_write)) 
		{
			s = StatementWrite();
		}
		else if(isKind(IDENTIFIER)|isKind(Color)) 
		{
			s = StatementAssignment();
		}
		else if(isKind(KW_while)) 
		{
			s = StatementWhile();
		}
		else if(isKind(KW_if)) 
		{
			s = StatementIf();
		}
		else if(isKind(KW_show)) 
		{
			s = StatementShow();
		}
		else if(isKind(KW_sleep))
		{
			s = StatementSleep();
		}
		else throw new SyntaxException(t,"Syntaxerror");
		return s;
	}
	//StatementInput ::= input IDENTIFIER from @ Expression
	public Statement StatementInput() throws SyntaxException{
		//TODO
		System.out.print("   StatementInput");
		Statement s = null;
		Expression e = null;
		if(isKind(KW_input))
		{
			match(KW_input);
			//match(IDENTIFIER);
			Token id= t;
			consume();
			match(KW_from);
			match(OP_AT);
			e = expression();
			
			s = new StatementInput(t, id , e);
		}
		else throw new SyntaxException(t,"Syntaxerror");
		return s;
	}
	//StatementWrite ::= write IDENTIFIER to IDENTIFIER

	public Statement StatementWrite() throws SyntaxException{
		//TODO
		System.out.print("   StatementWrite");
		Statement s = null;
		if(isKind(KW_write))
		{
		    consume();
			Token op = t;
			
			match(IDENTIFIER);
			if(isKind(KW_to)) 
			{
				consume();
				Token op1 = t;
				
				match(IDENTIFIER);
				s =  new StatementWrite(t, op,op1);
			}
			else  throw new SyntaxException(t,"Syntaxerror");
			
		}
		else throw new SyntaxException(t,"Syntaxerror");
		return s;
	}
	//StatementAssignment ::=  LHS := Expression
	public Statement StatementAssignment() throws SyntaxException{
		//TODO
		System.out.print("   StatementAssignment");
		Expression e=null;
		LHS lhs=null;
		Statement s=null;
		lhs = LHS();
		match(OP_ASSIGN);
		e = expression();
		return s = new StatementAssign(t,lhs,e);
		
	}
	//LHS ::=  IDENTIFIER | IDENTIFIER PixelSelector | Color ( IDENTIFIER PixelSelector )
	public LHS LHS() throws SyntaxException{
		//TODO
		LHS lhs = null;
		ASTNode pixelselector = null;
		System.out.print("   LHS");
		if(isKind(IDENTIFIER))
		{	
			Token id = t;
			consume();
			lhs =new LHSIdent(t,id);
			//match(IDENTIFIER);
			if(isKind(LSQUARE)) 
			{
				//match(LSQUARE);
				pixelselector = PixelSelector();
				lhs= new LHSPixel(t,id,(PixelSelector)pixelselector);
			}
			
		}
		else if(isKind(Color))
		{
			Token color=t;
			consume();
			if(isKind(KW_red))match(KW_red);
			else if( isKind(KW_green))match(KW_green);
			else if(isKind(KW_blue ))match(KW_blue);
			else if(isKind(KW_alpha))match(KW_alpha);
			match(LPAREN);
			//match(IDENTIFIER);
			Token id=t;
			consume();
			pixelselector = PixelSelector();
			match(RPAREN);
			lhs = new LHSSample(t, id, (PixelSelector)pixelselector,color);
		}
		else throw new SyntaxException(t,"Syntaxerror");
		return lhs;
	}
	//StatementWhile ::=  while (Expression ) Block

	public Statement StatementWhile() throws SyntaxException{
		//TODO
		System.out.print("   StatementWhile");
		Expression guard=null;
		Block b=null;
		Statement s=null;
		if(isKind(KW_while))
		{
			match(KW_while);
			match(LPAREN);
			guard = expression();
			match(RPAREN);
			b = block();
			s= new StatementWhile(t,guard,b);
		}
		else throw new SyntaxException(t,"Syntaxerror");
		return s;
	}
	//StatementIf ::=  if ( Expression ) Block 

	public Statement StatementIf() throws SyntaxException{
		//TODO
		System.out.print("   StatementIf");
		Expression guard=null;
		Block b=null;
		Statement s=null;
		if(isKind(KW_if))
		{
			match(KW_if);
			match(LPAREN);
			guard = expression();
			match(RPAREN);
			b = block();
			s = new StatementIf(t,guard,b);
		}
		else throw new SyntaxException(t,"Syntaxerror");
		return s;
	}
	//StatementShow ::=  show Expression

	public Statement StatementShow() throws SyntaxException{
		//TODO
		System.out.print("   StatementShow");
		Expression e=null;
		Statement s=null;
		if(isKind(KW_show)) 
		{
			match(KW_show);
			e = expression();
			s= new StatementShow(t,e);
		}
		else throw new SyntaxException(t,"Syntaxerror");
		return s;
	}
	//StatementSleep ::=  sleep Expression

	public Statement StatementSleep() throws SyntaxException{
		//TODO
		System.out.print("   StatementSleep");
		Expression duration;
		Statement s=null;
		if(isKind(KW_sleep)) 
		{
			match(KW_sleep);
			duration = expression();
			s= new StatementSleep(t,duration);
		}
		else throw new SyntaxException(t,"Syntaxerror");
		return s;
	}
	//Expression ::=  OrExpression  ?  Expression  :  Expression | OrExpression
	public Expression expression () throws SyntaxException {
		//TODO
		System.out.print("   Expression");
		Expression guard = null;
		Expression trueExpression = null;
		Expression falseExpression = null;
		guard = OrExpression();
		if(isKind(OP_QUESTION)) 
		{
			match(OP_QUESTION);
			trueExpression = expression();
			match(OP_COLON);
			falseExpression = expression();
			guard = new ExpressionConditional(t,guard,trueExpression,falseExpression);
			
		}
		
		return guard;
		
		//throw new UnsupportedOperationException();
	}
	
	//OrExpression  ::=  AndExpression   (  |  AndExpression ) *
	public Expression OrExpression () throws SyntaxException {
		//TODO
		System.out.print("   OrExpression");
		Expression leftnode = null;
		Expression rightnode = null;
		leftnode = AndExpression();
		while(isKind(OP_OR)) 
		{
			Token op = t;
			consume();
			//match(OP_OR);
			rightnode = AndExpression();
			leftnode =new ExpressionBinary(t,leftnode,op,rightnode);
		}
		return leftnode;		
	}
	//AndExpression ::=  EqExpression ( & EqExpression )*
	public Expression AndExpression () throws SyntaxException {
		//TODO
		System.out.print("   AndExpression");
		Expression leftnode = null;
		Expression rightnode = null;
		leftnode = EqExpression();
		while(isKind(OP_AND)) 
		{
			
			//match(OP_AND);
			Token op = t;
			consume();
			rightnode = EqExpression();
			leftnode =new ExpressionBinary(t,leftnode,op,rightnode);
		}
		return leftnode;
	}
	//EqExpression ::=  RelExpression  (  (== | != )  RelExpression )*
	public  Expression  EqExpression () throws SyntaxException {
		//TODO
		System.out.print("   EqExpression");
		Expression leftnode = null;
		Expression rightnode = null;
		leftnode = RelExpression();
		while(isKind(OP_EQ)|isKind(OP_NEQ)) 
		{
			Token op = t;
			consume();
			rightnode = RelExpression();
			leftnode =new ExpressionBinary(t,leftnode,op,rightnode);
		}
		return leftnode;
	}
	//RelExpression ::= AddExpression (  (<  | > |  <=  | >= )   AddExpression)*
	public Expression RelExpression  () throws SyntaxException {
		//TODO
		System.out.print("   RelExpression");
		Expression leftnode = null;
		Expression rightnode = null;
		leftnode=AddExpression();
		while(isKind(OP_LT)|isKind(OP_GT)|isKind(OP_LE)|isKind(OP_GE)) 
		{
			Token op = t;
			consume();
			rightnode =  AddExpression();
			leftnode =new ExpressionBinary(t,leftnode,op,rightnode);
		}
		return leftnode;
		
	}
	//AddExpression ::= MultExpression   (  ( + | - ) MultExpression )*
	public Expression AddExpression  () throws SyntaxException {
		//TODO
		System.out.print("   AddExpression");
		Expression leftnode = null;
		Expression rightnode = null;
		leftnode = MultExpression();
		while(isKind(OP_PLUS)|isKind(OP_MINUS)) 
		{
			Token op = t;
			consume();
			rightnode = MultExpression();
			leftnode =new ExpressionBinary(t,leftnode,op,rightnode);
		}
		return leftnode;
	}
	//MultExpression := PowerExpression ( ( * | /  | % ) PowerExpression )*
	public Expression MultExpression  () throws SyntaxException {
		//TODO
		System.out.print("   MultExpression");
		Expression leftnode = null;
		Expression rightnode = null;
		leftnode = PowerExpression();
		while(isKind(OP_TIMES)|isKind(OP_DIV)|isKind(OP_MOD)) 
		{
			Token op = t;
			consume();
			rightnode = PowerExpression();
			leftnode =new ExpressionBinary(t,leftnode,op,rightnode);
		}
		return leftnode;
	}
	
	//PowerExpression := UnaryExpression  (** PowerExpression | Îµ)
	public Expression PowerExpression   () throws SyntaxException {
		//TODO
		System.out.print("   PowerExpression");
		Expression leftnode;
		Expression rightnode;
		
		leftnode = UnaryExpression();
		if(isKind(OP_POWER)) 
		{
			Token op = t;
			consume();
			if(!isKind(EOF)) 
			{
				rightnode = PowerExpression();
				leftnode = new ExpressionBinary(t,leftnode,op,rightnode);
			}
		}
		return leftnode;
	}
	//UnaryExpression ::= + UnaryExpression | - UnaryExpression | UnaryExpressionNotPlusMinus
//	public Expression UnaryExpression() throws SyntaxException {
//		//TODO
//		System.out.print("   UnaryExpression");
//		Expression e = null;
//		Expression unary = null;
//		if(isKind(OP_PLUS)||isKind(OP_MINUS))
//		{
//			Token op = t;
//			consume();
//			e = UnaryExpression();
//			unary = new ExpressionUnary(t, op, e);
//			return unary;
//		}
//		else if(isKind(OP_EXCLAMATION)||isKind(INTEGER_LITERAL)||isKind(BOOLEAN_LITERAL)||isKind(FLOAT_LITERAL)||isKind(IDENTIFIER)||isKind(FunctionName)||isKind(PredefinedName)||isKind(LPAREN)||isKind(LPIXEL))
//		{
//			Token op = t;
//			e = UnaryExpressionNotPlusMinus();
//			//unary = new ExpressionUnary(t, op, e);
//			return e;
//		}
//		return unary;
//		
//	}
//	//UnaryExpressionNotPlusMinus ::=  ! UnaryExpression  | Primary
//	public Expression UnaryExpressionNotPlusMinus   () throws SyntaxException {
//		//TODO
//		System.out.print("   UnaryExpressionNotPlusMinus");
//		Expression unary = null;
//		Expression e = null;
//		Token op = t;
//		
//		if(isKind(OP_EXCLAMATION))
//		{   
//			System.out.print(" \n");
//			consume();
//			e = UnaryExpression();
//			unary = new ExpressionUnary(t, op, e);
//			return unary;
//		}
//		else if(isKind(INTEGER_LITERAL)||isKind(BOOLEAN_LITERAL)||isKind(FLOAT_LITERAL)||isKind(IDENTIFIER)||isKind(FunctionName)||isKind(PredefinedName)||isKind(LPAREN)||isKind(LPIXEL))
//		{
//			
//			e = Primary();
//			return e;
//			//consume();
//		}
//		else throw new SyntaxException(t,"Syntaxerror");
//		
//	}
	 Expression UnaryExpression() throws SyntaxException {
			Token first = t;
			if (isKind(OP_PLUS)) {  //throw away the plus here
				Token op = consume();
				Expression e = UnaryExpression();
				return new ExpressionUnary(first, op, e);
			}
			else if (isKind(OP_MINUS)){
				Token op = consume();
				Expression e = UnaryExpression();
				return new ExpressionUnary(first, op, e);
			}
			else {
				return UnaryExpressionNotPlusMinus();
			}
		}

		
		/**
		UnaryExpressionNotPlusMinus ::=  ! UnaryExpression  | Primary 

		 */
		
		 Expression UnaryExpressionNotPlusMinus() throws SyntaxException {
			Token first = t;
			if (isKind(OP_EXCLAMATION)) {
				Token op = consume();
				Expression e = UnaryExpression();
				return new ExpressionUnary(first,op,e);
			} else 	{
				return Primary(); //errors will be reported by primary()
			}
		}
		

	//Primary ::= INTEGER_LITERAL | BOOLEAN_LITERAL | FLOAT_LITERAL |  ( Expression ) | 
	//FunctionApplication  | IDENTIFIER | PixelExpression | PredefinedName | PixelConstructor

	public Expression Primary() throws SyntaxException 
	{
		//TODO
		System.out.print("   Primary"+t.getText()+"\n");
	
		if (isKind(INTEGER_LITERAL)) 
		{
			//match(INTEGER_LITERAL);
			Token intliteral = t;
			consume();
			return new ExpressionIntegerLiteral(t,intliteral);	
		}
		else if (isKind(BOOLEAN_LITERAL)) 
		{
			//match(BOOLEAN_LITERAL);
			Token boolliteral = t;
			consume();
			return new ExpressionBooleanLiteral(t, boolliteral);	
		}
		else if (isKind(FLOAT_LITERAL))
		{
			//match(FLOAT_LITERAL); 
			Token floatliteral = t;
			consume();
			return new ExpressionFloatLiteral(t, floatliteral);	
		}
		else if (isKind(IDENTIFIER)) 
			{
				Token id = t;
				    consume();
				if(isKind(LSQUARE)) 
				{
					ASTNode p = PixelExpression();
					return new ExpressionPixel(t,id,(PixelSelector)p);
				}
				else 
				{
					System.out.print("ExpressionIdent\n");
					return new ExpressionIdent(t,id);
				}
				
			}
		else if (isKind(FunctionName)) 
		{
			
			Expression e = FunctionApplication();
			return e;
		}
		else if (isKind(PredefinedName)) 
		{
			Token id = t;
		    consume();
		    System.out.print("PredefinedName\n");
			return new ExpressionPredefinedName(t,id);
		}
		else if (isKind(LPAREN)) 
		{
			Expression e=null;
			match(LPAREN);
			e = expression();
			match(RPAREN);
			return e;
			
		}
		else if (isKind(LPIXEL)) 
		{
			Expression e=null;
			e = PixelConstructor();
			return e;
		}
		else throw new SyntaxException(t,"Syntaxerror");
		
	}
	//FunctionApplication ::= FunctionName ( Expression )  | FunctionName  [ Expression , Expression ] 
	public Expression FunctionApplication  () throws SyntaxException 
	{
		//isKind(KW_sin)||isKind(KW_cos)|| isKind(KW_atan)||isKind(KW_abs)||isKind(KW_log)||isKind(KW_cart_x)||isKind(KW_cart_y )||isKind(KW_polar_a)||isKind(KW_polar_r) 
		//||isKind(KW_int )||isKind(KW_float)||isKind(KW_width)||isKind(KW_height)||isKind(KW_red)||isKind( KW_green)||isKind(KW_blue)||isKind( KW_alpha)
		System.out.print("   FunctionApplication");
		Expression functionApplication = null;
		Expression e =null;
		Expression e1 =null;
		if(isKind(FunctionName))
		{
			Token name = t;
			consume();
			if(isKind(LPAREN)) 
			{
				match(LPAREN);
				e = expression();
				match(RPAREN);
				functionApplication =new ExpressionFunctionAppWithExpressionArg(t,name,e);
			}
			else if(isKind(LSQUARE))
			{
				match(LSQUARE);
				e = expression();
				match(COMMA);
				e1 = expression();
				match(RSQUARE);
				functionApplication =new ExpressionFunctionAppWithPixel(t,name,e,e1);
			}
			else throw new SyntaxException(t,"Syntaxerror");
			
		}
		else throw new SyntaxException(t,"Syntaxerror");
		return functionApplication;
	}
	//PixelConstructor ::=  <<  Expression , Expression , Expression , Expression  >> 
	public Expression PixelConstructor    () throws SyntaxException 
	{
		System.out.print("   PixelConstructor");
		Expression a = null;
		Expression b = null;
		Expression c = null;
		Expression d = null;
		Expression pixelConstructor = null;
		if(isKind(LPIXEL))
		{
			match(LPIXEL);
			a =expression();
			match(COMMA);
			b = expression();
			match(COMMA);
			c = expression();
			match(COMMA);
			d = expression();
			match(RPIXEL);
			pixelConstructor = new ExpressionPixelConstructor(t, a,b,c,d);
		}
		else throw new SyntaxException(t,"Syntaxerror");
		return pixelConstructor;
	}
	//PixelExpression ::= IDENTIFIER PixelSelector
	public ASTNode PixelExpression    () throws SyntaxException 
	{
		System.out.print("   PixelExpression");
		ASTNode p=null;
		
		if(isKind(LSQUARE))
		{
			
			
			p = PixelSelector();
			//expressionPixel = new ExpressionPixel(t ,id, (PixelSelector)p);
		}
		else throw new SyntaxException(t,"Syntaxerror");
		return p;
	}
	//PixelSelector ::= [ Expression , Expression ]
	public ASTNode PixelSelector    () throws SyntaxException 
	{
		System.out.print("   PixelSelector");
		ASTNode pixelselector ;
		Expression a = null;
		Expression b = null;
		if(isKind(LSQUARE))
		{
			match(LSQUARE);
			a = expression();
			match(COMMA);
			b = expression();
			match(RSQUARE);
			pixelselector = new PixelSelector(t,a,b);
		}
		else throw new SyntaxException(t,"Syntaxerror");
		return pixelselector;
	}
	protected boolean isKind(Kind kind) {
		return t.kind == kind;
	}

	protected boolean isKind(Kind... kinds) {
		for (Kind k : kinds) {
			if (k == t.kind)
				return true;
		}
		return false;
	}


	/**
	 * Precondition: kind != EOF
	 * 
	 * @param kind
	 * @return
	 * @throws SyntaxException
	 */
	private Token match(Kind kind) throws SyntaxException {
		Token tmp = t;
		if (isKind(kind)) {
			consume();
			return tmp;
		}
		String errormessage="Syntax Error at"+pos;
		throw new SyntaxException(t,"Syntax Error"); //TODO  give a better error message!
	}


	private Token consume() throws SyntaxException {
		Token tmp = t;
		if (isKind( EOF)) {
			
			throw new SyntaxException(t,"Syntax Error"); //TODO  give a better error message!  
			//Note that EOF should be matched by the matchEOF method which is called only in parse().  
			//Anywhere else is an error. */
		}
		t = scanner.nextToken();
		return tmp;
	}


	/**
	 * Only for check at end of program. Does not "consume" EOF so no attempt to get
	 * nonexistent next Token.
	 * 
	 * @return
	 * @throws SyntaxException
	 */
	private Token matchEOF() throws SyntaxException {
		if (isKind(EOF)) {
			return t;
		}
		throw new SyntaxException(t,"Syntax Error"); //TODO  give a better error message!
	}
	

}

