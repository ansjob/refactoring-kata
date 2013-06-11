package compiler;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import semant.amsyntax.Code;
import semant.amsyntax.Inst;
import semant.amsyntax.Push;
import semant.amsyntax.Store;
import semant.whilesyntax.Assignment;
import semant.whilesyntax.Compound;
import semant.whilesyntax.Num;
import semant.whilesyntax.Stm;
import semant.whilesyntax.Var;

public class SimpleCompoundCompilerTest extends CompilerTestBase {
	
	
	private Stm ass1 = new Assignment(new Var("x"), new Num("10"));
	private Stm ass2 = new Assignment(new Var("y"), new Num("5"));
	
	public SimpleCompoundCompilerTest() {
		ast = new Compound(ass1, ass2);
		code = ast.accept(compiler);
	}
	
	@Before 
	public void setupExpectedCode() {
		Inst push10 = new Push("10");
		Inst storeX = new Store("x");
		
		Inst push5 = new Push("5");
		Inst storeY = new Store("y");
		
		push10.stmControlPoint = storeX.stmControlPoint = 0;
		push5.stmControlPoint = storeY.stmControlPoint = 1;
		
		expected.add(push10);
		expected.add(storeX);
		expected.add(push5);
		expected.add(storeY);
	}
	


}
