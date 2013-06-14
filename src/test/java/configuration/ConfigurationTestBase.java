package configuration;

import static org.junit.Assert.*;

import java.util.Set;

import org.junit.Test;

import semant.Configuration;
import semant.Operations;
import semant.signexc.SignExc;
import semant.signexc.TTExc;

public abstract class ConfigurationTestBase  {
	
	Configuration initialConfig;
	Set<Configuration> expectedNexts;
	
	protected ConfigurationTestBase(Configuration initialConfig, Set<Configuration> expectedNexts) {
		this.initialConfig = initialConfig;
		this.expectedNexts = expectedNexts;
	}
	
	@Test
	public void nextConfigFits() {
		assertEquals(expectedNexts, initialConfig.step(getMockOps()));
	}
	protected abstract Operations<SignExc, TTExc> getMockOps();
}
