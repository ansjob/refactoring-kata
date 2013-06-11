package configuration;

import static org.junit.Assert.*;

import org.junit.Test;

import semant.Configuration;
import semant.Operations;
import semant.signexc.SignExc;
import semant.signexc.TTExc;

public abstract class ConfigurationTestBase  {
	
	Configuration initialConfig, expectedNext;
	
	protected ConfigurationTestBase(Configuration initialConfig, Configuration expectedNext) {
		this.initialConfig = initialConfig;
		this.expectedNext = expectedNext;
	}
	
	@Test
	public void nextConfigFits() {
		assertEquals(expectedNext, initialConfig.step(getMockOps()));
	}

	protected abstract Operations<SignExc, TTExc> getMockOps();

}
