package semant.amsyntax;

import semant.Main;

public class Store extends Inst {
    
    public final String x;
    
    public Store(String x) {
        super(Opcode.STORE);
        this.x = x;
    }
    
    public String toString() {
    	if (Main.LATEX)
    		return "\\texttt{STORE-}" + x;
    	else
    		return super.toString() + "-" + x;
    }
}
