package compiler.sanitychecks;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import semant.CompileVisitor;
import semant.amsyntax.Code;
import semant.amsyntax.Inst;
import semant.amsyntax.Push;
import semant.amsyntax.Store;
import semant.whilesyntax.Assignment;
import semant.whilesyntax.Num;
import semant.whilesyntax.Stm;
import semant.whilesyntax.Var;


public class SimpleAssignmentCompilerTest extends CompilerTestBase {
	
	
	@Before
	public void compileProgram() {
		//The program "x := 15"
		ast = new Assignment(new Var("x"), new Num("15"));
		compiler = new CompileVisitor();
		code = ast.accept(compiler);
	}
	
	@Before
	public void the_code_compiled_is_correct() {
		
		Inst push15 = new Push("15");
		Inst storeX = new Store("x");
		
		push15.stmControlPoint = storeX.stmControlPoint = 0;
		
		expected.add(push15);
		expected.add(storeX);
	}

}
