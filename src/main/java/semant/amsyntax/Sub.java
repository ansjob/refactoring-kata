package semant.amsyntax;

import java.util.HashSet;
import java.util.Set;

import semant.Configuration;
import semant.Operations;

public class Sub extends Inst {
    public Sub() {
        super(Opcode.SUB);
    }
    
    @SuppressWarnings("unchecked")
	public <A, B> Set<Configuration> step(Configuration from, Operations<A,B> ops){
    	
    	Configuration next = from.copy();
		next.c.remove(0);

		A a1 = (A) next.stack.pop();
		A a2 = (A) next.stack.pop();
		A res = ops.subtract(a1, a2);
		next.stack.push(res);
		
		Set<Configuration> result = new HashSet<Configuration>();
		result.add(next);
		return result;
    }
}
