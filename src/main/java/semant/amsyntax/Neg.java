package semant.amsyntax;

import java.util.HashSet;
import java.util.Set;

import semant.Configuration;
import semant.Operations;

public class Neg extends Inst {
    public Neg() {
        super(Opcode.NEG);
    }
    
    @SuppressWarnings("unchecked")
	public <A, B> Set<Configuration> step(Configuration from, Operations<A, B> ops) {
    	Configuration next = from.copy();
		next.c.remove(0);

		B b = (B) next.stack.pop();
		next.stack.push(ops.neg(b));

		Set<Configuration> result = new HashSet<Configuration>();
		result.add(next);
		return result;
    }
}
