package semant;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import semant.amsyntax.Code;
import semant.signexc.SignExc;
import semant.signexc.SignExcLattice;
import semant.signexc.SignExcOps;
import semant.signexc.TTExc;
import semant.signexc.TTExcLattice;
import semant.whilesyntax.Compound;
import semant.whilesyntax.EndOfProgram;
import semant.whilesyntax.Stm;

public class Main {
    public static final boolean DEBUG = false;
    public static boolean LATEX = false;

	public static void main(String[] args) throws Exception {
        Stm s = WhileParser.parse(args[0]);
        
        Stm program = new Compound(s, new EndOfProgram());
        // - Compile s into AM Code and assign control points
        CompileVisitor compiler = new CompileVisitor();
        Code c = program.accept(compiler);
        
        String[] labelMessages = new String[compiler.nextControlPoint];
        
        Set<String> varNames = VariableExtractor.extractVars(s);
        
        Map<String, Object> INIT = new HashMap<String, Object>();
        for (String var : varNames) {
        	INIT.put(var, SignExc.NONE_A);
        }
        
        for ( int i = 0; i < labelMessages.length; ++i) {
			labelMessages[i] = INIT.toString();
		}
        
        Lattice<SignExc> a_lattice = new SignExcLattice();
        Lattice<TTExc> b_lattice = new TTExcLattice();
        Operations<SignExc, TTExc> ops = new SignExcOps();
        ArrayList<Object> rhss = new ArrayList<Object>();
        AMExecutor<SignExc, TTExc> exec = new AMExecutor<SignExc, TTExc>(ops, a_lattice, b_lattice, labelMessages, rhss);
        exec.run(c, varNames, new HashMap<String, Object>());
        
        PrettyPrinter<SignExc> pp = new PrettyPrinter<SignExc>(labelMessages, rhss);
        program.accept(pp);
        if (!exec.normalTerminationPossible && !exec.possibleUncaughtException) {
        	System.out.println("Program does not terminate!");
        } else {
        	System.out.printf("Abnormal termination %spossible!\n", exec.possibleUncaughtException ? "" : "im");
            System.out.printf("Normal termination %spossible!\n",  exec.normalTerminationPossible ? "" : "im");
        }
        
    }

	
}
