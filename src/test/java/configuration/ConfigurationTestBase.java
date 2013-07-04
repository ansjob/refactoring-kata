package configuration;

import static org.junit.Assert.assertEquals;

import java.util.Set;

import org.junit.Test;

import semant.Configuration;
import semant.Operations;
import semant.signexc.SignExc;
import semant.signexc.TTExc;

public abstract class ConfigurationTestBase  {
	
	@Test
	public void nextConfigFits() {
		assertEquals(expectedNexts(), initialConfig().step(getMockOps()));
	}
	
	protected abstract Configuration initialConfig();
	
	protected abstract Set<Configuration> expectedNexts(); 

	protected abstract Operations<SignExc, TTExc> getMockOps();
}
