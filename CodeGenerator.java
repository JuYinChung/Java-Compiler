/**
 * Starter code for CodeGenerator.java used n the class project in COP5556 Programming Language Principles 
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
import static cop5556sp18.Scanner.Kind.OP_AND;
import static cop5556sp18.Scanner.Kind.OP_OR;
import static cop5556sp18.Scanner.Kind.OP_PLUS;
import static cop5556sp18.Scanner.Kind.OP_MINUS;
import static cop5556sp18.Scanner.Kind.OP_TIMES;
import static cop5556sp18.Scanner.Kind.OP_DIV;
import static cop5556sp18.Scanner.Kind.OP_EXCLAMATION;
import cop5556sp18.Types.Type;
import cop5556sp18.AST.ASTNode;
import cop5556sp18.AST.ASTVisitor;
import cop5556sp18.AST.Block;
import cop5556sp18.AST.Declaration;
import cop5556sp18.AST.Expression;
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
import cop5556sp18.AST.Statement;
import cop5556sp18.AST.StatementAssign;
import cop5556sp18.AST.StatementIf;
import cop5556sp18.AST.StatementInput;
import cop5556sp18.AST.StatementShow;
import cop5556sp18.AST.StatementSleep;
import cop5556sp18.AST.StatementWhile;
import cop5556sp18.AST.StatementWrite;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import java.util.regex.Pattern;
import cop5556sp18.CodeGenUtils;
import cop5556sp18.Scanner.Kind;
import cop5556sp18.TypeChecker;
public class CodeGenerator implements ASTVisitor, Opcodes {

	/**
	 * All methods and variable static.
	 */

	static final int Z = 255;

	private static final int ICMPEQ = 0;

	ClassWriter cw;
	String className;
	String classDesc;
	String sourceFileName;
	MethodVisitor mv; // visitor of method currently under construction

	/** Indicates whether genPrint and genPrintTOS should generate code. */
	final boolean DEVEL;
	final boolean GRADE;
	
	final int defaultWidth;
	final int defaultHeight;
	ArrayList<Declaration> 	m_localDecList;
	int 	m_currentSlotNumber;
	// final boolean itf = false;
	/**
	 * @param DEVEL
	 *            used as parameter to genPrint and genPrintTOS
	 * @param GRADE
	 *            used as parameter to genPrint and genPrintTOS
	 * @param sourceFileName
	 *            name of source file, may be null.
	 * @param defaultWidth
	 *            default width of images
	 * @param defaultHeight
	 *            default height of images
	 */
	
	public CodeGenerator(boolean DEVEL, boolean GRADE, String sourceFileName,
			int defaultWidth, int defaultHeight) {
		super();
		this.DEVEL = DEVEL;
		this.GRADE = GRADE;
		this.sourceFileName = sourceFileName;
		this.defaultWidth = defaultWidth;
		this.defaultHeight = defaultHeight;
	}
//	static HashMap<String,Integer> localtable=new HashMap<String,Integer>();
	static int slot=1;
	@Override
	public Object visitBlock(Block block, Object arg) throws Exception {
		System.out.print("6visitBlock\n");


	        for (ASTNode node : block.decsOrStatements)
	        {
	        	//if(node instanceof Statement)
	        	{
	        		System.out.print("Statement\n"+node.toString()+"\n");
	 	           	node.visit(this, arg);
	        	}
	           
	        }
	
	        
//	        for (ASTNode node : block.decsOrStatements)
//	        {
//	        	if(node instanceof Declaration)
//	        	{
//	        		System.out.print("goDeclare2\n"+node.toString()+"\n");
//	 	           	node.visit(this, arg);
//	        	}
//	        }
	       
	        return null;
	    }

	@Override
	public Object visitBooleanLiteral(
			ExpressionBooleanLiteral expressionBooleanLiteral, Object arg)
			throws Exception {
		System.out.print("6visitBooleanLiteral\n");
		mv.visitLdcInsn(expressionBooleanLiteral.value);
		
		return Type.BOOLEAN;
	}

	@Override
	public Object visitDeclaration(Declaration declaration, Object arg)
			throws Exception 
	{		        	
		System.out.print("6visitDeclaration"+declaration.getType()+"\n");
	    declaration.set_current_slot(m_currentSlotNumber++);
		m_localDecList.add(declaration);
		
		if(declaration.getType().equals(KW_int))
	    {	
			 //System.out.print("I"+declaration.get_current_slot(declaration.name)+"\n");
	       	 declaration.SetJVMTypeDesc("I");
	        //mv.visitLocalVariable(declaration.name, "I", null, cStart, cEnd, declaration.get_current_slot(declaration.name));
	    }
	    else if(declaration.getType().equals(KW_float))
	    {	
	    	//System.out.print("F"+declaration.get_current_slot(declaration.name)+"\n");
	        //mv.visitLocalVariable(declaration.name, "F", null, cStart, cEnd, declaration.get_current_slot(declaration.name));
	    	declaration.SetJVMTypeDesc("F");
	    }
	    else if(declaration.getType().equals(KW_boolean))
	    {	
	    	//System.out.print("B"+declaration.get_current_slot(declaration.name)+"\n");
	       // mv.visitLocalVariable(declaration.name, "Z", null, cStart, cEnd, declaration.get_current_slot(declaration.name));
	    	declaration.SetJVMTypeDesc("Z");
	    }
	    else if(declaration.getType().equals(KW_filename))
	    {	
	    	//System.out.print("F"+declaration.get_current_slot(declaration.name)+"\n");
	       // mv.visitLocalVariable(declaration.name, "Ljava/lang/String;", null, cStart, cEnd, declaration.get_current_slot(declaration.name));
	    	declaration.SetJVMTypeDesc("Ljava/lang/String;");
	    }
	    else if(declaration.getType().equals(KW_image))
	    {	
	    	//System.out.print("IM"+declaration.get_current_slot(declaration.name)+"\n");
	    	declaration.SetJVMTypeDesc("Ljava/awt/image/BufferedImage;");
	       // mv.visitLocalVariable(declaration.name, "Ljava/awt/image/BufferedImage;", null, cStart, cEnd, declaration.get_current_slot(declaration.name));
	        if(declaration.height != null)
	        {	
	        	declaration.width.visit(this, arg);
	            declaration.height.visit(this, arg);
	           	mv.visitMethodInsn(INVOKESTATIC, "cop5556sp18/RuntimeImageSupport", "makeImage", RuntimeImageSupport.makeImageSig, false);
	        }
	        else
	        {
	        	
	        	mv.visitLdcInsn(defaultWidth);
	        	mv.visitLdcInsn(defaultHeight);
	        	System.out.print("defaultWidth"+defaultWidth+"sss"+defaultHeight+"\n");
	            mv.visitMethodInsn(INVOKESTATIC, "cop5556sp18/RuntimeImageSupport", "makeImage", RuntimeImageSupport.makeImageSig, false);
	        }
	        mv.visitVarInsn(ASTORE, declaration.get_current_slot());
	    }

	    return null;
	}
	

	@Override
	public Object visitExpressionBinary(ExpressionBinary expressionBinary,
			Object arg) throws Exception {
		// TODO Auto-generated method stub
		System.out.print("6visitExpressionBinary\n"+expressionBinary.leftExpression+expressionBinary.op+expressionBinary.rightExpression);
	

		Type t1 =(Type) expressionBinary.leftExpression.visit(this, arg);	
	    Type t2 =(Type) expressionBinary.rightExpression.visit(this, arg);	
	    Type returnType=Type.INTEGER;
	    if(expressionBinary.op.equals(Kind.OP_PLUS))
	    {																					
	            if(t1.equals(Type.INTEGER) && t2.equals(Type.INTEGER))
	 			{
	 				mv.visitInsn(IADD);
	 				returnType=Type.INTEGER;
	 			}
	 			else if(t1.equals(Type.FLOAT) && t2.equals(Type.FLOAT))
	 			{
	 				mv.visitInsn(FADD);
	 				returnType=Type.FLOAT;
	 			}
	 			else if(t1.equals(Type.INTEGER) && t2.equals(Type.FLOAT))
	 			{
	 				mv.visitInsn(SWAP);
	 				mv.visitInsn(I2F);
	 				mv.visitInsn(FADD);
	 				returnType=Type.FLOAT;
	 			}
	 			else if(t1.equals(Type.FLOAT) && t2.equals(Type.INTEGER))
	 			{
	 				mv.visitInsn(I2F);
	 				mv.visitInsn(FADD);
	 				returnType=Type.FLOAT;
	 			}
	     }
	     else if(expressionBinary.op.equals(Kind.OP_MINUS))
	     {
	    	 if(t1.equals(Type.INTEGER) && t2.equals(Type.INTEGER))
	 			{
	 				mv.visitInsn(ISUB);
	 				returnType=Type.INTEGER;
	 			}
	 			else if(t1.equals(Type.FLOAT) && t2.equals(Type.FLOAT))
	 			{
	 				mv.visitInsn(FSUB);
	 				returnType=Type.FLOAT;
	 			}
	 			else if(t1.equals(Type.INTEGER) && t2.equals(Type.FLOAT))
	 			{
	 				mv.visitInsn(SWAP);
	 				mv.visitInsn(I2F);
	 				mv.visitInsn(FSUB);
	 				returnType=Type.FLOAT;
	 			}
	 			else if(t1.equals(Type.FLOAT) && t2.equals(Type.INTEGER))
	 			{
	 				mv.visitInsn(I2F);
	 				mv.visitInsn(FSUB);
	 				returnType=Type.FLOAT;
	 			}
	     }
	     else if(expressionBinary.op.equals(Kind.OP_TIMES))
	     {
	    	 if(t1.equals(Type.INTEGER) && t2.equals(Type.INTEGER))
	 			{
	 				mv.visitInsn(IMUL);
	 				returnType=Type.INTEGER;
	 			}
	 			else if(t1.equals(Type.FLOAT) && t2.equals(Type.FLOAT))
	 			{
	 				mv.visitInsn(FMUL);
	 				returnType=Type.FLOAT;
	 			}
	 			else if(t1.equals(Type.INTEGER) && t2.equals(Type.FLOAT))
	 			{
	 				mv.visitInsn(SWAP);
	 				mv.visitInsn(I2F);
	 				mv.visitInsn(FMUL);
	 				returnType=Type.FLOAT;
	 			}
	 			else if(t1.equals(Type.FLOAT) && t2.equals(Type.INTEGER))
	 			{
	 				mv.visitInsn(I2F);
	 				mv.visitInsn(FMUL);
	 				returnType=Type.FLOAT;
	 			}
	     }
	     else if(expressionBinary.op.equals(Kind.OP_POWER))
	        {	
	            if(t1.equals(Type.FLOAT) && t2.equals(Type.FLOAT))
	            {	
	            	mv.visitInsn(F2D);
					mv.visitVarInsn(DSTORE, 6);
					mv.visitInsn(F2D);
					mv.visitVarInsn(DLOAD, 6);
	            	
	            	mv.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Math",
							"pow", "(DD)D", false);
	            	mv.visitInsn(D2F);
	            	returnType=Type.FLOAT;
	            }
	            if(t1.equals(Type.INTEGER) && t2.equals(Type.INTEGER))
	            {
	            	mv.visitInsn(I2D);
	            	mv.visitVarInsn(DSTORE, 6);
	       	     	mv.visitInsn(I2D);
	       	     	mv.visitVarInsn(DLOAD, 6);
	            	mv.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Math",
							"pow", "(DD)D", false);
	            	mv.visitInsn(D2I);
	            	returnType=Type.INTEGER;
	            }
	            if(t1.equals(Type.INTEGER) && t2.equals(Type.FLOAT))
	            {
	            	
	            	mv.visitInsn(F2D);
	            	mv.visitVarInsn(DSTORE, 6);
	       	     	mv.visitInsn(I2D);
	       	     	mv.visitVarInsn(DLOAD, 6);
	            	mv.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Math",
							"pow", "(DD)D", false);
	            	mv.visitInsn(D2F);
	            	returnType=Type.FLOAT;
	            }
	            if(t1.equals(Type.FLOAT) && t2.equals(Type.INTEGER))
	            {
	            	mv.visitInsn(I2D);
	            	
	            	mv.visitVarInsn(DSTORE, 6);
	            	
	            	mv.visitInsn(F2D);
	       	        mv.visitVarInsn(DLOAD, 6);
	            	mv.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Math",
							"pow", "(DD)D", false);
	            	mv.visitInsn(D2F);
	            	returnType=Type.FLOAT;
	            }
	        }
	     else if(expressionBinary.op.equals(Kind.OP_DIV))
	     {
	    	 if(t1.equals(Type.INTEGER) && t2.equals(Type.INTEGER))
	 			{
	 				mv.visitInsn(IDIV);
	 				returnType=Type.INTEGER;
	 			}
	 			else if(t1.equals(Type.FLOAT) && t2.equals(Type.FLOAT))
	 			{
	 				mv.visitInsn(FDIV);
	 				returnType=Type.FLOAT;
	 			}
	 			else if(t1.equals(Type.INTEGER) && t2.equals(Type.FLOAT))
	 			{
	 				mv.visitInsn(SWAP);
	 				mv.visitInsn(I2F);
	 				mv.visitInsn(FDIV);
	 				returnType=Type.FLOAT;
	 			}
	 			else if(t1.equals(Type.FLOAT) && t2.equals(Type.INTEGER))
	 			{
	 				mv.visitInsn(I2F);
	 				mv.visitInsn(FDIV);
	 				returnType=Type.FLOAT;
	 			}
	     }
	     else if(expressionBinary.op.equals(Kind.OP_MOD))
	     {
	    	
	         mv.visitInsn(IREM);
	         returnType=Type.INTEGER;
	    }
	    else if(expressionBinary.op.equals(Kind.OP_AND))
	    {
	         mv.visitInsn(IAND);
	         if(t1==Type.INTEGER)returnType=Type.INTEGER;
	         else if(t1==Type.BOOLEAN)returnType=Type.BOOLEAN;
	            
	    }
	    else if(expressionBinary.op.equals(Kind.OP_OR))
	    {
             mv.visitInsn(IOR);
             if(t1==Type.INTEGER)returnType=Type.INTEGER;
	         else if(t1==Type.BOOLEAN)returnType=Type.BOOLEAN;
	            
	    }
	    else
	    	LogicalOperation(expressionBinary, t1, t2);
	    
	    return returnType;
	}
	private void LogicalOperation(ExpressionBinary expressionBinary, Type t1, Type t2)
	{
		Label set_true = new Label();
        Label l = new Label();
        
		if(expressionBinary.op.equals(Kind.OP_EQ))
		{
			if(t1.equals(Type.INTEGER) || t2.equals(Type.BOOLEAN))
			{
				mv.visitJumpInsn(IF_ICMPEQ, set_true);
			}
			else
			{
				mv.visitInsn(FCMPL);
				mv.visitJumpInsn(IFEQ, set_true);
			}
			mv.visitLdcInsn(false);
		}
		else if(expressionBinary.op.equals(Kind.OP_NEQ))
		{
			if(t1.equals(Type.INTEGER) || t2.equals(Type.BOOLEAN))
			{
				mv.visitJumpInsn(IF_ICMPNE, set_true);
			}
			else
			{
				mv.visitInsn(FCMPL);
				mv.visitJumpInsn(IFNE, set_true);
			}
			mv.visitLdcInsn(false);
		}
		else if(expressionBinary.op.equals(Kind.OP_GT))
		{
			if(t1.equals(Type.INTEGER) || t2.equals(Type.BOOLEAN))
			{
				mv.visitJumpInsn(IF_ICMPGT, set_true);
			}
			else
			{
				mv.visitInsn(FCMPL);
				mv.visitJumpInsn(IFGT, set_true);
			}
			mv.visitLdcInsn(false);
		}
		else if(expressionBinary.op.equals(Kind.OP_GE))
		{
			if(t1.equals(Type.INTEGER) || t2.equals(Type.BOOLEAN))
			{
				mv.visitJumpInsn(IF_ICMPGE, set_true);
			}
			else
			{
				mv.visitInsn(FCMPL);
				mv.visitJumpInsn(IFGE, set_true);
			}
			mv.visitLdcInsn(false);
		}
		else if(expressionBinary.op.equals(Kind.OP_LT))
		{
			if(t1.equals(Type.INTEGER) || t2.equals(Type.BOOLEAN))
			{
				mv.visitJumpInsn(IF_ICMPLT, set_true);
			}
			else
			{
				mv.visitInsn(FCMPL);
				mv.visitJumpInsn(IFLT, set_true);
			}
			mv.visitLdcInsn(false);
		}
		else if(expressionBinary.op.equals(Kind.OP_LE))
		{
			if(t1.equals(Type.INTEGER) || t2.equals(Type.BOOLEAN))
			{
				mv.visitJumpInsn(IF_ICMPLE, set_true);
			}
			else
			{
				mv.visitInsn(FCMPL);
				mv.visitJumpInsn(IFLE, set_true);
			}
			mv.visitLdcInsn(false);
		}
	    mv.visitJumpInsn(GOTO, l);
	    mv.visitLabel(set_true);
	    mv.visitLdcInsn(true);
	    mv.visitLabel(l);       
	}
	//sixth
	@Override
	public Object visitExpressionConditional(
			ExpressionConditional expressionConditional, Object arg)
			throws Exception {
		Type t1;
		expressionConditional.guard.visit(this, arg);
		Label set_true = new Label();
		Label l = new Label();
		mv.visitJumpInsn(IFNE, set_true);
		t1=(Type)expressionConditional.falseExpression.visit(this, arg);
		mv.visitJumpInsn(GOTO, l);
		mv.visitLabel(set_true);
		t1=(Type)expressionConditional.trueExpression.visit(this, arg);
		mv.visitLabel(l);
		
		return t1;
	}

	@Override
	public Object visitExpressionFloatLiteral(
			ExpressionFloatLiteral expressionFloatLiteral, Object arg)
			throws Exception {
		// TODO Auto-generated method stub
		System.out.print("6visitExpressionFloatLiteral\n");
		mv.visitLdcInsn(expressionFloatLiteral.value);
		return Type.FLOAT;
		
	}

	@Override
	public Object visitExpressionFunctionAppWithExpressionArg(
			ExpressionFunctionAppWithExpressionArg expressionFunctionAppWithExpressionArg,
			Object arg) throws Exception {
		// TODO Auto-generated method stub
		System.out.print("6visitExpressionFunctionAppWithExpressionArg\n");
		Label set_true=new Label();
		Label l=new Label();
		Type t =(Type) expressionFunctionAppWithExpressionArg.e.visit(this, arg);
		if(expressionFunctionAppWithExpressionArg.function.equals(Kind.KW_sin))
		{
			mv.visitInsn(F2D);
			mv.visitMethodInsn(INVOKESTATIC, "java/lang/Math", "sin", "(D)D", false);
			mv.visitInsn(D2F);
			return Type.FLOAT;
		}
		else if(expressionFunctionAppWithExpressionArg.function.equals(Kind.KW_cos))
		{
			mv.visitInsn(F2D);
			mv.visitMethodInsn(INVOKESTATIC, "java/lang/Math", "cos", "(D)D", false);
			mv.visitInsn(D2F);
			return Type.FLOAT;
		}
		else if(expressionFunctionAppWithExpressionArg.function.equals(Kind.KW_atan))
		{
			mv.visitInsn(F2D);
			mv.visitMethodInsn(INVOKESTATIC, "java/lang/Math", "atan", "(D)D", false);
			mv.visitInsn(D2F);
			return Type.FLOAT;
		}
		else if(expressionFunctionAppWithExpressionArg.function.equals(Kind.KW_log))
		{
			mv.visitInsn(F2D);
			mv.visitMethodInsn(INVOKESTATIC, "java/lang/Math", "log", "(D)D", false);
			mv.visitInsn(D2F);
			return Type.FLOAT;
		}
		else if(expressionFunctionAppWithExpressionArg.function.equals(Kind.KW_abs))
		{
			if(t.equals(Type.FLOAT))
			{
				mv.visitMethodInsn(INVOKESTATIC, "java/lang/Math", "abs", "(F)F", false);
				return Type.FLOAT;
			}
			else if(t.equals(Type.INTEGER))
			{
				mv.visitMethodInsn(INVOKESTATIC, "java/lang/Math", "abs", "(I)I", false);
				return Type.INTEGER;
			}
		}
		else if(expressionFunctionAppWithExpressionArg.function.equals(Kind.KW_int))
		{
			if(t.equals(Type.FLOAT))
			{
				mv.visitInsn(F2I);
			}
			return Type.INTEGER;
		}
		else if(expressionFunctionAppWithExpressionArg.function.equals(Kind.KW_float))
		{
			if(t.equals(Type.INTEGER))
			{
				mv.visitInsn(I2F);
			}
			return Type.FLOAT;
		}
		
		
		else if(expressionFunctionAppWithExpressionArg.function.equals(Kind.KW_alpha))
		{
			
			mv.visitMethodInsn(INVOKESTATIC, "cop5556sp18/RuntimePixelOps", "getAlpha", RuntimePixelOps.getAlphaSig, false);
			return Type.INTEGER;
		}
		else if(expressionFunctionAppWithExpressionArg.function.equals(Kind.KW_red))
		{
	
			mv.visitMethodInsn(INVOKESTATIC, "cop5556sp18/RuntimePixelOps", "getRed", RuntimePixelOps.getRedSig, false);
			return Type.INTEGER;
		}
		else if(expressionFunctionAppWithExpressionArg.function.equals(Kind.KW_blue))
		{
		
			mv.visitMethodInsn(INVOKESTATIC, "cop5556sp18/RuntimePixelOps", "getBlue", RuntimePixelOps.getBlueSig, false);
			return Type.INTEGER;
		}
		else if(expressionFunctionAppWithExpressionArg.function.equals(Kind.KW_green))
		{
		
			mv.visitMethodInsn(INVOKESTATIC, "cop5556sp18/RuntimePixelOps", "getGreen", RuntimePixelOps.getGreenSig, false);
			return Type.INTEGER;
		}
		else if(expressionFunctionAppWithExpressionArg.function.equals(Kind.KW_width))
		{
			
			mv.visitMethodInsn(INVOKESTATIC, "cop5556sp18/RuntimeImageSupport", "getWidth", RuntimeImageSupport.getWidthSig, false);
			return Type.INTEGER;
		}
		else if(expressionFunctionAppWithExpressionArg.function.equals(Kind.KW_height))
		{
			mv.visitMethodInsn(INVOKESTATIC, "cop5556sp18/RuntimeImageSupport", "getHeight", RuntimeImageSupport.getHeightSig, false);
			return Type.INTEGER;
		}
		
		return Type.INTEGER;
	}
	//sixth
	@Override
	public Object visitExpressionFunctionAppWithPixel(
			ExpressionFunctionAppWithPixel expressionFunctionAppWithPixel,
			Object arg) throws Exception {
		// TODO Auto-generated method stub
		System.out.print("6visitExpressionFunctionAppWithPixel\n"+expressionFunctionAppWithPixel.e0+"<<"+"\n");
		Type t1=(Type) expressionFunctionAppWithPixel.e0.visit(this, arg);
		Type t2=(Type) expressionFunctionAppWithPixel.e1.visit(this, arg);
		Type returnType=Type.INTEGER;
		Label set_true = new Label();
        Label l = new Label();
        if(expressionFunctionAppWithPixel.name==KW_polar_a)
        {
        	expressionFunctionAppWithPixel.e1.visit(this, arg);	
        	mv.visitInsn(I2D);
        	expressionFunctionAppWithPixel.e0.visit(this, arg);
   	     	mv.visitInsn(I2D);
   	        
        	mv.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Math",
					"atan2", "(DD)D", false);
        	mv.visitInsn(D2F);
        	returnType=Type.FLOAT;
	       
        }
        else if(expressionFunctionAppWithPixel.name==KW_polar_r)
        {
        	expressionFunctionAppWithPixel.e0.visit(this, arg);	
        	mv.visitInsn(I2D);
        	expressionFunctionAppWithPixel.e1.visit(this, arg);
   	     	mv.visitInsn(I2D);
        	mv.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Math",
					"hypot", "(DD)D", false);
        	mv.visitInsn(D2F);
        	returnType=Type.FLOAT;
        }
        else if(expressionFunctionAppWithPixel.name==KW_cart_x)
        {
        	expressionFunctionAppWithPixel.e0.visit(this, arg);	
        	mv.visitInsn(F2D);
        	expressionFunctionAppWithPixel.e1.visit(this, arg);
   	     	mv.visitInsn(F2D);
        	mv.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Math",
					"cos", "(D)D", false);
        	mv.visitInsn(DMUL);
        	mv.visitInsn(D2I);
        	returnType=Type.INTEGER;
        	
        }
        else if (expressionFunctionAppWithPixel.name==KW_cart_y)
        {
        	expressionFunctionAppWithPixel.e0.visit(this, arg);	
        	mv.visitInsn(F2D);
        	expressionFunctionAppWithPixel.e1.visit(this, arg);
   	     	mv.visitInsn(F2D);
        	mv.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Math",
					"sin", "(D)D", false);
        	mv.visitInsn(DMUL);
        	mv.visitInsn(D2I);
        	returnType=Type.INTEGER;
        }
        mv.visitJumpInsn(GOTO, l);
        mv.visitLabel(set_true);
        mv.visitLdcInsn(true);
        mv.visitLabel(l);    
		return returnType;
	}

	@Override
	public Object visitExpressionIdent(ExpressionIdent expressionIdent,
			Object arg) throws Exception {
		// TODO Auto-generated method stub
		System.out.print("6visitExpressionIdent\n"+expressionIdent.getDec());
		//mv.visitLdcInsn(expressionIdent.name);
		int num=expressionIdent.getDec().get_current_slot();
		
		Types type=new Types();
		Type t1=type.getType(expressionIdent.getDec().type);
		
		if(t1==Type.INTEGER||t1==Type.BOOLEAN)
		{
			mv.visitIntInsn(ILOAD, num);
		}
		else if(t1==Type.FLOAT)
		{
			mv.visitIntInsn(FLOAD, num);
		}
		else if(t1==Type.FILE)
		{
			mv.visitIntInsn(ALOAD, num);
		}
		else if(t1==Type.IMAGE)
		{	
			System.out.print("enter image\n\n\n");
			mv.visitVarInsn(ALOAD, num);
		}
		
		return t1;
		
	}

	@Override
	public Object visitExpressionIntegerLiteral(
			ExpressionIntegerLiteral expressionIntegerLiteral, Object arg)
			throws Exception {
		// This one is all done!
		System.out.print("6visitExpressionIntegerLiteral\n");
		mv.visitLdcInsn(expressionIntegerLiteral.value);
		//i devised
		return Type.INTEGER;
	}
	//sixth
	@Override
	public Object visitExpressionPixel(ExpressionPixel expressionPixel,
			Object arg) throws Exception {
		// TODO Auto-generated method stub
		System.out.print("6visitExpressionPixel\n"+expressionPixel.getDec());

		
		mv.visitVarInsn(ALOAD,expressionPixel.getDec().get_current_slot() );
		expressionPixel.pixelSelector.visit(this,arg);
        mv.visitMethodInsn(INVOKESTATIC, "cop5556sp18/RuntimeImageSupport", "getPixel", RuntimeImageSupport.getPixelSig, false);
        
        return Type.INTEGER;
	}
	//sixth
	@Override
	public Object visitExpressionPixelConstructor(
			ExpressionPixelConstructor expressionPixelConstructor, Object arg)
			throws Exception {
		System.out.print("6visitExpressionPixelConstructor\n");
		expressionPixelConstructor.alpha.visit(this, arg);
		expressionPixelConstructor.red.visit(this, arg);
		expressionPixelConstructor.green.visit(this, arg);
		expressionPixelConstructor.blue.visit(this, arg);
        mv.visitMethodInsn(INVOKESTATIC, "cop5556sp18/RuntimePixelOps", "makePixel", RuntimePixelOps.makePixelSig, false);
        //mv.visitVarInsn(ISTORE,51);
        return Type.INTEGER;
		}

	@Override
	public Object visitExpressionPredefinedName(
			ExpressionPredefinedName expressionPredefinedName, Object arg)
			throws Exception {
		// TODO Auto-generated method stub
		System.out.print("6visitExpressionPredefinedName\n");
		if(expressionPredefinedName.name==KW_Z)
		{
			mv.visitLdcInsn(255);
		}
		else if(expressionPredefinedName.name==KW_default_height)
		{
			mv.visitLdcInsn(defaultHeight);
		}
		else if(expressionPredefinedName.name==KW_default_width)
		{
			mv.visitLdcInsn(defaultWidth);
		}
		return Type.INTEGER;
	}

	@Override
	public Object visitExpressionUnary(ExpressionUnary expressionUnary,
			Object arg) throws Exception {
		Type t1 = (Type)expressionUnary.expression.visit(this, arg);
		Type returnType=Type.INTEGER;
		Label set_true = new Label();
		Label l = new Label();
		if(expressionUnary.op.equals(Kind.OP_MINUS))
		{
			if(t1.equals(Type.INTEGER))
			{
				mv.visitInsn(DUP);
				mv.visitVarInsn(ISTORE, 7);
				mv.visitJumpInsn(IFGT, set_true);
				returnType = Type.INTEGER;
			}
			else if(t1.equals(Type.FLOAT))
			{
				mv.visitInsn(DUP);
				mv.visitVarInsn(FSTORE, 7);
				mv.visitLdcInsn((float) 7);
				mv.visitInsn(FCMPL);
				mv.visitJumpInsn(IFGT, set_true);
				returnType = Type.FLOAT;
			}
		}
		else if(expressionUnary.op.equals(Kind.OP_EXCLAMATION))
		{
			if(t1.equals(Type.INTEGER))
			{
				mv.visitLdcInsn(-1);
				mv.visitInsn(IXOR);
				returnType = Type.INTEGER;
			}
			else if(t1.equals(Type.BOOLEAN))
			{
				mv.visitLdcInsn(true);
				mv.visitJumpInsn(IF_ICMPEQ, set_true);
				mv.visitLdcInsn(true);
				returnType = Type.BOOLEAN;
			}
			
		}
		if(t1.equals(Type.INTEGER) && expressionUnary.op.equals(Kind.OP_MINUS))
		{
			mv.visitVarInsn(ILOAD, 7);
			returnType = Type.INTEGER;
		}
		if(t1.equals(Type.FLOAT) && expressionUnary.op.equals(Kind.OP_MINUS))
		{
			mv.visitVarInsn(FLOAD, 7);
			returnType = Type.FLOAT;
		}
		mv.visitJumpInsn(GOTO, l);
		mv.visitLabel(set_true);
		if(t1.equals(Type.INTEGER) && expressionUnary.op.equals(Kind.OP_MINUS))
		{
			mv.visitVarInsn(ILOAD, 7);
			mv.visitInsn(INEG);
			returnType = Type.INTEGER;
		}
		if(t1.equals(Type.FLOAT) && expressionUnary.op.equals(Kind.OP_MINUS))
		{
			mv.visitVarInsn(FLOAD, 7);
			mv.visitInsn(FNEG);
			returnType = Type.FLOAT;
		}
		if(t1.equals(Type.BOOLEAN) && expressionUnary.op.equals(Kind.OP_EXCLAMATION))
		{
			mv.visitLdcInsn(false);
			returnType = Type.BOOLEAN;
		}
		mv.visitLabel(l);
		return returnType;
	}

	@Override
	public Object visitLHSIdent(LHSIdent lhsIdent, Object arg)
			throws Exception {
		System.out.print("6visitLHSIdent\n");
		
		Types type=new Types();
		//System.out.print("array:"+arr+"\n");
		Type t1=type.getType(lhsIdent.getDec().type);
		System.out.print("6:"+t1+"6:"+lhsIdent.getDec()+"\n");
		if(t1==Type.INTEGER||t1==Type.BOOLEAN)
		{
			mv.visitVarInsn(ISTORE,lhsIdent.getDec().get_current_slot() );
		}
		else if(t1==Type.FLOAT)
		{
			mv.visitVarInsn(FSTORE,lhsIdent.getDec().get_current_slot() );
		}
		else if(t1==Type.IMAGE)
		{
	        mv.visitMethodInsn(Opcodes.INVOKESTATIC, "cop5556sp18/RuntimeImageSupport", "deepCopy", RuntimeImageSupport.deepCopySig, false);
	        mv.visitVarInsn(ASTORE,lhsIdent.getDec().get_current_slot() );

		}
		else if(t1==Type.FILE)
        {
            mv.visitVarInsn(ASTORE, lhsIdent.getDec().get_current_slot() );
        }
		
		return null;
	}
	//(int rgb, BufferedImage image, int x, int y)
	@Override
	public Object visitLHSPixel(LHSPixel lhsPixel, Object arg)
			throws Exception {
		// TODO Auto-generated method stub
		System.out.print("6visitLHSPixel\n");
		
		
		// Now the stack is:
		//  |rgb| value
		//  |x  |
		//  |y  |
		mv.visitVarInsn(ALOAD,TypeChecker.lookupd(lhsPixel.name).get_current_slot() );
		lhsPixel.pixelSelector.visit(this,arg);
		mv.visitMethodInsn(INVOKESTATIC, "cop5556sp18/RuntimeImageSupport", "setPixel", RuntimeImageSupport.setPixelSig, false);	
		return null;	
	}
	//(smaple, color, x,y,image) 
	@Override
	public Object visitLHSSample(LHSSample lhsSample, Object arg)
			throws Exception {
		// TODO Auto-generated method stub
		
		System.out.print("6visitLHSSample");
		
		switch(lhsSample.color)
		{
			case KW_green:mv.visitLdcInsn(2);
			break;
			case KW_blue:mv.visitLdcInsn(3);
			break;
			case KW_red:mv.visitLdcInsn(1);
			break;
			case KW_alpha:mv.visitLdcInsn(0);
			break;
		}
		lhsSample.pixelSelector.visit(this,arg);
		mv.visitVarInsn(ALOAD,TypeChecker.lookupd(lhsSample.name).get_current_slot() );
		mv.visitMethodInsn(INVOKESTATIC, "cop5556sp18/RuntimeImageSupport", "updatePixelColor", RuntimeImageSupport.updatePixelColorSig, false);	

		return null;
	}

	//sixth
	@Override
	public Object visitPixelSelector(PixelSelector pixelSelector, Object arg)
			throws Exception {
		// TODO Auto-generated method stub
		System.out.print("6visitPixelSelector\n");
		
		
		pixelSelector.ex.visit(this, arg);
		pixelSelector.ey.visit(this, arg);
		return null;
	}

	@Override
	public Object visitProgram(Program program, Object arg) throws Exception {
		// TODO refactor and extend as necessary
		System.out.print("6visitProgram\n");
		cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES);
		// cw = new ClassWriter(0); //If the call to mv.visitMaxs(1, 1) crashes,
		// it is
		// sometime helpful to
		// temporarily run it without COMPUTE_FRAMES. You probably
		// won't get a completely correct classfile, but
		// you will be able to see the code that was
		// generated.
		className = program.progName;
		classDesc = "L" + className + ";";
		String sourceFileName = (String) arg;
		cw.visit(52, ACC_PUBLIC + ACC_SUPER, className, null,
				"java/lang/Object", null);
		cw.visitSource(sourceFileName, null);

		// create main method
		mv = cw.visitMethod(ACC_PUBLIC + ACC_STATIC, "main",
				"([Ljava/lang/String;)V", null, null);
		// initialize
		mv.visitCode();

		// add label before first instruction
		Label mainStart = new Label();
		Label mainEnd = new Label();
		mv.visitLabel(mainStart);
		
		CodeGenUtils.genLog(DEVEL, mv, "entering main");
        if(program.block!=null) 
        {
        	m_localDecList = new ArrayList<Declaration>();
        	m_currentSlotNumber = 1;
        	program.block.visit(this, arg);
        }
		CodeGenUtils.genLog(DEVEL, mv, "leaving main");
		//CodeGenUtils.genLog(DEVEL, mv, "args");
		// adds the required (by the JVM) return statement to main
		mv.visitInsn(RETURN);

		// adds label at end of code
		
		mv.visitLabel(mainEnd);
		mv.visitLocalVariable("args", "[Ljava/lang/String;", null, mainStart,
				mainEnd, 0);
		if(program.block!=null) 
        {
			for(int i=0;i<m_localDecList.size();i++)
			{
				//TypeName type = Type.getTypeName(m_localDecList.get(i).getType());
				mv.visitLocalVariable(m_localDecList.get(i).name, m_localDecList.get(i).GetJVMTypeDesc(), null,
						mainStart, mainEnd, m_localDecList.get(i).get_current_slot());
			}
        }
		// Because we use ClassWriter.COMPUTE_FRAMES as a parameter in the
		// constructor,
		// asm will calculate this itself and the parameters are ignored.
		// If you have trouble with failures in this routine, it may be useful
		// to temporarily change the parameter in the ClassWriter constructor
		// from COMPUTE_FRAMES to 0.
		// The generated classfile will not be correct, but you will at least be
		// able to see what is in it.
		mv.visitMaxs(3, 2);

		// terminate construction of main method
		mv.visitEnd();

		// terminate class construction
		cw.visitEnd();

		// generate classfile as byte array and return
		return cw.toByteArray();
	}

	@Override
	public Object visitStatementAssign(StatementAssign statementAssign,
			Object arg) throws Exception {
		// TODO Auto-generated method stub
		System.out.print("6visitStatementAssign\n");
		Type t1 = (Type)statementAssign.e.visit(this, arg);	//right hand side
		statementAssign.lhs.visit(this, arg);				//left hand side

		
		return null;
	}

	@Override
	public Object visitStatementIf(StatementIf statementIf, Object arg)
			throws Exception {
		System.out.print("6visitStatementIf\n");
		Label afterIf = new Label();
		statementIf.guard.visit(this, arg); // Expression
		mv.visitJumpInsn(IFEQ, afterIf); //IFEQ AFTER
		Label startIf = new Label();
		mv.visitLabel(startIf);
		statementIf.b.visit(this, arg); //BLOCK
		Label endIf = new Label();
		mv.visitLabel(endIf);
		mv.visitLabel(afterIf); //AFTER
		Label endAfterIf = new Label();
		mv.visitLabel(endAfterIf);

		return null;
	}

	@Override
	public Object visitStatementInput(StatementInput statementInput, Object arg)
			throws Exception {
		// TODO Auto-generated method stub
		System.out.print("6visitStatementInput\n");
		//int x=((ExpressionIntegerLiteral)statementInput.e).value;
		Types type=new Types();
		Type t1 =  type.getType(TypeChecker.lookupd(statementInput.destName).type);
		
		if(t1==Type.INTEGER||t1==Type.BOOLEAN)
		{
			mv.visitVarInsn(ALOAD, 0);
			statementInput.e.visit(this, arg);
			mv.visitInsn(AALOAD);
			if(t1.equals(Type.INTEGER))
			{
				mv.visitMethodInsn(INVOKESTATIC, "java/lang/Integer", "parseInt", "(Ljava/lang/String;)I", false);
			}
			else
			{
				mv.visitMethodInsn(INVOKESTATIC, "java/lang/Boolean", "parseBoolean", "(Ljava/lang/String;)Z", false);
			}
			mv.visitVarInsn(ISTORE, TypeChecker.lookupd(statementInput.destName).get_current_slot()); 
			
		}
		else if(t1==Type.FLOAT)
		{

			mv.visitVarInsn(ALOAD, 0);
			statementInput.e.visit(this, arg);
			mv.visitInsn(AALOAD);
			mv.visitMethodInsn(INVOKESTATIC, "java/lang/Float", "parseFloat", "(Ljava/lang/String;)F", false);
			mv.visitVarInsn(FSTORE, TypeChecker.lookupd(statementInput.destName).get_current_slot());		}
		else if(t1.equals(Type.IMAGE))
			
	    {	
			
			mv.visitVarInsn(ALOAD, 0);
			statementInput.e.visit(this, arg);
			mv.visitInsn(AALOAD);
			ExpressionIntegerLiteral heightExpr = ((ExpressionIntegerLiteral)TypeChecker.lookupd(statementInput.destName).height);
			ExpressionIntegerLiteral widthExpr = ((ExpressionIntegerLiteral)TypeChecker.lookupd(statementInput.destName).width);
			int height;
			int width;
			if(heightExpr!=null)
			{
				height = heightExpr.value;
				width = widthExpr.value;
				mv.visitLdcInsn(width);
				mv.visitMethodInsn(INVOKESTATIC, "java/lang/Integer", "valueOf","(I)Ljava/lang/Integer;", false);
				mv.visitLdcInsn(height);
				mv.visitMethodInsn(INVOKESTATIC, "java/lang/Integer", "valueOf","(I)Ljava/lang/Integer;", false);
			}
			else
			{
				height = NULL;
				width = NULL;
				mv.visitInsn(ACONST_NULL);
				mv.visitInsn(ACONST_NULL);
			}

		    mv.visitMethodInsn(INVOKESTATIC, "cop5556sp18/RuntimeImageSupport", "readImage", RuntimeImageSupport.readImageSig, false);
	        mv.visitVarInsn(ASTORE, TypeChecker.lookupd(statementInput.destName).get_current_slot());
	    }
	    else if(t1.equals(Type.FILE))
			
	    {	
			
			mv.visitVarInsn(ALOAD, 0);
			statementInput.e.visit(this, arg);
			mv.visitInsn(AALOAD);
			
			//mv.visitMethodInsn(INVOKESTATIC, "cop5556sp18/RuntimeImageSupport", "readFromFile", RuntimeImageSupport.readFromFileSig, false);
	        mv.visitVarInsn(ASTORE, TypeChecker.lookupd(statementInput.destName).get_current_slot());
	        //CodeGenUtils.genLogTOS(GRADE, mv, t1);
	    }
		return null;
	}

	@Override
	public Object visitStatementShow(StatementShow statementShow, Object arg)
			throws Exception {
		/**
		 * TODO refactor and complete implementation.
		 * 
		 * For integers, booleans, and floats, generate code to print to
		 * console. For images, generate code to display in a frame.
		 * 
		 * In all cases, invoke CodeGenUtils.genLogTOS(GRADE, mv, type); before
		 * consuming top of stack.
		 */
		
		Type type=(Type)statementShow.e.visit(this, arg);
		System.out.print("6visitStatementShow\n"+type+"\n");
		switch (type) {
			case INTEGER : {
				CodeGenUtils.genLogTOS(GRADE, mv, type);
				
				mv.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System", "out",
						"Ljava/io/PrintStream;");
				mv.visitInsn(Opcodes.SWAP);
				mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/io/PrintStream",
						"println", "(I)V", false);
			}
				break;
			case BOOLEAN : {
				CodeGenUtils.genLogTOS(GRADE, mv, type);
				// TODO implement functionality
				
				mv.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System", "out",
						"Ljava/io/PrintStream;");
				mv.visitInsn(Opcodes.SWAP);
				mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/io/PrintStream",
						"println", "(Z)V", false);
				
			}
			break;
			case FLOAT : {
				CodeGenUtils.genLogTOS(GRADE, mv, type);
				// TODO implement functionality
				
				mv.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System", "out",
						"Ljava/io/PrintStream;");
				mv.visitInsn(Opcodes.SWAP);
				mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/io/PrintStream",
						"println", "(F)V", false);
				
			}
			break;
			case IMAGE : {
				CodeGenUtils.genLogTOS(GRADE, mv, type);
				// TODO implement functionality
				//mv.visitInsn(opcode);
				mv.visitInsn(DUP);
				mv.visitInsn(DUP);
				mv.visitMethodInsn(Opcodes.INVOKESTATIC, "cop5556sp18/RuntimeImageSupport",
						"getWidth",RuntimeImageSupport.getWidthSig , false);
				mv.visitInsn(SWAP);
				mv.visitMethodInsn(Opcodes.INVOKESTATIC, "cop5556sp18/RuntimeImageSupport",
						"getHeight",RuntimeImageSupport.getHeightSig , false);
				
				mv.visitMethodInsn(Opcodes.INVOKESTATIC, "cop5556sp18/RuntimeImageSupport",
						"makeFrame",RuntimeImageSupport.makeFrameSig , false);
			}
			break;

		}
		return null;
	}

	@Override
	public Object visitStatementSleep(StatementSleep statementSleep, Object arg)
			throws Exception {
		// TODO Auto-generated method stub
		System.out.print("6visitStatementSleep\n");
		statementSleep.duration.visit(this, arg);
		mv.visitInsn(I2L);
		mv.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Thread", "sleep", "(J)V",false);
		return null;
	}
	//sixth
	@Override
	public Object visitStatementWhile(StatementWhile statementWhile, Object arg)
			throws Exception {
		System.out.print("6visitStatementWhile\n");
		Label l1 = new Label();
		mv.visitJumpInsn(GOTO, l1);
		Label l2 = new Label();
		mv.visitLabel(l2);
		statementWhile.b.visit(this, arg);
		mv.visitLabel(l1);
		statementWhile.guard.visit(this, arg);
		mv.visitJumpInsn(IFNE, l2);
		
		return null;
	}
	
	//sixth
	@Override
	public Object visitStatementWrite(StatementWrite statementWrite, Object arg)
			throws Exception {
		// TODO Auto-generated method stub
		System.out.print("6visitStatementWrite\n");
		mv.visitVarInsn(ALOAD,statementWrite.getSourceDec().get_current_slot());
		mv.visitVarInsn(ALOAD,statementWrite.getDestDec().get_current_slot());
        mv.visitMethodInsn(Opcodes.INVOKESTATIC, "cop5556sp18/RuntimeImageSupport", "write", RuntimeImageSupport.writeSig, false);
		return null;
	}

}