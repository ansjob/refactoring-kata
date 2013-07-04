package compiler.sanitychecks;

import static java.util.Arrays.asList;

import org.junit.Before;

import semant.amsyntax.Add;
import semant.amsyntax.Inst;
import semant.amsyntax.Push;
import semant.amsyntax.Store;
import semant.amsyntax.Sub;
import semant.whilesyntax.Assignment;
import semant.whilesyntax.Minus;
import semant.whilesyntax.Num;
import semant.whilesyntax.Plus;
import semant.whilesyntax.Var;

public class SimpleArithmeticCompilerTest extends CompilerTestBase {
	
	public SimpleArithmeticCompilerTest() {
		ast = new Assignment(new Var("index"), new Plus(new Num("0"), new Minus(new Num("5"), new Num("3"))));
		code = ast.accept(compiler);
	}
	
	@Before
	public void setupExpectedCode() {
		Inst push0 = new Push("0");
		Inst push5 = new Push("5");
		Inst push3 = new Push("3");
		Inst sub = new Sub();
		Inst storeIndex = new Store("index");
		Inst plus = new Add();
		expected.addAll(asList(push3, push5, sub, push0, plus, storeIndex));
		for (Inst i : expected) {
			i.stmControlPoint = 0;
		}
	}

}
