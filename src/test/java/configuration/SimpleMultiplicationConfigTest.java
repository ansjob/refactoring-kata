package configuration;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import semant.amsyntax.Inst;
import semant.amsyntax.Mult;
import semant.signexc.SignExc;

@RunWith(Parameterized.class)
public class SimpleMultiplicationConfigTest extends SimpleArtihmeticOperationConfigTest {
	
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

	public SimpleMultiplicationConfigTest(int a, int b) {
		super(a, b);
	}

	@Override
	protected SignExc expectedValue(int a, int b) {
		return getMockOps().abs(a*b);
	}

	@Override
	protected Inst getInstruction() {
		return new Mult();
	}

}
