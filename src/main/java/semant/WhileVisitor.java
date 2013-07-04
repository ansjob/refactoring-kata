package semant;

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

public interface WhileVisitor {
    public Code visit(Compound compound);
    public Code visit(Not not);
    public Code visit(Conjunction and);
    public Code visit(Assignment assignment);
    public Code visit(Conditional conditional);
    public Code visit(Equals equals);
    public Code visit(FalseConst f);
    public Code visit(LessThanEq lessthaneq);
    public Code visit(Minus minus);
    public Code visit(Num num);
    public Code visit(Plus plus);
    public Code visit(Skip skip);
    public Code visit(Times times);
    public Code visit(TrueConst t);
    public Code visit(Var var);
    public Code visit(While whyle);
    public Code visit(TryCatch trycatch);
    public Code visit(Divide div);
    public Code visit(EndOfProgram eop);
}
