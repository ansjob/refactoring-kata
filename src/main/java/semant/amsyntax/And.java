package semant.amsyntax;

import java.util.HashSet;
import java.util.Set;

import semant.Configuration;
import semant.Operations;

public class And extends Inst {
    public And() {
        super(Opcode.AND);
    }
    
    public <A, B> Set<Configuration> step(Configuration from, Operations<A,B> ops){
    	Configuration next = from.copy();
    	next.c.remove(0);

    	@SuppressWarnings("unchecked")
    	B b1 = (B) next.stack.pop();
    	@SuppressWarnings("unchecked")
    	B b2 = (B) next.stack.pop();
    	B res = ops.and(b1, b2);
    	next.stack.push(res);

    	Set<Configuration> result = new HashSet<Configuration>();
    	result.add(next);
    	return result;
    }
    
}
