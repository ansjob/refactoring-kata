package semant.amsyntax;

import java.util.HashSet;
import java.util.Set;

import semant.Configuration;
import semant.Operations;

public class Add extends Inst {
    public Add() {
        super(Opcode.ADD);
    }
    
    public <A,B> Set<Configuration> step(Configuration from, Operations<A,B> ops) {
    	Configuration next = from.copy();
    	next.c.remove(0);
    	
    	@SuppressWarnings("unchecked")
		A a1 = (A) next.stack.pop();
		@SuppressWarnings("unchecked")
		A a2 = (A) next.stack.pop();

		A sum = ops.add(a1, a2);
		next.stack.push(sum);

		Set<Configuration> result = new HashSet<Configuration>();
		result.add(next);
		return result;
    }


}
