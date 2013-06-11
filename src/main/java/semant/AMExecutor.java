package semant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

import semant.amsyntax.Branch;
import semant.amsyntax.Code;
import semant.amsyntax.Inst;
import semant.amsyntax.Noop;
import semant.amsyntax.Store;

public class AMExecutor<A, B> {

	Operations<A, B> ops;
	String[] labelMessages;
	ArrayList<Object> rhss;
	Lattice<A> a_lattice;
	Lattice<B> b_lattice;
	boolean possibleUncaughtException = false;
	boolean normalTerminationPossible = false;

	Set<Configuration> visited = new HashSet<Configuration>();
	Map<String, A>[] stateAt;

	@SuppressWarnings("unchecked")
	public AMExecutor(
			Operations<A, B> ops,
			Lattice<A> a_lattice,
			Lattice<B> b_lattice,
			String[] labelMessages,
			ArrayList<Object> rhss) 
	{
		
		this.ops = ops;
		this.labelMessages = labelMessages;
		stateAt = new Map[labelMessages.length];
		this.a_lattice = a_lattice;
		this.b_lattice = b_lattice;
		this.rhss = rhss;
		for (int i = 0; i < labelMessages.length; ++i) {
			rhss.add(null);
		}
		
	}

	private Map<String, A> merge(Map<String, A> old, Map<String, A> cur) {
		Map<String, A> res = new HashMap<String, A>();
		
		if (old == null) return new HashMap<String, A>(cur);

		for (Map.Entry<String, A> e : old.entrySet()) {
			if (cur.containsKey(e.getKey())) {
				res.put(e.getKey(),
						a_lattice.lub(e.getValue(), cur.get(e.getKey())));
			} else {
				res.put(e.getKey(), e.getValue());
			}
		}
		
		for (Map.Entry<String, A> e : cur.entrySet()) {
			if (!res.containsKey(e.getKey())) {
				res.put(e.getKey(), e.getValue());
			}
		}
		return res;
	}

	@SuppressWarnings("unchecked")
	public void execute(Configuration startConfig) {
		System.out.println(startConfig);
		visited.add(startConfig);
		if (startConfig.isFinal()) {
			if (startConfig.exception != -1) {
				possibleUncaughtException = true; 
			} else {
				normalTerminationPossible = true;
			}
			int finalCtrlPoint = labelMessages.length-1;
			stateAt[finalCtrlPoint] = merge(stateAt[finalCtrlPoint], (Map<String, A>) startConfig.sto);
			labelMessages[finalCtrlPoint] = stateAt[finalCtrlPoint].toString();
			return;
		}
		int curControlPoint = startConfig.c.get(0).stmControlPoint;
		if (startConfig.exception == -1){
			stateAt[curControlPoint] = merge(stateAt[curControlPoint], (Map<String, A>) startConfig.sto);
			labelMessages[curControlPoint] = stateAt[curControlPoint].toString();
		}
		
		Inst nextInstr = startConfig.c.get(0);
		int curCtrlPoint = nextInstr.stmControlPoint;
		if (startConfig.exception == curCtrlPoint || startConfig.exception == -1) {
			if (nextInstr instanceof Branch) {
				if (rhss.get(nextInstr.stmControlPoint) == null) {
					rhss.set(nextInstr.stmControlPoint, startConfig.stack.peek());
				}
				else {
					rhss.set(nextInstr.stmControlPoint, b_lattice.lub((B)startConfig.stack.peek(), (B)rhss.get(nextInstr.stmControlPoint)));
				}
			}
			else if (nextInstr instanceof Store) {
				if (rhss.get(nextInstr.stmControlPoint) == null) {
					rhss.set(nextInstr.stmControlPoint, startConfig.stack.peek());
				}
				else {
					rhss.set(nextInstr.stmControlPoint, a_lattice.lub((A)startConfig.stack.peek(), (A)rhss.get(nextInstr.stmControlPoint)));
				}
			}
			else if (nextInstr instanceof Noop) {
				Noop noop = (Noop) nextInstr;
				if (!noop.isWhile)
					rhss.set(nextInstr.stmControlPoint, "" /*Something not-null */);
			}
		}
		
		Set<Configuration> nexts = startConfig.step(ops);
		for (Configuration c : nexts) {
			if (!visited.contains(c))
				execute(c);
		}
	}
	
	public void run(Code c, Set<String> unmappedNames, Map<String, Object> mappingsDone) {

		if (unmappedNames.isEmpty()) {
			Configuration conf = new Configuration(mappingsDone, new Stack<Object>(), c, -1);
			System.out.println("=== Running from " + mappingsDone);
			execute(conf);
		}

		for (String var : unmappedNames) {
			for (A e : ops.allAValues()) {
				if (!ops.isInt(e))
					continue;
				Map<String, Object> mD = new HashMap<String, Object>(
						mappingsDone);
				mD.put(var, e);
				Set<String> uN = new HashSet<String>(unmappedNames);
				uN.remove(var);
				run(c, uN, mD);
			}
		}
	}

}
