package configuration;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

import semant.Configuration;
import semant.Operations;
import semant.amsyntax.Code;
import semant.amsyntax.Noop;
import semant.signexc.SignExc;
import semant.signexc.SignExcOps;
import semant.signexc.TTExc;

public class SimpleSkipConfigTest extends ConfigurationTestBase {



	@Override
	protected Set<Configuration> expectedNexts() {
		Set<Configuration> ret = new HashSet<Configuration>();
		ret.add(finalConfig());
		return ret;
	}

	protected static Configuration finalConfig() {
		
		Map<String, Object> sto = new HashMap<String, Object>();
		Stack<Object> stack = new Stack<Object>();
		Code c = new Code();
		int exception = -1;
		Configuration empty = new Configuration(sto, stack, c , exception );
		return empty;
	}

	@Override
	protected Configuration initialConfig() {
		
		Map<String, Object> sto = new HashMap<String, Object>();
		Stack<Object> stack = new Stack<Object>();
		Code c = new Code();
		c.add(new Noop());
		Configuration config = new Configuration(sto , stack , c , -1);
		return config;
	}

	@Override
	protected Operations<SignExc, TTExc> getMockOps() {
		return new SignExcOps();
	}

}
