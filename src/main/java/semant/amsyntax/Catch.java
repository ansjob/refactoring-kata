package semant.amsyntax;


public class Catch extends Inst {
	
	public final Code c;

	public Catch(Code c) {
		super(Inst.Opcode.CATCH);
		this.c = c;
	}
	
	public String toString() {
		return String.format(super.toString() + "(%s)", c.toString());
	}

}
