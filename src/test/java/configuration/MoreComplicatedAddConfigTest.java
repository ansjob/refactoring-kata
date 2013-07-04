package configuration;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

import semant.Configuration;
import semant.Operations;
import semant.amsyntax.Add;
import semant.amsyntax.Code;
import semant.amsyntax.Noop;
import semant.signexc.SignExc;
import semant.signexc.SignExcOps;
import semant.signexc.TTExc;

public class MoreComplicatedAddConfigTest extends ConfigurationTestBase {

	@Override
	protected Configuration initialConfig() {
		Map<String, Object> sto = new HashMap<String, Object>();
		Stack<Object> stack = new Stack<Object>();
		
		stack.push(getMockOps().abs(5));
		stack.push(getMockOps().abs(-5));
		
		Code c = new Code();
		c.add(new Add());
		c.add(new Noop());
		
		Configuration initial = new Configuration(sto , stack , c , -1);
		return initial;
	}

	@Override
	protected Set<Configuration> expectedNexts() {
		Set<Configuration> nexts = new HashSet<Configuration>();
		
		Map<String, Object> sto = new HashMap<String, Object>();
		Stack<Object> stack = new Stack<Object>();
		stack.push(SignExc.Z);
		
		Code c = new Code();
		c.add(new Noop());
		
		Configuration next = new Configuration(sto , stack , c , -1);
		
		nexts.add(next);
		
		return nexts;
	}

	@Override
	protected Operations<SignExc, TTExc> getMockOps() {
		return new SignExcOps();
	}

}
