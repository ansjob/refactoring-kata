package compiler.sanitychecks;

import org.junit.Before;

import semant.amsyntax.And;
import semant.amsyntax.Branch;
import semant.amsyntax.Code;
import semant.amsyntax.Eq;
import semant.amsyntax.False;
import semant.amsyntax.Inst;
import semant.amsyntax.Neg;
import semant.amsyntax.Noop;
import semant.amsyntax.Push;
import semant.whilesyntax.Bexp;
import semant.whilesyntax.Conditional;
import semant.whilesyntax.Conjunction;
import semant.whilesyntax.Equals;
import semant.whilesyntax.FalseConst;
import semant.whilesyntax.Not;
import semant.whilesyntax.Num;
import semant.whilesyntax.Skip;
import semant.whilesyntax.Stm;

import static java.util.Arrays.*;

public class SimpleLogicCompilerTest extends CompilerTestBase {

	public SimpleLogicCompilerTest() {
		Bexp guard = new Conjunction(new Not(new Equals(new Num("5"), new Num("5"))), new FalseConst());
		Stm skip1 = new Skip();
		Stm skip2 = new Skip();
		ast = new Conditional(guard , skip1, skip2);
		code = ast.accept(compiler);
	}
	
	@Before
	public void setupExpectedCode() {
		
		Code trueBranch = new Code();
		Inst nop1 = new Noop();
		nop1.stmControlPoint = 1;
		trueBranch.add(nop1);
		
		Code falseBranch = new Code();
		Inst nop2 = new Noop();
		nop2.stmControlPoint = 2;
		falseBranch.add(nop2);
		
		Inst branch = new Branch(trueBranch , falseBranch );
		branch.stmControlPoint = 0;
		
		Code guardCode = new Code();
		Inst conj = new And();
		Inst push51 = new Push("5");
		Inst push52 = new Push("5");
		Inst eq = new Eq();
		Inst neg = new Neg();
		Inst fals = new False();
		guardCode.addAll(asList(fals, push51 , push52 , eq , neg , conj));
		
		for (Inst i : guardCode) {
			i.stmControlPoint = 0;
		}
		
		expected.addAll(guardCode);
		expected.add(branch);
	}
	
}
