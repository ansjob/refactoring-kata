package compiler.sanitychecks;

import java.util.Collection;

import org.junit.Before;

import semant.amsyntax.Branch;
import semant.amsyntax.Code;
import semant.amsyntax.Inst;
import semant.amsyntax.Le;
import semant.amsyntax.Noop;
import semant.amsyntax.Push;
import semant.whilesyntax.Assignment;
import semant.whilesyntax.Bexp;
import semant.whilesyntax.Conditional;
import semant.whilesyntax.LessThanEq;
import semant.whilesyntax.Num;
import semant.whilesyntax.Skip;
import semant.whilesyntax.Stm;

import static java.util.Arrays.*;

public class SimpleLessEqualCompilerTest extends CompilerTestBase {
	
	public SimpleLessEqualCompilerTest() {
		Bexp guard = new LessThanEq(new Num("5"), new Num("3"));
		Stm s1 = new Skip();
		Stm s2 = new Skip();
		ast = new Conditional(guard , s1 , s2 );
		code = ast.accept(compiler);
	}
	
	@Before
	public void setUpExpectedCode() {
		Code guardCode = new Code();
		Inst push3 = new Push("3");
		Inst push5 = new Push("5");
		Inst leq = new Le();
		guardCode.addAll(asList(push3, push5 , leq ));
		
		for (Inst i : guardCode) {
			i.stmControlPoint = 0;
		}
		
		Code trueBranch = new Code();
		Inst nop1 = new Noop();
		nop1.stmControlPoint = 1;
		trueBranch.add(nop1);
		
		Code falseBranch = new Code();
		Inst nop2 = new Noop();
		nop2.stmControlPoint = 2;
		falseBranch.add(nop2);
		
		Inst branch = new Branch(trueBranch, falseBranch);
		branch.stmControlPoint = 0;
		
		expected.addAll(guardCode);
		expected.add(branch);
	}

}
