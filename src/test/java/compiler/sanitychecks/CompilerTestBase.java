package compiler.sanitychecks;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import semant.CompileVisitor;
import semant.amsyntax.Code;
import semant.whilesyntax.Stm;

public abstract class CompilerTestBase {
	
	protected Code code;
	protected  CompileVisitor compiler = new CompileVisitor();
	Stm ast;
	protected Code expected = new Code();
	
	@Test
	public void code_seems_correct() {
		assertEquals(expected, code);
	}

}
