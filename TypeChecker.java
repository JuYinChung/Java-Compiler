package cop5556sp18;

import cop5556sp18.Scanner.Kind;
import cop5556sp18.Scanner.Token;
import cop5556sp18.Types.Type;

import static cop5556sp18.Scanner.Kind.IDENTIFIER;
import static cop5556sp18.Scanner.Kind.KW_Z;
import static cop5556sp18.Scanner.Kind.KW_abs;
import static cop5556sp18.Scanner.Kind.KW_alpha;
import static cop5556sp18.Scanner.Kind.KW_atan;
import static cop5556sp18.Scanner.Kind.KW_blue;
import static cop5556sp18.Scanner.Kind.KW_boolean;
import static cop5556sp18.Scanner.Kind.KW_cart_x;
import static cop5556sp18.Scanner.Kind.KW_cart_y;
import static cop5556sp18.Scanner.Kind.KW_cos;
import static cop5556sp18.Scanner.Kind.KW_default_height;
import static cop5556sp18.Scanner.Kind.KW_default_width;
import static cop5556sp18.Scanner.Kind.KW_filename;
import static cop5556sp18.Scanner.Kind.KW_float;
import static cop5556sp18.Scanner.Kind.KW_green;
import static cop5556sp18.Scanner.Kind.KW_height;
import static cop5556sp18.Scanner.Kind.KW_if;
import static cop5556sp18.Scanner.Kind.KW_image;
import static cop5556sp18.Scanner.Kind.KW_input;
import static cop5556sp18.Scanner.Kind.KW_int;
import static cop5556sp18.Scanner.Kind.KW_log;
import static cop5556sp18.Scanner.Kind.KW_polar_a;
import static cop5556sp18.Scanner.Kind.KW_polar_r;
import static cop5556sp18.Scanner.Kind.KW_red;
import static cop5556sp18.Scanner.Kind.KW_show;
import static cop5556sp18.Scanner.Kind.KW_sin;
import static cop5556sp18.Scanner.Kind.KW_sleep;
import static cop5556sp18.Scanner.Kind.KW_while;
import static cop5556sp18.Scanner.Kind.KW_width;
import static cop5556sp18.Scanner.Kind.KW_write;

import java.security.KeyStore.Entry;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

import cop5556sp18.AST.ASTNode;
import cop5556sp18.AST.ASTVisitor;
import cop5556sp18.AST.Block;
import cop5556sp18.AST.Declaration;
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
import cop5556sp18.AST.LHSIdent;
import cop5556sp18.AST.LHSPixel;
import cop5556sp18.AST.LHSSample;
import cop5556sp18.AST.PixelSelector;
import cop5556sp18.AST.Program;
import cop5556sp18.AST.StatementAssign;
import cop5556sp18.AST.StatementIf;
import cop5556sp18.AST.StatementInput;
import cop5556sp18.AST.StatementShow;
import cop5556sp18.AST.StatementSleep;
import cop5556sp18.AST.StatementWhile;
import cop5556sp18.AST.StatementWrite;
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

public class TypeChecker implements ASTVisitor {


	//private static final Kind OP_PLUS = null;

	Kind[] firstDec = { KW_int, KW_boolean, KW_image, KW_float, KW_filename };
	Kind[] firstStatement = {KW_input,KW_write,IDENTIFIER,KW_while,KW_if,KW_show,KW_sleep };
	Kind[] FunctionName  = { KW_sin ,KW_cos ,KW_atan ,KW_abs ,KW_log ,KW_cart_x ,KW_cart_y ,KW_polar_a ,KW_polar_r 
			,KW_int ,KW_float ,KW_width ,KW_height ,KW_red , KW_green, KW_blue , KW_alpha  };
	Kind[] PredefinedName  = {KW_Z ,KW_default_height ,KW_default_width };
	Kind[] Color   = {  KW_red , KW_green, KW_blue , KW_alpha };

	TypeChecker() {
	}

	@SuppressWarnings("serial")
	public static class SemanticException extends Exception {
		Token t;

		public SemanticException(Token t, String message) {
			super(message);
			this.t = t;
		}
	}
	static int  current_scope;
	int next_scope=0;
	static Stack<Integer> scope_stack = new Stack<Integer>();
	public void enterScope()
	{   
		current_scope = next_scope++; 
		scope_stack.push(current_scope);
		System.out.print("********//enterScope//***********"+current_scope+"\n");
	}

	public void closeScope()
	{  
		scope_stack.pop();
		if(!scope_stack.isEmpty())current_scope = scope_stack.peek();
		System.out.print("********//closescope//***********"+current_scope+"\n");
	}
	static Map<Declaration,List<Integer>> hash=new HashMap<Declaration,List<Integer>>();
	
	public static Declaration lookup(String name) 
	{   System.out.print("LOOKUP: "+name+"\n");
		
		Set<Declaration> keys = hash.keySet(); 
		
		Iterator<Declaration> it = keys.iterator();
		for(int i=current_scope;i>=0;i--) 
		{
			for(Map.Entry<Declaration,List<Integer>> entry : hash.entrySet())
			{
				
				Declaration d=(Declaration)entry.getKey();
				 System.out.print("Declaration: "+d.type+d.name+"\n");
				 if(d.name.equals(name)) 
				 {   
					 List<Integer> list=entry.getValue();
					 int count = 0;
				     ListIterator<Integer> ittt = list.listIterator();
					 while(ittt.hasNext()) 	 
					 {		
						 System.out.print(list.get(count)+"."+i+"scope_stack"+scope_stack+"\n");
						 if((int)list.get(count)==i&&scope_stack.contains(list.get(count))) 
						 {   
							 System.out.println("i "+i+" lookup: "+d.type+" list:"+list.get(count)+"\n");
							 return d;
						 }
							 ittt.next();
							 count++;
					 }			
				 }
			}
		}
		System.out.println("return null\n");
		return null;	
	}
	public static Declaration lookupd(String name) 
	{  
		Set<Declaration> keys = hash.keySet(); 
		
		Iterator<Declaration> it = keys.iterator();
		
		for(Map.Entry<Declaration,List<Integer>> entry : hash.entrySet())
		{
			
			Declaration d=(Declaration)entry.getKey();
			 if(d.name.equals(name)) 
			 {   
				 return d;
			 }
		}
	    
		System.out.println("return null\n");
		return null;	
	}
	public void addentry(Declaration declaration) 
	{   
		
		Set<Declaration> keys = hash.keySet();
		Iterator<Declaration> it = keys.iterator();
		List<Integer> list=new ArrayList<Integer>();
		 System.out.println("addentry:"+declaration.type+"\n");
		//Declaration declaration=null;
		while (it.hasNext()) 
		{
			
			 Declaration d=(Declaration)it.next();
			
			 if(d.equals(declaration)) 
			 {   
				list=hash.get(d);
				System.out.print("listsize"+list.size()+" is"+list.get(0)+"\n");
				list.add(0,scope_stack.peek());
				hash.put(declaration, list);
				for(int i=0;i<list.size();i++)
				{
				    System.out.println("hasaddentry:"+declaration.type+" list:"+list.get(i)+"\n");
				} 
				 break;
			 }
		}
		if(list.isEmpty()) 
		{
			list.add(0,current_scope);
			hash.put(declaration, list);
			for(int i=0;i<list.size();i++)
			{
				
				   System.out.println("addentry:"+declaration.name+" list:"+list.get(i)+"\n");
			} 
		}
		
		
	}

	
	// Name is only used for naming the output file. 
	// Visit the child block to type check program.
	@Override
	public Object visitProgram(Program program, Object arg) throws Exception {
		if(program.block==null) {return null;}
		else 
		{	
			hash=new HashMap<Declaration,List<Integer>>();
			System.out.print("new map"+hash.size()+"\n\n\n\n");
			program.block.visit(this, arg);
			return null;
		}
	}

	@Override
	public Object visitBlock(Block block, Object arg) throws Exception {
		// TODO Auto-generated method stub
		System.out.print("blocksize:"+block.decsOrStatements.size()+"\n");
		enterScope();
		System.out.print("scope_stack"+scope_stack+"\n");
		for(int i =0;i<block.decsOrStatements.size();i++)
		{
			block.decOrStatement(i).visit(this, arg);
		}
		closeScope();
		return null;
		//throw new UnsupportedOperationException();
	}

	@Override
	public Type visitDeclaration(Declaration declaration, Object arg) throws Exception {
		// TODO Auto-generated method stub
		System.out.print("visitDeclaration "+"w"+declaration.width+"h"+declaration.height+"\n");
		Types type1=new Types();
		if(declaration.width!=null)System.out.print("test "+"w"+declaration.width.visit(this, arg)+"2"+"\n");
		if(declaration.height!=null)System.out.print("test "+"H"+declaration.height.visit(this, arg)+"2"+"\n");
		if(declaration.width == null|| (declaration.width.visit(this, arg)== Type.INTEGER && type1.getType(declaration.type)== Type.IMAGE)
		&&(declaration.height ==null||(declaration.height.visit(this, arg)==Type.INTEGER && type1.getType(declaration.type)== Type.IMAGE))
	
		||(declaration.width == null&&declaration.height ==null))
		{
			System.out.print("case1\n");
			addentry(declaration);
			return null;
		}
		
		else throw new SemanticException(declaration.firstToken, "error:declaration");
		//return null;
	}
		
		

	@Override
	public Object visitStatementWrite(StatementWrite statementWrite, Object arg) throws Exception {
		// TODO Auto-generated method stub
		Types type=new Types();
		System.out.print("visitStatementWrite\n");
		System.out.print("destName"+statementWrite.destName+"\n");
		System.out.print("srcName"+statementWrite.sourceName+"\n");
		if(lookup(statementWrite.destName)!=null&&lookup(statementWrite.sourceName)!=null) 
		{	
			if(	type.getType(lookup(statementWrite.sourceName).type)==Type.IMAGE&&type.getType(lookup(statementWrite.destName).type)==Type.FILE)
			
			{
				
			}
			else throw new SemanticException(statementWrite.firstToken, "error:StatementWrite");
		}
		else throw new SemanticException(statementWrite.firstToken, "error:StatementWrite");
		statementWrite.setSourceDec(lookup(statementWrite.sourceName));
		statementWrite.setDestDec(lookup(statementWrite.destName));
		return null;
	}

	@Override
	public Object visitStatementInput(StatementInput statementInput, Object arg) throws Exception {
		// TODO Auto-generated method stub
		System.out.print("visitStatementInput\n"+"destName"+statementInput.destName+"\n");
		if(lookup(statementInput.destName)!=null&&statementInput.e.visit(this, arg)==Type.INTEGER) 
		{
			
		}
		else throw new SemanticException(statementInput.firstToken, "error:statementInput");
		statementInput.setDec(lookup(statementInput.destName));
		return null;
	}

	@Override
	public Object visitPixelSelector(PixelSelector pixelSelector, Object arg) throws Exception {
		// TODO Auto-generated method stub
		System.out.print("visitPixelSelector\n"+pixelSelector.ex.visit(this, arg)+"ey"+pixelSelector.ey.visit(this, arg)+"\n");
		Types type=new Types();
		if((pixelSelector.ex.visit(this, arg)==Type.INTEGER&&pixelSelector.ey.visit(this, arg)==Type.INTEGER) 
		||(pixelSelector.ex.visit(this, arg)==Type.FLOAT&&pixelSelector.ey.visit(this, arg)==Type.FLOAT))
		{
			
		}
		
		else throw new SemanticException(pixelSelector.firstToken, "error:pixelSelector");
		return null;
	}

	@Override
	public Object visitExpressionConditional(ExpressionConditional expressionConditional, Object arg) throws Exception {
		// TODO Auto-generated method stub
		System.out.print("visitExpressionConditional\n");
		if(expressionConditional.guard.visit(this, arg)==Type.BOOLEAN&&expressionConditional.trueExpression.visit(this, arg)==expressionConditional.falseExpression.visit(this, arg))
		{
			
		}
		else throw new SemanticException(expressionConditional.firstToken, "error:expressionConditional");
		return (Type)expressionConditional.trueExpression.visit(this, arg);
	}

	@Override
	public Object visitExpressionBinary(ExpressionBinary expressionBinary, Object arg) throws Exception {
		// TODO Auto-generated method stub
		Types type=new Types();
		System.out.print("visitExpressionBinary"+expressionBinary.op+"\n");
		Type e1=(Type)expressionBinary.leftExpression.visit(this, arg);
		Type e2=(Type)expressionBinary.rightExpression.visit(this, arg);
		System.out.print("e1"+e1+"e2"+e2+"op"+expressionBinary.op+"\n");
	
		if(expressionBinary.op==OP_PLUS)System.out.print("yes!!!\n\n");
		if(e1==Type.INTEGER&&e2==Type.INTEGER&&(expressionBinary.op==OP_PLUS||expressionBinary.op==OP_MINUS||expressionBinary.op==OP_TIMES||expressionBinary.op==OP_DIV||expressionBinary.op==OP_MOD||expressionBinary.op==OP_AND||expressionBinary.op==OP_POWER||expressionBinary.op==OP_OR))
			return Type.INTEGER;
		else if(e1==Type.FLOAT&&e2==Type.FLOAT&&(expressionBinary.op==OP_PLUS||expressionBinary.op==OP_MINUS||expressionBinary.op==OP_TIMES||expressionBinary.op==OP_DIV||expressionBinary.op==OP_POWER))
			return Type.FLOAT;
		else if(e1==Type.FLOAT&&e2==Type.INTEGER&&(expressionBinary.op==OP_PLUS||expressionBinary.op==OP_MINUS||expressionBinary.op==OP_TIMES||expressionBinary.op==OP_DIV||expressionBinary.op==OP_POWER))
			return Type.FLOAT;
		else if(e1==Type.INTEGER&&e2==Type.FLOAT&&(expressionBinary.op==OP_PLUS||expressionBinary.op==OP_MINUS||expressionBinary.op==OP_TIMES||expressionBinary.op==OP_DIV||expressionBinary.op==OP_POWER))
			return Type.FLOAT;
		else if(e1==Type.BOOLEAN&&e2==Type.BOOLEAN&&(expressionBinary.op==OP_AND||expressionBinary.op==OP_OR))
			return Type.BOOLEAN;
		else if(e1==Type.INTEGER&&e2==Type.INTEGER&&(expressionBinary.op==OP_AND||expressionBinary.op==OP_OR))
			return Type.INTEGER;
		else if(e1==Type.INTEGER&&e2==Type.INTEGER&&(expressionBinary.op==OP_EQ||expressionBinary.op==OP_NEQ||expressionBinary.op==OP_GE||expressionBinary.op==OP_LE||expressionBinary.op==OP_GT||expressionBinary.op==OP_LT))
			return Type.BOOLEAN;
		else if(e1==Type.FLOAT&&e2==Type.FLOAT&&(expressionBinary.op==OP_EQ||expressionBinary.op==OP_NEQ||expressionBinary.op==OP_GE||expressionBinary.op==OP_LE||expressionBinary.op==OP_GT||expressionBinary.op==OP_LT))
			return Type.BOOLEAN;
		else if(e1==Type.BOOLEAN&&e2==Type.BOOLEAN&&(expressionBinary.op==OP_EQ||expressionBinary.op==OP_NEQ||expressionBinary.op==OP_GE||expressionBinary.op==OP_LE||expressionBinary.op==OP_GT||expressionBinary.op==OP_LT))
			return Type.BOOLEAN;	
		else throw new SemanticException(expressionBinary.firstToken, "error:expressionBinary");

		
		//throw new UnsupportedOperationException();
	}

	@Override
	public Object visitExpressionUnary(ExpressionUnary expressionUnary, Object arg) throws Exception {
		// TODO Auto-generated method stub
		System.out.print("visitExpressionUnary\n");
		return (Type)expressionUnary.expression.visit(this, arg);
	}

	@Override
	public Object visitExpressionIntegerLiteral(ExpressionIntegerLiteral expressionIntegerLiteral, Object arg)
			throws Exception {
		// TODO Auto-generated method stub
	
		
		//System.out.print("visitExpressionIntegerLiteral"+"\n");
		System.out.print("Type.INTEGER"+"\n");
		return Type.INTEGER;
		//throw new UnsupportedOperationException();
	}

	@Override
	public Object visitBooleanLiteral(ExpressionBooleanLiteral expressionBooleanLiteral, Object arg) throws Exception {
		// TODO Auto-generated method stub
		System.out.print("Type.BOOLEAN"+"\n");
		return Type.BOOLEAN;
	}

	@Override
	public Object visitExpressionPredefinedName(ExpressionPredefinedName expressionPredefinedName, Object arg)
			throws Exception {
		System.out.print("visitExpressionPredefinedName\n");
		return Type.INTEGER;
	
	}

	@Override
	public Object visitExpressionFloatLiteral(ExpressionFloatLiteral expressionFloatLiteral, Object arg)
			throws Exception {
		System.out.print("Type.FLOAT"+"\n");
		return Type.FLOAT;
	}

	@Override
	public Object visitExpressionFunctionAppWithExpressionArg(
			ExpressionFunctionAppWithExpressionArg expressionFunctionAppWithExpressionArg, Object arg)
			throws Exception {
		// TODO Auto-generated method stub\
		
		Type e = (Type)expressionFunctionAppWithExpressionArg.e.visit(this, arg);
		System.out.print("ExpressionFunctionApp"+expressionFunctionAppWithExpressionArg.e+"type"+e+"\n");
		Kind function = expressionFunctionAppWithExpressionArg.function;
		System.out.print("visitExpressionFunctionAppWithExpressionArg,type:"+e+"function"+function+"\n");
		if(e==Type.INTEGER&&(function==KW_red ||function==KW_green||function==KW_blue ||function==KW_abs||function==KW_alpha))
			return Type.INTEGER;
		else if(e==Type.FLOAT&&(function==KW_abs ||function==KW_sin||function==KW_cos ||function==KW_atan||function==KW_log))
			return Type.FLOAT;
		else if(e==Type.IMAGE&&(function==KW_width ||function==KW_height))
			return Type.INTEGER;
		else if(e==Type.INTEGER&&function==KW_float)
			return Type.FLOAT;
		else if(e==Type.FLOAT&&function==KW_float)
			return Type.FLOAT;
		else if(e==Type.FLOAT&&function==KW_int )
			return Type.INTEGER;
		else if(e==Type.INTEGER&&function==KW_int)
			return Type.INTEGER;
		else throw new SemanticException(expressionFunctionAppWithExpressionArg.firstToken, "error:expressionFunctionAppWithExpressionArg");
	}

	@Override
	public Object visitExpressionFunctionAppWithPixel(ExpressionFunctionAppWithPixel expressionFunctionAppWithPixel,
			Object arg) throws Exception {
		// TODO Auto-generated method stub
		if(expressionFunctionAppWithPixel.name==KW_cart_x||expressionFunctionAppWithPixel.name==KW_cart_y)
		{
			if(expressionFunctionAppWithPixel.e0.visit(this, arg)==Type.FLOAT&&expressionFunctionAppWithPixel.e1.visit(this, arg)==Type.FLOAT) 
				return Type.INTEGER;
			else throw new SemanticException(expressionFunctionAppWithPixel.firstToken, "error:expressionFunctionAppWithPixel");
		}
		else if(expressionFunctionAppWithPixel.name==KW_polar_a||expressionFunctionAppWithPixel.name==KW_polar_r)
		{
			if(expressionFunctionAppWithPixel.e0.visit(this, arg)==Type.INTEGER&&expressionFunctionAppWithPixel.e1.visit(this, arg)==Type.INTEGER) 
				return Type.FLOAT;
			else throw new SemanticException(expressionFunctionAppWithPixel.firstToken, "error:expressionFunctionAppWithPixel");
		}	
		else throw new SemanticException(expressionFunctionAppWithPixel.firstToken, "error:expressionFunctionAppWithPixel");
	}

	@Override
	public Object visitExpressionPixelConstructor(ExpressionPixelConstructor expressionPixelConstructor, Object arg)
			throws Exception {

		System.out.print("visitExpressionPixelConstructor"+"\n");
		if(expressionPixelConstructor.alpha.visit(this, arg)==Type.INTEGER&&expressionPixelConstructor.red.visit(this, arg)==Type.INTEGER&&
				expressionPixelConstructor.blue.visit(this, arg)==Type.INTEGER&&expressionPixelConstructor.green.visit(this, arg)==Type.INTEGER)
		{
			return Type.INTEGER;
		}
		// TODO Auto-generated method stub
		else throw new SemanticException(expressionPixelConstructor.firstToken, "error:expressionPixelConstructor");
	}

	@Override
	public Object visitStatementAssign(StatementAssign statementAssign, Object arg) throws Exception {
		// TODO Auto-generated method stub
		//System.out.print("visitStatementAssign\n"+e+e1);
		Type e = (Type)statementAssign.lhs.visit(this, arg);
		Type e1 = (Type)statementAssign.e.visit(this, arg);
		System.out.print("visitStatementAssign\n"+e+":="+e1+"\n");
		if(e==e1) 
		{
			return null;
		}
		else throw new SemanticException(statementAssign.firstToken, "error:statementAssign");
	}

	@Override
	public Object visitStatementShow(StatementShow statementShow, Object arg) throws Exception {
		// TODO Auto-generated method stub
		System.out.print("visitStatementShow\\n");
		Type e = (Type)statementShow.e.visit(this, arg);
		if(e == Type.INTEGER||e == Type.IMAGE||e == Type.FLOAT||e == Type.BOOLEAN) 
		{
			return null;
		}
		
		else throw new SemanticException(statementShow.firstToken, "error:statementShow");
	}

	@Override
	public Object visitExpressionPixel(ExpressionPixel expressionPixel, Object arg) throws Exception {
		// TODO Auto-generated method stub
		System.out.print("visitExpressionPixel\\n");
		expressionPixel.pixelSelector.visit(this, arg);
		Types type=new Types();
		if(lookup(expressionPixel.name)!=null&&type.getType(lookup(expressionPixel.name).type)==Type.IMAGE)
		{
			expressionPixel.setDec((Declaration)lookup(expressionPixel.name));
			return Type.INTEGER;
		}
		else throw new SemanticException(expressionPixel.firstToken, "error:expressionPixel");
	}

	@Override
	public Object visitExpressionIdent(ExpressionIdent expressionIdent, Object arg) throws Exception {
		// TODO Auto-generated method stub
		System.out.print("visitExpressionIdent: "+expressionIdent.name+lookup(expressionIdent.name)+"\n");
		Types type=new Types();
		if(lookup(expressionIdent.name)!=null)
		{
			expressionIdent.setDec((Declaration)lookup(expressionIdent.name));
			return type.getType(lookup(expressionIdent.name).type);
		}
		else throw new SemanticException(expressionIdent.firstToken, "error:expressionIdent");
	}

	@Override
	public Object visitLHSSample(LHSSample lhsSample, Object arg) throws Exception {
		System.out.print("visitLHSSample\n");lhsSample.pixelSelector.visit(this, arg);
		Types type=new Types();
		System.out.print("asdasd"+lhsSample.firstToken.getText());
		if(lookup(lhsSample.name)!=null&&type.getType(lookup(lhsSample.name).type)==Type.IMAGE)
		{
			lhsSample.setDec((Declaration)lookup(lhsSample.name));
			return Type.INTEGER;
		}
		else throw new SemanticException(lhsSample.firstToken, "error:lhsSample");
	}

	@Override
	public Object visitLHSPixel(LHSPixel lhsPixel, Object arg) throws Exception {
		// TODO Auto-generated method stub
		System.out.print("visitLHSPixel\n"+"name:"+lhsPixel.name+"lookupreturn"+lookup(lhsPixel.name)+"\n");
		Types type=new Types();
		lhsPixel.pixelSelector.visit(this, arg);
		if(lookup(lhsPixel.name)!=null&&type.getType(lookup(lhsPixel.name).type)==Type.IMAGE)
		{
			lhsPixel.setDec((Declaration)lookup(lhsPixel.name));
			return Type.INTEGER;
		}
		else throw new SemanticException(lhsPixel.firstToken, "error:lhsPixel");
	}

	@Override
	public Object visitLHSIdent(LHSIdent lhsIdent, Object arg) throws Exception {
		// TODO Auto-generated method stub
		System.out.print("visitLHSIdent\n name:"+lhsIdent.name+"\n");
		Types type=new Types();
		if(lookup(lhsIdent.name)!=null)
		{
			lhsIdent.setDec((Declaration)lookup(lhsIdent.name));
			return type.getType(lookup(lhsIdent.name).type);
		}
		else throw new SemanticException(lhsIdent.firstToken, "error:lhsIdent");
	}

	@Override
	public Object visitStatementIf(StatementIf statementIf, Object arg) throws Exception {
		System.out.print("visitStatementIf\n");
		Type e = (Type)statementIf.guard.visit(this, arg);
		if(statementIf.b!=null)statementIf.b.visit(this, arg);
		if(e == Type.BOOLEAN)
			return e;
		else throw new SemanticException(statementIf.firstToken, "error:statementIf");
	}

	@Override
	public Object visitStatementWhile(StatementWhile statementWhile, Object arg) throws Exception {
		
		Type e = (Type)statementWhile.guard.visit(this, arg);
		if(statementWhile.b!=null)statementWhile.b.visit(this, arg);
		if(e == Type.BOOLEAN)
			return e;
		else throw new SemanticException(statementWhile.firstToken, "error:statementWhile");
	}

	@Override
	public Object visitStatementSleep(StatementSleep statementSleep, Object arg) throws Exception {
		System.out.print("visitStatementSleep");
		// TODO Auto-generated method stub
		
		Type e = (Type)statementSleep.duration.visit(this, arg);
		if(e == Type.INTEGER)
			return null;
		else throw new SemanticException(statementSleep.firstToken, "error:statementSleep");
	}

	
	


}
