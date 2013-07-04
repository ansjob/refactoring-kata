package configuration;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import semant.Configuration;
import semant.Operations;
import semant.amsyntax.Branch;
import semant.amsyntax.Code;
import semant.amsyntax.Fetch;
import semant.amsyntax.Noop;
import semant.amsyntax.Store;
import semant.signexc.SignExc;
import semant.signexc.SignExcOps;
import semant.signexc.TTExc;

@RunWith(Parameterized.class)
public class SimpleBranchConfigTest extends ConfigurationTestBase {
	
	Code branch1, branch2;
	boolean exp;
	
	public SimpleBranchConfigTest(boolean exp) {
		this.exp = exp;
		branch1 = new Code();
		branch1.add(new Fetch("x"));
		branch1.add(new Store("y"));
		
		branch2 = new Code();
		branch2.add(new Noop());
	}
	
	@Parameters
	public static List<Object[]> params()
	{
		return Arrays.asList(
				new Object[] {Boolean.TRUE},
				new Object[] {Boolean.FALSE}
				);
	}

	@Override
	protected Configuration initialConfig() {
		Map<String, Object> sto = new HashMap<String, Object>();
		Stack<Object> stack = new Stack<Object>();
		stack.add(getMockOps().abs(exp));
		
		Code c = new Code();
		
		c.add(new Branch(branch1, branch2));
		return new Configuration(sto , stack , c , -1);
	}

	@Override
	protected Set<Configuration> expectedNexts() {
		Set<Configuration> nexts = new HashSet<Configuration>();
		
		Map<String, Object> sto = new HashMap<String, Object>();
		Stack<Object> stack = new Stack<Object>();
		Code c = new Code();
		c.addAll(exp ? branch1 : branch2);
		
		Configuration next = new Configuration(sto , stack , c , -1);
		nexts.add(next);
		
		return nexts;
	}

	@Override
	protected Operations<SignExc, TTExc> getMockOps() {
		return new SignExcOps();
	}

}
