package semant.amsyntax;


public class Try extends Inst {
	
	public final Code c;

	public Try(Code c) {
		super(Inst.Opcode.TRY);
		this.c = c;
	}
	
	public String toString() {
		return String.format(super.toString() + "(%s)", c.toString());
	}

}
