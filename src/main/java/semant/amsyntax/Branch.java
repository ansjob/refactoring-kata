package semant.amsyntax;

import java.util.HashSet;
import java.util.Set;

import semant.Configuration;
import semant.Operations;


public class Branch extends Inst {

    public final Code c1, c2;
    
    public Branch(Code c1, Code c2) {
        super(Opcode.BRANCH);
        this.c1 = c1;
        this.c2 = c2;
    }
    
    public <A, B> Set<Configuration> step(Configuration from, Operations<A, B> ops) {
    	Configuration next = from.copy();
		next.c.remove(0);

		@SuppressWarnings("unchecked")
		B guard = (B) next.stack.pop();
		if (from.exception != -1) {
			Set<Configuration> result = new HashSet<Configuration>();
			result.add(next);
			return result;
		}
		
		Set<Configuration> result = new HashSet<Configuration>();

		if (ops.possiblyTrue(guard)) {
			Configuration nextp = next.copy();
			nextp.c.addAll(0, c1);
			result.add(nextp);
		}
		if(ops.possiblyFalse(guard)) {
			Configuration nextp = next.copy();
			nextp.c.addAll(0, c2);
			result.add(nextp);
		}
		if(ops.possiblyBErr(guard)) {
			Configuration nextp = next.copy();
			nextp.exception = stmControlPoint;
			result.add(nextp);
		}

		return result;
    }
    
    public String toString() {
        return super.toString() + "(" + c1 + ", " + c2 + ")";
    }
}
