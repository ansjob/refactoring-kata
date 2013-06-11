package compiler.sanitychecks;

import org.junit.Before;

import semant.amsyntax.Code;
import semant.amsyntax.Inst;
import semant.amsyntax.Loop;
import semant.amsyntax.Noop;
import semant.amsyntax.True;
import semant.whilesyntax.Skip;
import semant.whilesyntax.TrueConst;
import semant.whilesyntax.While;

public class SimpleWhileCompilerTest extends CompilerTestBase {
	
	
	public SimpleWhileCompilerTest() {
		ast = new While(new TrueConst(), new Skip());
		code = ast.accept(compiler);
	}
	
	@Before
	public void setUpExpected() {
		
		Code exprCode = new Code();
		Inst tru = new True();
		tru.stmControlPoint = 0;
		exprCode.add(tru);
		
		Code bodyCode = new Code();
		Inst noop = new Noop();
		noop.stmControlPoint = 1;
		bodyCode.add(noop);
		

		Inst loop = new Loop(exprCode, bodyCode);
		loop.stmControlPoint = 0;
		expected.add(loop);
	}

}
