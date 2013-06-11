package semant;

import java.util.HashSet;
import java.util.Set;

import semant.amsyntax.Code;
import semant.whilesyntax.Assignment;
import semant.whilesyntax.Compound;
import semant.whilesyntax.Conditional;
import semant.whilesyntax.Conjunction;
import semant.whilesyntax.Divide;
import semant.whilesyntax.EndOfProgram;
import semant.whilesyntax.Equals;
import semant.whilesyntax.FalseConst;
import semant.whilesyntax.LessThanEq;
import semant.whilesyntax.Minus;
import semant.whilesyntax.Not;
import semant.whilesyntax.Num;
import semant.whilesyntax.Plus;
import semant.whilesyntax.Skip;
import semant.whilesyntax.Stm;
import semant.whilesyntax.Times;
import semant.whilesyntax.TrueConst;
import semant.whilesyntax.TryCatch;
import semant.whilesyntax.Var;
import semant.whilesyntax.While;

public class VariableExtractor implements WhileVisitor {
	
	
	public Set<String> varNames = new HashSet<String>();

	@Override
	public Code visit(Compound compound) {
		compound.s1.accept(this);
		compound.s2.accept(this);
		return null;
	}

	@Override
	public Code visit(Not not) {
		not.b.accept(this);
		return null;
	}

	@Override
	public Code visit(Conjunction and) {
		and.b1.accept(this);
		and.b2.accept(this);
		return null;
	}

	@Override
	public Code visit(Assignment assignment) {
		varNames.add(assignment.x.id);
		assignment.a.accept(this);
		return null;
	}

	@Override
	public Code visit(Conditional conditional) {
		conditional.b.accept(this);
		conditional.s1.accept(this);
		conditional.s2.accept(this);
		return null;
	}

	@Override
	public Code visit(Equals equals) {
		equals.a1.accept(this);
		equals.a2.accept(this);
		return null;
	}

	@Override
	public Code visit(FalseConst f) {
		return null;
	}

	@Override
	public Code visit(LessThanEq lessthaneq) {
		lessthaneq.a1.accept(this);
		lessthaneq.a2.accept(this);
		return null;
	}

	@Override
	public Code visit(Minus minus) {
		minus.a1.accept(this);
		minus.a2.accept(this);
		return null;
	}

	@Override
	public Code visit(Num num) {
		return null;
	}

	@Override
	public Code visit(Plus plus) {
		plus.a1.accept(this);
		plus.a2.accept(this);
		return null;
	}

	@Override
	public Code visit(Skip skip) {
		return null;
	}

	@Override
	public Code visit(Times times) {
		times.a1.accept(this);
		times.a2.accept(this);
		return null;
	}

	@Override
	public Code visit(TrueConst t) {
		return null;
	}

	@Override
	public Code visit(Var var) {
		varNames.add(var.id);
		return null;
	}

	@Override
	public Code visit(While whyle) {
		whyle.b.accept(this);
		whyle.s.accept(this);
		return null;
	}

	@Override
	public Code visit(TryCatch trycatch) {
		trycatch.s1.accept(this);
		trycatch.s2.accept(this);
		return null;
	}

	@Override
	public Code visit(Divide div) {
		div.a1.accept(this);
		div.a2.accept(this);
		return null;
	}

	@Override
	public Code visit(EndOfProgram eop) {
		return null;
	}

	public static Set<String> extractVars(Stm c) {
		VariableExtractor ex = new VariableExtractor();
		c.accept(ex);
		return ex.varNames;
	}

}
