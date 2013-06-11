package semant.whilesyntax;

import semant.WhileVisitor;
import semant.amsyntax.Code;
;

public class EndOfProgram extends Stm {

	@Override
	public Code accept(WhileVisitor v) {
		return v.visit(this);
	}

}
