package configuration;

import java.util.ArrayList;
import java.util.Collection;
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
import semant.amsyntax.Add;
import semant.amsyntax.Code;
import semant.signexc.SignExc;
import semant.signexc.SignExcOps;
import semant.signexc.TTExc;

@RunWith(Parameterized.class)
public class SimpleAddConfigTest extends ConfigurationTestBase {
	
	protected int a, b;
	
	@Parameters
	public static Collection<Object[]> parameters() {
		List<Object[]> parameterSets = new ArrayList<Object[]>();
		
		parameterSets.add(new Object[] {5, 0});
		parameterSets.add(new Object[] {0, 5});
		parameterSets.add(new Object[] {0, 0});
		parameterSets.add(new Object[] {-5, -2});
		parameterSets.add(new Object[] {-2, 0});
		
		return parameterSets;
	}
	
	public SimpleAddConfigTest(int a, int b) {
		this.a = a;
		this.b = b;
	}

	@Override
	protected Configuration initialConfig() {
		Map<String, Object> sto = new HashMap<String, Object>();
		Stack<Object> stack = new Stack<Object>();
		stack.push(getMockOps().abs(a));
		stack.push(getMockOps().abs(b));
		Code c = new Code();
		c.add(new Add());
		int exception = -1;
		Configuration config = new Configuration(sto , stack , c , exception);
		return config;
	}

	@Override
	protected Set<Configuration> expectedNexts() {
		Set<Configuration> nexts = new HashSet<Configuration>();
		
		Map<String, Object> sto = new HashMap<String, Object>();
		Stack<Object> stack = new Stack<Object>();
		stack.push(getMockOps().abs(a+b));
		Code c = new Code();
		Configuration expectedNext = new Configuration(sto , stack , c , -1);
		
		nexts.add(expectedNext);
		
		return nexts;
	}

	@Override
	protected Operations<SignExc, TTExc> getMockOps() {
		return new SignExcOps();
	}

}
