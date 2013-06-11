package compiler.sanitychecks;

import org.junit.Before;


import semant.amsyntax.And;
import semant.amsyntax.Code;
import semant.amsyntax.False;
import semant.amsyntax.Inst;
import semant.amsyntax.Loop;
import semant.amsyntax.Noop;
import semant.amsyntax.True;
import semant.whilesyntax.Bexp;
import semant.whilesyntax.Conjunction;
import semant.whilesyntax.FalseConst;
import semant.whilesyntax.Skip;
import semant.whilesyntax.TrueConst;
import semant.whilesyntax.While;
import static java.util.Arrays.*;

public class SimpleWhileCompilerTest extends CompilerTestBase {
	
	
	public SimpleWhileCompilerTest() {
		Bexp guard = new Conjunction(new FalseConst(), new TrueConst());
		ast = new While(guard, new Skip());
		code = ast.accept(compiler);
	}
	
	@Before
	public void setUpExpected() {
		
		Code exprCode = new Code();
		Inst fals = new False();
		Inst tru = new True();
		Inst and = new And();
		
		fals.stmControlPoint = 0;
		tru.stmControlPoint = 0;
		and.stmControlPoint = 0;
		
		exprCode.addAll(asList(tru, fals, and));
		
		Code bodyCode = new Code();
		Inst noop = new Noop();
		noop.stmControlPoint = 1;
		bodyCode.add(noop);
		

		Inst loop = new Loop(exprCode, bodyCode);
		loop.stmControlPoint = 0;
		expected.add(loop);
	}

}
