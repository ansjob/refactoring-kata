package compiler;

import org.junit.Before;

import semant.amsyntax.Inst;
import semant.amsyntax.Noop;
import semant.whilesyntax.Skip;

public class SimpleSkipCompilerTest extends CompilerTestBase {
	
	public SimpleSkipCompilerTest() {
		ast = new Skip();
		code = ast.accept(compiler);
	}
	
	@Before
	public void setup() {
		Inst noop = new Noop();
		noop.stmControlPoint = 0;
		expected.add(noop);
	}
	
	
}
