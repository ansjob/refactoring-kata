package semant;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.Stack;

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

public class Configuration {

	final Map<String, Object> sto;
	public final Stack<Object> stack;
	public final Code c;
	public int exception;

	public Configuration(Map<String, Object> sto, Stack<Object> stack, Code c,
			int exception) {
		super();
		this.sto = sto;
		this.stack = stack;
		this.c = c;
		this.exception = exception;
	}

	public <A, B> Set<Configuration> step(Operations<A,B> ops) {
		Inst inst = c.get(0);
		switch (inst.opcode) {
		case ADD:
			return ((Add)inst).step(this, ops);
		case AND:
			return ((And) inst).step(this, ops);
		case BRANCH:
			return ((Branch) inst).step(this, ops);
		case EQ:
			return execute((Eq) inst, ops);
		case FALSE:
			return execute((False) inst, ops);
		case FETCH:
			return execute((Fetch) inst, ops);
		case LE:
			return execute((Le) inst, ops);
		case LOOP:
			return execute((Loop) inst, ops);
		case MULT:
			return ((Mult) inst).step(this, ops);
		case NEG:
			return execute((Neg) inst, ops);
		case NOOP:
			return ((Noop) inst).step(this, ops);
		case PUSH:
			return execute((Push) inst, ops);
		case STORE:
			return execute((Store) inst, ops);
		case SUB:
			return ((Sub) inst).step(this, ops);
		case TRUE:
			return execute((True) inst, ops);
		case CATCH:
			return execute((Catch) inst, ops);
		case DIV:
			return execute((Div) inst, ops);
		case TRY:
			return execute((Try) inst, ops);
		default:
			throw new RuntimeException("Unknown Instruction Opcode:"
					+ inst.opcode);
		}
	}
	
	public Configuration copy() {
		@SuppressWarnings("unchecked")
		Stack<Object> newStack = (Stack<Object>) stack.clone();
		Code newCode = (Code) c.clone();
		Map<String, Object> newStore = new HashMap<String, Object>(sto);
		return new Configuration(newStore, newStack, newCode, exception);
	}

	private <A, B> Set<Configuration> execute(Try inst, Operations<A,B> ops) {

		if (exception != -1) {
			Configuration next = copy();
			next.c.remove(0);
			next.c.remove(0); //Consume the catch instruction

			Set<Configuration> result = new HashSet<Configuration>();
			result.add(next);
			return result;

		} else {
			Configuration next = copy();
			next.c.remove(0);
			next.c.addAll(0, inst.c);
			Set<Configuration> result = new HashSet<Configuration>();
			result.add(next);
			return result;
		}
	}

	@SuppressWarnings("unchecked")
	private <A, B> Set<Configuration> execute(Div inst, Operations<A, B> ops) {		
		Configuration next = this.copy();
		next.c.remove(0);

		A a1 = (A) next.stack.pop();
		A a2 = (A) next.stack.pop();
		A div;

		div = ops.divide(a1, a2);
		
		if (exception != -1) {
			next.stack.push(div);
			Set<Configuration> res = new HashSet<Configuration>();
			res.add(next);
			return res;
		}
		
		/* We are not in an exceptional state, should we introduce it? */
		
		Set<Configuration> res = new HashSet<Configuration>();
		
		if (!ops.possiblyZero(a2)) {
			//We know the division works!
			next.stack.push(div);
			res.add(next);
			return res;
		} else if (ops.isDefZero(a2)) {
			Configuration ex = next.copy();
			ex.stack.push(div);
			ex.exception = inst.stmControlPoint;
			res.add(ex);
			return res;
		} else {
			// a2 could be zero, pretend it either is, or isn't!
			
			//It is not zero
			Configuration ok = next.copy();
			div = ops.divide(a1, ops.removeZero(a2));
			//ok.stack.push(SignExc.Z);
			ok.stack.push(div);
			res.add(ok);
			
			//It is zero!
			Configuration ex = next.copy();
			div = ops.divide(a1, ops.abs(0));
			ex.stack.push(div);
			ex.exception = inst.stmControlPoint;
			res.add(ex);
			return res;
		}
		
	}

	private <A, B> Set<Configuration> execute(Catch inst, Operations<A,B> ops) {
		Configuration next = this.copy();
		next.c.remove(0);
		
		if (next.exception != -1) {
			next.c.addAll(0, inst.c);
			next.exception = -1;
		}
		
		Set<Configuration> result = new HashSet<Configuration>();
		result.add(next);
		return result;
	}

	public boolean isFinal() {
		return c.isEmpty();
	}

	public String toString() {
		StringBuilder res = new StringBuilder();
		if (Main.LATEX)
			res.append("&\\langle ");
		else
			res.append("<");
		for (Inst c1 : c) {
			res.append(c1.toString());
			if (Main.LATEX)
				res.append("\\colon ");
			else
				res.append(":");
		}
		if (Main.LATEX)
			res.append("\\epsilon, ");
		else
			res.append("\u03b5, ");

		Stack<Object> rev = new Stack<Object>();
		@SuppressWarnings("unchecked")
		Stack<Object> tmpStack = (Stack<Object>) stack.clone();
		while (!tmpStack.empty()) {
			rev.push(tmpStack.pop());
		}

		for (Object o : rev) {
			if (o == null && Main.LATEX)
				res.append("\\perp");
			else if (o == null)
				res.append("(nil)");
			else
				res.append(o.toString());
			if (Main.LATEX)
				res.append("\\colon ");
			else
				res.append(":");
		}

		if (Main.LATEX)
			res.append("\\epsilon, ");
		else
			res.append("\u03b5, ");

		if (exception != -1) {
			if (Main.LATEX)
				res.append("\\hat s");
			else
				res.append("ŝ");
		} else {
			res.append("s");
		}

		if (!sto.isEmpty()) {
			res.append("[");
		}
		boolean first = true;
		for (Entry<String, Object> e : sto.entrySet()) {
			if (!first)
				res.append(",");
			first = false;
			res.append(e.getKey());
			if (Main.LATEX)
				res.append(" \\mapsto ");
			else
				res.append("\u2192");
			res.append(e.getValue());
		}
		if (!sto.isEmpty()) {
			res.append("]");
		}

		if (Main.LATEX)
			res.append(" \\rangle \\\\");
		else
			res.append(">");

		return res.toString();
	}

	@SuppressWarnings("unchecked")
	public <A, B> Set<Configuration> execute(Eq eq, Operations<A,B> ops) {
		Configuration next = this.copy();
		next.c.remove(0);
		
		A a1 = (A) next.stack.pop();
		A a2 = (A) next.stack.pop();
		B res = ops.eq(a1, a2);
		next.stack.push(res);

		Set<Configuration> result = new HashSet<Configuration>();
		result.add(next);
		return result;
	}

	public <A, B> Set<Configuration> execute(False falseConst, Operations<A, B> ops) {
		Configuration next = this.copy();
		next.c.remove(0);

		next.stack.push(ops.abs(false));

		Set<Configuration> result = new HashSet<Configuration>();
		result.add(next);
		return result;
	}

	@SuppressWarnings("unchecked")
	public <A, B> Set<Configuration> execute(Fetch fetch, Operations<A,B> ops) {

		Configuration next = this.copy();
		next.c.remove(0);
		
		A a = (A) next.sto.get(fetch.x);
		if (a == null) {
			a = ops.abs(null);
		}
		next.stack.push(a);

		Set<Configuration> result = new HashSet<Configuration>();
		result.add(next);
		return result;
	}

	@SuppressWarnings("unchecked")
	public <A, B> Set<Configuration> execute(Le le, Operations<A, B> ops) {
		
		Configuration next = this.copy();
		next.c.remove(0);

		A a1 = (A) next.stack.pop();
		A a2 = (A) next.stack.pop();
		B res = ops.leq(a1, a2);
		next.stack.push(res);

		Set<Configuration> result = new HashSet<Configuration>();
		result.add(next);
		return result;

	}

	public <A, B> Set<Configuration> execute(Loop loop, Operations<A, B> ops) {

		Configuration next = this.copy();
		next.c.remove(0);

		if (exception != -1) {
			Set<Configuration> result = new HashSet<Configuration>();
			result.add(next);
			return result;
		}

		Code trueCode = new Code();
		Code falseCode = new Code();

		trueCode.addAll(loop.c2);
		trueCode.add(loop);

		Noop nop = new Noop(true);
		nop.stmControlPoint = loop.stmControlPoint;
		falseCode.add(nop);

		Inst branch = new Branch(trueCode, falseCode);
		branch.stmControlPoint = loop.stmControlPoint;
		
		next.c.add(0, branch);
		next.c.addAll(0, loop.c1);

		Set<Configuration> result = new HashSet<Configuration>();
		result.add(next);
		return result;

	}

	@SuppressWarnings("unchecked")
	public <A, B> Set<Configuration> execute(Neg neg, Operations<A, B> ops) {

		
		Configuration next = this.copy();
		next.c.remove(0);

		B b = (B) next.stack.pop();
		next.stack.push(ops.neg(b));

		Set<Configuration> result = new HashSet<Configuration>();
		result.add(next);
		return result;
	}

	public <A, B> Set<Configuration> execute(Push push, Operations<A, B> ops) {

		Configuration next = this.copy();
		next.c.remove(0);
		
		Integer x = Integer.parseInt(push.n);
		A a = ops.abs(x);
		
		next.stack.push(a);
		
		Set<Configuration> result = new HashSet<Configuration>();
		result.add(next);
		return result;
	}

	@SuppressWarnings("unchecked")
	public <A,B> Set<Configuration> execute(Store store, Operations<A, B> ops) {

		Configuration next = this.copy();
		next.c.remove(0);

		A x = (A) next.stack.pop();
		if (next.exception == -1) {
			next.sto.put(store.x, x);
		}
		
		Set<Configuration> result = new HashSet<Configuration>();
		result.add(next);
		return result;
		
	}

	public <A, B> Set<Configuration> execute(True trueConst, Operations<A, B> ops) {

		Configuration next = this.copy();
		next.c.remove(0);

		next.stack.push(ops.abs(true));
		
		Set<Configuration> result = new HashSet<Configuration>();
		result.add(next);
		return result;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((c == null) ? 0 : c.hashCode());
		result = prime * result + (exception * 1231);
		result = prime * result + ((stack == null) ? 0 : stack.hashCode());
		result = prime * result + ((sto == null) ? 0 : sto.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Configuration) {
			Configuration other = (Configuration) obj;
			return other.sto.equals(sto) &&
					other.exception == exception &&
					other.c.equals(c) &&
					other.stack.equals(stack);
		}
		return false;
	}
	
	
}
