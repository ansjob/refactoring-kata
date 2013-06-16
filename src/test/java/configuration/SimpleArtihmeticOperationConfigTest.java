package configuration;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

import semant.Configuration;
import semant.Operations;
import semant.amsyntax.Code;
import semant.amsyntax.Inst;
import semant.signexc.SignExc;
import semant.signexc.SignExcOps;
import semant.signexc.TTExc;

public abstract class SimpleArtihmeticOperationConfigTest extends
		ConfigurationTestBase {

	protected int a;
	protected int b;

	

	public SimpleArtihmeticOperationConfigTest(int a,int b) {
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
		c.add(getInstruction());
		int exception = -1;
		Configuration config = new Configuration(sto , stack , c , exception);
		return config;
	}

	protected abstract Inst getInstruction();

	@Override
	protected Set<Configuration> expectedNexts() {
		Set<Configuration> nexts = new HashSet<Configuration>();
		
		Map<String, Object> sto = new HashMap<String, Object>();
		Stack<Object> stack = new Stack<Object>();
		stack.push(expectedValue(a,b));
		Code c = new Code();
		Configuration expectedNext = new Configuration(sto , stack , c , -1);
		
		nexts.add(expectedNext);
		
		return nexts;
	}
	

	protected abstract SignExc expectedValue(int a, int b);

	@Override
	protected Operations<SignExc, TTExc> getMockOps() {
		return new SignExcOps();
	}

}