package tests.integration;

import java.io.File;

import org.junit.Before;
import org.junit.Test;

import semant.WhileParser;
import semant.whilesyntax.Stm;

public class IntegrationTestBase {

	protected String inputProgramFileName, expectedOutputFileName;
	
	protected Stm ast;
	

	
	public IntegrationTestBase(String inputProgramFileName,
			String expectedOutputFileName) {
		super();
		this.inputProgramFileName = inputProgramFileName;
		this.expectedOutputFileName = expectedOutputFileName;
	}



	@Before
	public void readInput() {
		ast = WhileParser.parse(inputProgramFileName);
	}
	
}
