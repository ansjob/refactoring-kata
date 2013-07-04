package semant.amsyntax;

import java.util.HashSet;
import java.util.Set;

import semant.Configuration;
import semant.Operations;

public class Mult extends Inst {
    public Mult() {
        super(Opcode.MULT);
    }
    
    @SuppressWarnings("unchecked")
	public <A, B>  Set<Configuration> step(Configuration from, Operations<A,B> ops) {
    	Configuration next = from.copy();
		next.c.remove(0);

		A a2 = (A) next.stack.pop();
		A a1 = (A) next.stack.pop();
		next.stack.push(ops.multiply(a1, a2));

		Set<Configuration> result = new HashSet<Configuration>();
		result.add(next);
		return result;
    }
}
