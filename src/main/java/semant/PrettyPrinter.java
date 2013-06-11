package semant;

import java.util.ArrayList;

import semant.amsyntax.Code;
import semant.whilesyntax.Assignment;
import semant.whilesyntax.Compound;
import semant.whilesyntax.Conditional;
import semant.whilesyntax.Conjunction;
import semant.whilesyntax.Divide;
import semant.whilesyntax.EndOfProgram;
import semant.whilesyntax.Equals;
import semant.whilesyntax.FalseConst;
import semant.whilesyntax.LessThanEq;
import semant.whilesyntax.Minus;
import semant.whilesyntax.Not;
import semant.whilesyntax.Num;
import semant.whilesyntax.Plus;
import semant.whilesyntax.Skip;
import semant.whilesyntax.Times;
import semant.whilesyntax.TrueConst;
import semant.whilesyntax.TryCatch;
import semant.whilesyntax.Var;
import semant.whilesyntax.While;

public class PrettyPrinter<A> implements WhileVisitor {
	
	private String[] labelMessages;
	ArrayList<Object> rhss;
	
	public PrettyPrinter(String[] labelMessages, ArrayList<Object> rhss) {
		this.labelMessages = labelMessages;
		this.rhss = rhss;
	}
    
    String i = "";
    
    public Code visit(Conjunction and) {
        and.b1.accept(this);
        System.out.print(" & ");
        and.b2.accept(this);
        return null;
    }
    
    public Code visit(Assignment assignment) {
        System.out.println();
        System.out.print(i);
        if (rhss.get(assignment.controlPoint) != null) {
        	System.out.printf("%s rhs: %s\n", labelMessages[assignment.controlPoint], rhss.get(assignment.controlPoint).toString());
        } else {
        	System.out.printf("%s rhs: %s\n", labelMessages[assignment.controlPoint], "NONE (Dead Code)");
        }
        
        System.out.print(i);
        assignment.x.accept(this);
        System.out.print(" := ");
        assignment.a.accept(this);
        return null;
    }
    
    public Code visit(Compound compound) {
        compound.s1.accept(this);
        System.out.print(";");
        compound.s2.accept(this);
        return null;
    }
    
    public Code visit(Conditional conditional) {
        System.out.println();
        System.out.print(i);
        if (rhss.get(conditional.controlPoint) != null) {
        	System.out.printf("%s rhs: %s\n", labelMessages[conditional.controlPoint], rhss.get(conditional.controlPoint));
        } else {
        	System.out.printf("%s rhs: %s\n", labelMessages[conditional.controlPoint], "NONE (Dead Code)");
        }
        System.out.print(i);
        System.out.print("if ");
        conditional.b.accept(this);
        System.out.print(" then");
        indent();
        conditional.s1.accept(this);
        outdent();
        System.out.println();
        System.out.print(i + "else");
        indent();
        conditional.s2.accept(this);
        outdent();
        return null;
    }
    
    public Code visit(Equals equals) {
        equals.a1.accept(this);
        System.out.print(" = ");
        equals.a2.accept(this);
        return null;
    }
    
    public Code visit(FalseConst f) {
        System.out.print("false");
        return null;
    }
    
    public Code visit(LessThanEq leq) {
        leq.a1.accept(this);
        System.out.print(" <= ");
        leq.a2.accept(this);
        return null;
    }
    
    public Code visit(Minus minus) {
        System.out.print("(");
        minus.a1.accept(this);
        System.out.print(" - ");
        minus.a2.accept(this);
        System.out.print(")");
        return null;
    }
    
    public Code visit(Not not) {
        System.out.print("!(");
        not.b.accept(this);
        System.out.print(")");
        return null;
    }
    
    public Code visit(Num num) {
        System.out.print(num.n);
        return null;
    }
    
    public Code visit(Plus plus) {
        System.out.print("(");
        plus.a1.accept(this);
        System.out.print(" + ");
        plus.a2.accept(this);
        System.out.print(")");
        return null;
    }
    
    public Code visit(Skip skip) {
        if (rhss.get(skip.controlPoint) == null) {
            System.out.println();
        	System.out.print(i + "(Dead Code)");
        }
        System.out.println();
        System.out.print(i + "skip");
        
        return null;
    }
    
    public Code visit(Times times) {
        System.out.print("(");
        times.a1.accept(this);
        System.out.print(" * ");
        times.a2.accept(this);
        System.out.print(")");
        return null;
    }
    
    public Code visit(TrueConst t) {
        System.out.print("true");
        return null;
    }
    
    public Code visit(Var var) {
        System.out.print(var.id);
        return null;
    }
    
    public Code visit(While whyle) {
        System.out.println();
        System.out.print(i);
        System.out.printf("%s rhs: %s\n%swhile ", labelMessages[whyle.controlPoint], rhss.get(whyle.controlPoint) , i);
        whyle.b.accept(this);
        System.out.print(" do");
        indent();
        whyle.s.accept(this);
        outdent();
        return null;
    }
    
    public Code visit(Divide div) {
        System.out.print("(");
        div.a1.accept(this);
        System.out.print(" / ");
        div.a2.accept(this);
        System.out.print(")");
        return null;
    }

    public Code visit(TryCatch trycatch) {
        System.out.println();
        System.out.println(i + labelMessages[trycatch.controlPoint]);
        System.out.print(i + "try");
        indent();
        trycatch.s1.accept(this);
        outdent();
        System.out.println();
        System.out.print(i + "catch");
        indent();
        trycatch.s2.accept(this);
        outdent();
        return null;
    }

    private void indent() {
        i += "    ";
    }
    
    private void outdent() {
        i = i.substring(4);
    }

	@Override
	public Code visit(EndOfProgram eop) {
        System.out.printf("\n%s\n", labelMessages[eop.controlPoint]);
		return null;
	}
}
