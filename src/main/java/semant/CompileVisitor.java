package semant;

import semant.amsyntax.Add;
import semant.amsyntax.And;
import semant.amsyntax.Branch;
import semant.amsyntax.Catch;
import semant.amsyntax.Code;
import semant.amsyntax.Div;
import semant.amsyntax.Eq;
import semant.amsyntax.False;
import semant.amsyntax.Fetch;
import semant.amsyntax.Inst;
import semant.amsyntax.Le;
import semant.amsyntax.Loop;
import semant.amsyntax.Mult;
import semant.amsyntax.Neg;
import semant.amsyntax.Noop;
import semant.amsyntax.Push;
import semant.amsyntax.Store;
import semant.amsyntax.Sub;
import semant.amsyntax.True;
import semant.amsyntax.Try;
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
import semant.whilesyntax.Times;
import semant.whilesyntax.TrueConst;
import semant.whilesyntax.TryCatch;
import semant.whilesyntax.Var;
import semant.whilesyntax.While;

public class CompileVisitor implements WhileVisitor {
	
	public int nextControlPoint;

	public Code visit(Compound compound) {
		Code c = new Code();
		c.addAll(compound.s1.accept(this));
		c.addAll(compound.s2.accept(this));
		return c;
	}

	public Code visit(Not not) {
		Code c = new Code();
		c.addAll(not.b.accept(this));
		c.add(new Neg());
		return c;
	}

	public Code visit(Conjunction and) {
		Code res = new Code();
		res.addAll(and.b2.accept(this));
		res.addAll(and.b1.accept(this));
		res.add(new And());
		return res;
	}

	public Code visit(Assignment assignment) {
		Code c = new Code();
		c.addAll(assignment.a.accept(this));
		c.add(new Store(assignment.x.id));
		for (Inst i : c) {
			i.stmControlPoint = nextControlPoint;
		}
		assignment.controlPoint = nextControlPoint;
		nextControlPoint++;
		return c;
	}

	public Code visit(Conditional conditional) {

		Code res = new Code();
		
		conditional.controlPoint = nextControlPoint++;

		Code guard = conditional.b.accept(this);

		Code c1 = conditional.s1.accept(this);
		Code c2 = conditional.s2.accept(this);
		Inst inst = new Branch(c1, c2);

		res.addAll(guard);
		res.add(inst);
		for (Inst i : res) {
			i.stmControlPoint = conditional.controlPoint;
		}
		return res;
	}

	public Code visit(Equals equals) {
		Code res = new Code();
		res.addAll(equals.a2.accept(this));
		res.addAll(equals.a1.accept(this));
		res.add(new Eq());
		return res;
	}

	public Code visit(FalseConst f) {
		Code res = new Code();

		res.add(new False());

		return res;
	}

	public Code visit(LessThanEq lessthaneq) {
		Code res = new Code();
		res.addAll(lessthaneq.a2.accept(this));
		res.addAll(lessthaneq.a1.accept(this));
		res.add(new Le());
		return res;
	}

	public Code visit(Minus minus) {
		Code res = new Code();
		res.addAll(minus.a2.accept(this));
		res.addAll(minus.a1.accept(this));
		res.add(new Sub());
		return res;
	}

	public Code visit(Num num) {
		Code res = new Code();

		res.add(new Push(num.n));

		return res;
	}

	public Code visit(Plus plus) {
		Code res = new Code();
		res.addAll(plus.a2.accept(this));
		res.addAll(plus.a1.accept(this));
		res.add(new Add());
		return res;
	}

	public Code visit(Skip skip) {
		Code res = new Code();
		
		res.add(new Noop());
		skip.controlPoint = nextControlPoint++;
		res.get(0).stmControlPoint = skip.controlPoint;
		return res;
	}

	public Code visit(Times times) {
		Code res = new Code();
		res.addAll(times.a2.accept(this));
		res.addAll(times.a1.accept(this));
		res.add(new Mult());
		return res;
	}

	public Code visit(TrueConst t) {
		Code res = new Code();

		res.add(new True());

		return res;
	}

	public Code visit(Var var) {
		Code c = new Code();
		
		c.add(new Fetch(var.id));

		return c;
	}

	public Code visit(While whyle) {
		Code c = new Code();
		whyle.controlPoint = nextControlPoint++;
		Loop l = new Loop(whyle.b.accept(this), whyle.s.accept(this));
		c.add(l);
		l.stmControlPoint = whyle.controlPoint;
		for (Inst i : l.c1) {
			i.stmControlPoint = whyle.controlPoint;
		}
		return c;
	}

	public Code visit(TryCatch trycatch) {
		int ctrl = nextControlPoint++;
		trycatch.controlPoint = ctrl;
		Code c1 = trycatch.s1.accept(this);
		Code c2 = trycatch.s2.accept(this);
		
		Inst t = new Try(c1);
		t.stmControlPoint = ctrl;
		Inst ca = new Catch(c2);
		ca.stmControlPoint = nextControlPoint++;
		
		Code c = new Code();
		c.add(t);
		c.add(ca);
		
		return c;
	}

	public Code visit(Divide div) {
		Code res = new Code();
		res.addAll(div.a2.accept(this));
		res.addAll(div.a1.accept(this));
		res.add(new Div());
		return res;
	}

	@Override
	public Code visit(EndOfProgram eop) {
		eop.controlPoint = nextControlPoint++;
		return new Code();
	}
	
}
