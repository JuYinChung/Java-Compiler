package cop5556sp18;

import java.util.Hashtable;
import java.util.Stack;

import cop5556sp18.Scanner.Kind;

public class SymbolTable {

	Hashtable<Kind,Integer> hashKind=new Hashtable<Kind,Integer>();
	Stack<Integer> s = new Stack<Integer>();
	public static void main(String[] arg0)
	{	
		Hashtable<Integer,Integer> hash=new Hashtable<Integer,Integer>();
		hash.put(4, 1);
		hash.put(5, 2);
		hash.put(6, 3);
		System.out.print(hash.get(6));
	}
	
	
}
