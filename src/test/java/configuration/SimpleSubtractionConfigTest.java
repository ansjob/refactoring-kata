package configuration;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import semant.Operations;
import semant.amsyntax.Inst;
import semant.amsyntax.Sub;
import semant.signexc.SignExc;
import semant.signexc.TTExc;

@RunWith(Parameterized.class)
public class SimpleSubtractionConfigTest extends SimpleArtihmeticOperationConfigTest {
	
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

	public SimpleSubtractionConfigTest(int a, int b) {
		super(a, b);
	}

	@Override
	protected Inst getInstruction() {
		return new Sub();
	}

	@Override
	protected SignExc expectedValue(int a, int b) {
		Operations<SignExc, TTExc> ops = getMockOps();
		return ops.subtract(ops.abs(b), ops.abs(a));
	}



}
