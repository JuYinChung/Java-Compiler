package cop5556sp18;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import cop5556sp18.Parser;
import cop5556sp18.Scanner;
import cop5556sp18.AST.ASTVisitor;
import cop5556sp18.AST.Program;
import cop5556sp18.TypeChecker.SemanticException;

public class TypeCheckerTest {

	/*
	 * set Junit to be able to catch exceptions
	 */
	@Rule
	public ExpectedException thrown = ExpectedException.none();

	/**
	 * Prints objects in a way that is easy to turn on and off
	 */
	static final boolean doPrint = true;

	private void show(Object input) {
		if (doPrint) {
			System.out.println(input.toString());
		}
	}

	/**
	 * Scans, parses, and type checks the input string
	 * 
	 * @param input
	 * @throws Exception
	 */
	void typeCheck(String input) throws Exception {
		show(input);
		// instantiate a Scanner and scan input
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		// instantiate a Parser and parse input to obtain and AST
		Program ast = new Parser(scanner).parse();
		show(ast);
		// instantiate a TypeChecker and visit the ast to perform type checking and
		// decorate the AST.
		ASTVisitor v = new TypeChecker();
		ast.visit(v, null);
	}



	/**
	 * Simple test case with an almost empty program.
	 * 
	 * @throws Exception
	 */
	
	
	@Test
	public void emptyProg() throws Exception {
		String input = 

				"prog{boolean a; boolean b; if(a & b){};}";
		typeCheck(input);
	}
	@Test
	public void emptyProg5() throws Exception {
		String input = 

				"prog{image var1; red( var1[0,0.0]) := 5;}";
		typeCheck(input);
	}
	@Test
	public void emptyProg6() throws Exception {
		String input = 

				" {float var; var := 5.0;}";
		typeCheck(input);
	}
	@Test
	public void emptyProg7() throws Exception {
		String input = 

				" p{int var; if(true) {float var; var := 5.0;}; var := 5.0;}";
		typeCheck(input);
	}
	@Test
	public void emptyProg1() throws Exception {
		String input = 

				"prog{filename f1; write f1 to f1;}";
		typeCheck(input);
	}
	@Test
	public void emptyProg2() throws Exception {
		String input = 

				"prog{int var1; float var2; boolean var3; image var4; filename var5; image var6[500,500];if(true){int var1; float var2; boolean var3; image var4; filename var5; image var6[500,500];};}";
		typeCheck(input);
	}
	@Test
	public void emptyProg3() throws Exception {
		String input = 

				"prog{image image1; write image1 to image1;}";
		typeCheck(input);
	}
	@Test
	public void expression1() throws Exception {
		String input = "PolarR2{int a;a:=5;int b;b:=6;a:=(a==b?1:2);image im[1024,1024];int x;x:=1;while(x<width(im)) {int x;x:=5;int y;while(x<height(im)) {float p;p:=polar_r[x,y];int r;r:=int(p)%Z;im[x,y]:=<<Z,0,0,r>>;y:=y+1;};int x;x:=7;x:=x+1;};show im;}";
		typeCheck(input);
	}

	@Test
	public void expression2_fail() throws Exception {
		String input ="samples{image bird;filename abc;write bird to abc;show bird;sleep(4000);image bird2[width(bird),height(bird)];int x;x:=0;while(x<width(bird2)) {int y;y:=0;while(y<height(bird2)) {blue(bird2[x,y]):=red(bird[x,y]);green(bird2[x,y]):=blue(bird[x,y]);red(bird2[x,y]):=green(bird[x,y]);alpha(bird2[x,y]):=Z;y:=y+1;};x:=x+1;};show bird2;sleep(4000);}";
		typeCheck(input);
//		thrown.expect(SemanticException.class);
//		try {
//			typeCheck(input);
//		} catch (SemanticException e) {
//			show(e);
//			throw e;
//		}
	}
}
