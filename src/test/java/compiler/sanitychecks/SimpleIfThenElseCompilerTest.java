package compiler.sanitychecks;

import static java.util.Arrays.asList;

import org.junit.Before;

import semant.amsyntax.Branch;
import semant.amsyntax.Code;
import semant.amsyntax.Inst;
import semant.amsyntax.Noop;
import semant.amsyntax.Push;
import semant.amsyntax.Store;
import semant.amsyntax.True;
import semant.whilesyntax.Assignment;
import semant.whilesyntax.Bexp;
import semant.whilesyntax.Conditional;
import semant.whilesyntax.Num;
import semant.whilesyntax.Skip;
import semant.whilesyntax.Stm;
import semant.whilesyntax.TrueConst;
import semant.whilesyntax.Var;

public class SimpleIfThenElseCompilerTest extends CompilerTestBase {
	
	Stm ifBody = new Assignment(new Var("foo"), new Num("5"));
	Stm elseBody = new Skip();
	Bexp b = new TrueConst();
	
	public SimpleIfThenElseCompilerTest() {
		ast = new Conditional(b, ifBody, elseBody);
		code = ast.accept(compiler);
	}
	
	@Before
	public void setup() {
		Inst tru = new True();
		tru.stmControlPoint = 0;
		
		Code ifBodyCode = new Code();
		Inst push5 = new Push("5");
		Inst storeFoo = new Store("foo");
		push5.stmControlPoint = storeFoo.stmControlPoint = 1;
		ifBodyCode.addAll(asList(push5, storeFoo));
		
		Code elseBodyCode = new Code();
		Inst noop = new Noop();
		noop.stmControlPoint = 2;
		elseBodyCode.add(noop);
		
		Inst cond = new Branch(ifBodyCode, elseBodyCode);
		cond.stmControlPoint = 0;
		
		expected.add(tru);
		expected.add(cond);
	}

}
