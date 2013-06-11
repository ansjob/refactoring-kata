package compiler.sanitychecks;

import org.junit.Before;

import semant.amsyntax.Catch;
import semant.amsyntax.Code;
import semant.amsyntax.Div;
import semant.amsyntax.Fetch;
import semant.amsyntax.Inst;
import semant.amsyntax.Noop;
import semant.amsyntax.Push;
import semant.amsyntax.Store;
import semant.amsyntax.Try;
import semant.whilesyntax.Assignment;
import semant.whilesyntax.Divide;
import semant.whilesyntax.Num;
import semant.whilesyntax.Skip;
import semant.whilesyntax.Stm;
import semant.whilesyntax.TryCatch;
import semant.whilesyntax.Var;

import static java.util.Arrays.*;

public class SimpleDivisionCompilerTest extends CompilerTestBase {

	
	public SimpleDivisionCompilerTest() {
		Stm div = new Assignment(new Var("y"), new Divide(new Num("5"), new Var("x")));
		Stm skip = new Skip();
		ast = new TryCatch(div , skip );
		code = ast.accept(compiler);
	}
	
	@Before
	public void setUpExpected() {
		
		Code divOp = new Code();
		Inst push5 = new Push("5");
		Inst fetchX = new Fetch("x");
		Inst divide = new Div();
		Inst storeY = new Store("y");
		divOp.addAll(asList(fetchX, push5, divide, storeY));
		
		for (Inst i : divOp) {
			i.stmControlPoint = 1;
		}
		
		Inst tryy = new Try(divOp );
		tryy.stmControlPoint = 0;
		
		Code catchBody = new Code();
		Inst noop = new Noop();
		noop.stmControlPoint = 2;
		catchBody.add(noop );
		Inst caatch = new Catch(catchBody);
		caatch.stmControlPoint = 3;
		
		expected.addAll(asList(tryy, caatch ));
	}
}
