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
import semant.amsyntax.Code;
import semant.amsyntax.Neg;
import semant.signexc.SignExc;
import semant.signexc.SignExcOps;
import semant.signexc.TTExc;

@RunWith(Parameterized.class)
public class SimpleNotConfigTest extends ConfigurationTestBase {
	
	boolean bool;

	public SimpleNotConfigTest(boolean bool) {
		this.bool = bool;
	}
	
	@Parameters
	public static List<Object[]> params()
	{
		return Arrays.asList(
				new Object[] {true},
				new Object[] {false}
				);
	}

	@Override
	protected Configuration initialConfig() {
		
		Map<String, Object> sto = new HashMap<String, Object>();
		Stack<Object> stack = new Stack<Object>();
		stack.push(getMockOps().abs(bool));
		Code c = new Code();
		
		c.add(new Neg());
		
		return new Configuration(sto , stack , c , -1);
	}

	@Override
	protected Set<Configuration> expectedNexts() {
		Set<Configuration> nexts = new HashSet<Configuration>();
		
		Map<String, Object> sto = new HashMap<String, Object>();
		Stack<Object> stack = new Stack<Object>();
		stack.add(getMockOps().abs(!bool));
		
		Code c = new Code();
		Configuration next = new Configuration(sto , stack , c , -1);
		
		nexts.add(next);
		return nexts;
	}

	@Override
	protected Operations<SignExc, TTExc> getMockOps() {
		return new SignExcOps();
	}

}
