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
import semant.amsyntax.And;
import semant.amsyntax.Code;
import semant.signexc.SignExc;
import semant.signexc.SignExcOps;
import semant.signexc.TTExc;

@RunWith(Parameterized.class)
public class SimpleAndConfigTest extends ConfigurationTestBase {
	
	
	boolean arg1, arg2;
	
	public SimpleAndConfigTest(Boolean arg1, Boolean arg2) {
		this.arg1 = arg1;
		this.arg2 = arg2;
	}
	
	@Parameters
	public static List<Object[]> getParams() {
		return Arrays.asList(
				new Object[] {false, false},
				new Object[] {false, true},
				new Object[] {true, false},
				new Object[] {true, true}
				);
	}

	@Override
	protected Configuration initialConfig() {
		Map<String, Object> sto = new HashMap<String, Object>();
		
		Stack<Object> stack = new Stack<Object>();
		stack.push(getMockOps().abs(arg1));
		stack.push(getMockOps().abs(arg2));
		
		Code c = new Code();
		c.add(new And());
		return new Configuration(sto, stack, c , -1);
	}

	@Override
	protected Set<Configuration> expectedNexts() {
		Set<Configuration> nexts = new HashSet<Configuration>();
		
		Map<String, Object> sto = new HashMap<String, Object>();
		Stack<Object> stack = new Stack<Object>();
		stack.push(getMockOps().abs(arg1 && arg2));
		Code c = new Code();
		
		Configuration next = new Configuration(sto , stack , c, -1);
		
		nexts.add(next);
		return nexts;
	}

	@Override
	protected Operations<SignExc, TTExc> getMockOps() {
		return new SignExcOps();
	}

}
