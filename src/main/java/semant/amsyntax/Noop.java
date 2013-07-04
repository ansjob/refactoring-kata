package semant.amsyntax;

import java.util.HashSet;
import java.util.Set;

import semant.Configuration;
import semant.Operations;

public class Noop extends Inst {
	public boolean isWhile;
    public Noop(boolean isWhile) {
        super(Opcode.NOOP);
    	this.isWhile = isWhile;
    }
    public Noop() {
    	this(false);
    }
    
    public <A, B> Set<Configuration> step(Configuration from, Operations<A,B> ops){
    	Configuration next = from.copy();
		next.c.remove(0);
		Set<Configuration> result = new HashSet<Configuration>();
		result.add(next);
		return result;
    }
}
