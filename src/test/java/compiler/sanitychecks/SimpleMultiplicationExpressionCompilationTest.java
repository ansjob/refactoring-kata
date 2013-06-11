package compiler.sanitychecks;

import org.junit.Before;

import semant.amsyntax.Code;
import semant.amsyntax.Inst;
import semant.amsyntax.Mult;
import semant.amsyntax.Push;
import semant.amsyntax.Store;
import semant.whilesyntax.Assignment;
import semant.whilesyntax.Minus;
import semant.whilesyntax.Num;
import semant.whilesyntax.Times;
import semant.whilesyntax.Var;

import static java.util.Arrays.*;

public class SimpleMultiplicationExpressionCompilationTest extends
		CompilerTestBase {

	public SimpleMultiplicationExpressionCompilationTest() {
		ast = new Assignment(new Var("bar"), new Times(new Num("5"), new Num(
				"3")));
		code = ast.accept(compiler);
	}

	@Before
	public void setupExpected() {

		Inst push5 = new Push("5");
		Inst push3 = new Push("3");
		Inst mult = new Mult();
		Inst storeBar = new Store("bar");

		push5.stmControlPoint = push3.stmControlPoint = mult.stmControlPoint = storeBar.stmControlPoint = 0;

		expected.addAll(asList(push3, push5, mult, storeBar));
	}
}
