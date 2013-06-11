package semant.amsyntax;

import semant.Main;

public class Push extends Inst {
    
    public final String n;
    
    public Push(String n) {
        super(Opcode.PUSH);
        this.n = n;
    }
    
    public int getValue() {
        return Integer.parseInt(n);
    }
    
    
    public String toString() {
    	if (Main.LATEX)
    		return "\\texttt{PUSH-}" + n;
    	else
    		return super.toString() + "-" + n;
    }
}
