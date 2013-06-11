package semant.amsyntax;

import semant.Main;

public class Fetch extends Inst {
    
    public final String x;
    
    public Fetch(String x) {
        super(Opcode.FETCH);
        this.x = x;
    }
    
    public String toString() {
    	if (Main.LATEX)
    		return "\\texttt{FETCH-}" + x;
    	else
    		return super.toString() + "-" + x;
    }
}
