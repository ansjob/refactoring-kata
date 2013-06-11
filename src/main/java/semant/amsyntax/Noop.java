package semant.amsyntax;

public class Noop extends Inst {
	public boolean isWhile;
    public Noop(boolean isWhile) {
        super(Opcode.NOOP);
    	this.isWhile = isWhile;
    }
    public Noop() {
    	this(false);
    }
}
